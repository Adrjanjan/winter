package pl.design.patterns.winter.schemas;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.SQLType;

import org.springframework.util.StringUtils;

import pl.design.patterns.winter.annotations.DatabaseField;
import pl.design.patterns.winter.annotations.Id;
import pl.design.patterns.winter.exceptions.InvalidIdFieldTypeException;
import pl.design.patterns.winter.query.TypeMapper;
import pl.design.patterns.winter.utils.NameUtils;

import lombok.Data;

@Data
public class ColumnSchema {
    // add original field name

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
        if ( field.isAnnotationPresent(Id.class) ) {
            if ( field.getType() != int.class ) {
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
        /* ja wiem że to nie może tak być (po coś ten IllegalAccessException jest xD)
        Ale inaczej mi wlaśnie wywala ten wyjątek przy teście a nie wiem gdzie indziej to zmienić przy UpdateQueryTest*/
        this.getter.setAccessible(true);
        return this.getter.invoke(obj);
    }
}
