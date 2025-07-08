package gitlet.commands;

import gitlet.Repository;
import gitlet.Stage;
import gitlet.Utils;

import java.io.File;


public class RmCommand implements Command{
    @Override
    public void execute(String[] args) {
        String fileName = args[1];
        Stage stage = Stage.getStage();

        boolean removed = stage.remove(fileName);
        if (!removed) {
            System.out.println("No reason to remove this file.");
            return;
        }

        File fileInCWD = Utils.join(Repository.CWD, fileName);
        if (fileInCWD.exists()) {
            fileInCWD.delete();
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
