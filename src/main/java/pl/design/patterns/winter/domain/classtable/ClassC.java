package pl.design.patterns.winter.domain.classtable;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import pl.design.patterns.winter.annotations.DatabaseField;
import pl.design.patterns.winter.annotations.DatabaseTable;
import pl.design.patterns.winter.inheritance.InheritanceMappingType;

@Getter
@Setter
@ToString(callSuper = true)
@DatabaseTable(inheritanceType = InheritanceMappingType.CLASS_TABLE)
public class ClassC extends ClassB {
    @DatabaseField
    protected String stringC;

    @DatabaseField
    private int intC;
}