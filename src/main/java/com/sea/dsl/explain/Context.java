package com.sea.dsl.explain;

import org.apache.spark.sql.DataFrame;

import java.util.ArrayList;
import java.util.List;

public class Context {
    private List<String> leftColumns, rightColumns;
    private List<Variable> variables;
    private DataFrame dataFrame;

    public Context(List<String> left, List<String> right) {
        this.leftColumns = left;
        this.rightColumns = right;
        this.variables = new ArrayList<Variable>();
    }

    public void setDataFrame(DataFrame dataFrame) {
        this.dataFrame = dataFrame;
    }

    public List<String> getLeftColumns() {
        return leftColumns;
    }

    public List<String> getRightColumns() {
        return rightColumns;
    }

    public List<Variable> getVarColumns() {
        return variables;
    }

    public int leftIndexOf(String column) {
        return leftColumns.indexOf(column);
    }

    public int rightIndexOf(String column) {
        return rightColumns.indexOf(column);
    }

    public void putVar(Variable variable){
        variables.add(variable);
    }

    

}
