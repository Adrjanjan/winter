package pl.design.patterns.winter;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
class WinterApplicationTests {

	@Test
	void SimpleInheritanceMappingTest() {
        // WinterDataSource ds = new WinterDataSource(/*PARAMETERS*/);
        //
        // Dao<Student> studentDao = (Dao<Student>) OrmManager.getDao(Student.class);
        //
        // Student addedStudent = new Student("Computer Science", "222222", "12345678910", "On", "On");
        // studentDao.add(addedStudent);
        //
        // Student studentFromDb = studentDao.findById("12345678910");
        //
        // assertEquals(studentFromDb, addedStudent);
	}

}
