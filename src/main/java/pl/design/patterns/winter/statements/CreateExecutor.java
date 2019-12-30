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
        StringBuilder createQuery = new StringBuilder("CREATE TABLE ");
        createQuery.append(inheritanceMapping.getTableName())
                .append('(');

        for(var columns : inheritanceMapping.getColumns())
                createQuery.append()


        var column = columnMap.get(key.next());
        query.append(column.getName()).append(" ").append(column.getType());
        if(!column.isNullable())
            query.append(" not null");

        while(key.hasNext()){
            query.append(",\n");
            column = columnMap.get(key.next());
            query.append(column.getName()).append(" ").append(column.getType());
            if(!column.isNullable())
                query.append(" not null");
        }

        query.append(",\nprimary key(").append(mapper.getPrimaryKey().getColumnScheme().getName()).append(")");

        if(!mapper.getForeignKeys().isEmpty()){
            for(var fkey : mapper.getForeignKeys()){

                query.append(",\nforeign key(")
                        .append(fkey.getColumnScheme().getName())
                        .append(") ")
                        .append("references ")
                        .append(fkey.getReferences());
            }
        }
        query.append(");");
        System.out.println(query);
        return query.toString();

        // open connection
        // commit
        // close connection
    }
}
