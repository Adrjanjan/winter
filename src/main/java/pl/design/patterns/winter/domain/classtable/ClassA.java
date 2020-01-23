package pl.design.patterns.winter.domain.classtable;

import lombok.Getter;
import lombok.Setter;
import pl.design.patterns.winter.annotations.DatabaseField;
import pl.design.patterns.winter.annotations.DatabaseTable;
import pl.design.patterns.winter.annotations.Id;
import pl.design.patterns.winter.inheritance.InheritanceMappingType;

@Getter
@Setter
@DatabaseTable(inheritanceType = InheritanceMappingType.CLASS_TABLE)
class ClassA {
    @DatabaseField
    public String stringA;

    @Id
    @DatabaseField
    private int intA;
}