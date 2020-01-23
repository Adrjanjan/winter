package pl.design.patterns.winter.query;

import lombok.Getter;
import lombok.Setter;
import org.junit.jupiter.api.Test;
import pl.design.patterns.winter.annotations.DatabaseField;
import pl.design.patterns.winter.annotations.DatabaseTable;
import pl.design.patterns.winter.annotations.Id;
import pl.design.patterns.winter.inheritance.InheritanceMappingType;
import pl.design.patterns.winter.inheritance.mappers.ClassTableInheritanceMapper;
import pl.design.patterns.winter.inheritance.mappers.InheritanceMapper;
import pl.design.patterns.winter.inheritance.mapping.InheritanceMapping;
import pl.design.patterns.winter.schemas.DatabaseSchema;
import pl.design.patterns.winter.statements.UpdateExecutor;

import java.lang.reflect.InvocationTargetException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UpdateQueryTest {

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

        //then
        assertEquals("UPDATE a SET (AInt,AString,ADouble) = (1,ala,5.55) WHERE a_int = 1;", UpdateQuery.prepareUpdate(testAVariable, UpdateQueryTest.A.class, mappingOfUpdateQueryA));

    }

    @Getter
    @Setter
    @DatabaseTable(inheritanceType = InheritanceMappingType.CLASS_TABLE)
    class A {
        @Id
        @DatabaseField
        public int AInt;

        @DatabaseField
        public String AString;

        @DatabaseField
        public double ADouble;

    }

}
