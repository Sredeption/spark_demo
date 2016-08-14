package com.sea.dsl.explain;

import groovy.lang.Script;

public abstract class DramaScript extends Script {

    private Context context() {
        return (Context) getBinding().getProperty("context");
    }

    public Variable let(String var) {
        return new Variable(context(), var);
    }

    public DramaObject left(String name) {
        return context().variables.get("leftTable_" + name);
    }

    public DramaObject right(String name) {
        return context().variables.get("rightTable_" + name);
    }

    public Condition when(DramaBoolean dramaBoolean) {
        return new Condition(context(), dramaBoolean);
    }
}
