package pl.design.patterns.winter.schemas;

import lombok.Data;
import org.springframework.util.StringUtils;

import pl.design.patterns.winter.annotations.DatabaseField;
import pl.design.patterns.winter.annotations.Id;
import pl.design.patterns.winter.exceptions.InvalidIdFieldTypeException;
import pl.design.patterns.winter.query.TypeMapper;
import pl.design.patterns.winter.utils.NameUtils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.SQLType;

@Data
public class ColumnSchema {

    private Class<?> parent;

    private Method getter;

    private Method setter;

    private String columnName;

    private SQLType sqlType;

    private Class javaType;

    private boolean isGeneratedId;

    private boolean isNullable;

    private boolean isForeignKey;

    public ColumnSchema(Field field) {
        final var databaseFieldAnnotation = field.getAnnotation(DatabaseField.class);
        this.columnName = NameUtils.extractColumnName(field);
        if (field.isAnnotationPresent(Id.class)) {
            if (field.getType() != int.class) {
                throw new InvalidIdFieldTypeException();
            }
            this.isGeneratedId = field.getAnnotation(Id.class)
                    .generated();
        } else {
            this.isGeneratedId = false;
        }
        this.isNullable = databaseFieldAnnotation.nullable();
        this.javaType = field.getType();
        this.sqlType = TypeMapper.getSqlType(field.getType());
        this.parent = field.getDeclaringClass();

        this.getter = findFieldGetter(field);
        this.setter = findFieldSetter(field);
    }

    public void set(Object obj, Object val) {
        try {
            this.setter.invoke(obj, val);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException("Couldn't set value");
        }
    }

    public Object get(Object obj) {
        try {
            return this.getter.invoke(obj);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException("Couldn't get value");
        }
    }

    private Method findFieldGetter(Field field) {
        Method fieldGetter = null;
        try {
            if (field.getType().equals(boolean.class)) {
                fieldGetter = this.parent.getDeclaredMethod("is" + StringUtils.capitalize(field.getName()));
            }
            else {
                fieldGetter = this.parent.getDeclaredMethod("get" + StringUtils.capitalize(field.getName()));
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

        if (fieldGetter == null || fieldGetter.getReturnType() != field.getType()) {
            return null;
        }

        return fieldGetter;
    }

    private Method findFieldSetter(Field field) {
        Method fieldSetter = null;
        try {
            fieldSetter = this.parent.getDeclaredMethod("set" + StringUtils.capitalize(field.getName()), field.getType());
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

        if (fieldSetter == null || fieldSetter.getReturnType() != field.getType()) {
            return null;
        }

        return fieldSetter;
    }
}
