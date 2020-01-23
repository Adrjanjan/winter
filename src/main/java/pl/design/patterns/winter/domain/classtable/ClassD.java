package pl.design.patterns.winter.domain.classtable;

import lombok.Getter;
import lombok.Setter;
import pl.design.patterns.winter.annotations.DatabaseField;
import pl.design.patterns.winter.annotations.DatabaseTable;
import pl.design.patterns.winter.inheritance.InheritanceMappingType;

@Getter
@Setter
@DatabaseTable(inheritanceType = InheritanceMappingType.CLASS_TABLE)
class ClassD extends ClassA {
    @DatabaseField
    int intD;

    @DatabaseField
    String stringD;
}