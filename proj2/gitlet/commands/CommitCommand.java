package gitlet.commands;

import gitlet.*;

import java.io.File;
import java.util.Date;
import java.util.Map;

public class CommitCommand implements Command{
    @Override
    public void execute(String[] args) {
        String message = args[1];

        if (message.trim().isEmpty()) {
            System.out.println("Please enter a commit message.");
            return;
        }

        Stage stage = Stage.getStage();
        if (stage.isEmpty()) {
            System.out.println("No changes added to the commit.");
            return;
        }

        Commit currentCommit = Commit.getCurrentCommit();
        String currentCommitId = currentCommit.getId();
        Map<String, String> updatedTrackedFiles = currentCommit.updateTrackedFile();

        Commit commit = new Commit(message, currentCommitId,new Date(), updatedTrackedFiles);
        commit.save();

        String newCommitId = commit.getId();
        File file = Utils.join(Repository.REF_DIR, Utils.readContentsAsString(Repository.HEAD_FILE));
        Branch.setHeadCommitOfBranch(file, newCommitId);

        Stage.initStage();
    }

    @Override
    public boolean requiresRepo() {
        return true;
    }

    @Override
    public boolean isArgumentIllegal(String[] args) {
        return args.length != 2;
    }
}
