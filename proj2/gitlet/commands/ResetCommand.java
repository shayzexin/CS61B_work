package gitlet.commands;

import gitlet.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ResetCommand implements Command{
    @Override
    public void execute(String[] args) {
        String commitIdPrefix = args[1];
        List<String> commitIds = Utils.plainFilenamesIn(Repository.COMMIT_DIR);

        if (commitIds == null) {
            throw new GitletException("No commit in this repository.");
        }

        boolean commitIsExist = false;
        Commit commit = null;
        int length = commitIdPrefix.length();

        for (String id : commitIds) {
            if (id.substring(0, length).equals(commitIdPrefix)) {
                commitIsExist = true;
                commit = Commit.getFromId(id);
                break;
            }
        }

        if (!commitIsExist) {
            System.out.println("No commit with that id exists.");
            System.exit(0);
        }

        Commit headCommit =  Commit.getFromId(commit.getId());

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
                        System.out.println("There is an untracked file in the" +
                                " way; delete it, or add and commit it first.");
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

        for (Map.Entry<String, String> entry :
                headCommit.getTrackedFile().entrySet()) {
            String fileName = entry.getKey();
            String blobId = entry.getValue();
            byte[] content = Blob.getBlob(blobId).getContent();
            Utils.writeContents(Utils.join(Repository.CWD, fileName), content);
        }

        String branchName = Branch.getCurrentHead();
        File branchFile = Utils.join(Repository.REF_DIR, branchName);
        Utils.writeContents(branchFile, commit.getId());

        Stage.initStage();
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
