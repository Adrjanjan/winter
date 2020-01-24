package pl.design.patterns.winter.statements.query;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.lang.reflect.InvocationTargetException;

import org.junit.jupiter.api.Test;

import pl.design.patterns.winter.annotations.DatabaseField;
import pl.design.patterns.winter.annotations.DatabaseTable;
import pl.design.patterns.winter.annotations.Id;
import pl.design.patterns.winter.inheritance.InheritanceMappingType;
import pl.design.patterns.winter.inheritance.mappers.ClassTableInheritanceMapper;
import pl.design.patterns.winter.inheritance.mappers.InheritanceMapper;
import pl.design.patterns.winter.inheritance.mapping.InheritanceMapping;
import pl.design.patterns.winter.schemas.DatabaseSchema;

import lombok.Getter;
import lombok.Setter;

public class UpdateQueryBuilderTest<T> {

    @Test
    void prepareUpdateQuery() throws IllegalAccessException, InvocationTargetException {
        // given
        DatabaseSchema databaseSchema = new DatabaseSchema();
        InheritanceMapper mapper = new ClassTableInheritanceMapper(databaseSchema);

        // when
        A testAVariable = new A();
        testAVariable.setAInt(1);
        testAVariable.setAString("ala");
        testAVariable.setADouble(5.55);
        InheritanceMapping mappingOfUpdateQueryA = mapper.map(A.class);

        QueryBuilder builder = new UpdateQueryBuilder(mappingOfUpdateQueryA);
        QueryBuildDirector<T> queryBuildDirector = new QueryBuildDirector<>(builder);
        String query = queryBuildDirector.withObject((T)testAVariable)
                .build();
        //then
        assertEquals("UPDATE a SET (AInt,AString,ADouble) = (1,\"ala\",5.55) WHERE a_int = 1;", query);

    }

    @Getter
    @Setter
    @DatabaseTable(inheritanceType = InheritanceMappingType.CLASS_TABLE)
    public class A {
        @Id
        @DatabaseField
        public int AInt;

        @DatabaseField
        public String AString;

        @DatabaseField
        public double ADouble;

    }

}
