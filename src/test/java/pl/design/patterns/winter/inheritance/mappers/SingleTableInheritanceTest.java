package pl.design.patterns.winter.inheritance.mappers;

import lombok.Getter;
import lombok.Setter;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import pl.design.patterns.winter.annotations.DatabaseField;
import pl.design.patterns.winter.annotations.DatabaseTable;
import pl.design.patterns.winter.annotations.Id;
import pl.design.patterns.winter.domain.singletable.SingleB;
import pl.design.patterns.winter.domain.singletable.SingleC;
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
    void insertQueryB() throws InvocationTargetException, IllegalAccessException {
        // given
        DatabaseSchema databaseSchema = new DatabaseSchema();
        InheritanceMapper mapper = new SingleTableInheritanceMapper(databaseSchema);

        InsertQueryBuilder insertQueryBuilder = new InsertQueryBuilder(mapper.map(SingleB.class));

        // when
        var b= new SingleB();
        b.setIntA(1);
        b.setStringA("A");
        b.setStringB("B");
        b.setIntB(2);
        var sql = insertQueryBuilder.prepare(b);
        //then
        //Assert.assertEquals("", sql,"");
    }

    @Test
    void insertQueryC() throws InvocationTargetException, IllegalAccessException {
        // given
        DatabaseSchema databaseSchema = new DatabaseSchema();
        InheritanceMapper mapper = new SingleTableInheritanceMapper(databaseSchema);

        InsertQueryBuilder insertQueryBuilder = new InsertQueryBuilder(mapper.map(SingleC.class));

        // when
        var c= new SingleC();
        c.setIntA(1);
        c.setStringA("A");
        //c.setStringB("B");
        c.setIntB(2);
        c.setIntC(3);
        c.setStringC("C");
        var sql = insertQueryBuilder.prepare(c);
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
