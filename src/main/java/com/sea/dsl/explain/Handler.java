package com.sea.dsl.explain;

import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import groovy.lang.Script;
import org.codehaus.groovy.control.CompilerConfiguration;

import java.io.InputStream;
import java.util.Scanner;

public class Handler {
    private Script script;

    public Handler(String source) {
        CompilerConfiguration config = new CompilerConfiguration();
        config.setScriptBaseClass(DramaScript.class.getName());
        ClassLoader classLoader = getClass().getClassLoader();
        Binding binding = new Binding();
        binding.setVariable("left", new LeftTuple());
        binding.setVariable("right", new RightTuple());
        GroovyShell shell = new GroovyShell(classLoader, binding, config);
        script = shell.parse(source);
        script.setProperty("context", new Context(null, null));
    }

    public void run() {
        script.run();
    }


    public static void main(String[] args) {
        InputStream inputStream = Handler.class.getClassLoader().getResourceAsStream("exp1.groovy");
        Scanner scanner = new Scanner(inputStream);
        String source = "";
        while (scanner.hasNext()) {
            source += scanner.nextLine();
        }

        Handler handler = new Handler(source);
        handler.run();
    }
}
