package pl.design.patterns.winter.query;

import pl.design.patterns.winter.annotations.DatabaseField;
import pl.design.patterns.winter.inheritance.mapping.InheritanceMapping;
import pl.design.patterns.winter.schemas.ColumnSchema;
import pl.design.patterns.winter.schemas.TableSchema;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.stream.Collectors;

public class InsertQueryBuilder extends QueryBuilder {

    private InheritanceMapping inheritanceMapping;

    public InsertQueryBuilder(InheritanceMapping inheritanceMapping)
    {
        this.inheritanceMapping = inheritanceMapping;
    }

    //TODO działa dla concreteTableInheritance, Single wymaga poprawy budowania TableSchema
    @Override
    public <T> String prepare(T object) throws InvocationTargetException, IllegalAccessException {
        StringBuilder sb = new StringBuilder();
        Map<TableSchema, StringBuilder> mapTableSchemaToBuilder = new HashMap<>();
        Set<TableSchema> setTableSchema  = new HashSet<>();

        List<Field> fields = getFieldsToIncludeInQuery(object);

        //Zapisujemy mapę(tabela->StringBuilder) oraz zbór tabel
        for(Field field : fields)
        {
            mapTableSchemaToBuilder.put(inheritanceMapping.getTableSchema(field.getName()), new StringBuilder());
            setTableSchema.add(inheritanceMapping.getTableSchema(field.getName()));
        }

        //Dla każdej tabeli (w której wylądował) obiekt
        for(var tableSchema: setTableSchema) {
            //bierzemy Buildera "pod polecenia"
            var stringBuilder = mapTableSchemaToBuilder.get(tableSchema);
            stringBuilder.append("INSERT INTO ")
                    .append(tableSchema.getTableName())
                    .append(" (");

            //Kolumny pod polecenia
            for (ColumnSchema column : tableSchema.getColumns()) {
                stringBuilder.append(column.getColumnName()).append(", ");
            }
            stringBuilder.delete(stringBuilder.length() - 2, stringBuilder.length() - 1);
            stringBuilder.append(") VALUES ( ");

            //TODO to powinno byc lepiej zrobione ale nie mam pomysłu jak
            //bo dla każdego typu musielibyśmy zrobić rzutowanie
            //wartosc pol obiektu
            for (ColumnSchema column : tableSchema.getColumns()) {
                Class c = column.getJavaType();
                Object o = column.get(object);

                if(o.getClass() == String.class)
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

            //łączenie stringBuilderów w jedno wieksze polecenie
            sb.append(stringBuilder.toString())
                    .append(" ");
        }

        return sb.toString();
    }
}
