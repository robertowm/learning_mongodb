package com.tengen.homework;

import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import java.net.UnknownHostException;
import java.util.List;

/**
 *
 * @author robertowm
 */
public class Homework3_1 {

    public static void main(String[] args) throws UnknownHostException {
        final DBCollection collection = collection();
        try (DBCursor cursor = collection.find()) {
            while(cursor.hasNext()) {
                final DBObject obj = cursor.next();
                DBObject lowestHomework = null;
                Double lowestScore = Double.MAX_VALUE;
                List<DBObject> scores = (List<DBObject>) obj.get("scores");
                for (DBObject score : scores) {
                    final String type = (String) score.get("type");
                    final Double value = (Double) score.get("score");
                    if (type.equals("homework")) {
                        if (lowestScore > value) {
                            lowestHomework = score;
                            lowestScore = value;
                        }
                    }
                }
                if (lowestHomework != null) {
                    scores.remove(lowestHomework);
                    collection.save(obj);
                }
            }
        }
    }

    private static DBCollection collection() throws UnknownHostException {
        return new MongoClient()
                .getDB("school")
                .getCollection("students");
    }
}
