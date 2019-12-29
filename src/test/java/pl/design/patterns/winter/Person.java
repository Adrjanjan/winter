package pl.design.patterns.winter;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.design.patterns.winter.annotations.DatabaseField;
import pl.design.patterns.winter.annotations.DatabaseTable;
import pl.design.patterns.winter.annotations.Id;
import pl.design.patterns.winter.inheritance.mapping.SingleTableInheritance;

@Data
@NoArgsConstructor
@AllArgsConstructor
@DatabaseTable(inheritanceMapper = SingleTableInheritance.class)
public class Person {

    @Id(generated = true)
    @DatabaseField
    int id;

    @DatabaseField
    String pesel;

    @DatabaseField
    String firstName;

    @DatabaseField
    String lastName;

}
