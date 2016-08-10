package com.sea.dsl.explain;

import groovy.lang.Script;

public abstract class DramaScript extends Script {
    public Variable let(String var) {
        System.out.println("let " + var);
        Context context = (Context) getProperty("context");
        Variable variable = new Variable(var);
        context.putVar(variable);
        return variable;
    }
}
