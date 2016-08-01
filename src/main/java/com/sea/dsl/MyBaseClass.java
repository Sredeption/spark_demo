package com.sea.dsl;

import groovy.lang.Closure;
import groovy.lang.Script;

abstract public class MyBaseClass extends Script {
    public String name;

    public void greet(String name) {
        System.out.println("Hello, " + name + "!");
    }

    public void email(Closure cl) {
        EmailSpec email = new EmailSpec();
        cl.setDelegate(email);
        cl.setResolveStrategy(Closure.DELEGATE_ONLY);
        cl.run();
    }
}
