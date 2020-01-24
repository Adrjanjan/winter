package pl.design.patterns.winter.domain.singletable;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import pl.design.patterns.winter.annotations.DatabaseField;
import pl.design.patterns.winter.annotations.DatabaseTable;
import pl.design.patterns.winter.inheritance.InheritanceMappingType;

@Getter
@Setter
@ToString(callSuper = true)
@DatabaseTable(inheritanceType = InheritanceMappingType.SINGLE_TABLE)
public class SingleC extends SingleB {
    @DatabaseField
    private int intC;

    @DatabaseField
    protected String stringC;

}