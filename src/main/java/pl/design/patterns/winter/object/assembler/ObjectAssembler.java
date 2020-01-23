package pl.design.patterns.winter.object.assembler;

import pl.design.patterns.winter.annotations.DatabaseField;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ObjectAssembler<T> {

    public T assemble(Class<T> clazz, ResultSet resultSet) {
        T object = null;
        try {
            object = clazz.getConstructor()
                    .newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }
        if (object != null) {
            loadResultSetIntoObject(resultSet, object);
        }
        else {
            throw new RuntimeException();
        }
        return object;
    }

    public List<T> assembleMultiple(Class<T> clazz, ResultSet resultSet) {
        List<T> list = new ArrayList<>();
        while (true) {
            try {
                if (!resultSet.next()) break;
            } catch (SQLException e) {
                e.printStackTrace();
            }
            list.add(assemble(clazz, resultSet));
        }
        return list;
    }

    private void loadResultSetIntoObject(ResultSet resultSet, Object object) {
        Class<?> clazz = object.getClass();
        for (Field field : clazz.getDeclaredFields()) {
            field.setAccessible(true);
            DatabaseField column = field.getAnnotation(DatabaseField.class);
            Object value = null;
            try {
                value = resultSet.getObject(column.name());
            } catch (SQLException e) {
                e.printStackTrace();
            }
            Class<?> type = field.getType();
            Class<?> boxed = boxPrimitiveClass(type);
            boxed.cast(value);
            try {
                field.set(object, value);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    private Class<?> boxPrimitiveClass(Class<?> type) {
        if ( type == int.class ) {
            return Integer.class;
        } else if ( type == long.class ) {
            return Long.class;
        } else if ( type == double.class ) {
            return Double.class;
        } else if ( type == float.class ) {
            return Float.class;
        } else if ( type == boolean.class ) {
            return Boolean.class;
        } else if ( type == byte.class ) {
            return Byte.class;
        } else if ( type == char.class ) {
            return Character.class;
        } else if ( type == short.class ) {
            return Short.class;
        } else {
            return type;
        }
    }
}
