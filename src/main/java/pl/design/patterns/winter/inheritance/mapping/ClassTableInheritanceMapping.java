package pl.design.patterns.winter.inheritance.mapping;

import pl.design.patterns.winter.schemas.TableSchema;

import java.util.Map;

public class ClassTableInheritanceMapping extends InheritanceMapping {
    private InheritanceMapping parent;

    public ClassTableInheritanceMapping(Map<String, TableSchema> columnNameToTable, InheritanceMapping parent) {
        super(columnNameToTable);
        this.parent = parent;
    }

    @Override
    public TableSchema getTableSchema(String fieldName) {
        var tableSchema = super.getTableSchema(fieldName);
        if ( tableSchema == null ) {
            tableSchema = parent.getTableSchema(fieldName);
        }
        return tableSchema;
    }

}
