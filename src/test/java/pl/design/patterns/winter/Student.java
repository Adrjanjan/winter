package pl.design.patterns.winter;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.design.patterns.winter.annotations.DatabaseField;
import pl.design.patterns.winter.annotations.DatabaseTable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@DatabaseTable
public class Student extends UniversityPerson {

    @DatabaseField
    String fieldOfStudy;

}
