package pl.design.patterns.winter.schemas;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.sql.SQLType;

import org.springframework.util.StringUtils;

import pl.design.patterns.winter.annotations.DatabaseField;
import pl.design.patterns.winter.annotations.Id;
import pl.design.patterns.winter.inheritance.mapping.TypeMapper;

import lombok.Data;

@Data
public class ColumnSchema {
    private Class<?> parent;

    private Method getter;

    private Method setter;

    private String columnName;

    private SQLType sqlType;

    private Type javaType;

    private boolean isGeneratedId;

    private boolean isNullable;

    private boolean isForeignKey;

    public ColumnSchema(Field field) {
        final var databaseFieldAnnotation = field.getAnnotation(DatabaseField.class);
        this.columnName = databaseFieldAnnotation.name()
                .equals("") ? field.getName() : databaseFieldAnnotation.name();
        this.isGeneratedId = field.getAnnotation(Id.class)
                .generated();
        this.isNullable = databaseFieldAnnotation.nullable();
        this.javaType = field.getType();
        this.sqlType = TypeMapper.get(this.javaType);
        this.parent = field.getDeclaringClass();

        try {
            this.getter = this.parent.getDeclaredMethod("get" + StringUtils.capitalize(field.getName()));
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        try {
            this.setter = this.parent.getDeclaredMethod("set" + StringUtils.capitalize(field.getName()), field.getType());
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    public void set(Object obj, Object val) throws InvocationTargetException, IllegalAccessException {
        this.setter.invoke(obj, val);
    }

    public Object get(Object obj) throws InvocationTargetException, IllegalAccessException {
        return this.getter.invoke(obj);
    }
}
