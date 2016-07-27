package com.sea.dsl;

import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import org.codehaus.groovy.control.CompilerConfiguration;
import org.codehaus.groovy.control.customizers.ImportCustomizer;

import java.io.InputStream;
import java.util.Scanner;

public class DslDemo {

    public static void main(String[] args) throws IllegalAccessException, InstantiationException {
        InputStream inputStream = DslDemo.class.getClassLoader().getResourceAsStream("exp1.groovy");
        Scanner scanner = new Scanner(inputStream);
        String source = "";
        while (scanner.hasNext()) {
            source += scanner.nextLine() + "\n";
        }
        ClassLoader classLoader = DslDemo.class.getClassLoader();
        CompilerConfiguration config = new CompilerConfiguration();
        config.setScriptBaseClass("com.sea.dsl.MyBaseClass");
        ImportCustomizer icz=new ImportCustomizer();
        icz.addImports("groovy.time.TimeCategory");
        config.addCompilationCustomizers(icz);
        GroovyShell shell = new GroovyShell(classLoader, new Binding(), config);
        shell.parse(source).run();
    }
}
