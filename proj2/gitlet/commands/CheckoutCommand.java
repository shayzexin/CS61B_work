package gitlet.commands;

import gitlet.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class CheckoutCommand implements Command{
    @Override
    public void execute(String[] args) {
        int length = args.length;
        switch (length) {
            case 2:
                twoArgumentExecute(args[1]);
                break;
            case 3:
                threeArgumentExecute(args[2]);
                break;
            case 4:
                fourArgumentExecute(args[1], args[3]);
                break;
        }
    }

    private void twoArgumentExecute(String branch) {
        File branchFile = Utils.join(Repository.REF_DIR, branch);
        if (!branchFile.exists()) {
            System.out.println("No such branch exist");
            System.exit(0);
        }

        if (branch.equals(Branch.getCurrentHead())) {
            System.out.println("No need to checkout the current branch.");
            System.exit(0);
        }

        Commit headCommit =
                Commit.getFromId(Utils.readContentsAsString(Utils.join(Repository.REF_DIR, branch)));
        Map<String, String> headCommitTrackedFiles =
                headCommit.getTrackedFile();

        Set<String> currentCommitTrackedFiles =
                Commit.getCurrentCommit().getTrackedFile().keySet();
        Set<String> additions = Stage.getStage().getAdditions().keySet();
        List<String> cwdFiles = Utils.plainFilenamesIn(Repository.CWD);


        if (cwdFiles == null) {
            List<String> deletedFiles = new ArrayList<>();

            for (String file : cwdFiles) {
                if (!currentCommitTrackedFiles.contains(file) && !additions.contains(file)) {
                    if (headCommitTrackedFiles.containsKey(file)) {
                        System.out.println("There is an untracked file in the " +
                                "way; delete it, or add and commit it first.");
                        System.exit(0);
                    }
                    continue;
                }
                deletedFiles.add(file);
            }

            for (String deletedFile : deletedFiles) {
                Utils.join(Repository.CWD, deletedFile).delete();
            }
        }

        for (Map.Entry<String, String> entry : headCommitTrackedFiles.entrySet()) {
            File file = Utils.join(Repository.CWD, entry.getKey());
            try {
                file.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            byte[] content = Blob.getBlob(entry.getValue()).getContent();
            Utils.writeContents(file, content);
        }

        Branch.setCurrentHead(branch);
        Stage.initStage();
    }

    private void threeArgumentExecute(String fileName) {
        Commit currentCommit = Commit.getCurrentCommit();
        Map<String, String> trackedFile = currentCommit.getTrackedFile();

        if (!trackedFile.containsKey(fileName)) {
            System.out.println("File does not exist in that commit.");
            System.exit(0);
        }

        Blob blob = Blob.getBlob(trackedFile.get(fileName));
        byte[] content = blob.getContent();
        Utils.writeContents(Utils.join(Repository.CWD, fileName), content);
    }

    private void fourArgumentExecute(String commitId, String fileName) {
        List<String> commitIds = Utils.plainFilenamesIn(Repository.COMMIT_DIR);

        if (commitIds == null) {
            throw new GitletException("no commit in this repository");
        }

        boolean commitIsExist = false;
        Commit commit = null;
        int length = commitId.length();

        for (String id : commitIds) {
            if (id.substring(0, length).equals(commitId)) {
                commitIsExist = true;
                commit = Commit.getFromId(id);
                break;
            }
        }

        if (!commitIsExist) {
            System.out.println("No commit with that id exists.");
            System.exit(0);
        }

        Map<String, String> trackedFile = commit.getTrackedFile();

        if (!trackedFile.containsKey(fileName)) {
            System.out.println("File does not exist in that commit.");
            System.exit(0);
        }

        Blob blob = Blob.getBlob(trackedFile.get(fileName));
        byte[] content = blob.getContent();
        Utils.writeContents(Utils.join(Repository.CWD, fileName), content);
    }


    @Override
    public boolean requiresRepo() {
        return true;
    }

    @Override
    public boolean isArgumentIllegal(String[] args) {
        if (args.length == 2) {
            return false;
        }

        if (args.length == 3 && args[1].equals("--")) {
            return false;
        }

        if (args.length == 4 && args[2].equals("--")) {
            return false;
        }

        return true;
    }
}
