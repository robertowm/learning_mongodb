package com.tengen;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import java.io.IOException;
import java.io.StringWriter;
import java.net.UnknownHostException;
import spark.Spark;

/**
 *
 * @author robertowm
 */
public class HelloWorldMongoDBSparkFreemarkerStyle {

    public static void main(String[] args) throws UnknownHostException {
        final Configuration configuration = new Configuration();
        configuration.setClassForTemplateLoading(HelloWorldMongoDBSparkFreemarkerStyle.class, "/");
        
        MongoClient client = new MongoClient(new ServerAddress("localhost", 27017));
        
        DB database = client.getDB("course");
        final DBCollection collection = database.getCollection("hello");
        
        Spark.get("/", (req, res) -> {
            final StringWriter writer = new StringWriter();
            try {
                Template helloTemplate = configuration.getTemplate("hello.ftl");
                
                DBObject document = collection.findOne();

                helloTemplate.process(document, writer);
            } catch (IOException | TemplateException ex) {
                Spark.halt(500);
                ex.printStackTrace();
            }
            return writer;
        });
    }

}
