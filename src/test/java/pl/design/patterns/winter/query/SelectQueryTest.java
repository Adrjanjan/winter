package pl.design.patterns.winter.query;

import lombok.Getter;
import lombok.Setter;
import org.junit.jupiter.api.Test;
import pl.design.patterns.winter.annotations.DatabaseField;
import pl.design.patterns.winter.annotations.DatabaseTable;
import pl.design.patterns.winter.annotations.Id;
import pl.design.patterns.winter.inheritance.InheritanceMappingType;
import pl.design.patterns.winter.inheritance.mappers.ClassTableInheritanceMapper;
import pl.design.patterns.winter.inheritance.mappers.InheritanceMapper;
import pl.design.patterns.winter.inheritance.mapping.InheritanceMapping;
import pl.design.patterns.winter.schemas.DatabaseSchema;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SelectQueryTest {

    @Test
    void prepareFindById()
    {
        // given
        DatabaseSchema databaseSchema = new DatabaseSchema();
        InheritanceMapper mapper = new ClassTableInheritanceMapper(databaseSchema);

        // when
        InheritanceMapping mappingOfSelectQueryTestClass = mapper.map(SelectQueryTestClass1.class);

        //then
        assertEquals("SELECT * FROM select_query_test_class1 WHERE param1_int = 12;", SelectQuery.prepareFindById(12, SelectQueryTestClass1.class, mappingOfSelectQueryTestClass));
        //TODO Inny test zasugerowany przez Adriana
    }

    @Getter
    @Setter
    @DatabaseTable(inheritanceType = InheritanceMappingType.CLASS_TABLE)
    class SelectQueryTestClass1 {
        @Id
        @DatabaseField
        public int param1Int;

        @DatabaseField
        public String param2String;

        @DatabaseField
        public double param3Double;
    }
}
