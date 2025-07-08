package gitlet.commands;

import gitlet.Commit;
import gitlet.Repository;
import gitlet.Utils;

import java.util.List;

public class GlobalLogCommand implements Command{
    @Override
    public void execute(String[] args) {
        List<String> files = Utils.plainFilenamesIn(Repository.COMMIT_DIR);

        if (files == null) {
            return;
        }

        for (String id : files) {
            Commit c = Commit.getFromId(id);
            printCommit(c);
        }
    }

    private void printCommit(Commit c) {
        System.out.println("===");
        System.out.println("commit " + c.getId());
        System.out.println("Date: " + c.getDate());
        System.out.println(c.getMessage());
        System.out.println();
    }

    @Override
    public boolean requiresRepo() {
        return true;
    }

    @Override
    public boolean isArgumentIllegal(String[] args) {
        return args.length != 1;
    }
}
