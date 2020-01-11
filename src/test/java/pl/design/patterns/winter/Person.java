package pl.design.patterns.winter;

import pl.design.patterns.winter.annotations.DatabaseField;
import pl.design.patterns.winter.annotations.DatabaseTable;
import pl.design.patterns.winter.annotations.Id;
import pl.design.patterns.winter.inheritance.mappers.SingleTableInheritanceMapper;

@DatabaseTable(inheritanceMapper = SingleTableInheritanceMapper.class)
public class Person {

    @Id
    @DatabaseField
    private String pesel;
    @DatabaseField
    private String name;
    @DatabaseField
    private String surname;

    public Person(String pesel, String name, String surname) {
        this.pesel = pesel;
        this.name = name;
        this.surname = surname;
    }

    public String getPesel() {
        return pesel;
    }

    public void setPesel(String pesel) {
        this.pesel = pesel;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }
}
