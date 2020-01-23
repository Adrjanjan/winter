package pl.design.patterns.winter.query;

import java.sql.JDBCType;
import java.sql.Timestamp;
import java.util.Date;

import javax.lang.model.type.NullType;

import com.google.common.collect.HashBiMap;

public class TypeMapper {
//    private static HashBiMap<Class, JDBCType> javaToSqlTypes;
private static HashBiMap<Class, String> javaToSqlTypes;
    static {
        //---Marcina
        javaToSqlTypes = HashBiMap.create();
        javaToSqlTypes.put(int.class, "INTEGER");
        javaToSqlTypes.put(String.class, "VARCHAR");
        javaToSqlTypes.put(Float.class, "REAL");
        javaToSqlTypes.put(double.class, "DOUBLE PRECISION");
        javaToSqlTypes.put(boolean.class, "BOOLEAN");
        javaToSqlTypes.put(Character.class, "CHAR");
        javaToSqlTypes.put(NullType.class, "NULL");
        javaToSqlTypes.put(Timestamp.class, "TIMESTAMP");
        javaToSqlTypes.put(Date.class, "DATE");
        //---Marcina
//        javaToSqlTypes.put(int.class, JDBCType.INTEGER);
//        javaToSqlTypes.put(String.class, JDBCType.VARCHAR);
//        javaToSqlTypes.put(Float.class, JDBCType.FLOAT);
//        javaToSqlTypes.put(double.class, JDBCType.DOUBLE);
//        javaToSqlTypes.put(boolean.class, JDBCType.BOOLEAN);
//        javaToSqlTypes.put(Character.class, JDBCType.CHAR);
//        javaToSqlTypes.put(NullType.class, JDBCType.NULL);
//        javaToSqlTypes.put(Timestamp.class, JDBCType.TIMESTAMP);
//        javaToSqlTypes.put(Date.class, JDBCType.DATE);
    }

    public static String getSqlType(Class cl) {
        return javaToSqlTypes.get(cl);
    }

    public static Class getJavaType(JDBCType type) {
        return javaToSqlTypes.inverse()
                .get(type);
    }

    public static void addMapping(Class classObject, String type) {
        if (classObject.getSuperclass() != Object.class) {
            javaToSqlTypes.put(classObject.getSuperclass(), type);
        } else
            javaToSqlTypes.put(classObject, type);
    }
}