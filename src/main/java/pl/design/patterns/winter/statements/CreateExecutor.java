package pl.design.patterns.winter.statements;

import pl.design.patterns.winter.inheritance.mapping.InheritanceMapping;

public class CreateExecutor {
    private InheritanceMapping inheritanceMapping;

    public CreateExecutor(InheritanceMapping inheritanceMapping) {
    }

    public boolean tableExists() {
        // todo check in DB if table was already created (matters in SingleTable and ClassTable mappings)
        return false;
    }

    public void create() {
        // create query
        // open connection
        // commit
        // close connection
    }
}
