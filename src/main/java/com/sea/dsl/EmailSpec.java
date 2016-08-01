package com.sea.dsl;

public class EmailSpec {
    void from(String from) {
        System.out.println(from);
    }

    void to(String... to) {
    }

    void subject(String subject) {
    }

}
