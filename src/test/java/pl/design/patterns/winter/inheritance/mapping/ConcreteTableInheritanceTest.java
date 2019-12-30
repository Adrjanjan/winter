package pl.design.patterns.winter.inheritance.mapping;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import pl.design.patterns.winter.annotations.DatabaseField;
import pl.design.patterns.winter.annotations.DatabaseTable;
import pl.design.patterns.winter.annotations.Id;
import pl.design.patterns.winter.inheritance.mappers.ConcreteTableInheritance;
import pl.design.patterns.winter.inheritance.mappers.InheritanceMapper;
import pl.design.patterns.winter.schemas.ColumnSchema;

import lombok.Data;

class ConcreteTableInheritanceTest {

    @Test
    void map() {
        InheritanceMapper mapper = new ConcreteTableInheritance();

        InheritanceMapping<C> mapping = mapper.map(C.class);

        assertEquals(3, mapping.getHierarchy()
                .size());
    }

    @Test
    void tryFieldSetGet() throws NoSuchFieldException, IllegalAccessException {
        C c = new C();
        c.setC(3);
        c.setCc("cc");

        ColumnSchema f = new ColumnSchema(c.getClass()
                .getDeclaredFields()[0]);

        System.out.println(Arrays.toString(C.class.getDeclaredFields()));
        System.out.println(C.class.getDeclaredField("c")
                .get(c));
        System.out.println(C.class.getDeclaredField("cc")
                .get(c));
        c.getClass()
                .getSuperclass()
                .getSuperclass()
                .getDeclaredField("aa")
                .set(c, "aa");
        System.out.println(C.class.getSuperclass()
                .getSuperclass()
                .getDeclaredField("aa")
                .get(c));
    }

    @Data
    @DatabaseTable(inheritanceMapper = ConcreteTableInheritance.class)
    class A {
        @DatabaseField
        public String aa;

        @Id
        @DatabaseField
        private int a;
    }

    @Data
    @DatabaseTable(inheritanceMapper = ConcreteTableInheritance.class)
    class B extends A {
        @Id
        @DatabaseField
        protected int b;

        @DatabaseField
        String bb;
    }

    @Data
    @DatabaseTable(inheritanceMapper = ConcreteTableInheritance.class)
    class C extends B {
        @DatabaseField
        protected String cc;

        @Id
        @DatabaseField
        private int c;
    }

    @Data
    @DatabaseTable(inheritanceMapper = ConcreteTableInheritance.class)
    class D extends A {
        @Id
        @DatabaseField
        int d;

        @DatabaseField
        String dd;
    }

}
