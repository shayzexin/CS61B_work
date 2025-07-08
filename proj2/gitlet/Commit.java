package gitlet;

import java.io.Serializable;
import java.util.*;

public class Commit implements Serializable {
    private String id;
    private String message;
    private String parent;
    private String secondParent;
    private Date date;
    private Map<String, String> trackedFile;

    public Commit(String message, String parent, Date date, Map<String, String> trackedFile) {
        this.message = message;
        this.parent = parent;
        this.date = date;
        this.trackedFile = trackedFile;
        this.id = Utils.sha1(
                message,
                parent == null ? "" : parent,
                date.toString(),
                trackedFileToString(trackedFile)
        );
    }

    public String getId() {
        return this.id;
    }

    public Map<String, String> getTrackedFile() {
        return trackedFile;
    }

    public String getParent() {
        return this.parent;
    }

    public String getSecondParent() {
        return this.secondParent;
    }

    public String getDate() {
        return this.date.toString();
    }

    public String getMessage() {
        return this.message;
    }

    public Map<String, String> updateTrackedFile() {
        Stage stage =  Stage.getStage();

        Map<String, String> updated = new HashMap<>(this.trackedFile);
        Map<String, String> additions = stage.getAdditions();
        Set<String> removals = stage.getRemovals();

        for (String key : additions.keySet()) {
            updated.put(key, additions.get(key));
        }

        for (String removal : removals) {
            updated.remove(removal);
        }

        return updated;
    }

    public void updateId() {
        this.id = Utils.sha1(
                message,
                parent,
                date.toString(),
                trackedFileToString(trackedFile)
        );
    }

    public static Commit getCurrentCommit() {
        String currentHead = Utils.readContentsAsString(Repository.HEAD_FILE);
        String currentHeadCommitId =
                Utils.readContentsAsString(Utils.join(Repository.REF_DIR,
                        currentHead));
        return Utils.readObject(Utils.join(Repository.COMMIT_DIR, currentHeadCommitId),
                Commit.class);
    }

    public static Commit getFromId(String id) {
        if (id == null) {
            return null;
        }

        return Utils.readObject(Utils.join(Repository.COMMIT_DIR, id),
                Commit.class);
    }

    public void save() {
        Utils.writeObject(Utils.join(Repository.COMMIT_DIR, id), this);
    }

    private String trackedFileToString(Map<String, String> trackedFile) {
        StringBuilder sb = new StringBuilder();
        List<String> filenames = new ArrayList<>(trackedFile.keySet());
        Collections.sort(filenames);
        for (String name : filenames) {
            sb.append(name).append(":").append(trackedFile.get(name)).append(";");
        }
        return sb.toString();
    }

}
