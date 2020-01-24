package pl.design.patterns.winter.domain.concretetable;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import pl.design.patterns.winter.annotations.DatabaseField;
import pl.design.patterns.winter.annotations.DatabaseTable;
import pl.design.patterns.winter.inheritance.InheritanceMappingType;

@Getter
@Setter
@ToString(callSuper = true)
@DatabaseTable(inheritanceType = InheritanceMappingType.CONCRETE_TABLE)
public class ConcreteB extends ConcreteA {
    @DatabaseField
    protected int intB;

    @DatabaseField
    String stringB;
}
