package pl.design.patterns.winter.query;

import java.sql.JDBCType;

public interface ITypeMapper {

    JDBCType getSqlType(Class classObject);

    Class getJavaType(JDBCType type);

    void addMapping(Class classObject, JDBCType type);
}
