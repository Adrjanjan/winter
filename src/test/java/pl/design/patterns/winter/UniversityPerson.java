package pl.design.patterns.winter;

import pl.design.patterns.winter.annotations.DatabaseField;
import pl.design.patterns.winter.annotations.DatabaseTable;
import pl.design.patterns.winter.annotations.Id;

@DatabaseTable()
public class UniversityPerson extends Person {

    @Id
    @DatabaseField
    String universityId;

    public UniversityPerson(String universityId, String pesel, String name, String surname) {
        super(pesel, name, surname);
        this.universityId = universityId;
    }
}
