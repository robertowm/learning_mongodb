package com.tengen;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoException;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.List;
import org.bson.types.ObjectId;

/**
 *
 * @author robertowm
 */
public class UpdateTest {

    public static void main(String[] args) throws UnknownHostException {
        DBCollection collection = createCollection();

        List<String> names = Arrays.asList("alice", "bobby", "cathy", "david", "ethan");
        names.stream().forEach((name) -> {
            collection.insert(new BasicDBObject("_id", name));
        });
        
        collection.update(new BasicDBObject("_id", "alice"),
                new BasicDBObject("age", 24));
        
        collection.update(new BasicDBObject("_id", "alice"),
                new BasicDBObject("$set", new BasicDBObject("gender", "F")));
        
        collection.update(new BasicDBObject("_id", "frank"),
                new BasicDBObject("$set", new BasicDBObject("gender", "M")), true, false);
        
        collection.update(new BasicDBObject(),
                new BasicDBObject("$set", new BasicDBObject("title", "Dr.")), false, true);
////////  Remove all documents
//        collection.remove(new BasicDBObject());
        
        collection.remove(new BasicDBObject("_id", "alice"));
        
        printCollection(collection);
    }

    private static DBCollection createCollection() throws UnknownHostException {
        MongoClient client = new MongoClient();
        DB db = client.getDB("course");
        DBCollection collection = db.getCollection("updateTest");
        collection.drop();
        return collection;
    }

    private static void printCollection(DBCollection collection) {
        try (DBCursor cursor = collection.find()) {
            while(cursor.hasNext()) {
                System.out.println(cursor.next());
            }
        }
    }

}
