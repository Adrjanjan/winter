package pl.design.patterns.winter.domain.classtable;

import lombok.ToString;
import pl.design.patterns.winter.annotations.DatabaseField;
import pl.design.patterns.winter.annotations.DatabaseTable;
import pl.design.patterns.winter.inheritance.InheritanceMappingType;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ToString(callSuper=true)
@DatabaseTable(inheritanceType = InheritanceMappingType.CLASS_TABLE)
public class ClassB extends ClassA {
    @DatabaseField
    String stringB;

    @DatabaseField
    protected int intB;
}
