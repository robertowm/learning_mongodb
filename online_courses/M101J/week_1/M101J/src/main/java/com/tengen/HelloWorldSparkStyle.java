package com.tengen;

import spark.Spark;

/**
 *
 * @author robertowm
 */
public class HelloWorldSparkStyle {
    public static void main(String[] args) {
        Spark.get("/", (req, res) -> "Hello World From Spark");
    }
    
}
