package pl.design.patterns.winter.object.assembler;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ObjectAssembler<T> {

    public T assemble(Class<T> clazz, ResultSet resultSet)
            throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException, SQLException {
        T object = clazz.getConstructor()
                .newInstance();
        loadResultSetIntoObject(resultSet, object);
        return object;
    }

    public List<T> assembleMultiple(Class<T> clazz, ResultSet resultSet)
            throws SQLException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        List<T> list = new ArrayList<>();
        while (resultSet.next()) {
            list.add(assemble(clazz, resultSet));
        }
        return list;
    }

    private void loadResultSetIntoObject(ResultSet resultSet, Object object) throws IllegalArgumentException, IllegalAccessException, SQLException {
        Class<?> clazz = object.getClass();
        for (Field field : clazz.getDeclaredFields()) {
            field.setAccessible(true);
            Object value = resultSet.getObject(0, object.getClass());
            if (value != null) {
                Class<?> type = field.getType();
                Class<?> boxed = boxPrimitiveClass(type);
                boxed.cast(value);
                field.set(object, value);
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
