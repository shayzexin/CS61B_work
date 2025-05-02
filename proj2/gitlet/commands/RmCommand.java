package gitlet.commands;

public class RmCommand implements Command{
    @Override
    public void execute(String[] args) {

    }

    @Override
    public boolean requiresRepo() {
        return true;
    }

    @Override
    public boolean isArgumentIllegal(String[] args) {
        return false;
    }
}
