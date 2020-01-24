package pl.design.patterns.winter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import pl.design.patterns.winter.dao.Dao;
import pl.design.patterns.winter.domain.classtable.ClassC;
import pl.design.patterns.winter.domain.concretetable.ConcreteC;
import pl.design.patterns.winter.domain.concretetable.ConcreteD;
import pl.design.patterns.winter.domain.singletable.SingleC;

@SpringBootApplication
public class WinterApplication {

	public static void main(String[] args) {

		SpringApplication.run(WinterApplication.class, args);

        ConcreteC concreteC = new ConcreteC();
        concreteC.setIntA(1);
        concreteC.setStringA("string a");
        concreteC.setStringB("string b");
        concreteC.setIntC(3);

        Dao<ConcreteC> concreteCDao = OrmManager.getDao(ConcreteC.class);

        concreteCDao.insert(concreteC);
        concreteCDao.findAll().forEach(System.out::println);


        ClassC classC = new ClassC();
        classC.setIntA(1);
        classC.setStringA("string a");
        classC.setStringB("string b");
        classC.setIntC(3);

        Dao<ClassC> classCDao = OrmManager.getDao(ClassC.class);

        classCDao.insert(classC);
        classCDao.findAll().forEach(System.out::println);


        SingleC singleC = new SingleC();
        singleC.setIntA(1);
        singleC.setIntB(2);
        singleC.setStringA("string a");
        singleC.setStringB("string b");
        singleC.setIntC(3);

        Dao<SingleC> singleCDao = OrmManager.getDao(SingleC.class);

        singleCDao.insert(singleC);
        singleCDao.findAll().forEach(System.out::println);
        System.out.println(singleCDao.findById(1));


        ConcreteD concreteD = new ConcreteD();
        concreteD.setIntA(1);
        concreteD.setIntD(4);
        concreteD.setStringA("string a");
        concreteD.setStringD("string d");

        Dao<ConcreteD> concreteDDao = OrmManager.getDao(ConcreteD.class);

        concreteDDao.insert(concreteD);
        concreteDDao.findAll().forEach(System.out::println);




	}
}
