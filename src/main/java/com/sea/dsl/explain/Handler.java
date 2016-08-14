package com.sea.dsl.explain;

import org.apache.spark.sql.DataFrame;

import java.io.InputStream;
import java.util.Scanner;

public class Handler {
    private String source;

    public Handler() {
        InputStream inputStream = Handler.class.getClassLoader().getResourceAsStream("exp1.groovy");
        Scanner scanner = new Scanner(inputStream);
        source = "";
        while (scanner.hasNext()) {
            source += scanner.nextLine();
        }
    }

    public void handle(DataFrame dataFrame) {
        new DramaContext(source, dataFrame).run();
    }

    public static void main(String[] args) {


    }
}
