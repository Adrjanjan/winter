package pl.design.patterns.winter.query;

import lombok.Getter;
import lombok.Setter;
import org.junit.jupiter.api.Test;
import pl.design.patterns.winter.annotations.DatabaseField;
import pl.design.patterns.winter.annotations.DatabaseTable;
import pl.design.patterns.winter.annotations.Id;
import pl.design.patterns.winter.inheritance.InheritanceMappingType;
import pl.design.patterns.winter.inheritance.mappers.ClassTableInheritanceMapper;
import pl.design.patterns.winter.inheritance.mappers.ClassTableInheritanceTest;
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
        InheritanceMapping mappingOfSelectQueryTestClass = mapper.map(selectQueryTestClass.class);
        SelectQuery selectQuery = new SelectQuery();

        //then
        assertEquals("SELECT * FROM select_query_test_class WHERE param1Int = 12;", selectQuery.prepareFindById(12,selectQueryTestClass.class,mappingOfSelectQueryTestClass));

    }

    @Getter
    @Setter
    @DatabaseTable(inheritanceType = InheritanceMappingType.CLASS_TABLE)
    class selectQueryTestClass{
        @Id
        @DatabaseField
        public int param1Int;

        @DatabaseField
        public String param2String;

        @DatabaseField
        public double param3Double;
    }
}
