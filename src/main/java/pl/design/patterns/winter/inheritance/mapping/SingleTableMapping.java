package pl.design.patterns.winter.inheritance.mapping;

import java.util.List;
import java.util.Map;

import pl.design.patterns.winter.annotations.DiscriminatorValue;
import pl.design.patterns.winter.schemas.ColumnSchema;
import pl.design.patterns.winter.schemas.TableSchema;

import lombok.Builder;

@Builder
public class SingleTableMapping<T> implements InheritanceMapping {
    private TableSchema<? extends T> tableSchema;

    private Map<String, List<ColumnSchema>> listOfColumnDiscriminators;

    @Override
    public TableSchema getTableSchema(Class clazz) {
        return tableSchema.withNewColumns(listOfColumnDiscriminators.get(getClassDiscriminator(clazz)));
    }

    private String getClassDiscriminator(Class<T> clazz) {
        return clazz.getDeclaredAnnotation(DiscriminatorValue.class)
                .value();
    }

}
