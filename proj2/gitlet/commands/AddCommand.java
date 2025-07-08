package gitlet.commands;

import gitlet.*;
import java.io.File;

public class AddCommand implements Command{
    @Override
    public void execute(String[] args) {
        String fileName = args[1];
        File file = Utils.join(Repository.CWD, fileName);

        if (!file.exists()) {
            System.out.println("File does not exist.");
            System.exit(0);
        }

        Blob blob = new Blob(file);
        String blobId = blob.getId();
        blob.save();

        Stage stage = Stage.getStage();
        Commit head = Commit.getCurrentCommit();
        String trackedBlobId = head.getTrackedFile().get(fileName);

        if (blobId.equals(trackedBlobId)) {
            stage.removeFromAdditions(fileName);
        } else {
            stage.add(fileName, blobId);
        }

        stage.save();

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
