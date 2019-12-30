package pl.design.patterns.winter.dao;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import pl.design.patterns.winter.annotations.DatabaseTable;
import pl.design.patterns.winter.inheritance.mappers.InheritanceMapper;
import pl.design.patterns.winter.inheritance.mapping.InheritanceMapping;

public class DaoCreator {

    public static void create(Class<?> clazz) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        InheritanceMapper inheritanceMapper = clazz.getDeclaredAnnotation(DatabaseTable.class)
                .inheritanceMapper()
                .getConstructor()
                .newInstance();
        InheritanceMapping mapping = inheritanceMapper.map(clazz);

        // ...
        //
	}

	public static List<Dao<?>> getAll() {
		return null;
	}
}
