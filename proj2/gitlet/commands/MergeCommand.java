package gitlet.commands;

public class MergeCommand implements Command{
    @Override
    public void execute(String[] args) {
        System.out.println("Not implement");
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
