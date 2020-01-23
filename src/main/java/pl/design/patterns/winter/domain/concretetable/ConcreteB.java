package pl.design.patterns.winter.domain.concretetable;

import lombok.Getter;
import lombok.Setter;
import pl.design.patterns.winter.annotations.DatabaseField;
import pl.design.patterns.winter.annotations.DatabaseTable;
import pl.design.patterns.winter.inheritance.InheritanceMappingType;

@Getter
@Setter
@DatabaseTable(inheritanceType = InheritanceMappingType.CONCRETE_TABLE)
class ConcreteB extends ConcreteA {
    @DatabaseField
    protected int intB;

    @DatabaseField
    String stringB;
}
