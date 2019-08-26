package database;

import java.util.ArrayList;
import java.util.List;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import model.Commit;
import org.bson.Document;
import org.eclipse.jgit.revwalk.RevCommit;

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

    public void insertRevCommits(final Iterable<RevCommit> revCommitList, final MongoCollection<Document> collection){
        final List<Document> documents = new ArrayList<>();
        for (final RevCommit commit : revCommitList) {
            final Document document = new Document("commitID", commit.getName())
                    .append("author-name",commit.getAuthorIdent().getName())
                    .append("author-email",commit.getAuthorIdent().getEmailAddress())
                    .append("commiter-name", commit.getCommitterIdent().getName())
                    .append("commiter-email", commit.getCommitterIdent().getEmailAddress())
                    .append("FullMessage", commit.getFullMessage())
                    .append("time", commit.getCommitterIdent().getTimeZone().getDisplayName())
                    .append("date", commit.getCommitterIdent().getWhen())
                    .append("isMerge", commit.getParents().length > 2)
                    .append("short-message", commit.getShortMessage());
            documents.add(document);
        }
        collection.insertMany(documents);
    }

}
