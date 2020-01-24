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

        testConcrete();

        testSingle();

        testClass();

        boolean dropTablesAfterExecute = true;
        if (dropTablesAfterExecute) {
            context.getBean(DropTablesExecutor.class)
                    .dropTables();
        }
    }

    private static void testConcrete() {
        System.out.println("TEST CONCRETE");

        Dao<ConcreteC> concreteCDao = OrmManager.getDao(ConcreteC.class);

        ConcreteC concreteC = new ConcreteC();
        concreteC.setIntA(1);
        concreteC.setStringA("string a");
        concreteC.setStringB("string b");
        concreteC.setIntC(3);

        concreteCDao.insert(concreteC);
        ConcreteC concreteCFromDb = concreteCDao.findById(concreteC.getIntA());
        System.out.println("concreteC: " + concreteC);
        System.out.println("concreteCFromDb: " + concreteCFromDb);


        Dao<ConcreteD> concreteDDao = OrmManager.getDao(ConcreteD.class);

        ConcreteD concreteD = new ConcreteD();
        concreteD.setIntA(1);
        concreteD.setIntD(4);
        concreteD.setStringD("string d");

        concreteDDao.insert(concreteD);

        ConcreteD concreteDFromDb = concreteDDao.findById(concreteD.getIntA());
        System.out.println("concreteD: " + concreteD);
        System.out.println("concreteDFromDb: " + concreteDFromDb);
        concreteD.setStringA("string a");
        concreteDDao.update(concreteD);
        ConcreteD concreteDFromDbAfterUpdate = concreteDDao.findById(concreteD.getIntA());
        System.out.println("concreteDFromDbAfterUpdate: " + concreteDFromDbAfterUpdate);

    }

    private static void testSingle() {
        System.out.println("TEST SINGLE");

        Dao<SingleC> singleCDao = OrmManager.getDao(SingleC.class);

        SingleC singleC1 = new SingleC();
        singleC1.setIntA(1);
        singleC1.setIntB(2);
        singleC1.setStringA("string a1");
        singleC1.setStringB("string b1");
        singleC1.setIntC(3);

        SingleC singleC2 = new SingleC();
        singleC2.setIntA(2);
        singleC2.setIntB(4);
        singleC2.setStringA("string a2");
        singleC2.setStringB("string b2");
        singleC2.setIntC(6);

        singleCDao.insert(singleC1);
        singleCDao.insert(singleC2);

        singleCDao.findAll().forEach(s -> System.out.println("singleCFromDb: " + s));

    }

    private static void testClass() {
        System.out.println("TEST CLASS");

        Dao<ClassC> classCDao = OrmManager.getDao(ClassC.class);

        ClassC classC = new ClassC();
        classC.setIntA(1);
        classC.setStringA("string a");
        classC.setStringB("string b");
        classC.setIntC(3);

        classCDao.insert(classC);
        classCDao.delete(classC);

        System.out.println("Obiektow ClassC w bazie jest: " + classCDao.findAll().size());

    }
}
