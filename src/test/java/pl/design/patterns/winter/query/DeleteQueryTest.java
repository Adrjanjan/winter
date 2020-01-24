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

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DeleteQueryTest {

    @Test
    void prepareDelete() {
        // given
        DatabaseSchema databaseSchema = new DatabaseSchema();
        InheritanceMapper mapper = new ClassTableInheritanceMapper(databaseSchema);

        // when
        InheritanceMapping mappingOfDeleteQueryC = mapper.map(C.class);
        InheritanceMapping mappingOfDeleteQueryB = mapper.map(B.class);

        //then
//        assertEquals("DELETE FROM b WHERE b_int = 12; DELETE FROM a  WHERE a_int = 12;", DeleteQuery.prepareDelete(12, B.class,mappingOfDeleteQueryB));
//        assertEquals("DELETE FROM c WHERE c_int = 100;", DeleteQuery.prepareDelete(100, C.class,mappingOfDeleteQueryC));

    }

    @Getter
    @Setter
    @DatabaseTable(inheritanceType = InheritanceMappingType.CLASS_TABLE)
    class A{
        @Id
        @DatabaseField
        public int AInt;

        @DatabaseField
        public String AString;

        @DatabaseField
        public double ADouble;
    }

    @Getter
    @Setter
    @DatabaseTable(inheritanceType = InheritanceMappingType.CLASS_TABLE)
    class B extends A{
        @Id
        @DatabaseField
        public int BInt;

        @DatabaseField
        public String BString;

    }

    @Getter
    @Setter
    @DatabaseTable(inheritanceType = InheritanceMappingType.CLASS_TABLE)
    class C {
        @Id
        @DatabaseField
        public int CInt;

        @DatabaseField
        public float CFloat;

    }

}
