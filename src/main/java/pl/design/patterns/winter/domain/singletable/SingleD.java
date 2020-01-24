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
public class SingleD extends SingleA {
    @DatabaseField
    int intD;

    @DatabaseField
    String stringD;
}