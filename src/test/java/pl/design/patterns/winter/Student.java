package pl.design.patterns.winter;

import pl.design.patterns.winter.annotations.DatabaseField;
import pl.design.patterns.winter.annotations.DatabaseTable;
import pl.design.patterns.winter.inheritance.mapping.SingleTableInheritance;

@DatabaseTable(inheritanceMapper = SingleTableInheritance.class)
public class Student extends UniversityPerson {

    @DatabaseField
    String fieldOfStudy;

    public Student( String fieldOfStudy, String universityId, String pesel, String name, String surname) {
        super(universityId, pesel, name, surname);
        this.fieldOfStudy = fieldOfStudy;
    }

    public String getFieldOfStudy() {
        return fieldOfStudy;
    }

    public void setFieldOfStudy(String fieldOfStudy) {
        this.fieldOfStudy = fieldOfStudy;
    }
}
