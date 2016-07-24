package com.sea.dsl;

import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import groovy.lang.Script;
import org.apache.spark.sql.Row;
import org.codehaus.groovy.control.CompilerConfiguration;

import java.io.InputStream;
import java.io.Serializable;

public class Explainer implements Serializable {

    private Script script;

    public Explainer(InputStream source) {
        ClassLoader classLoader = getClass().getClassLoader();
        CompilerConfiguration config = new CompilerConfiguration();
        GroovyShell shell = new GroovyShell(classLoader, new Binding(), config);
        script = shell.parse(source);
    }

    public void explain(Row row) {
        script.getBinding().setVariable("row", row);
        script.run();
    }
}
