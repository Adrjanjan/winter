package pl.design.patterns.winter.inheritance.mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.lang.reflect.InvocationTargetException;

import org.junit.Assert;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import pl.design.patterns.winter.domain.concretetable.ConcreteB;
import pl.design.patterns.winter.domain.concretetable.ConcreteC;
import pl.design.patterns.winter.domain.concretetable.ConcreteD;
import pl.design.patterns.winter.inheritance.mapping.InheritanceMapping;
import pl.design.patterns.winter.statements.query.DeleteQueryBuilder;
import pl.design.patterns.winter.schemas.DatabaseSchema;
import pl.design.patterns.winter.statements.query.InsertQueryBuilder;
import pl.design.patterns.winter.statements.query.QueryBuildDirector;

@Disabled
class ConcreteTableInheritanceTest<T> {

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

        QueryBuildDirector<T> queryBuildDirector = new QueryBuildDirector<>(insertQueryBuilder);
        String sql = queryBuildDirector.withObject((T) b)
                .build();

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

        QueryBuildDirector<T> queryBuildDirector = new QueryBuildDirector<>(insertQueryBuilder);
        String sql = queryBuildDirector.withObject((T) c)
                .build();

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

        QueryBuildDirector<T> queryBuildDirector = new QueryBuildDirector<>(insertQueryBuilder);
        String sql = queryBuildDirector.withObject((T) d)
                .build();

        // then
        Assert.assertEquals("INSERT INTO concrete_d ( int_d, string_d, int_a, string_a ) VALUES ( 4, \"D\", 1, \"A\" ); ", sql);
    }

    @Test
    void deleteQueryForClassConcreteB() throws InvocationTargetException, IllegalAccessException {
        // given
        DatabaseSchema databaseSchema = new DatabaseSchema();
        InheritanceMapper mapper = new ConcreteTableInheritanceMapper(databaseSchema);
        DeleteQueryBuilder deleteQueryBuilder = new DeleteQueryBuilder(mapper.map(ConcreteB.class));

        // when
        var b = new ConcreteB();
        b.setIntB(2);
        b.setStringB("B");
        b.setStringA("A");
        b.setIntA(1);

        QueryBuildDirector<T> queryBuildDirector = new QueryBuildDirector<>(deleteQueryBuilder);
        String sql = queryBuildDirector.withObject((T) b)
                .build();

        // then
        Assert.assertEquals("DELETE FROM concrete_b WHERE int_a = 1; ", sql);
    }

    @Test
    void deleteQueryForClassConcreteC() throws InvocationTargetException, IllegalAccessException {
        // given
        DatabaseSchema databaseSchema = new DatabaseSchema();
        InheritanceMapper mapper = new ConcreteTableInheritanceMapper(databaseSchema);
        DeleteQueryBuilder deleteQueryBuilder = new DeleteQueryBuilder(mapper.map(ConcreteC.class));

        // when
        var c = new ConcreteC();
        c.setIntB(2);
        c.setStringB("c");
        c.setStringA("A");
        c.setIntA(1);

        QueryBuildDirector<T> queryBuildDirector = new QueryBuildDirector<>(deleteQueryBuilder);
        String sql = queryBuildDirector.withObject((T) c)
                .build();

        // then
        Assert.assertEquals("DELETE FROM concrete_c WHERE int_a = 1; ", sql);
    }

    @Test
    void deleteQueryForClassConcreteD() throws InvocationTargetException, IllegalAccessException {
        // given
        DatabaseSchema databaseSchema = new DatabaseSchema();
        InheritanceMapper mapper = new ConcreteTableInheritanceMapper(databaseSchema);
        DeleteQueryBuilder deleteQueryBuilder = new DeleteQueryBuilder(mapper.map(ConcreteD.class));

        // when
        var d = new ConcreteD();
        d.setIntD(4);
        d.setStringD("D");
        d.setIntA(1);
        d.setStringA("A");

        QueryBuildDirector<T> queryBuildDirector = new QueryBuildDirector<>(deleteQueryBuilder);
        String sql = queryBuildDirector.withObject((T) d)
                .build();

        // then
        Assert.assertEquals("DELETE FROM concrete_d WHERE int_a = 1; ", sql);
    }
}
