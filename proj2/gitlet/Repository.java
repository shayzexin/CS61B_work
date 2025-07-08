package gitlet;

import java.io.File;
import static gitlet.Utils.*;

public class Repository {
    public static final File CWD = new File(System.getProperty("user.dir"));
    public static final File GITLET_DIR = join(CWD, ".gitlet");
    public static final File COMMIT_DIR = join(GITLET_DIR, "commits");
    public static final File BLOB_DIR = join(GITLET_DIR, "bolbs");
    public static final File HEAD_FILE = join(GITLET_DIR, "HEAD");
    public static final File REF_DIR = join(GITLET_DIR, "refs");
//    public static final File HEADS_DIR = join(REF_DIR, "heads");
    public static final File STAGE_FILE = join(GITLET_DIR, "stage");

    public static boolean exists() {
        return GITLET_DIR.exists();
    }
}
