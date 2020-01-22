package pl.design.patterns.winter.domain.abcd;

import lombok.Getter;
import lombok.Setter;
import pl.design.patterns.winter.annotations.DatabaseField;
import pl.design.patterns.winter.annotations.DatabaseTable;
import pl.design.patterns.winter.annotations.Id;
import pl.design.patterns.winter.inheritance.InheritanceMappingType;

@Getter
@Setter
@DatabaseTable(inheritanceType = InheritanceMappingType.CONCRETE_TABLE)
//Klasa do testowania
class D extends A {
    @Id
    @DatabaseField
    int intD;

    @DatabaseField
    String stringD;
}