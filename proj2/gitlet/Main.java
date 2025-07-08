package gitlet;

import gitlet.commands.*;
import java.util.Map;

import static java.util.Map.entry;

/** Driver class for Gitlet, a subset of the Git version-control system.
 *  @author shadoxx
 */
public class Main {
    private static final Map<String, Command> COMMANDS = Map.ofEntries(
            entry("init", new InitCommand()),
            entry("add", new AddCommand()),
            entry("commit", new CommitCommand()),
            entry("rm", new RmCommand()),
            entry("log", new LogCommand()),
            entry("global-log", new GlobalLogCommand()),
            entry("find", new FindCommand()),
            entry("status", new StatusCommand()),
            entry("checkout", new CheckoutCommand()),
            entry("branch", new BranchCommand()),
            entry("rm-branch", new RmBranchCommand()),
            entry("reset", new ResetCommand()),
            entry("merge", new MergeCommand())
    );

    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Please enter a command.");
            System.exit(0);
        }

        String commandName = args[0];
        Command command = COMMANDS.get(commandName);

        if (command == null) {
            System.out.println("No command with that name exists.");
            System.exit(0);
        }

        if (command.isArgumentIllegal(args)) {
            System.out.println("Incorrect operands.");
            System.exit(0);
        }

        if (command.requiresRepo() && !Repository.exists()) {
            System.out.println("Not in an initialized Gitlet directory.");
            System.exit(0);
        }

        try {
            command.execute(args);
        } catch (GitletException e) {
            System.out.println(e.getMessage());
            System.exit(0);
        }
    }
}
