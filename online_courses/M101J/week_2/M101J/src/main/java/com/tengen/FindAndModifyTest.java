package com.tengen;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author robertowm
 */
public class FindAndModifyTest {

    public static void main(String[] args) throws UnknownHostException {
        DBCollection collection = createCollection();

        final String counterId = "abc";
        int first;
        int numNeeded;
        
        numNeeded = 2;
        first = getRange(counterId, numNeeded, collection);
        System.out.println("Range: " + first + "-" + (first + numNeeded - 1));
        
        numNeeded = 3;
        first = getRange(counterId, numNeeded, collection);
        System.out.println("Range: " + first + "-" + (first + numNeeded - 1));
        
        numNeeded = 10;
        first = getRange(counterId, numNeeded, collection);
        System.out.println("Range: " + first + "-" + (first + numNeeded - 1));
        
        System.out.println();
        
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

    private static int getRange(String id, int range, DBCollection collection) {
        DBObject doc = collection.findAndModify(
                new BasicDBObject("_id", id), null, null, false,
                new BasicDBObject("$inc", new BasicDBObject("counter", range)),
                true, true);
        return (int) doc.get("counter") - range + 1;
    }

}
