package utils;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

public class DataBaseUtils {

    private MongoDatabase mongoDatabase;
    private MongoClient mongoClient;

    public DataBaseUtils(String uri) {
        mongoClient = MongoClients.create(uri);
        this.mongoDatabase = mongoClient.getDatabase("estim");
    }

    public Document getUserByUserName(String username){
        MongoCollection<Document> users = mongoDatabase.getCollection("users");
        return users.find(new Document("username", username)).first();
    }

    public void close(){
        if (mongoClient != null){
            mongoClient.close();
        }
    }
}
