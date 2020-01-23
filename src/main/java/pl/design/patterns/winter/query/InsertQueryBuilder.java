package pl.design.patterns.winter.query;

import pl.design.patterns.winter.inheritance.mapping.InheritanceMapping;
import pl.design.patterns.winter.schemas.ColumnSchema;
import pl.design.patterns.winter.schemas.TableSchema;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

public class InsertQueryBuilder extends QueryBuilder {

    private InheritanceMapping inheritanceMapping;

    public InsertQueryBuilder(InheritanceMapping inheritanceMapping)
    {
        this.inheritanceMapping = inheritanceMapping;
    }

    @Override
    public <T> String prepare(T object) throws InvocationTargetException, IllegalAccessException {
        StringBuilder sb = new StringBuilder();
        Map<TableSchema, StringBuilder> mapTableSchemaToBuilder = new HashMap<>();
        Set<TableSchema> setTableSchema  = new HashSet<>();

        List<Field> fields = getFieldsToIncludeInQuery(object);

        for(Field field : fields)
        {
            mapTableSchemaToBuilder.put(inheritanceMapping.getTableSchema(field.getName()), new StringBuilder());
            setTableSchema.add(inheritanceMapping.getTableSchema(field.getName()));
        }

        for(var tableSchema: setTableSchema) {
            var stringBuilder = mapTableSchemaToBuilder.get(tableSchema);
            stringBuilder.append("INSERT INTO ")
                    .append(tableSchema.getTableName())
                    .append(" (");

            for (ColumnSchema column : tableSchema.getColumns()) {
                stringBuilder.append(column.getColumnName()).append(", ");
            }
            stringBuilder.delete(stringBuilder.length() - 2, stringBuilder.length() - 1);
            stringBuilder.append(") VALUES ( ");

            for (ColumnSchema column : tableSchema.getColumns()) {
                Class c = column.getJavaType();
                Object o = column.get(object);

                if(o == null && column.isNullable())
                {
                    stringBuilder.append("NULL, ");
                }
                else if(o.getClass() == String.class)
                {
                    stringBuilder.append("\"")
                            .append(c.cast(o).toString())
                            .append("\", ");
                }
                else
                {
                    stringBuilder.append(o.toString()).append(", ");
                }
            }

            stringBuilder.delete(stringBuilder.length() - 2, stringBuilder.length() - 1);
            stringBuilder.append(");");

            sb.append(stringBuilder.toString())
                    .append(" ");
        }

        return sb.toString();
    }
}
