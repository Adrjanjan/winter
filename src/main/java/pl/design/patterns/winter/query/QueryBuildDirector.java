package pl.design.patterns.winter.query;

import java.lang.reflect.InvocationTargetException;

public class QueryBuildDirector<T> {

    private QueryBuilder queryBuilder;

    private T object;

    public QueryBuildDirector(QueryBuilder queryBuilder) {
        this.queryBuilder = queryBuilder;
    }

    public QueryBuildDirector withObject(T object) {
        this.object = object;
        return this;
    }

    public String build() throws InvocationTargetException, IllegalAccessException {
        return queryBuilder.withObject(object)
                .createOperation()
                .setTable()
                .setFields()
                .setValues()
                .withCondition()
                .compose()
                .generate();
    }

}
