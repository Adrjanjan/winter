package pl.design.patterns.winter.inheritance.mapping;

import com.google.common.base.CaseFormat;
import lombok.Data;
import pl.design.patterns.winter.annotations.DatabaseTable;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

@Data
public class TableType {

    private Class<?> className;
    private String tableName;
    private Class<?> parentClass;
    private List<FieldType> fields;
    private Field idField;

    public TableType(Class<?> clazz) {
        className = clazz;
        tableName = extractTableName(clazz);
        List<FieldType> fields = new ArrayList<>();
        for (Field field : clazz.getDeclaredFields()) {
            FieldType fieldType = new FieldType(field, tableName);
            fields.add(fieldType);
        }
        this.fields = fields;
    }

    private static String extractTableName(Class<?> clazz) {
        DatabaseTable databaseTable = clazz.getAnnotation(DatabaseTable.class);
        String name = null;
        if (databaseTable != null && !databaseTable.name().isEmpty()) {
            name = databaseTable.name();
        }
        if (name == null) {
            name = CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, clazz.getSimpleName());
        }

        return name;
    }
}
