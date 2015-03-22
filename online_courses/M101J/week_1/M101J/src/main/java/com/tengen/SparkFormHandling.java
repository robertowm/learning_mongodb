package com.tengen;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import spark.Spark;

/**
 *
 * @author robertowm
 */
public class SparkFormHandling {

    public static void main(String[] args) {
        final Configuration configuration = new Configuration();
        configuration.setClassForTemplateLoading(SparkFormHandling.class, "/");

        Spark.get("/", (req, res) -> {
            final StringWriter writer = new StringWriter();
            try {
                final Map<String, Object> fruitsMap = new HashMap<>();
                fruitsMap.put("fruits", Arrays.asList("apple", "orange", "banana", "peach"));

                final Template fruitPickerTemplate = configuration.getTemplate("fruitPicker.ftl");
                fruitPickerTemplate.process(fruitsMap, writer);
            } catch (IOException | TemplateException ex) {
                Spark.halt(500);
                ex.printStackTrace();
            }
            return writer;
        });

        Spark.post("/favorite_fruit", (req, res) -> {
            final String fruit = req.queryParams("fruit");
            if (fruit == null) {
                return "Why don't you pick one?";
            }
            return "Your favorite fruit is " + fruit;
        });
    }

}
