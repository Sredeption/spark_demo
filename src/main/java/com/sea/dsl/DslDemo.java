package com.sea.dsl;

import java.io.InputStream;
import java.util.Scanner;
import groovy.lang.GroovyClassLoader;

public class DslDemo {

    public static void main(String[] args) throws IllegalAccessException, InstantiationException {
        InputStream inputStream = DslDemo.class.getClassLoader().getResourceAsStream("demo.groovy");
        Scanner scanner = new Scanner(inputStream);
        String source = "";
        while (scanner.hasNext()) {
            source += scanner.nextLine()+"\n";
        }
        System.out.println(source);
        ClassLoader classLoader= DslDemo.class.getClassLoader();
        GroovyClassLoader groovyClassLoader= new GroovyClassLoader(classLoader);
        Class groovyClass = groovyClassLoader.parseClass(source);
        IFoo foo = (IFoo) groovyClass.newInstance();
        System.out.println(foo.run(Integer.valueOf(args[0])));
    }
}
