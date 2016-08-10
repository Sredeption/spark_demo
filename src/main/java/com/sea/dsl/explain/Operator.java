package com.sea.dsl.explain;

import org.apache.spark.sql.DataFrame;

public interface Operator {
    
    DataFrame operate(DataFrame dataFrame);
}
