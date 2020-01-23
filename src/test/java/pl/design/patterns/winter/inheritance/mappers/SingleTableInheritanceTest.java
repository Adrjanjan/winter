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
import pl.design.patterns.winter.statements.InsertExecutor;

import java.lang.reflect.InvocationTargetException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

public class SingleTableInheritanceTest {

    @Test
    void singleTableInheritance() throws InvocationTargetException, IllegalAccessException {
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

    @Test
    void insertQuery() throws InvocationTargetException, IllegalAccessException {
        // given
        DatabaseSchema databaseSchema = new DatabaseSchema();
        InheritanceMapper mapper = new ConcreteTableInheritanceMapper(databaseSchema);

        InsertQueryBuilder insertQueryBuilder = new InsertQueryBuilder(mapper.map(A.class));

        // when
        var a= new A();
        a.setIntA(1);
        a.setStringA("A");
        var sql = insertQueryBuilder.prepare(a);
        //sql = INSERT INTO a (string_a, int_a ) VALUES ( "A", 1 );
        insertQueryBuilder = new InsertQueryBuilder(mapper.map(B.class));

        // when
        var b= new B();
        b.setIntA(1);
        b.setStringA("A");
        b.setStringB("B");
        b.setIntB(2);
        sql = insertQueryBuilder.prepare(b);
        //sql = INSERT INTO b (int_b, string_b, string_a, int_a ) VALUES ( 2, "B", "A", 1 );
        //TODO zła nazwa tabeli ale pola już lepsze XD
        //then
        //Assert.assertEquals("", sql,"");
    }

    @Getter
    @Setter
    @DatabaseTable(inheritanceType = InheritanceMappingType.SINGLE_TABLE)
    public class A {
        @DatabaseField
        public String stringA;

        @Id
        @DatabaseField
        private int intA;
    }

    @Getter
    @Setter
    @DatabaseTable(inheritanceType = InheritanceMappingType.SINGLE_TABLE)
    public class B extends A {

        @Id
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
