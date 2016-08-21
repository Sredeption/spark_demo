package com.sea.dsl.explain;

import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import groovy.lang.Script;
import org.apache.spark.rdd.RDD;
import org.apache.spark.sql.Column;
import org.apache.spark.sql.DataFrame;
import org.apache.spark.sql.Row;
import org.codehaus.groovy.control.CompilerConfiguration;
import scala.Function1;

abstract public class Context {
    protected int fp;

    private Script script;
    protected Binding binding;

    public Context(String source) {
        CompilerConfiguration config = new CompilerConfiguration();
        config.setScriptBaseClass(DramaScript.class.getName());
        ClassLoader classLoader = getClass().getClassLoader();
        this.binding = new Binding();
        GroovyShell shell = new GroovyShell(classLoader, binding, config);
        this.script = shell.parse(source);
    }

    public void run() {
        script.run();
    }

    protected void let(String name, DramaObject dramaObject) {
        binding.setVariable(name, dramaObject);
    }

    abstract protected String[] getFields();

    public DataFrame getDataFrame() {
        return null;
    }

    abstract void stepRow(Function1<Row, Row> f);

    abstract void stepRDD(Function1<RDD<Row>, RDD<Row>> f);

}
