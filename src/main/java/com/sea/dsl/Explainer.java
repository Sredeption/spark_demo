package com.sea.dsl;

import com.sea.dsl.explain.Tuple;
import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import groovy.lang.Script;
import org.apache.spark.sql.Row;
import org.codehaus.groovy.control.CompilerConfiguration;

import java.io.InputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Explainer implements Serializable {

    static private Map<String, Script> script = new HashMap<String, Script>();

    public Explainer(InputStream source) throws IllegalAccessException, InstantiationException {
        Scanner scanner = new Scanner(source);
        String str = "";
        while (scanner.hasNext()) {
            str += scanner.nextLine();
        }
        ClassLoader classLoader = getClass().getClassLoader();
        CompilerConfiguration config = new CompilerConfiguration();
        config.setScriptBaseClass(MyBaseClass.class.getName());
        GroovyShell shell = new GroovyShell(classLoader, new Binding(), config);
        script.put("script", shell.parse(str));
    }

    public void explain(Row row) {
        script.get("script").getBinding().setVariable("row", new Tuple(row));
        script.get("script").run();
    }
}
