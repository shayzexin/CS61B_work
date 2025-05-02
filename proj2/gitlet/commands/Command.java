package gitlet.commands;

public interface Command {
    void execute(String[] args);
    boolean requiresRepo();
    boolean isArgumentIllegal(String[] args);
}
