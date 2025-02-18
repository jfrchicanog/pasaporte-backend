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

package es.uma.lcc.neo.pasaportebackend.dto;

import es.uma.lcc.neo.pasaportebackend.entity.Seccion;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToMany;
import lombok.Builder;
import lombok.Data;

import java.sql.Date;
import java.util.List;

@Data
@Builder
public class PasaporteDTO {
    private Long id;
    private String nombre;
    private String apellido1;
    private String apellido2;
    private String foto;
    // TODO: mostramos solo la edad?
    private Date fechaNacimiento;
    private List<SeccionDTO> secciones;
}
