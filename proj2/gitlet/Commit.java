package gitlet;

import java.io.File;
import java.io.Serializable;
import java.util.Date;

/** Represents a gitlet commit object.
 *  TODO: It's a good idea to give a description here of what else this Class
 *  does at a high level.
 *
 *  @author shadoxx
 */
public class Commit implements Serializable {
    /**
     * TODO: add instance variables here.
     *
     * List all instance variables of the Commit class here with a useful
     * comment above them describing what that variable represents and how that
     * variable is used. We've provided one example for `message`.
     */

    /** The message of this Commit. */
    private String message;
    private Date commitTime;
    private String parent;
    private String FileTreeID;
    private String id;

    public Commit(String message, String parent, Date commitTime, String FileTreeID) {
        this.message = message;
        this.parent = parent;
        this.commitTime = commitTime;
        this.FileTreeID = FileTreeID;
        id = convertHash();
    }

    private String convertHash() {
        return Utils.sha1(
                parent ==  null ? "" : parent,
                message,
                Long.toString(commitTime.getTime()),
                FileTreeID
        );
    }

    public String save() {
        File commitPath = Utils.join(Repository.OBJECT_DIR, "commit_" + id);
        Utils.writeObject(commitPath, Utils.serialize(this));

        return id;
    }
}
