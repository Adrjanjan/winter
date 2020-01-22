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

public class ClassTableInheritanceTest {

    @Test
    void classTableInheritance() {
        // given
        DatabaseSchema databaseSchema = new DatabaseSchema();
        InheritanceMapper mapper = new ClassTableInheritanceMapper(databaseSchema);

        // when
        InheritanceMapping mappingOfClassC = mapper.map(C.class);
        InheritanceMapping mappingOfClassD = mapper.map(D.class);

        // then for C
        assertEquals("c", mappingOfClassC.getTableSchema("stringC")
                .getTableName());
        assertEquals("b", mappingOfClassC.getTableSchema("stringB")
                .getTableName());
        assertEquals("a", mappingOfClassC.getTableSchema("stringA")
                .getTableName());

        // then for D
        assertEquals("d", mappingOfClassD.getTableSchema("stringD")
                .getTableName());
        assertEquals("a", mappingOfClassD.getTableSchema("stringA")
                .getTableName());
    }

    @Test
    void InsertQuery() throws InvocationTargetException, IllegalAccessException {
        //given
        DatabaseSchema databaseSchema = new DatabaseSchema();
        InheritanceMapper mapper = new ClassTableInheritanceMapper(databaseSchema);
        InsertQueryBuilder insertQueryBuilder = new InsertQueryBuilder(mapper.map(B.class));

        //when
        var b= new B();
        b.setIntB(2);
        b.setStringB("B");
        b.setIntA(1);
        b.setStringA("A");
        var sql = insertQueryBuilder.prepare(b);

        //then
        Assert.assertEquals("", sql,
                "INSERT INTO b (int_b, string_b ) VALUES ( 2, \"B\" ); INSERT INTO a (string_a, int_a ) VALUES ( \"A\", 1 ); ");
    }

    @Getter
    @Setter
    @DatabaseTable(inheritanceType = InheritanceMappingType.CLASS_TABLE)
    public class A {
        @DatabaseField
        public String stringA;

        @Id
        @DatabaseField
        private int intA;
    }

    @Getter
    @Setter
    @DatabaseTable(inheritanceType = InheritanceMappingType.CLASS_TABLE)
    public class B extends A {
        @Id
        @DatabaseField
        protected int intB;

        @DatabaseField
        String stringB;
    }

    @Getter
    @Setter
    @DatabaseTable(inheritanceType = InheritanceMappingType.CLASS_TABLE)
    class C extends B {
        @DatabaseField
        protected String stringC;

        @Id
        @DatabaseField
        private int intC;
    }

    @Getter
    @Setter
    @DatabaseTable(inheritanceType = InheritanceMappingType.CLASS_TABLE)
    class D extends A {
        @Id
        @DatabaseField
        int intD;

        @DatabaseField
        String stringD;
    }

}
