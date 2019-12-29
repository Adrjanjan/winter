package pl.design.patterns.winter;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.design.patterns.winter.annotations.DatabaseField;
import pl.design.patterns.winter.annotations.DatabaseTable;
import pl.design.patterns.winter.annotations.Id;

@Data
@NoArgsConstructor
@AllArgsConstructor
@DatabaseTable
public class UniversityPerson extends Person {

    @Id
    @DatabaseField
    int universityId;

}
