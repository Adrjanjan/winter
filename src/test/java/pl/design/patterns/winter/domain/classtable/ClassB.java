package pl.design.patterns.winter.domain.classtable;

import pl.design.patterns.winter.annotations.DatabaseField;
import pl.design.patterns.winter.annotations.DatabaseTable;
import pl.design.patterns.winter.inheritance.InheritanceMappingType;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@DatabaseTable(inheritanceType = InheritanceMappingType.CLASS_TABLE)
public class ClassB extends ClassA {
    @DatabaseField
    String stringB;

    @DatabaseField
    protected int intB;
}
