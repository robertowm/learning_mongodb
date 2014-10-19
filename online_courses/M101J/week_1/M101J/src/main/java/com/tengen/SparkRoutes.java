package com.tengen;

import spark.Spark;

/**
 *
 * @author robertowm
 */
public class SparkRoutes {

    public static void main(String[] args) {
        Spark.get("/", (req, res) -> "Hello World\n");
        Spark.get("/test", (req, res) -> "This is a test page\n");
        Spark.get("/echo/:thing", (req, res) -> req.params(":thing"));
    }
    
}
