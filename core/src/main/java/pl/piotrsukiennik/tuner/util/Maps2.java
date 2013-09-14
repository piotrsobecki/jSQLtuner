package pl.piotrsukiennik.tuner.util;

import java.util.Map;

/**
 * Author: Piotr Sukiennik
 * Date: 12.09.13
 * Time: 21:12
 */
public class Maps2 {
    private Maps2(){}
    public static <K,T> T get(Map<K,T> map,K key, Class<T> clazz){
        if (map==null||key==null||clazz==null){
            return null;
        }
        T o = map.get(key);
        if (o==null){
            o = Objects2.newInstance(clazz);
            map.put(key,o);
        }
        return o;
    }
}
