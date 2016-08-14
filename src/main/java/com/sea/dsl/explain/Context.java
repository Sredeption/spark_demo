package com.sea.dsl.explain;

import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import groovy.lang.Script;
import org.apache.spark.rdd.RDD;
import org.apache.spark.sql.DataFrame;
import org.apache.spark.sql.Row;
import org.codehaus.groovy.control.CompilerConfiguration;
import scala.Function1;

import java.util.*;

abstract public class Context {
    Map<String, DramaObject> variables;
    protected int fp;

    private Stack<List<DramaObject>> scopes;
    private Script script;
    private Binding binding;

    public Context(String source) {
        this.scopes = new Stack<List<DramaObject>>();
        this.variables = new HashMap<String, DramaObject>();
        newScope();
        CompilerConfiguration config = new CompilerConfiguration();
        config.setScriptBaseClass(DramaScript.class.getName());
        ClassLoader classLoader = getClass().getClassLoader();
        this.binding = new Binding();
        this.binding.setProperty("context", this);
        GroovyShell shell = new GroovyShell(classLoader, binding, config);
        this.script = shell.parse(source);
    }

    public void run() {
        script.run();
    }

    protected void let(String name, DramaObject object) {
        System.out.printf("let %s be %s%n", name, object);
        variables.put(name, object);
        binding.setVariable(name, object);
    }

    public List<DramaObject> newScope() {
        List<DramaObject> objects = new ArrayList<DramaObject>();
        scopes.push(objects);
        return objects;
    }

    public List<DramaObject> leaveScope() {
        List<DramaObject> objects = scopes.pop();
        fp -= objects.size();
        List<String> removeKeys = new ArrayList<String>();
        for (DramaObject object : objects) {
            for (Map.Entry<String, DramaObject> entry : variables.entrySet()) {
                if (object.equals(entry.getValue())) {
                    removeKeys.add(entry.getKey());
                    break;
                }
            }
        }

        for (String key : removeKeys) {
            variables.remove(key);
            binding.setVariable(key, null);
        }

        return objects;
    }

    private void newObject(DramaObject object) {
        object.setIndex(fp);
        fp++;
        scopes.peek().add(object);
    }

    public DramaInt newInt() {
        DramaInt dramaInt = new DramaInt(this);
        this.newObject(dramaInt);
        return dramaInt;
    }

    public DramaLong newLong() {
        DramaLong dramaLong = new DramaLong(this);
        this.newObject(dramaLong);
        return dramaLong;
    }

    public DramaDouble newDouble() {
        DramaDouble dramaDouble = new DramaDouble(this);
        this.newObject(dramaDouble);
        return dramaDouble;
    }

    public DramaString newString() {
        DramaString dramaString = new DramaString(this);
        this.newObject(dramaString);
        return dramaString;
    }

    public DramaBoolean newBoolean() {
        DramaBoolean dramaBoolean = new DramaBoolean(this);
        this.newObject(dramaBoolean);
        return dramaBoolean;
    }

    abstract protected String[] getFields();

    public DataFrame getDataFrame() {
        return null;
    }

    abstract void stepRow(Function1<Row, Row> f);

    abstract void stepRDD(Function1<RDD<Row>, RDD<Row>> f);

    abstract void explain(DramaBoolean dramaBoolean, String content);
}
