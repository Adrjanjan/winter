package pl.design.patterns.winter.domain.classtable;

import lombok.ToString;
import pl.design.patterns.winter.annotations.DatabaseField;
import pl.design.patterns.winter.annotations.DatabaseTable;
import pl.design.patterns.winter.inheritance.InheritanceMappingType;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ToString(callSuper=true) @DatabaseTable(inheritanceType = InheritanceMappingType.CLASS_TABLE)
public class ClassD extends ClassA {
    @DatabaseField
    String stringD;

    @DatabaseField
    int intD;
}