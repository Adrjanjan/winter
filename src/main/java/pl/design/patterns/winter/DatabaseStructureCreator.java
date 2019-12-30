package pl.design.patterns.winter;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.stereotype.Component;

import pl.design.patterns.winter.annotations.DatabaseTable;
import pl.design.patterns.winter.dao.Dao;
import pl.design.patterns.winter.dao.DaoCreator;

@Component
public class DatabaseStructureCreator implements CommandLineRunner {


	@Override
	public void run(String... args) throws Exception {
		System.out.println("Finding annotated classes using Spring:");
		new DatabaseStructureCreator().prepareDatabase();
	}

    public void prepareDatabase() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
		List<Class<?>> annotatedClasses = findAnnotatedClasses("pl.design.patterns.winter");
		for (Class<?> clazz : annotatedClasses) {
			// testing purposes only
			printMetadata(clazz);

			DaoCreator.create(clazz);
		}

		List<Dao<?>> daos = DaoCreator.getAll();
		for (Dao<?> dao : daos) {
			dao.createTable();
		}
	}

	private List<Class<?>> findAnnotatedClasses(String scanPackage) {
		ClassPathScanningCandidateComponentProvider provider = createComponentScanner();
		List<Class<?>> ret = new ArrayList<>();
		try {
			for (BeanDefinition beanDef : provider.findCandidateComponents(scanPackage)) {
				Class<?> clazz = Class.forName(beanDef.getBeanClassName());
				ret.add(clazz);
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		return ret;
	}

	private ClassPathScanningCandidateComponentProvider createComponentScanner() {
		ClassPathScanningCandidateComponentProvider provider
				= new ClassPathScanningCandidateComponentProvider(false);
		provider.addIncludeFilter(new AnnotationTypeFilter(DatabaseTable.class));
		return provider;
	}

	// method for testing purposes only
	private void printMetadata(Class<?> cl) {
		try {
			DatabaseTable table = cl.getAnnotation(DatabaseTable.class);
			System.out.printf("Found class: %s, with meta name: %s%n",
					cl.getSimpleName(), table.name());
		} catch (Exception e) {
			System.err.println("Got exception: " + e.getMessage());
		}
	}

}