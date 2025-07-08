package gitlet.commands;

import gitlet.*;

import java.io.File;
import java.util.*;

public class StatusCommand implements Command{
    @Override
    public void execute(String[] args) {
        printBranches();
        System.out.println();
        printStagedFiles();
        System.out.println();
        printRemovedFiles();
        System.out.println();
        printModifications();
        System.out.println();
        printUntrackedFiles();
    }

    private void printBranches() {
        System.out.println("=== Branches ===");
        String currentHead = Branch.getCurrentHead();

        List<String> branches = Utils.plainFilenamesIn(Repository.REF_DIR);
        Deque<String> output = new ArrayDeque<>();

        if (branches == null) {
            throw new GitletException("No branch in this repository.");
        }

        for (String branch : branches) {
            if (branch.equals(currentHead)) {
                output.addFirst("*" + branch);
                continue;
            }
            output.addLast(branch);
        }

        for (String branch : output) {
            System.out.println(branch);
        }
    }

    private void printStagedFiles() {
        System.out.println("=== Staged Files ===");

        Set<String> stagedFiles = Stage.getStage().getAdditions().keySet();

        for (String file : stagedFiles) {
            System.out.println(file);
        }
    }

    private void printRemovedFiles() {
        System.out.println("=== Removed Files ===");

        Set<String> removedFiles = Stage.getStage().getRemovals();

        for (String file : removedFiles) {
            System.out.println(file);
        }
    }

    private void printModifications() {
        System.out.println("=== Modifications Not Staged For Commit ===");

        Map<String, String> trackedFiles = Commit.getCurrentCommit().getTrackedFile();
        Set<String> removals = Stage.getStage().getRemovals();
        Set<String> additions = Stage.getStage().getAdditions().keySet();
        for (Map.Entry<String, String> entry : trackedFiles.entrySet()) {
            String fileName = entry.getKey();
            File file = Utils.join(Repository.CWD, fileName);
            if (file.exists()) {
                if (Utils.sha1(Utils.readContentsAsString(file)).equals(entry.getValue()) && !additions.contains(fileName)) {
                    System.out.println(fileName + " (modified)");
                }
            } else {
                if (!removals.contains(fileName)) {
                    System.out.println(fileName + " (deleted)");
                }
            }
        }
    }

    private void printUntrackedFiles() {
        System.out.println("=== Untracked Files ===");

        Set<String> trackedFiles = Commit.getCurrentCommit().getTrackedFile().keySet();
        Set<String> additions = Stage.getStage().getAdditions().keySet();
        List<String> files = Utils.plainFilenamesIn(Repository.CWD);
        if (files == null) {
            return;
        }
        for (String file : files) {
            if (!trackedFiles.contains(file) && !additions.contains(file)) {
                System.out.println(file);
            }
        }
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
