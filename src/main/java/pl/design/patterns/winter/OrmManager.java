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

    public static Dao<?> getDao(Class<?> classToMap) {
        return daos.get(classToMap);
    }
}
