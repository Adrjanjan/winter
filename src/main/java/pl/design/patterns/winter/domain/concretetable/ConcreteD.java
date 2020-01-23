package pl.design.patterns.winter.domain.concretetable;

import lombok.Getter;
import lombok.Setter;
import pl.design.patterns.winter.annotations.DatabaseField;
import pl.design.patterns.winter.annotations.DatabaseTable;
import pl.design.patterns.winter.inheritance.InheritanceMappingType;

@Getter
@Setter
@DatabaseTable(inheritanceType = InheritanceMappingType.CONCRETE_TABLE)
class ConcreteD extends ConcreteA {
    @DatabaseField
    int intD;

    @DatabaseField
    String stringD;
}