package pl.design.patterns.winter.domain.concretetable;

import pl.design.patterns.winter.annotations.DatabaseField;
import pl.design.patterns.winter.annotations.DatabaseTable;
import pl.design.patterns.winter.inheritance.InheritanceMappingType;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@DatabaseTable(inheritanceType = InheritanceMappingType.CONCRETE_TABLE)
public class ConcreteD extends ConcreteA {
    @DatabaseField
    int intD;

    @DatabaseField
    String stringD;
}