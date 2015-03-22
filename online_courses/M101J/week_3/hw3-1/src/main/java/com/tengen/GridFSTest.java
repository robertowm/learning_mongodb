package com.tengen;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.MongoClient;
import com.mongodb.gridfs.GridFS;
import com.mongodb.gridfs.GridFSDBFile;
import com.mongodb.gridfs.GridFSInputFile;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;

/**
 *
 * @author robertowm
 */
public class GridFSTest {
    public static void main(String[] args) throws UnknownHostException, FileNotFoundException, IOException {
        String filename = "";
        MongoClient client = new MongoClient();
        DB db = client.getDB("course");
        FileInputStream inputStream = null;
        
        GridFS videos = new GridFS(db, "videos"); // returns GridFS bucket named "videos"
        
        try {
            inputStream = new FileInputStream(filename);
        } catch (FileNotFoundException ex) {
            System.out.println("Can't open file.");
            System.exit(1);
        }
        
        GridFSInputFile video = videos.createFile(inputStream, filename);
        
        BasicDBObject meta = new BasicDBObject("description", "Jennifed Singing");
        ArrayList<String> tags =  new ArrayList<>();
        tags.add("Singing");
        tags.add("Opera");
        meta.append("tags", tags);
        
        video.setMetaData(meta);
        video.save();
        
        System.out.println("Object ID in Files Collection: " + video.get("_id"));
        
        System.out.println("Saved the file to MongoDB");
        System.out.println("Now lets read it back out");
        
        GridFSDBFile gridFile = videos.findOne(new BasicDBObject("filename", filename));
        
        FileOutputStream outputStream = new FileOutputStream("recovered_" + filename);
        gridFile.writeTo(outputStream);
        
        System.out.println("Write the file back out");
        
    }
    
}
