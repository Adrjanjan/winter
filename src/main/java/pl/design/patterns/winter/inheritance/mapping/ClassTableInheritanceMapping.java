package pl.design.patterns.winter.inheritance.mapping;

import java.util.Map;

import pl.design.patterns.winter.schemas.TableSchema;

public class ClassTableInheritanceMapping extends InheritanceMapping {
    private InheritanceMapping parent;

    public ClassTableInheritanceMapping(Map<String, TableSchema> columnNameToTable, InheritanceMapping parent) {
        super(columnNameToTable);
        this.parent = parent;
    }

    @Override
    public TableSchema getTableSchema(String columnName) {
        var tableSchema = super.getTableSchema(columnName);
        if ( tableSchema == null ) {
            tableSchema = parent.getTableSchema(columnName);
        }
        return tableSchema;
    }

}
