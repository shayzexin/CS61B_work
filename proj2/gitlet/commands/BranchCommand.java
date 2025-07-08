package gitlet.commands;

import gitlet.Commit;
import gitlet.Repository;
import gitlet.Utils;

import java.io.File;
import java.io.IOException;

public class BranchCommand implements Command{
    @Override
    public void execute(String[] args) {
        String branchName = args[1];
        File branchFile = Utils.join(Repository.REF_DIR, branchName);

        if (branchFile.exists()) {
            System.out.println("A branch with that name already exists.");
            System.exit(0);
        }
        try {
            branchFile.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Utils.writeContents(branchFile, Commit.getCurrentCommit().getId());
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
