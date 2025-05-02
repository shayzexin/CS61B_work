package gitlet;

import java.io.File;
import java.io.Serializable;

public class Blob implements Serializable {
    private final byte[] content;
    private String id;

    public Blob(File file) {
        this.content = Utils.readContents(file);
        this.id = convertHash();
    }

    public String save() {
        File blobPath = Utils.join(Repository.OBJECT_DIR, "blob_" + id);
        Utils.writeObject(blobPath, Utils.serialize(this));

        return id;
    }

    private String convertHash() {
        return Utils.sha1((Object) this.content);
    }

    @Override
    public String toString() {
        return id;
    }
}
