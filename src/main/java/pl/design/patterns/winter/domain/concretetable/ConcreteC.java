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
public class ConcreteC extends ConcreteB {
    @DatabaseField
    private int intC;

    @DatabaseField
    protected String stringC;
}