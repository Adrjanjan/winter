package pl.design.patterns.winter.query;

import java.sql.JDBCType;

import pl.design.patterns.winter.schemas.ColumnSchema;
import pl.design.patterns.winter.schemas.TableSchema;

public class CreateTableQuery {

    public static String prepare(TableSchema tableSchema) {
        StringBuilder sb = new StringBuilder();

        sb.append("CREATE TABLE IF NOT EXISTS");

        sb.append(" ");
        sb.append(tableSchema.getTableName());
        sb.append(" ");

        sb.append("(\n");

        for (ColumnSchema columnSchema : tableSchema.getColumns()) {
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
        sb.append(tableSchema.getIdField()
                .getColumnName());
        sb.append(")\n");

        sb.append(");");

        return sb.toString();
    }

}
