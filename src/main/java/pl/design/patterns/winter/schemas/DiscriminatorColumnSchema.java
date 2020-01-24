package pl.design.patterns.winter.schemas;

import lombok.Data;

@Data
public class DiscriminatorColumnSchema extends ColumnSchema {
    public DiscriminatorColumnSchema(Class<?> parent, String columnName, String sqlType) {
        super.setParent(parent);
        super.setColumnName(columnName);
        super.setSqlType(sqlType);
    }
}
