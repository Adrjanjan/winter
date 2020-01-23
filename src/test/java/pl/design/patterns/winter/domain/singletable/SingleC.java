package pl.design.patterns.winter.domain.singletable;

import pl.design.patterns.winter.annotations.DatabaseField;
import pl.design.patterns.winter.annotations.DatabaseTable;
import pl.design.patterns.winter.inheritance.InheritanceMappingType;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@DatabaseTable(inheritanceType = InheritanceMappingType.SINGLE_TABLE)
public class SingleC extends SingleB {
    @DatabaseField
    private int intC;

    @DatabaseField
    protected String stringC;

}