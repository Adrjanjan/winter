package pl.design.patterns.winter;

import lombok.extern.apachecommons.CommonsLog;
import pl.design.patterns.winter.dao.Dao;

import java.util.HashMap;
import java.util.Map;

@CommonsLog
public class OrmManager {

    public static Map<Class<?>, Dao<?>> daos = new HashMap<>();

    public static void addDao(Class<?> c, Dao<?> dao) {
        log.info(String.format("Dodaje nowe Dao dla klasy %s", c.getName()));
        daos.put(c, dao);
    }

    @SuppressWarnings("unchecked")
    public static <T> Dao<T> getDao(Class<T> classToMap) {
        if (daos.containsKey(classToMap)) {
            return (Dao<T>) daos.get(classToMap);
        }
        else {
            throw new RuntimeException(String.format("Couldn't get Dao for class %s", classToMap.getName()));
        }
    }
}
