package pl.design.patterns.winter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.stereotype.Component;
import pl.design.patterns.winter.annotations.DatabaseTable;
import pl.design.patterns.winter.inheritance.mappers.InheritanceMapper;
import pl.design.patterns.winter.inheritance.mapping.InheritanceMapping;
import pl.design.patterns.winter.schemas.DatabaseSchema;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

@Component
public class DatabaseStructureCreator implements CommandLineRunner {

    @Autowired
    private DatabaseSchema databaseSchema;

    private static final String SCAN_PACKAGE_NAME = "pl.design.patterns.winter";

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Finding annotated classes using Spring:");
        prepareDatabase();
    }

    public void prepareDatabase() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        List<Class<?>> annotatedClasses = findAnnotatedClasses();
        for (Class<?> clazz : annotatedClasses) {
            // testing purposes only
            printMetadata(clazz);
            // imo tworzenie tabel zostawiłbym już po zrobieniu mapowania dziedziczenia, trzeba by było:
            // 1. for - zrobić mapowania dziedziczenia - zmienia to tabele
            // 2. for - dla każdej tabeli wyciągniętej z
            //


            InheritanceMapper mapper = clazz.getAnnotation(DatabaseTable.class).inheritanceType().getMappingClass().getConstructor(DatabaseSchema.class).newInstance(databaseSchema);
            InheritanceMapping mapping = mapper.map(clazz);
            databaseSchema.addTableSchemas(mapping.getAllTableSchemas());

        }
        System.out.println();

    }

    private List<Class<?>> findAnnotatedClasses() {
        ClassPathScanningCandidateComponentProvider provider = createComponentScanner();
        List<Class<?>> ret = new ArrayList<>();
        try {
            for (BeanDefinition beanDef : provider.findCandidateComponents(SCAN_PACKAGE_NAME)) {
                Class<?> clazz = Class.forName(beanDef.getBeanClassName());
                ret.add(clazz);
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return ret;
    }

    private ClassPathScanningCandidateComponentProvider createComponentScanner() {
        ClassPathScanningCandidateComponentProvider provider = new ClassPathScanningCandidateComponentProvider(false);
        provider.addIncludeFilter(new AnnotationTypeFilter(DatabaseTable.class));
        return provider;
    }

    // method for testing purposes only
    private void printMetadata(Class<?> cl) {
        try {
            DatabaseTable table = cl.getAnnotation(DatabaseTable.class);
            System.out.printf("Found class: %s, with meta name: %s%n", cl.getSimpleName(), table.name());
        } catch (Exception e) {
            System.err.println("Got exception: " + e.getMessage());
        }
    }

}