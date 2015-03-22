package com.tengen;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.util.JSON;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.UnknownHostException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author robertowm
 */
public class ImportTweets {

    public static void main(String[] args) throws IOException, ParseException {
        final String screenName = args.length > 0 ? args[0] : "MongoDB";

        List<DBObject> tweets = getLastestTweets(screenName);

        MongoClient client = new MongoClient();
        DBCollection tweetsCollection = client.getDB("course").getCollection("twitter");
//        tweetsCollection.drop();

        for (DBObject cur : tweets) {
            cur.put("screen_name", screenName);
            massageTweetId(cur);
            massageTweet(cur);
            tweetsCollection.update(new BasicDBObject("_id", cur.get("_id")), cur, true, false);
        }

        System.out.println("Tweet count: " + tweetsCollection.count());

        client.close();
    }

    private static List<DBObject> getLastestTweets(String screenName) throws IOException {
        URL url = new URL("http://api.twitter.com/1/statuses/user_timeline.json?screen_name="
                + screenName + "&include_rts=1");

        InputStream is = url.openStream();

        ByteArrayOutputStream os = new ByteArrayOutputStream();
        int retVal;
        while ((retVal = is.read()) != -1) {
            os.write(retVal);
        }

        final String tweetsString = os.toString();

        return (List<DBObject>) JSON.parse(tweetsString);
    }

    private static void massageTweetId(final DBObject cur) {
        Object id = cur.get("id");
        cur.removeField("id");
        cur.put("_id", id);
    }

    private static void massageTweet(final DBObject cur) throws ParseException {
        SimpleDateFormat fmt = new SimpleDateFormat("EEE MMM d H:m:s Z y");
        cur.put("created_at", fmt.parse((String) cur.get("created_at")));

        DBObject userDoc = (DBObject) cur.get("user");
        Iterator<String> keyIterator = userDoc.keySet().iterator();
        while (keyIterator.hasNext()) {
            String key = keyIterator.next();
            switch (key) {
                case "id":
                case "name":
                case "screen_name":
                    break;
                default:
                    keyIterator.remove();
            }
        }
    }

}
