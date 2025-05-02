package gitlet.commands;

import gitlet.Repository;

public class InitCommand implements Command{

    @Override
    public void execute(String[] args) {
        Repository.init();
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
