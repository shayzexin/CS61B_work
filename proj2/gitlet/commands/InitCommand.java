package gitlet.commands;

import gitlet.*;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;

public class InitCommand implements Command{

    @Override
    public void execute(String[] args) throws GitletException{
        if (Repository.exists()) {
            System.out.println("A Gitlet version-control system already exists in the current directory.");
            return;
        }

        Repository.GITLET_DIR.mkdir();
        Repository.COMMIT_DIR.mkdir();
        Repository.BLOB_DIR.mkdir();
        Repository.REF_DIR.mkdir();

        try {
            Repository.HEAD_FILE.createNewFile();
            Repository.STAGE_FILE.createNewFile();
        } catch (IOException e) {
            throw new GitletException(e);
        }

        Commit initialCommit = new Commit("initial commit", null, new Date(0)
                , new HashMap<>());
        initialCommit.save();

        String initialCommitId = initialCommit.getId();
        Branch.save("master", initialCommitId);

        Branch.setCurrentHead("master");

        Stage.initStage();
    }

    @Override
    public boolean requiresRepo() {
        return false;
    }

    @Override
    public boolean isArgumentIllegal(String[] args) {
        return args.length > 1;
    }
}
