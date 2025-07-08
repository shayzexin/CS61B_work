package gitlet.commands;

import gitlet.Commit;

public class LogCommand implements Command{
    @Override
    public void execute(String[] args) {
        Commit current = Commit.getCurrentCommit();

        while (current != null) {
            printCommit(current);
            current = Commit.getFromId(current.getParent());
        }
    }

    private void printCommit(Commit c) {
        System.out.println("===");
        System.out.println("commit " + c.getId());

        if (c.getSecondParent() != null) {
            String firstParent = c.getParent();
            String secondParent = c.getSecondParent();
            System.out.println("Merge: " + firstParent + " " + secondParent);
        }

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
