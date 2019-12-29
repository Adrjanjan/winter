package pl.design.patterns.winter.dao;

import pl.design.patterns.winter.OrmManager;
import pl.design.patterns.winter.inheritance.mapping.TableType;

public class DaoCreator {

	public static void create(Class<?> clazz) {
        TableType tableInfo = new TableType(clazz);

        OrmManager.addDao(clazz, new Dao<>(tableInfo));
    }

}
