package pl.design.patterns.winter.domain.singletable;

import pl.design.patterns.winter.annotations.DatabaseField;
import pl.design.patterns.winter.annotations.DatabaseTable;
import pl.design.patterns.winter.annotations.Id;
import pl.design.patterns.winter.inheritance.InheritanceMappingType;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@DatabaseTable(inheritanceType = InheritanceMappingType.SINGLE_TABLE)
public class SingleA {
    @Id
    @DatabaseField
    private int intA;

    @DatabaseField
    public String stringA;
}