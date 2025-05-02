package gitlet;

import java.io.File;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class FileMap implements Serializable {
    private Map<String, Blob> fileToBlob;
    private String id;

    public Map<String, Blob> getFileMap() {
        return fileToBlob;
    }

    public FileMap() {
        fileToBlob = new HashMap<>();
        id = convertHash();
    }

    private String convertHash() {
        String treeText = fileToBlob.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .map(e -> String.format("blob %s\t%s",
                        e.getValue(),
                        e.getKey()))
                .collect(Collectors.joining("\n"));

        return Utils.sha1(treeText);
    }

    public String save() {
        File fileMapPath = Utils.join(Repository.OBJECT_DIR, "Map_" + id);
        Utils.writeObject(fileMapPath, Utils.serialize(this));

        return id;
    }
}
