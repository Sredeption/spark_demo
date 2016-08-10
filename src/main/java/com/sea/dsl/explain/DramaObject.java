package com.sea.dsl.explain;

public class DramaObject {
    private String obj;

    public DramaObject(String obj) {
        this.obj = obj;
    }

    public DramaObject plus(DramaObject dramaObject) {
        System.out.printf("DramaObject::plus(%s ,%s)%n", obj, dramaObject.obj);
        return this;
    }

    public DramaObject plus(Variable dramaObject) {
        System.out.println("DramaObject::plus");
        return this;
    }

    @Override
    public String toString() {
        return obj;
    }
}
