package gitlet;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import static gitlet.Utils.*;

/** Represents a gitlet repository.
 *  TODO: It's a good idea to give a description here of what else this Class
 *  does at a high level.
 *
 *  @author shadoxx
 */
public class Repository {
    /**
     * TODO: add instance variables here.
     *
     * List all instance variables of the Repository class here with a useful
     * comment above them describing what that variable represents and how that
     * variable is used. We've provided two examples for you.
     */

    /** The current working directory. */
    public static final File CWD = new File(System.getProperty("user.dir"));
    /** The .gitlet directory. */
    public static final File GITLET_DIR = join(CWD, ".gitlet");

    public static final File OBJECT_DIR = join(GITLET_DIR, "objects");

    public static final File HEAD_FILE = join(GITLET_DIR, "HEAD");

    public static final File REF_DIR = join(GITLET_DIR, "refs");

    public static final File HEADS_DIR = join(REF_DIR, "heads");

    public static final File INDEX_FILE = join(GITLET_DIR, "index");

    public static boolean exists() {
        return GITLET_DIR.exists();
    }

    public static void init() {
        if (exists()) {
            throw new GitletException("A Gitlet version-control system already exists in the current directory.");
        }

        GITLET_DIR.mkdir();
        OBJECT_DIR.mkdir();
        REF_DIR.mkdir();
        HEADS_DIR.mkdir();
        try {
            HEAD_FILE.createNewFile();
            INDEX_FILE.createNewFile();
        } catch (IOException e) {
            throw new GitletException("IO error occurred");
        }


        FileMap emptyMap = new FileMap();
        String emptyMapID = emptyMap.save();

        Commit initialCommit = new Commit("initial commit", null, new Date(0), emptyMapID);
        String initialCommitID = initialCommit.save();

        Branch masterBranch = new Branch("master", initialCommitID);
        masterBranch.save();

        Utils.writeContents(HEAD_FILE, "ref : refs/heads/master");
    }

}
