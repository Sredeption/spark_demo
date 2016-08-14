package com.sea.dsl.explain;

public class DramaObject {
    private int index;
    private Context context;

    public DramaObject(Context context) {
        this.context = context;
    }

    public Context getContext() {
        return context;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof DramaObject && this.index == ((DramaObject) obj).index;
    }

    @Override
    public int hashCode() {
        return index;
    }

    @Override
    public String toString() {
        return String.format("%s[%d]", this.getClass().getName(), index);
    }
}
