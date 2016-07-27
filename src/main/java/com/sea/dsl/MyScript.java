package com.sea.dsl;

import groovy.lang.Script;

import java.io.Serializable;

public abstract class MyScript extends Script implements Serializable{

    public MyScript(Script script){
        this.setMetaClass(script.getMetaClass());
        this.setBinding(script.getBinding());
    }

}
