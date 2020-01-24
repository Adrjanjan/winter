package pl.design.patterns.winter.query;

import java.sql.JDBCType;

import pl.design.patterns.winter.exceptions.InvalidObjectClassException;
import pl.design.patterns.winter.schemas.ColumnSchema;
import pl.design.patterns.winter.schemas.TableSchema;

public class CreateTableQuery extends QueryBuilder {
    private Object object;

    public CreateTableQuery() {
        query = new StringBuilder();
    }

    @Override
    <T> QueryBuilder withObject(T object) {
        if ( object.getClass() != TableSchema.class )
            throw new InvalidObjectClassException("Object has incorrect class: " + object.getClass()
                    .toString());
        this.object = object;
        return this;
    }

    @Override
    QueryBuilder createOperation() {
        query.append("CREATE TABLE IF NOT EXISTS ");

        return this;
    }

    @Override
    QueryBuilder setTable() {
        query.append(((TableSchema) object).getTableName())
                .append(" (");
        return this;
    }

    @Override
    QueryBuilder setFields() {
        for (ColumnSchema columnSchema : ((TableSchema) object).getColumns()) {
            query.append("\t");
            query.append(columnSchema.getColumnName());
            query.append(" ");

            if ( columnSchema.isGeneratedId() ) {
                query.append("SERIAL");
            } else {
                query.append(columnSchema.getSqlType() == JDBCType.VARCHAR ? "TEXT"
                        : columnSchema.getSqlType()
                                .getName());
            }

            if ( !columnSchema.isNullable() ) {
                query.append(" ");
                query.append("NOT NULL");
            }

            query.append(",\n");
        }

        query.append("PRIMARY KEY(");
        query.append(((TableSchema) object).getIdField()
                .getColumnName());
        query.append(")\n");

        query.append(");");
        return this;
    }

    @Override
    QueryBuilder setValues() {
        return this;
    }

    @Override
    QueryBuilder withCondition(int id, boolean isConditionSet) { return this; }

    @Override
    QueryBuilder compose() {
        return this;
    }
}
