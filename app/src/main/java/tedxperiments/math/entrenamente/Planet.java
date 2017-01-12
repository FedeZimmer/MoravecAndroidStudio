package tedxperiments.math.entrenamente;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;



public class Planet {
    public static final String[] PLANETS = {"sum1","sum2","mul1","mul2","mul3","sq1","sq2","sq3","sq4","sq5","sq6","sq7"};
    public static final String[] PLANETS2 = {"dot_1_2","dot_2_2","dot_1_3","dot_2_3","dot_3_3","dot_1_7","dot_2_7","dot_3_7","dot_4_7","dot_5_7","dot_6_7","dot_7_7"};
    public static final Map<String, String> PLANET_DETAIL;
       
    static {
    	
    	Map<String, String> planets = new HashMap<String, String>();
    	planets.put("sum1", "Ac� empez�s a familiarizarte con el m�todo de izquierda a derecha. Primero sum�s las decenas del segundo n�mero al primero y por �ltimo las unidades del segundo n�mero al resultado anterior."+"<br><br>");
        planets.put("sum2", "Este ejemplo es m�s dif�cil porque las unidades suman un n�mero mayor que 10."+"<br><br><br><br><br>");
        planets.put("mul1", "Para resolver multiplicaciones mentalmente tambi�n ten�s que familiarizarte con hacerlo de izquierda a derecha. Multiplicando primero las decenas del n�mero mayor y luego las unidades."+"<br><br>");
        planets.put("mul2", "Otro ejemplo para ejercitar la multiplicaci�n de izquierda a derecha."+"<br><br><br><br><br>");
        planets.put("mul3", "Se utiliza el mismo m�todo cuando el n�mero mayor es de 3 o 4 d�gitos. Siempre se multiplica de izquierda a derecha y se suman progresivamente los resultados parciales."+"<br><br>");
        planets.put("sq1", "Usamos la f�rmula x<sup><small><small>2</small></small></sup> = (x-a) (x+a) + a<sup><small><small>2</small></small></sup>. Donde x es el n�mero de dos d�gitos que quiero elevar al cuadrado. Y a es la diferencia entre x y el m�ltiplo de 10 m�s cercano a x. Si x es 12, a es 2."+"<br><br>");
        planets.put("sq2", "Podemos ver que si x = 46 entonces a= 4. Hacemos (x-a) (x+a) + a<sup><small><small>2</small></small></sup>."+"<br><br><br><br><br>");
        planets.put("sq3", "Cuando elevamos un n�mero de tres d�gitos usamos el mismo m�todo pero en este caso a es un n�mero de dos d�gitos. Sabiendo esto, repetimos el procedimiento conocido.\n"+"En este caso tenemos x = 512 y a = 12. Pero para elevar 12 al cuadrado, consideramos x = 12 y a = 2.");
        planets.put("sq4", "Va otro ejemplo con x = 684, a = 16.\n"+"Despu�s, para elevar 16 al cuadrado usamos x = 16, a = 4."+"<br><br><br><br>");
        planets.put("sq5", "En el Sistema Mayor de Memoria, asociamos cada d�gito del n�mero a recordar con una consonante como se ve en la tabla. Usando estas letras en orden formamos palabras, agregando las vocales necesarias. Por ejemplo, �lupa� es 59 porque �L� es 5, �P� es 9 y las vocales no son nada.");
        planets.put("sq6", "Para n�meros de 4 d�gitos usamos la f�rmula ya conocida. Pero antes de calcular a<sup><small><small>2</small></small></sup> utilizamos el Sistema Mayor para memorizar la primera parte del resultado. Si x es 6382, a es 382. 6764 x 6000 es 40 millones 584 mil entonces memorizamos 584 usando el Sistema Mayor.");
        planets.put("sq7", "Para no olvidar 584, formamos una palabra con sus d�gitos. 5 puede ser L, 8 G y 4 C entonces 584 puede ser codificado como �LaGo oCa�. Con esto en mente, seguimos los pasos de siempre y obtenemos 145.924. Sumamos 584 (�LaGo oCa�) y 145 = 729. Juntando todo:  40 millones 729 mil 924.");
        PLANET_DETAIL = Collections.unmodifiableMap(planets);
    }
}