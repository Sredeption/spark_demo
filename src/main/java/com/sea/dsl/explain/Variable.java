package com.sea.dsl.explain;

public class Variable {
    private String var;
    private Context context;
    private DramaObject dramaObject;

    public Variable(Context context, String var) {
        this.context = context;
        this.var = var;
    }

    public void be(DramaObject dramaObject) {
        this.dramaObject = dramaObject;
        this.context.let(this.var, this.dramaObject);
    }

    public DramaObject getDramaObject() {
        return dramaObject;
    }

    public String getVar() {
        return var;
    }
}
