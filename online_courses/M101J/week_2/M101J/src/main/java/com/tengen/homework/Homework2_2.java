package com.tengen.homework;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import java.net.UnknownHostException;

/**
 *
 * @author robertowm
 */
public class Homework2_2 {

    public static void main(String[] args) throws UnknownHostException {
        final DBCollection collection = collection();
        Integer lastId = null;
        try (DBCursor cursor = collection.find(new BasicDBObject("type", "homework"))
                .sort(new BasicDBObject("student_id", 1).append("score", 1))) {
            while(cursor.hasNext()) {
                final DBObject obj = cursor.next();
                final Integer studentId = (Integer) obj.get("student_id");
                if (!studentId.equals(lastId)) {
                    collection.remove(obj);
                }
                lastId = studentId;
            }
        }
    }

    private static DBCollection collection() throws UnknownHostException {
        return new MongoClient()
                .getDB("students")
                .getCollection("grades");
    }
}
