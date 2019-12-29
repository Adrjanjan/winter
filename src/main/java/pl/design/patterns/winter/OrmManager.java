package pl.design.patterns.winter;

import pl.design.patterns.winter.dao.Dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrmManager {

    private static Map<Class<?>, Dao<?>> daoCache = new HashMap<>();

    public static List<Dao<?>> getAll() {
        return new ArrayList<>(daoCache.values());
    }

    public static <D extends Dao<T>, T> D getDao(Class<T> classToMap) {
        @SuppressWarnings("unchecked")
        D dao = (D) daoCache.get(classToMap);

        return dao;
    }

    public static void addDao(Class<?> clazz, Dao<Class<?>> dao) {
        daoCache.put(clazz, dao);
    }
}
