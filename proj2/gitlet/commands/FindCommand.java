package gitlet.commands;

import gitlet.Commit;
import gitlet.Repository;
import gitlet.Utils;
import java.util.List;

public class FindCommand implements Command{
    @Override
    public void execute(String[] args) {
        String message = args[1];
        boolean isExist = false;

        List<String> list = Utils.plainFilenamesIn(Repository.COMMIT_DIR);
        if (list == null) {
            System.out.println("Found no commit with that message.");
            System.exit(0);
        }

        for (String id : list) {
            Commit c = Commit.getFromId(id);
            if (message.equals(c.getMessage())) {
                isExist = true;
                System.out.println(id);
            }
        }

        if (!isExist) {
            System.out.println("Found no commit with that message.");
        }

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
