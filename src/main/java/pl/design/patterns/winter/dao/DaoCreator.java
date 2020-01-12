package pl.design.patterns.winter.dao;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import pl.design.patterns.winter.annotations.DatabaseTable;
import pl.design.patterns.winter.inheritance.mappers.InheritanceMapper;
import pl.design.patterns.winter.inheritance.mapping.InheritanceMapping;
import pl.design.patterns.winter.schemas.DatabaseSchema;

@Component
public class DaoCreator {

    @Autowired
    DatabaseSchema databaseSchema;

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
