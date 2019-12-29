package pl.design.patterns.winter;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import pl.design.patterns.winter.dao.Dao;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class WinterApplicationTests {

	@Test
	void SimpleInheritanceMappingTest() {
		WinterDataSource ds = new WinterDataSource(/*PARAMETERS*/);

        Dao<Student> studentDao = OrmManager.getDao(Student.class);

        Student addedStudent = new Student();
        addedStudent.setFieldOfStudy("Computer Science");
        addedStudent.setUniversityId(222);
        addedStudent.setPesel("12345678910");
        addedStudent.setFirstName("Jan");
        addedStudent.setLastName("Kowalski");

        studentDao.insert(addedStudent);

        Student studentFromDb = studentDao.findById(1);

		assertEquals(studentFromDb, addedStudent);
	}

}
