package pl.design.patterns.winter.query;

import java.sql.JDBCType;
import java.sql.Timestamp;
import java.util.Date;

import javax.lang.model.type.NullType;

import com.google.common.collect.HashBiMap;

public class TypeMapper {
    private static HashBiMap<Class, JDBCType> javaToSqlTypes;

    static {
        javaToSqlTypes = HashBiMap.create();
        javaToSqlTypes.put(int.class, JDBCType.INTEGER);
        javaToSqlTypes.put(String.class, JDBCType.VARCHAR);
        javaToSqlTypes.put(Float.class, JDBCType.FLOAT);
        javaToSqlTypes.put(double.class, JDBCType.DOUBLE);
        javaToSqlTypes.put(boolean.class, JDBCType.BOOLEAN);
        javaToSqlTypes.put(Character.class, JDBCType.CHAR);
        javaToSqlTypes.put(NullType.class, JDBCType.NULL);
        javaToSqlTypes.put(Timestamp.class, JDBCType.TIMESTAMP);
        javaToSqlTypes.put(Date.class, JDBCType.DATE);
    }

    public static JDBCType getSqlType(Class cl) {
        return javaToSqlTypes.get(cl);
    }

    public static Class getJavaType(JDBCType type) {
        return javaToSqlTypes.inverse()
                .get(type);
    }

    public static void addMapping(Class classObject, JDBCType type) {
        if ( classObject.getSuperclass() != Object.class ) {
            javaToSqlTypes.put(classObject.getSuperclass(), type);
        } else
            javaToSqlTypes.put(classObject, type);
    }
}