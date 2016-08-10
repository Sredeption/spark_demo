package com.sea.dsl.explain;

public class Variable {
    private String var;
    private DramaObject dramaObject;

    public Variable(String var) {
        this.var = var;
    }

    public void be(DramaObject dramaObject) {
        this.dramaObject = dramaObject;
        System.out.println("be::" + dramaObject);
    }

    public DramaObject getDramaObject() {
        return dramaObject;
    }

    public String getVar() {
        return var;
    }
}
