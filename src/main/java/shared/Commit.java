package shared;

import java.io.Serializable;
import java.util.List;

import org.eclipse.jgit.lib.PersonIdent;


public class Commit implements Serializable {

    private String name;

    private PersonIdent author;

    private PersonIdent committer;

    private List<String> paths;

    private List<String> oldPaths;

    private String fullMessage;

    private boolean isMerged;

    public Commit(final String name, final List<String> paths) {
        this.name = name;
        this.paths = paths;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public PersonIdent getAuthor() {
        return author;
    }

    public void setAuthor(final PersonIdent author) {
        this.author = author;
    }

    public PersonIdent getCommitter() {
        return committer;
    }

    public void setCommitter(final PersonIdent committer) {
        this.committer = committer;
    }

    public List<String> getPaths() {
        return paths;
    }

    public void setPaths(final List<String> paths) {
        this.paths = paths;
    }

    public List<String> getOldPaths() {
        return oldPaths;
    }

    public void setOldPaths(final List<String> oldPaths) {
        this.oldPaths = oldPaths;
    }

    public String getFullMessage() {
        return fullMessage;
    }

    public void setFullMessage(final String fullMessage) {
        this.fullMessage = fullMessage;
    }

    public boolean isMerged() {
        return isMerged;
    }

    public void setMerged(final boolean merged) {
        isMerged = merged;
    }
}
