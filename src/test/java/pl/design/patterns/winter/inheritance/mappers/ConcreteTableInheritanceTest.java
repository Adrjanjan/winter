package pl.design.patterns.winter.inheritance.mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.lang.reflect.InvocationTargetException;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import pl.design.patterns.winter.domain.concretetable.ConcreteB;
import pl.design.patterns.winter.domain.concretetable.ConcreteC;
import pl.design.patterns.winter.domain.concretetable.ConcreteD;
import pl.design.patterns.winter.inheritance.mapping.InheritanceMapping;
import pl.design.patterns.winter.query.InsertQueryBuilder;
import pl.design.patterns.winter.schemas.DatabaseSchema;

class ConcreteTableInheritanceTest {

    @Test
    void concreteTableInheritance_checkMappingsCorrectness() {
        // given
        DatabaseSchema databaseSchema = new DatabaseSchema();
        InheritanceMapper mapper = new ConcreteTableInheritanceMapper(databaseSchema);

        // when
        InheritanceMapping mappingOfClassC = mapper.map(ConcreteC.class);
        InheritanceMapping mappingOfClassD = mapper.map(ConcreteD.class);

        // then for C
        assertEquals("concrete_c", mappingOfClassC.getTableSchema("stringC")
                .getTableName());
        assertEquals("concrete_c", mappingOfClassC.getTableSchema("stringB")
                .getTableName());
        assertEquals("concrete_c", mappingOfClassC.getTableSchema("stringA")
                .getTableName());

        // then for D
        assertEquals("concrete_d", mappingOfClassD.getTableSchema("stringD")
                .getTableName());
        assertEquals("concrete_d", mappingOfClassD.getTableSchema("stringA")
                .getTableName());
    }

    @Test
    void insertQueryForClassConcreteB() throws InvocationTargetException, IllegalAccessException {
        // given
        DatabaseSchema databaseSchema = new DatabaseSchema();
        InheritanceMapper mapper = new ConcreteTableInheritanceMapper(databaseSchema);
        InsertQueryBuilder insertQueryBuilder = new InsertQueryBuilder(mapper.map(ConcreteB.class));

        // when
        var b = new ConcreteB();
        b.setIntB(2);
        b.setStringB("B");
        b.setStringA("A");
        b.setIntA(1);

        var sql = insertQueryBuilder.prepare(b);

        // then
        Assert.assertEquals("INSERT INTO concrete_b ( int_b, string_b, int_a, string_a ) VALUES ( 2, \"B\", 1, \"A\" ); ", sql);
    }

    @Test
    void insertQueryForClassConcreteC() throws InvocationTargetException, IllegalAccessException {
        // given
        DatabaseSchema databaseSchema = new DatabaseSchema();
        InheritanceMapper mapper = new ConcreteTableInheritanceMapper(databaseSchema);
        InsertQueryBuilder insertQueryBuilder = new InsertQueryBuilder(mapper.map(ConcreteC.class));

        // when
        var c = new ConcreteC();
        c.setIntC(3);
        c.setStringC("C");
        c.setIntB(2);
        c.setStringB("B");
        c.setIntA(1);
        c.setStringA("A");

        var sql = insertQueryBuilder.prepare(c);

        // then
        Assert.assertEquals("INSERT INTO concrete_c ( int_c, string_c, int_b, string_b, int_a, string_a ) VALUES ( 3, \"C\", 2, \"B\", 1, \"A\" ); ", sql);
    }

    @Test
    void insertQueryForClassConcreteD() throws InvocationTargetException, IllegalAccessException {
        // given
        DatabaseSchema databaseSchema = new DatabaseSchema();
        InheritanceMapper mapper = new ConcreteTableInheritanceMapper(databaseSchema);
        InsertQueryBuilder insertQueryBuilder = new InsertQueryBuilder(mapper.map(ConcreteD.class));

        // when
        var d = new ConcreteD();
        d.setIntD(4);
        d.setStringD("D");
        d.setIntA(1);
        d.setStringA("A");

        var sql = insertQueryBuilder.prepare(d);

        // then
        Assert.assertEquals("INSERT INTO concrete_d ( int_d, string_d, int_a, string_a ) VALUES ( 4, \"D\", 1, \"A\" ); ", sql);
    }
}
