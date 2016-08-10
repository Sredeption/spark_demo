package com.sea.dsl.explain;

public class LeftTuple implements Tuple {
    public DramaObject getAt(String field) {
        return new DramaObject("leftTable_" + field);
    }
}
