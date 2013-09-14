package pl.piotrsukiennik.tuner.util;

/**
 * Author: Piotr Sukiennik
 * Date: 12.09.13
 * Time: 21:14
 */
public class Objects2 {
    private Objects2(){}
    public static <T> T newInstance(Class<T> clazz){
        try{
            return clazz.newInstance();
        }catch (InstantiationException ie){
            throw new RuntimeException(ie.getCause());
        } catch (IllegalAccessException ie){
            throw new RuntimeException(ie.getCause());
        }
    }
}
