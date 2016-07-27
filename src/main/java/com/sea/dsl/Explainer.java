package com.sea.dsl;

import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import groovy.lang.Script;
import org.apache.spark.sql.Row;
import org.codehaus.groovy.control.CompilerConfiguration;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Explainer implements Serializable {

    static private Map<String, Script> script= new HashMap<String, Script>();

    public Explainer(InputStream source) {
        ClassLoader classLoader = getClass().getClassLoader();
        CompilerConfiguration config = new CompilerConfiguration();
        GroovyShell shell = new GroovyShell(classLoader, new Binding(), config);
        script.put("script",shell.parse(new InputStreamReader(source)));
    }

    public void explain(Row row) {
        script.get("script").getBinding().setVariable("row", row);
        script.get("script").run();
    }
}
