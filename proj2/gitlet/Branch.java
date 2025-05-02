package gitlet;

import java.io.File;

public class Branch {
    private String name;
    private String furtherCommitId;
    private String id;

    public Branch(String name, String furtherCommitId) {
        this.name = name;
        this.furtherCommitId = furtherCommitId;
        this.id = getID();
    }

    private String getID() {
        return name + ":" + furtherCommitId;
    }

    public String save() {
        File branchPath = Utils.join(Repository.HEADS_DIR, name);
        Utils.writeContents(branchPath, furtherCommitId);

        return id;
    }
}
