package database;

import java.util.ArrayList;
import java.util.List;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import model.Commit;
import org.bson.Document;

public class MongoDbHandler {

    public MongoDatabase connect(final String clientUrl, final String dbName) {
        final MongoClientURI uri = new MongoClientURI(clientUrl);
        final MongoClient mongoClient = new MongoClient(uri);
        return mongoClient.getDatabase(dbName);
    }

    public void insertCommits(final List<Commit> commitList, final MongoCollection<Document> collection) {
        final List<Document> documents = new ArrayList<>();
        for (final Commit commit : commitList) {
            final Document document = new Document("commitID", commit.getName())
                    .append("Author", commit.getAuthor().getEmailAddress())
                    .append("Commiter", commit.getCommitter().getEmailAddress())
                    .append("FullMessage", commit.getFullMessage())
                    .append("IsMerged", commit.isMerged())
                    .append("Commited files", commit.getPaths())
                    .append("Deleted files", commit.getOldPaths());

            documents.add(document);
        }
        collection.insertMany(documents);
    }

}
