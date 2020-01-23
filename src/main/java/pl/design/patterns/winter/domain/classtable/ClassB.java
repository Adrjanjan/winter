package pl.design.patterns.winter.domain.classtable;

import lombok.Getter;
import lombok.Setter;
import pl.design.patterns.winter.annotations.DatabaseField;
import pl.design.patterns.winter.annotations.DatabaseTable;
import pl.design.patterns.winter.inheritance.InheritanceMappingType;

@Getter
@Setter
@DatabaseTable(inheritanceType = InheritanceMappingType.CLASS_TABLE)
class ClassB extends ClassA {
    @DatabaseField
    protected int intB;

    @DatabaseField
    String stringB;
}
