package pl.design.patterns.winter.domain.singletable;

import lombok.Getter;
import lombok.Setter;
import pl.design.patterns.winter.annotations.DatabaseField;
import pl.design.patterns.winter.annotations.DatabaseTable;
import pl.design.patterns.winter.annotations.Id;
import pl.design.patterns.winter.inheritance.InheritanceMappingType;

@Getter
@Setter
@DatabaseTable(inheritanceType = InheritanceMappingType.SINGLE_TABLE)
class SingleA {
    @DatabaseField
    public String stringA;

    @Id
    @DatabaseField
    private int intA;
}