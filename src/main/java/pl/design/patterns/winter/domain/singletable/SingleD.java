package pl.design.patterns.winter.domain.singletable;

import lombok.Getter;
import lombok.Setter;
import pl.design.patterns.winter.annotations.DatabaseField;
import pl.design.patterns.winter.annotations.DatabaseTable;
import pl.design.patterns.winter.inheritance.InheritanceMappingType;

@Getter
@Setter
@DatabaseTable(inheritanceType = InheritanceMappingType.SINGLE_TABLE)
class SingleD extends SingleA {
    @DatabaseField
    int intD;

    @DatabaseField
    String stringD;
}