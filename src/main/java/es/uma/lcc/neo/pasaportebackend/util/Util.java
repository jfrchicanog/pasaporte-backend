package es.uma.lcc.neo.pasaportebackend.util;

import java.util.function.Function;

public class Util {

     public static <A, B> B nullableTransform(A obj, Function<A, B> fn) {
        if (obj == null) {
            return null;
        } else{
            return fn.apply(obj);
        }
    }
}
