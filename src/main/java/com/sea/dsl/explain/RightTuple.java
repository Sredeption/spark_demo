package com.sea.dsl.explain;

public class RightTuple implements Tuple {
    public DramaObject getAt(String field) {
        return new DramaObject("rightTable_" + field);
    }
}
