/*
 * Copyright "2025" Francisco Chicano, Javier Ferrer, Marina Calleja
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 *
 */

package es.uma.lcc.neo.pasaportebackend.service;

import es.uma.lcc.neo.pasaportebackend.entity.PasswordReset;
import es.uma.lcc.neo.pasaportebackend.entity.Usuario;
import es.uma.lcc.neo.pasaportebackend.exception.CredencialesIncorrectas;
import es.uma.lcc.neo.pasaportebackend.exception.TokenNoValidoException;
import es.uma.lcc.neo.pasaportebackend.exception.UsuarioExistente;
import es.uma.lcc.neo.pasaportebackend.exception.UsuarioInexistente;
import es.uma.lcc.neo.pasaportebackend.repository.PasswordResetRepo;
import es.uma.lcc.neo.pasaportebackend.repository.VisorRepo;
import es.uma.lcc.neo.pasaportebackend.security.JwtUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.UriComponentsBuilder;

import java.sql.Timestamp;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.logging.Logger;

import static es.uma.lcc.neo.pasaportebackend.security.SecurityConfguration.*;

@Service
@Transactional(noRollbackFor = TokenNoValidoException.class)
public class UsuarioService {
    private final VisorRepo<Usuario> usuarioRepo;
    private final PasswordResetRepo passwordResetRepo;
    private final PasswordEncoder passwordEncoder;
    private final JavaMailSender mailSender;

    private final JwtUtil jwtUtil;
    private final Logger log =Logger.getLogger(UsuarioService.class.getName());

    @Value("${baseURIOfFrontend}")
    private String baseURIOfFrontend;
    @Value("${passwordresettoken.expiration}")
    private long passwordResetTokenExpiration = 0;

    @Value("${spring.mail.username}")
    private String mailFrom;

    public UsuarioService(VisorRepo<Usuario> usuarioRepo,
                          PasswordEncoder passwordEncoder,
                          PasswordResetRepo passwordResetRepo,
                          JwtUtil jwtUtil,
                          JavaMailSender mailSender) {
        this.usuarioRepo = usuarioRepo;
        this.passwordEncoder = passwordEncoder;
        this.passwordResetRepo = passwordResetRepo;
        this.jwtUtil = jwtUtil;
        this.mailSender = mailSender;
    }

    public Usuario save(Usuario usuario) {

        Optional<UserDetails> user = getAuthenticatedUser();
        user.ifPresent(u -> log.info("Usuario autenticado: " + u.getUsername()));


        String password;
        if (usuario.getId() == null) {
            // Nuevo usuario
            if (usuarioRepo.usuarioByEmail(usuario.getEmail()).isPresent()) {
                throw new UsuarioExistente(usuario);
            }
            password = usuario.getHashContrasenia();
            if (password == null || password.isEmpty()) {
                password = generarPasswordAleatoria();
            }
            usuario.setHashContrasenia(passwordEncoder.encode(password));
        } else {
            // Actualizar usuario
            Usuario u = usuarioRepo.findById(usuario.getId())
                    .orElseThrow(() -> new UsuarioInexistente(usuario));
            password = usuario.getHashContrasenia();
            if (password == null || password.isEmpty()) {
                usuario.setHashContrasenia(u.getHashContrasenia());
            } else {
                usuario.setHashContrasenia(passwordEncoder.encode(password));
            }
        }

        return usuarioRepo.save(usuario);
    }

    public List<Usuario> findAll() {
        return usuarioRepo.findAll();
    }

    public Optional<Usuario> findById(Long id) {
        return usuarioRepo.findById(id);
    }

    public Optional<Usuario> findByEmail(String email) {
        return usuarioRepo.usuarioByEmail(email);
    }

    public void deleteById(Long id) {
        if (!usuarioRepo.existsById(id)) {
            throw new UsuarioInexistente(id);
        }
        usuarioRepo.deleteById(id);
    }

    public String compruebaLogin(String email, String password) {
        Optional<Usuario> usuario = usuarioRepo.usuarioByEmail(email);
        if (usuario.isEmpty()) {
            throw new UsuarioInexistente(email);
        }
        if (!passwordEncoder.matches(password, usuario.get().getHashContrasenia())) {
            throw new CredencialesIncorrectas();
        }

        String jwt = jwtUtil.generateToken(usuario.get());
        return jwt;
    }


    private String generarPasswordAleatoria() {
        return UUID.randomUUID().toString();
    }

    public boolean existsById(Long idUsuario) {
        return usuarioRepo.existsById(idUsuario);
    }

    public void forgottenPassword(String email) {
        usuarioRepo.usuarioByEmail(email).ifPresent(this::preparareinicioContrasenia);
    }

    private Timestamp now() {
        return new Timestamp(System.currentTimeMillis());
    }

    public void passwordReset(String token, String password) {
        var passwordReset = passwordResetRepo.findById(token)
                .orElseThrow(() -> new TokenNoValidoException());

        if (passwordReset.getTokenCreation().toInstant().plus(passwordResetTokenExpiration, ChronoUnit.SECONDS)
                .isBefore(now().toInstant())) {
            passwordResetRepo.delete(passwordReset);
            throw new TokenNoValidoException();
        }

        var usuario = passwordReset.getUsuario();
        usuario.setHashContrasenia(passwordEncoder.encode(password));
        usuarioRepo.save(usuario);
        passwordResetRepo.delete(passwordReset);
    }

    private void preparareinicioContrasenia(Usuario usuario) {
        // Elimina cualquier token previo
        var passwordReset = passwordResetRepo.findByUsuario(usuario);
        passwordReset.ifPresent(p-> {
            //log.info(p.toString());
            passwordResetRepo.delete(p);
        });

        String token = generarToken();
         var ps = PasswordReset.builder()
                    .tokenCreation(now())
                    .token(token)
                    .usuario(usuario)
                    .build();
        passwordResetRepo.save(ps);


        enviarMensajeParaReiniciarContrasnia(usuario.getEmail(), token);
    }

    private void enviarMensajeParaReiniciarContrasnia(String email, String token) {
        log.fine("Enviando mensaje de correo a "+email);

        String uri = UriComponentsBuilder.fromHttpUrl(baseURIOfFrontend+"/reset-password")
                .queryParam("token", token)
                .build().toUriString();

        StringBuffer mensaje = new StringBuffer();
        mensaje.append("Hola, \n");
        mensaje.append("Hemos recibido una solicitud para reiniciar su contraseña en la aplicación de entrenamientos deportivos. ");
        mensaje.append("Si no ha sido usted, ignore este mensaje. ");
        mensaje.append("Para reiniciar la contraseña, haga clic en el siguiente enlace: ");
        mensaje.append(uri);
        mensaje.append(" Si el enlace no funciona, copie y pegue la dirección en su navegador. \n");
        mensaje.append("Saludos cordiales,\n");
        mensaje.append("Equipo técnico de Pasaporte Educativo y Clínico de Comunicación");

        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setFrom(mailFrom);
        mail.setTo(email);
        mail.setSubject("[PECCO] Restablecimiento de contraseña.");
        mail.setText(mensaje.toString());
        mailSender.send(mail);

        log.fine(mensaje.toString());
    }



    private String generarToken() {
        return UUID.randomUUID().toString();
    }
}
