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
import static org.junit.jupiter.api.Assertions.assertSame;

public class SingleTableInheritanceTest {

    @Test
    void singleTableInheritance() {
        // given
        DatabaseSchema databaseSchema = new DatabaseSchema();
        InheritanceMapper mapper = new SingleTableInheritanceMapper(databaseSchema);

        // when
        InheritanceMapping mappingOfClassC = mapper.map(C.class);
        InheritanceMapping mappingOfClassD = mapper.map(D.class);

        // then for C
        assertEquals("a", mappingOfClassC.getTableSchema("stringC")
                .getTableName());
        assertEquals("a", mappingOfClassC.getTableSchema("stringB")
                .getTableName());
        assertEquals("a", mappingOfClassC.getTableSchema("stringA")
                .getTableName());

        // then for D
        assertEquals("a", mappingOfClassD.getTableSchema("stringD")
                .getTableName());
        assertEquals("a", mappingOfClassD.getTableSchema("stringA")
                .getTableName());

        assertSame(mappingOfClassC, mappingOfClassD);
    }

    @Getter
    @Setter
    @DatabaseTable(inheritanceType = InheritanceMappingType.SINGLE_TABLE)
    class A {
        @DatabaseField
        public String stringA;

        @Id
        @DatabaseField
        private int intA;
    }

    @Getter
    @Setter
    @DatabaseTable(inheritanceType = InheritanceMappingType.SINGLE_TABLE)
    class B extends A {

        @DatabaseField
        protected int intB;

        @DatabaseField
        String stringB;
    }

    @Getter
    @Setter
    @DatabaseTable(inheritanceType = InheritanceMappingType.SINGLE_TABLE)
    class C extends B {
        @DatabaseField
        protected String stringC;


        @DatabaseField
        private int intC;
    }

    @Getter
    @Setter
    @DatabaseTable(inheritanceType = InheritanceMappingType.SINGLE_TABLE)
    class D extends A {

        @DatabaseField
        int intD;

        @DatabaseField
        String stringD;
    }

}
