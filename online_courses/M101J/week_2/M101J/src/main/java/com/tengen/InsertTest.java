package com.tengen;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoException;
import java.net.UnknownHostException;
import org.bson.types.ObjectId;

/**
 *
 * @author robertowm
 */
public class InsertTest {

    public static void main(String[] args) throws UnknownHostException {
        MongoClient client = new MongoClient();
        DB db = client.getDB("course");
        DBCollection collection = db.getCollection("insertTest");

        collection.drop();

        DBObject doc = new BasicDBObject().append("x", 1);

        System.out.println("-- Inserting a document --");
        System.out.println(doc);

        collection.insert(doc);

        System.out.println(doc);

        System.out.println("-- Inserting duplicate document --");
        try {
            collection.insert(doc);
        } catch (MongoException me) {
            me.printStackTrace();
        }

        System.out.println("-- Removing document Id and re-inserting --");
        doc.removeField("_id");
        collection.insert(doc);
        System.out.println(doc);

        DBObject docWithId = new BasicDBObject("_id", new ObjectId()).append("x", 2);

        System.out.println("-- Inserting a document with id --");
        System.out.println(docWithId);

        collection.insert(docWithId);

        System.out.println(docWithId);

        collection.drop();

        System.out.println("-- Inserting multiple objects in one operation --");
        collection.insert(new BasicDBObject("y", 1), new BasicDBObject("y", 2));
        for (DBObject dBObject : collection.find()) {
            System.out.println(dBObject);
        }

    }

}
