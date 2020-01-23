package pl.design.patterns.winter.domain.concretetable;

import lombok.Getter;
import lombok.Setter;
import pl.design.patterns.winter.annotations.DatabaseField;
import pl.design.patterns.winter.annotations.DatabaseTable;
import pl.design.patterns.winter.annotations.Id;
import pl.design.patterns.winter.inheritance.InheritanceMappingType;

@Getter
@Setter
@DatabaseTable(inheritanceType = InheritanceMappingType.CONCRETE_TABLE)
class ConcreteA {
    @DatabaseField
    public String stringA;

    @Id
    @DatabaseField
    private int intA;
}