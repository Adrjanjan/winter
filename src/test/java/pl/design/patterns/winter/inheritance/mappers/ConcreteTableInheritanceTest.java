package pl.design.patterns.winter.inheritance.mappers;

import lombok.Getter;
import lombok.Setter;
import org.junit.jupiter.api.Test;
import pl.design.patterns.winter.annotations.DatabaseField;
import pl.design.patterns.winter.annotations.DatabaseTable;
import pl.design.patterns.winter.annotations.Id;
import pl.design.patterns.winter.inheritance.InheritanceMappingType;
import pl.design.patterns.winter.inheritance.mapping.InheritanceMapping;
import pl.design.patterns.winter.schemas.DatabaseSchema;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ConcreteTableInheritanceTest {

    @Test
    void concreteTableInheritance() {
        // given
        DatabaseSchema databaseSchema = new DatabaseSchema();
        InheritanceMapper mapper = new ConcreteTableInheritanceMapper(databaseSchema);

        // when
        InheritanceMapping mappingOfClassC = mapper.map(C.class);
        InheritanceMapping mappingOfClassD = mapper.map(D.class);

        // then for C
        assertEquals("C", mappingOfClassC.getTableSchema("stringC")
                .getTableName());
        assertEquals("C", mappingOfClassC.getTableSchema("stringB")
                .getTableName());
        assertEquals("C", mappingOfClassC.getTableSchema("stringA")
                .getTableName());

        // then for D
        assertEquals("D", mappingOfClassD.getTableSchema("stringD")
                .getTableName());
        assertEquals("D", mappingOfClassD.getTableSchema("stringA")
                .getTableName());
    }

    @Getter
    @Setter
    @DatabaseTable(inheritanceType = InheritanceMappingType.CONCRETE_TABLE)
    class A {
        @DatabaseField
        public String stringA;

        @Id
        @DatabaseField
        private int intA;
    }

    @Getter
    @Setter
    @DatabaseTable(inheritanceType = InheritanceMappingType.CONCRETE_TABLE)
    class B extends A {
        @Id
        @DatabaseField
        protected int intB;

        @DatabaseField
        String stringB;
    }

    @Getter
    @Setter
    @DatabaseTable(inheritanceType = InheritanceMappingType.CONCRETE_TABLE)
    class C extends B {
        @DatabaseField
        protected String stringC;

        @Id
        @DatabaseField
        private int intC;
    }

    @Getter
    @Setter
    @DatabaseTable(inheritanceType = InheritanceMappingType.CONCRETE_TABLE)
    class D extends A {
        @Id
        @DatabaseField
        int intD;

        @DatabaseField
        String stringD;
    }


}
