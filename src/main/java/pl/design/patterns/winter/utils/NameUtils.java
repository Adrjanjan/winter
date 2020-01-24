package pl.design.patterns.winter.utils;

import java.lang.reflect.Field;

import com.google.common.base.CaseFormat;

import pl.design.patterns.winter.annotations.DatabaseField;
import pl.design.patterns.winter.annotations.DatabaseTable;
import pl.design.patterns.winter.annotations.DiscriminatorColumn;
import pl.design.patterns.winter.annotations.DiscriminatorValue;

public class NameUtils {

    public static String extractTableName(Class<?> clazz) {
        DatabaseTable databaseTable = clazz.getAnnotation(DatabaseTable.class);
        String name = null;
        if ( databaseTable != null && !databaseTable.name()
                .isEmpty() ) {
            name = databaseTable.name();
        }
        if ( name == null ) {
            name = CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, clazz.getSimpleName());
        }

        return name;
    }

    public static String extractColumnName(Field field) {
        DatabaseField databaseField = field.getAnnotation(DatabaseField.class);
        String name = null;
        if ( databaseField != null && !databaseField.name()
                .isEmpty() ) {
            name = databaseField.name();
        }
        if ( name == null ) {
            name = CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, field.getName());
        }

        return name;
    }

    public static String extractColumnDicriminatorColumnName(Class<?> clazz) {
        DiscriminatorColumn discriminatorColumn = clazz.getAnnotation(DiscriminatorColumn.class);
        String name = null;
        if ( discriminatorColumn != null && !discriminatorColumn.name()
                .isEmpty() ) {
            name = discriminatorColumn.name();
        }
        if ( name == null ) {
            name = "discriminatorColumn";
        }
        return name;
    }

    public static String extractColumnDicriminatorValue(Class<?> clazz) {
        DiscriminatorValue discriminatorColumn = clazz.getAnnotation(DiscriminatorValue.class);
        String name = null;
        if ( discriminatorColumn != null && !discriminatorColumn.value()
                .isEmpty() ) {
            name = discriminatorColumn.value();
        }
        if ( name == null ) {
            name = CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, clazz.getSimpleName());
        }
        return name;
    }


}
