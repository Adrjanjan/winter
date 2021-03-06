package pl.design.patterns.winter.inheritance.mappers;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertEquals;

import java.lang.reflect.InvocationTargetException;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import pl.design.patterns.winter.domain.classtable.ClassB;
import pl.design.patterns.winter.domain.classtable.ClassC;
import pl.design.patterns.winter.domain.classtable.ClassD;
import pl.design.patterns.winter.inheritance.mapping.InheritanceMapping;
import pl.design.patterns.winter.statements.query.DeleteQueryBuilder;
import pl.design.patterns.winter.schemas.DatabaseSchema;
import pl.design.patterns.winter.statements.query.InsertQueryBuilder;
import pl.design.patterns.winter.statements.query.QueryBuildDirector;

public class ClassTableInheritanceTest<T> {

    @Test
    void classTableInheritance_checkMappingsCorrectness() {
        // given
        DatabaseSchema databaseSchema = new DatabaseSchema();
        InheritanceMapper mapper = new ClassTableInheritanceMapper(databaseSchema);

        // when
        InheritanceMapping mappingOfClassC = mapper.map(ClassC.class);
        InheritanceMapping mappingOfClassD = mapper.map(ClassD.class);

        // then for C
        assertEquals("class_c", mappingOfClassC.getTableSchema("stringC")
                .getTableName());
        assertEquals("class_b", mappingOfClassC.getTableSchema("stringB")
                .getTableName());
        assertEquals("class_a", mappingOfClassC.getTableSchema("stringA")
                .getTableName());

        // then for D
        assertEquals("class_d", mappingOfClassD.getTableSchema("stringD")
                .getTableName());
        assertEquals("class_a", mappingOfClassD.getTableSchema("stringA")
                .getTableName());
    }

    @Test
    void insertQueryForClassClassB() throws InvocationTargetException, IllegalAccessException {
        // given
        DatabaseSchema databaseSchema = new DatabaseSchema();
        InheritanceMapper mapper = new ClassTableInheritanceMapper(databaseSchema);
        InsertQueryBuilder insertQueryBuilder = new InsertQueryBuilder(mapper.map(ClassB.class));

        // when
        var b = new ClassB();
        b.setIntB(2);
        b.setStringB("B");
        b.setIntA(1);
        b.setStringA("A");

        QueryBuildDirector<T> queryBuildDirector = new QueryBuildDirector<>(insertQueryBuilder);
        String sql = queryBuildDirector.withObject((T) b)
                .build();

        // then
        Assert.assertThat(sql, containsString("INSERT INTO class_a ( string_a, int_a ) VALUES ( \"A\", 1 );"));
        Assert.assertThat(sql, containsString("INSERT INTO class_b ( string_b, int_b, int_a ) VALUES ( \"B\", 2, 1 );"));
    }

    @Test
    void insertQueryForClassClassC() throws InvocationTargetException, IllegalAccessException {
        // given
        DatabaseSchema databaseSchema = new DatabaseSchema();
        InheritanceMapper mapper = new ClassTableInheritanceMapper(databaseSchema);
        InsertQueryBuilder insertQueryBuilder = new InsertQueryBuilder(mapper.map(ClassC.class));

        // when
        var c = new ClassC();
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
        Assert.assertThat(sql, containsString("INSERT INTO class_a ( string_a, int_a ) VALUES ( \"A\", 1 );"));
        Assert.assertThat(sql, containsString("INSERT INTO class_b ( string_b, int_b, int_a ) VALUES ( \"B\", 2, 1 );"));
        Assert.assertThat(sql, containsString("INSERT INTO class_c ( string_c, int_c, int_a ) VALUES ( \"C\", 3, 1 ); "));
    }

    @Test
    void insertQueryForClassClassD() throws InvocationTargetException, IllegalAccessException {
        // given
        DatabaseSchema databaseSchema = new DatabaseSchema();
        InheritanceMapper mapper = new ClassTableInheritanceMapper(databaseSchema);
        InsertQueryBuilder insertQueryBuilder = new InsertQueryBuilder(mapper.map(ClassD.class));

        // when
        var d = new ClassD();
        d.setIntD(4);
        d.setStringD("D");
        d.setIntA(1);
        d.setStringA("A");

        QueryBuildDirector<T> queryBuildDirector = new QueryBuildDirector<>(insertQueryBuilder);
        String sql = queryBuildDirector.withObject((T) d)
                .build();

//        QueryBuildDirector<T> queryBuildDirector = new QueryBuildDirector<>(insertQueryBuilder);
//        String sql = queryBuildDirector.withObject((T) d)
//                .build();

        //then
        Assert.assertEquals("", sql,
                "INSERT INTO b (int_b, string_b ) VALUES ( 2, \"B\" ); INSERT INTO a (string_a, int_a ) VALUES ( \"A\", 1 ); ");
    }

    @Test
    void deleteQueryForClassClassB() throws InvocationTargetException, IllegalAccessException {
        // given
        DatabaseSchema databaseSchema = new DatabaseSchema();
        InheritanceMapper mapper = new SingleTableInheritanceMapper(databaseSchema);

        DeleteQueryBuilder deleteQueryBuilder = new DeleteQueryBuilder(mapper.map(ClassB.class));

        // when
        var b = new ClassB();
        b.setIntA(1);
        b.setStringA("A");
        b.setStringB("B");
        b.setIntB(2);

        QueryBuildDirector<T> queryBuildDirector = new QueryBuildDirector<>(deleteQueryBuilder);
        String sql = queryBuildDirector.withObject((T) b)
                .build();

        // then
        Assert.assertEquals("DELETE FROM class_a WHERE int_a = 0; ", sql);
    }

    @Test
    void deleteQueryForClassClassC() throws InvocationTargetException, IllegalAccessException {
        // given
        DatabaseSchema databaseSchema = new DatabaseSchema();
        InheritanceMapper mapper = new ClassTableInheritanceMapper(databaseSchema);
        DeleteQueryBuilder deleteQueryBuilder = new DeleteQueryBuilder(mapper.map(ClassC.class));

        // when
        var c = new ClassC();
        c.setIntC(3);
        c.setStringC("C");
        c.setIntB(2);
        c.setStringB("B");
        c.setIntA(1);
        c.setStringA("A");

        QueryBuildDirector<T> queryBuildDirector = new QueryBuildDirector<>(deleteQueryBuilder);
        String sql = queryBuildDirector.withObject((T) c)
                .build();

        // then
        Assert.assertEquals("DELETE FROM class_c WHERE int_a = 0; DELETE FROM class_a WHERE int_a = 0; DELETE FROM class_b WHERE int_a = 0; ", sql);
    }

    @Test
    void deleteQueryForClassClassD() throws InvocationTargetException, IllegalAccessException {
        // given
        DatabaseSchema databaseSchema = new DatabaseSchema();
        InheritanceMapper mapper = new ClassTableInheritanceMapper(databaseSchema);
        DeleteQueryBuilder deleteQueryBuilder = new DeleteQueryBuilder(mapper.map(ClassD.class));

        // when
        var d = new ClassD();
        d.setIntD(4);
        d.setStringD("D");
        d.setIntA(1);
        d.setStringA("A");

        QueryBuildDirector<T> queryBuildDirector = new QueryBuildDirector<>(deleteQueryBuilder);
        String sql = queryBuildDirector.withObject((T) d)
                .build();


        //then
        Assert.assertEquals("", sql, "DELETE FROM class_d WHERE int_a = 0; DELETE FROM class_a WHERE int_a = 0; ");
    }


}
