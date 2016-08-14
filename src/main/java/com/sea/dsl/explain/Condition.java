package com.sea.dsl.explain;

public class Condition {
    private Context context;
    private DramaBoolean dramaBoolean;

    public Condition(Context context, DramaBoolean dramaBoolean) {
        this.context = context;
        this.dramaBoolean = dramaBoolean;
    }

    public void explain(String content) {
        context.explain(dramaBoolean, content);
    }

}
