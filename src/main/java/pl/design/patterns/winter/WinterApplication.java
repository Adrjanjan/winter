package pl.design.patterns.winter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import pl.design.patterns.winter.dao.Dao;
import pl.design.patterns.winter.domain.classtable.ClassC;
import pl.design.patterns.winter.domain.concretetable.ConcreteC;
import pl.design.patterns.winter.domain.concretetable.ConcreteD;
import pl.design.patterns.winter.domain.singletable.SingleC;
import pl.design.patterns.winter.statements.executors.DropTablesExecutor;

@SpringBootApplication
public class WinterApplication {

    public static void main(String[] args) {

        final var context = SpringApplication.run(WinterApplication.class, args);

        ConcreteC concreteC = new ConcreteC();
        concreteC.setIntA(1);
        concreteC.setStringA("string a");
        concreteC.setStringB("string b");
        concreteC.setIntC(3);

        Dao<ConcreteC> concreteCDao = OrmManager.getDao(ConcreteC.class);

        concreteCDao.insert(concreteC);
        concreteC.setStringA("new string a");
        concreteCDao.update(concreteC);
        concreteCDao.findAll()
                .forEach(System.out::println);
        System.out.println(concreteCDao.findById(1));
        concreteCDao.deleteById(concreteC.getIntA());

        ClassC classC = new ClassC();
        classC.setIntA(1);
        classC.setStringA("string a");
        classC.setStringB("string b");
        classC.setIntC(3);

        Dao<ClassC> classCDao = OrmManager.getDao(ClassC.class);

        classCDao.insert(classC);
        classC.setStringA("new string a");
        classCDao.update(classC);
        classCDao.findAll().forEach(System.out::println);
        System.out.println(classCDao.findById(1));
        classCDao.deleteById(classC.getIntA());

        SingleC singleC = new SingleC();
        singleC.setIntA(1);
        singleC.setIntB(2);
        singleC.setStringA("string a");
        singleC.setStringB("string b");
        singleC.setIntC(3);

        Dao<SingleC> singleCDao = OrmManager.getDao(SingleC.class);

        singleCDao.insert(singleC);
        singleC.setStringA("new string a");
        singleCDao.update(singleC);
        singleCDao.findAll()
                .forEach(System.out::println);
        System.out.println(singleCDao.findById(1));
        singleCDao.deleteById(singleC.getIntA());

        ConcreteD concreteD = new ConcreteD();
        concreteD.setIntA(1);
        concreteD.setIntD(4);
        concreteD.setStringA("string a");
        concreteD.setStringD("string d");

        Dao<ConcreteD> concreteDDao = OrmManager.getDao(ConcreteD.class);

        concreteDDao.insert(concreteD);
        concreteDDao.findAll()
                .forEach(System.out::println);

        if ( context.getBean(DatabaseStructureCreator.class)
                .isDropTables() ) {
            context.getBean(DropTablesExecutor.class)
                    .dropTables();
        }
    }
}
