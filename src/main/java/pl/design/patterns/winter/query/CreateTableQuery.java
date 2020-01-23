package pl.design.patterns.winter.query;

import java.lang.reflect.InvocationTargetException;
import java.sql.JDBCType;

import pl.design.patterns.winter.exceptions.InvalidObjectClassException;
import pl.design.patterns.winter.schemas.ColumnSchema;
import pl.design.patterns.winter.schemas.TableSchema;

public class CreateTableQuery extends QueryBuilder{
    private Object object;
    private StringBuilder sb;

    public CreateTableQuery() {
        sb = new StringBuilder();
    }

    @Override
    public <T> QueryBuilder setObject(T object) {
        if(object.getClass() != TableSchema.class)
            throw new InvalidObjectClassException("Object has incorrect class: " + object.getClass().toString());
        this.object = object;
        return this;
    }

    @Override
    public QueryBuilder createOperation() {
        sb.append("CREATE TABLE IF NOT EXISTS ");

        return this;
    }

    @Override
    public QueryBuilder setTable() {
        sb.append(((TableSchema)object).getTableName())
                .append(" ");
        return this;
    }

    @Override
    public QueryBuilder setFields() {
        for (ColumnSchema columnSchema : ((TableSchema)object).getColumns()) {
            sb.append("\t");
            sb.append(columnSchema.getColumnName());
            sb.append(" ");

            if ( columnSchema.isGeneratedId() ) {
                sb.append("SERIAL");
            } else {
                sb.append(columnSchema.getSqlType() == JDBCType.VARCHAR ? "TEXT"
                        : columnSchema.getSqlType()
                        .getName());
            }

            if ( !columnSchema.isNullable() ) {
                sb.append(" ");
                sb.append("NOT NULL");
            }

            sb.append(",\n");
        }

        sb.append("PRIMARY KEY(");
        sb.append(((TableSchema)object).getIdField()
                .getColumnName());
        sb.append(")\n");

        sb.append(");");
        return this;
    }

    @Override
    public QueryBuilder setValues() {
        return this;
    }

    @Override
    public String generate() {
        return sb.toString();
    }
}
