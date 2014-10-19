package com.tengen;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;
import spark.Spark;

/**
 *
 * @author robertowm
 */
public class HelloWorldSparkFreemarkerStyle {

    public static void main(String[] args) {
        final Configuration configuration = new Configuration();
        configuration.setClassForTemplateLoading(HelloWorldSparkFreemarkerStyle.class, "/");
        
        Spark.get("/", (req, res) -> {
            final StringWriter writer = new StringWriter();
            try {
                Template helloTemplate = configuration.getTemplate("hello.ftl");
                
                Map<String, Object> helloMap = new HashMap<>();
                helloMap.put("name", "Freemarker");

                helloTemplate.process(helloMap, writer);
            } catch (IOException | TemplateException ex) {
                Spark.halt(500);
                ex.printStackTrace();
            }
            return writer;
        });
    }

}
