package pl.design.patterns.winter.inheritance.mappers;

import lombok.Getter;
import lombok.Setter;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import pl.design.patterns.winter.annotations.DatabaseField;
import pl.design.patterns.winter.annotations.DatabaseTable;
import pl.design.patterns.winter.annotations.Id;
import pl.design.patterns.winter.inheritance.InheritanceMappingType;
import pl.design.patterns.winter.inheritance.mapping.InheritanceMapping;
import pl.design.patterns.winter.query.InsertQueryBuilder;
import pl.design.patterns.winter.schemas.DatabaseSchema;

import java.lang.reflect.InvocationTargetException;

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
        assertEquals("c", mappingOfClassC.getTableSchema("stringC")
                .getTableName());
        assertEquals("c", mappingOfClassC.getTableSchema("stringB")
                .getTableName());
        assertEquals("c", mappingOfClassC.getTableSchema("stringA")
                .getTableName());

        // then for D
        assertEquals("d", mappingOfClassD.getTableSchema("stringD")
                .getTableName());
        assertEquals("d", mappingOfClassD.getTableSchema("stringA")
                .getTableName());
    }

    @Test
    void InsertQuery() throws InvocationTargetException, IllegalAccessException {
        // given
        DatabaseSchema databaseSchema = new DatabaseSchema();
        InheritanceMapper mapper = new ConcreteTableInheritanceMapper(databaseSchema);
        InsertQueryBuilder insertQueryBuilder = new InsertQueryBuilder(mapper.map(B.class));

        // when
        var b= new B();
        b.setIntB(2);
        b.setStringB("B");
        b.setBooleanB(false);
        b.setIntA(1);
        b.setStringA("A");
        b.setDoubleA(1.1);

        var sql = insertQueryBuilder.prepare(b);

        //then
        Assert.assertEquals("", sql,
                "INSERT INTO b (int_b, string_b, boolean_b, string_a, int_a, double_a ) VALUES ( 2, \"B\", false, \"A\", 1, 1.1 ); ");
    }

    @Getter
    @Setter
    @DatabaseTable(inheritanceType = InheritanceMappingType.CONCRETE_TABLE)
    public class A {
        @DatabaseField
        public String stringA;

        @Id
        @DatabaseField
        private int intA;

        @DatabaseField
        private double doubleA;
    }

    @Getter
    @Setter
    @DatabaseTable(inheritanceType = InheritanceMappingType.CONCRETE_TABLE)
    public class B extends A {
        @Id
        @DatabaseField
        protected int intB;

        @DatabaseField
        String stringB;

        @DatabaseField
        private boolean booleanB;

        public boolean getBooleanB() {
            return booleanB;
        }
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
