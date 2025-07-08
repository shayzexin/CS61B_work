package gitlet;

import java.io.File;
import java.io.Serializable;

public class Blob implements Serializable {
    private byte[] content;
    private String contentString;
    private String id;

    public Blob(File file) {
        this.content = Utils.readContents(file);
        this.contentString = Utils.readContentsAsString(file);
        this.id = sha1();
    }

    private String sha1() {
        return Utils.sha1(
                Utils.sha1(contentString)
        );
    }

    public byte[] getContent() {
        return this.content;
    }

    public String getId() {
        return this.id;
    }

    public void save() {
        File path = Utils.join(Repository.BLOB_DIR, id);
        Utils.writeObject(path, this);
    }

    public static Blob getBlob(String id) {
        File path = Utils.join(Repository.BLOB_DIR, id);
        return Utils.readObject(path, Blob.class);
    }
}
