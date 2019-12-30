package pl.design.patterns.winter.query;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

import javax.lang.model.type.NullType;
import java.sql.JDBCType;
import java.sql.Timestamp;
import java.util.Date;

public class TypeMapper implements ITypeMapper {
    private BiMap<Class, JDBCType> javaToSqlTypes;

    public TypeMapper() {
        javaToSqlTypes = HashBiMap.create();
        generateAllTypes();
    }

    private void generateAllTypes() {
        this.javaToSqlTypes.put(Integer.class, JDBCType.INTEGER);
        this.javaToSqlTypes.put(String.class, JDBCType.VARCHAR);
        this.javaToSqlTypes.put(Float.class, JDBCType.FLOAT);
        this.javaToSqlTypes.put(Double.class, JDBCType.DOUBLE);
        this.javaToSqlTypes.put(Boolean.class, JDBCType.BOOLEAN);
        this.javaToSqlTypes.put(Character.class, JDBCType.CHAR);
        this.javaToSqlTypes.put(NullType.class, JDBCType.NULL);
        this.javaToSqlTypes.put(Timestamp.class, JDBCType.TIMESTAMP);
        this.javaToSqlTypes.put(Date.class, JDBCType.DATE);
    }

    @Override
    public JDBCType getSqlType(Class cl) {
        return javaToSqlTypes.get(cl);
    }

    @Override
    public Class getJavaType(JDBCType type) {
        return javaToSqlTypes.inverse().get(type);
    }

    @Override
    public void addMapping(Class classObject, JDBCType type) {
        this.javaToSqlTypes.put(classObject, type);
    }
}