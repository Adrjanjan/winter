package pl.design.patterns.winter.inheritance.mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

import java.lang.reflect.InvocationTargetException;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import pl.design.patterns.winter.domain.singletable.SingleB;
import pl.design.patterns.winter.domain.singletable.SingleC;
import pl.design.patterns.winter.domain.singletable.SingleD;
import pl.design.patterns.winter.inheritance.mapping.InheritanceMapping;
import pl.design.patterns.winter.query.InsertQueryBuilder;
import pl.design.patterns.winter.schemas.DatabaseSchema;

public class SingleTableInheritanceTest {

    @Test
    void singleTableInheritance_checkMappingsCorrectness() throws InvocationTargetException, IllegalAccessException {
        // given
        DatabaseSchema databaseSchema = new DatabaseSchema();
        InheritanceMapper mapper = new SingleTableInheritanceMapper(databaseSchema);

        // when
        InheritanceMapping mappingOfClassC = mapper.map(SingleC.class);
        InheritanceMapping mappingOfClassD = mapper.map(SingleD.class);

        // then for C
        assertEquals("single_a", mappingOfClassC.getTableSchema("stringC")
                .getTableName());
        assertEquals("single_a", mappingOfClassC.getTableSchema("stringB")
                .getTableName());
        assertEquals("single_a", mappingOfClassC.getTableSchema("stringA")
                .getTableName());

        // then for D
        assertEquals("single_a", mappingOfClassD.getTableSchema("stringD")
                .getTableName());
        assertEquals("single_a", mappingOfClassD.getTableSchema("stringA")
                .getTableName());

        assertSame(mappingOfClassC, mappingOfClassD);
    }

    @Test
    void insertQueryForClassSingleB() throws InvocationTargetException, IllegalAccessException {
        // given
        DatabaseSchema databaseSchema = new DatabaseSchema();
        InheritanceMapper mapper = new SingleTableInheritanceMapper(databaseSchema);

        InsertQueryBuilder insertQueryBuilder = new InsertQueryBuilder(mapper.map(SingleB.class));

        // when
        var b = new SingleB();
        b.setIntA(1);
        b.setStringA("A");
        b.setStringB("B");
        b.setIntB(2);
        var sql = insertQueryBuilder.prepare(b);
        // then
        Assert.assertEquals("INSERT INTO single_a ( int_a, string_a, int_b, string_b ) VALUES ( 1, \"A\", 2, \"B\" ); ", sql);
    }

    @Test
    void insertQueryForClassSingleC() throws InvocationTargetException, IllegalAccessException {
        // given
        DatabaseSchema databaseSchema = new DatabaseSchema();
        InheritanceMapper mapper = new SingleTableInheritanceMapper(databaseSchema);

        InsertQueryBuilder insertQueryBuilder = new InsertQueryBuilder(mapper.map(SingleC.class));

        // when
        var c = new SingleC();
        c.setIntA(1);
        c.setStringA("A");
        c.setIntB(2);
        // setStringB intentionally not set to see if NULL will be in query
        c.setIntC(3);
        c.setStringC("C");
        var sql = insertQueryBuilder.prepare(c);
        // then
        Assert.assertEquals("INSERT INTO single_a ( int_a, string_a, int_b, string_b, int_c, string_c ) VALUES ( 1, \"A\", 2, NULL, 3, \"C\" ); ", sql);
    }

    @Test
    void insertQueryForClassSingleD() throws InvocationTargetException, IllegalAccessException {
        // given
        DatabaseSchema databaseSchema = new DatabaseSchema();
        InheritanceMapper mapper = new SingleTableInheritanceMapper(databaseSchema);

        InsertQueryBuilder insertQueryBuilder = new InsertQueryBuilder(mapper.map(SingleD.class));

        // when
        var d = new SingleD();
        d.setIntA(1);
        d.setStringA("A");
        d.setIntD(4);
        d.setStringD("D");
        var sql = insertQueryBuilder.prepare(d);
        // then
        Assert.assertEquals("INSERT INTO single_a ( int_a, string_a, int_d, string_d ) VALUES ( 1, \"A\", 4, \"D\" ); ", sql);
    }

}
