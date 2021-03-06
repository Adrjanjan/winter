package pl.design.patterns.winter.statements.query;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;

import javax.lang.model.type.NullType;

public class TypeMapper {

    private static HashMap<Class<?>, String> javaToSqlTypes;

    static {
        javaToSqlTypes = new HashMap<>();
        javaToSqlTypes.put(int.class, "INTEGER");
        javaToSqlTypes.put(Integer.class, "INTEGER");
        javaToSqlTypes.put(long.class, "BIGINT");
        javaToSqlTypes.put(Long.class, "BIGINT");
        javaToSqlTypes.put(short.class, "SMALLINT");
        javaToSqlTypes.put(Short.class, "SMALLINT");
        javaToSqlTypes.put(float.class, "REAL");
        javaToSqlTypes.put(Float.class, "REAL");
        javaToSqlTypes.put(double.class, "DOUBLE PRECISION");
        javaToSqlTypes.put(Double.class, "DOUBLE PRECISION");
        javaToSqlTypes.put(boolean.class, "BOOLEAN");
        javaToSqlTypes.put(Boolean.class, "BOOLEAN");
        javaToSqlTypes.put(String.class, "VARCHAR");
        javaToSqlTypes.put(char.class, "CHAR");
        javaToSqlTypes.put(Character.class, "CHAR");
        javaToSqlTypes.put(NullType.class, "NULL");
        javaToSqlTypes.put(Timestamp.class, "TIMESTAMP");
        javaToSqlTypes.put(Date.class, "DATE");
    }

    public static String getSqlType(Class<?> cl) {
        return javaToSqlTypes.get(cl);
    }

    public static void addMapping(Class<?> classObject, String type) {
        if ( classObject.getSuperclass() != Object.class ) {
            javaToSqlTypes.put(classObject.getSuperclass(), type);
        } else
            javaToSqlTypes.put(classObject, type);
    }
}