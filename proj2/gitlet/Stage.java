package gitlet;

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Stage implements Serializable {
    private Map<String, String> additions;
    private Set<String> removals;

    private Stage() {
        additions = new HashMap<>();
        removals = new HashSet<>();
    }

    public Map<String, String> getAdditions() {
        return additions;
    }

    public Set<String> getRemovals() {
        return removals;
    }

    public void add(String file, String bolbId) {
        additions.put(file, bolbId);
    }

    public boolean remove(String file) {
        boolean didSomething = false;

        if (additions.containsKey(file)) {
            additions.remove(file);
            didSomething = true;
        }

        Commit currentCommit = Commit.getCurrentCommit();
        Map<String, String> trackedFiles = currentCommit.getTrackedFile();

        if (trackedFiles.containsKey(file)) {
            removals.add(file);
            didSomething = true;
        }

        return didSomething;
    }

    public void removeFromAdditions(String fileName) {
        additions.remove(fileName);
    }

    public boolean isEmpty() {
        return additions.isEmpty() && removals.isEmpty();
    }

    public static void initStage() {
        Stage stage = new Stage();
        Utils.writeObject(Repository.STAGE_FILE, stage);
    }

    public static Stage getStage() {
        return Utils.readObject(Repository.STAGE_FILE, Stage.class);
    }

    public void save() {
        Utils.writeObject(Repository.STAGE_FILE, this);
    }
}
