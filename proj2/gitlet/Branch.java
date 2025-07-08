package gitlet;

import java.io.File;
import java.util.Arrays;

public class Branch {
    public static void save(String name, String commitId) {
        File path = Utils.join(Repository.REF_DIR, name);
        Utils.writeContents(path, commitId);
    }

    public static String getCommitFromBranch(String name) {
        File path = Utils.join(Repository.REF_DIR, name);
        return Utils.readContentsAsString(path);
    }

    public static String getCurrentHead() {
        return Utils.readContentsAsString(Repository.HEAD_FILE);
    }

    public static void setCurrentHead(String name) {
        Utils.writeContents(Repository.HEAD_FILE, name);
    }

    public static void setHeadCommitOfBranch(File file, String commitId) {
        Utils.writeContents(file, commitId);
    }
}
