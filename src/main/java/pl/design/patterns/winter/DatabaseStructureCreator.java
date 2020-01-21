package pl.design.patterns.winter;

import lombok.extern.apachecommons.CommonsLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.stereotype.Component;
import pl.design.patterns.winter.annotations.DatabaseTable;
import pl.design.patterns.winter.inheritance.mappers.InheritanceMapper;
import pl.design.patterns.winter.inheritance.mapping.InheritanceMapping;
import pl.design.patterns.winter.schemas.DatabaseSchema;
import pl.design.patterns.winter.statements.CreateTableExecutor;
import pl.design.patterns.winter.statements.DropTablesExecutor;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

@CommonsLog
@Component
public class DatabaseStructureCreator implements CommandLineRunner {

    @Autowired
    private DatabaseSchema databaseSchema;

    @Autowired
    private CreateTableExecutor createExecutor;

    @Autowired
    private DropTablesExecutor dropExecutor;

    @Value("${winter.drop-tables}")
    private boolean dropTables;

    @Value("${winter.scan-package-name}")
    private String scanPackageName;

    @Override
    public void run(String... args) throws Exception {
        prepareDatabase();
    }

    private void prepareDatabase() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        List<Class<?>> annotatedClasses = findAnnotatedClasses();
        for (Class<?> clazz : annotatedClasses) {

            InheritanceMapper mapper = clazz.getAnnotation(DatabaseTable.class).inheritanceType().getMappingClass().getConstructor(DatabaseSchema.class).newInstance(databaseSchema);
            InheritanceMapping mapping = mapper.map(clazz);
            databaseSchema.addTableSchemas(mapping.getAllTableSchemas());

        }

        if (dropTables) {
            dropExecutor.dropTables();
        }

        databaseSchema.getAllTables().forEach(tableSchema -> createExecutor.createTable(tableSchema));
    }

    private List<Class<?>> findAnnotatedClasses() {
        log.info("Szukam klas oznaczonych adnotacja w paczce: " + scanPackageName);
        ClassPathScanningCandidateComponentProvider provider = createComponentScanner();
        List<Class<?>> ret = new ArrayList<>();
        try {
            for (BeanDefinition beanDef : provider.findCandidateComponents(scanPackageName)) {
                Class<?> clazz = Class.forName(beanDef.getBeanClassName());
                ret.add(clazz);
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        log.info(String.format("Znalazlem %d oznaczonych klas", ret.size()));
        return ret;
    }

    private ClassPathScanningCandidateComponentProvider createComponentScanner() {
        ClassPathScanningCandidateComponentProvider provider = new ClassPathScanningCandidateComponentProvider(false);
        provider.addIncludeFilter(new AnnotationTypeFilter(DatabaseTable.class));
        return provider;
    }
}