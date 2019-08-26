import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import database.MongoDbHandler;
import git.GitCreator;
import org.bson.Document;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.revwalk.RevCommit;

public class Main {

    public static void main(String[] args) throws IOException, GitAPIException {
        final Logger logger = Logger.getLogger(Main.class.getName());
        final String repo = args[0];
        final String mongoHost = args[1];
        final String dbName = args[2];
        final String portNumber = args[3];
        final String collectionName = args[4];
        final String uri = "mongodb://" + mongoHost + ":" + portNumber + "/" + dbName;
        final MongoDbHandler mongoDbHandler = new MongoDbHandler();
        final MongoDatabase db = mongoDbHandler.connect(uri, dbName);
        MongoCollection<Document> commitCollection = db.getCollection(collectionName);
        BasicDBObject document = new BasicDBObject();
        commitCollection.deleteMany(document);
        final Git git = GitCreator.createLocalGitInstance(repo);
        final Iterable<RevCommit> revCommits = git.log().all().call();
        logger.log(Level.INFO, "Extract commits...");
        mongoDbHandler.insertRevCommits(revCommits, commitCollection);
    }
}
