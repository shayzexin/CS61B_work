package gitlet.commands;

import gitlet.Branch;
import gitlet.Repository;
import gitlet.Utils;

import java.io.File;

public class RmBranchCommand implements Command{
    @Override
    public void execute(String[] args) {
        String branchName = args[1];
        File branchFile = Utils.join(Repository.REF_DIR, branchName);

        if (!branchFile.exists()) {
            System.out.println("A branch with that name does not exist.");
            System.exit(0);
        }

        String currentBranch = Branch.getCurrentHead();
        if (currentBranch.equals(branchName)) {
            System.out.println("Cannot remove the current branch.");
            System.exit(0);
        }

        branchFile.delete();
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
