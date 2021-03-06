package pl.design.patterns.winter.domain.singletable;

import lombok.ToString;
import pl.design.patterns.winter.annotations.DatabaseField;
import pl.design.patterns.winter.annotations.DatabaseTable;
import pl.design.patterns.winter.inheritance.InheritanceMappingType;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ToString(callSuper=true) @DatabaseTable(inheritanceType = InheritanceMappingType.SINGLE_TABLE)
public class SingleB extends SingleA {
    @DatabaseField
    protected int intB;

    @DatabaseField
    String stringB;
}
