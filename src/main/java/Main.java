import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import database.MongoDbHandler;
import git.GitCreator;
import model.Commit;
import org.bson.Document;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.modelmapper.ModelMapper;
import shared.CommitConverter;
import shared.CommitDifferencesExtractor;
import shared.CommitExtractor;

public class Main {

    public static void main(String[] args) throws IOException, GitAPIException {
        final Logger logger = Logger.getLogger(Main.class.getName());
        final String repo = args[0];
        final String mongoHost = args[1];
        final String dbName = args[2];
        final String portNumber = args[3];
        final String uri = "mongodb://" + mongoHost + ":" + portNumber + "/" + dbName;
        final MongoDbHandler mongoDbHandler = new MongoDbHandler();
        final MongoDatabase db = mongoDbHandler.connect(uri, dbName);
        MongoCollection<Document> commitCollection = db.getCollection("commits");
        final Git git = GitCreator.createLocalGitInstance(repo);
        final CommitConverter commitConverter = new CommitConverter(new ModelMapper(), new CommitDifferencesExtractor());
        final CommitExtractor commitExtractor = new CommitExtractor(git, commitConverter, new CommitDifferencesExtractor());
        logger.log(Level.INFO, "Extract commits...");
        final List<Commit> commitList = commitExtractor.extract(false);
        mongoDbHandler.insertCommits(commitList, commitCollection);
    }
}
