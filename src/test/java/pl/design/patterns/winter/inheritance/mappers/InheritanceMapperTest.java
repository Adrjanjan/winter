package pl.design.patterns.winter.inheritance.mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

import org.junit.jupiter.api.Test;

import pl.design.patterns.winter.annotations.DatabaseField;
import pl.design.patterns.winter.annotations.DatabaseTable;
import pl.design.patterns.winter.annotations.Id;
import pl.design.patterns.winter.inheritance.mapping.InheritanceMapping;
import pl.design.patterns.winter.schemas.DatabaseSchema;

import lombok.Getter;
import lombok.Setter;

class InheritanceMapperTest {

    @Test
    void concreteTableInheritance() {
        // given
        InheritanceMapper mapper = new ConcreteTableInheritanceMapper();

        // when
        InheritanceMapping mappingOfClassC = mapper.map(C.class);
        InheritanceMapping mappingOfClassD = mapper.map(D.class);

        // then for C
        assertEquals("C", mappingOfClassC.getTableSchema("stringC")
                .getTableName());
        assertEquals(6, mappingOfClassC.getColumnNameToTable()
                .size());
        // then for D
        assertEquals("D", mappingOfClassD.getTableSchema("stringA")
                .getTableName());
        assertEquals(4, mappingOfClassD.getColumnNameToTable()
                .size());
    }

    @Test
    void singleTableInheritance() {
        // given
        DatabaseSchema databaseSchema = new DatabaseSchema();
        InheritanceMapper mapper = new SingleTableInheritanceMapper(databaseSchema);

        // when
        InheritanceMapping mappingOfClassC = mapper.map(C.class);
        InheritanceMapping mappingOfClassD = mapper.map(D.class);

        // then for C
        assertEquals("A", mappingOfClassC.getTableSchema("stringC")
                .getTableName());
        assertEquals(8, mappingOfClassC.getColumnNameToTable()
                .size());
        // then for D
        assertEquals("A", mappingOfClassD.getTableSchema("stringA")
                .getTableName());
        assertEquals(8, mappingOfClassD.getColumnNameToTable()
                .size());
        assertSame(mappingOfClassC, mappingOfClassD);
    }

    @Test
    void classTableInheritance() {
        // given
        DatabaseSchema databaseSchema = new DatabaseSchema();
        InheritanceMapper mapper = new ClassTableInheritanceMapper(databaseSchema);

        // when
        InheritanceMapping mappingOfClassC = mapper.map(C.class);
        InheritanceMapping mappingOfClassD = mapper.map(D.class);

        // then for C
        assertEquals("C", mappingOfClassC.getTableSchema("stringC")
                .getTableName());
        assertEquals("B", mappingOfClassC.getTableSchema("stringB")
                .getTableName());
        assertEquals("A", mappingOfClassC.getTableSchema("stringA")
                .getTableName());

        assertEquals(6, mappingOfClassC.getColumnNameToTable()
                .size());
        // then for D
        assertEquals("D", mappingOfClassD.getTableSchema("stringD")
                .getTableName());
        assertEquals("A", mappingOfClassD.getTableSchema("stringA")
                .getTableName());
        assertEquals(4, mappingOfClassD.getColumnNameToTable()
                .size());
    }

    @Getter
    @Setter
    @DatabaseTable(inheritanceMapper = ConcreteTableInheritanceMapper.class)
    class A {
        @DatabaseField
        public String stringA;

        @Id
        @DatabaseField
        private int intA;
    }

    @Getter
    @Setter
    @DatabaseTable(inheritanceMapper = ConcreteTableInheritanceMapper.class)
    class B extends A {
        @Id
        @DatabaseField
        protected int intB;

        @DatabaseField
        String stringB;
    }

    @Getter
    @Setter
    @DatabaseTable(inheritanceMapper = ConcreteTableInheritanceMapper.class)
    class C extends B {
        @DatabaseField
        protected String stringC;

        @Id
        @DatabaseField
        private int intC;
    }

    @Getter
    @Setter
    @DatabaseTable(inheritanceMapper = ConcreteTableInheritanceMapper.class)
    class D extends A {
        @Id
        @DatabaseField
        int intD;

        @DatabaseField
        String stringD;
    }

}
