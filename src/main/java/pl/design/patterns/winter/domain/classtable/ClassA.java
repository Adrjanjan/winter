package pl.design.patterns.winter.domain.classtable;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import pl.design.patterns.winter.annotations.DatabaseField;
import pl.design.patterns.winter.annotations.DatabaseTable;
import pl.design.patterns.winter.annotations.Id;
import pl.design.patterns.winter.inheritance.InheritanceMappingType;

@Getter
@Setter
@ToString
@DatabaseTable(inheritanceType = InheritanceMappingType.CLASS_TABLE)
public class ClassA {
    @DatabaseField
    public String stringA;

    @Id
    @DatabaseField
    private int intA;
}