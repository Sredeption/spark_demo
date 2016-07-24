package com.sea.dsl

abstract class MyBaseClass extends Script {
    String name

    public void greet() { println "Hello, $name!" }

    public void email(Closure cl) {
        def email = new EmailSpec()
        cl.setDelegate email
        cl.setResolveStrategy Closure.DELEGATE_ONLY
        cl.run()
    }
}


