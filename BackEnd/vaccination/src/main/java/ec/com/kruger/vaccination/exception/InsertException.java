package ec.com.kruger.vaccination.exception;

public class InsertException extends Exception {
    private final String collectionName;

    public InsertException(String collectionName, String message) {
        super(message);
        this.collectionName = collectionName;
    }

    public InsertException(String collectionName, String message, Throwable cause) {
        super(message, cause);
        this.collectionName = collectionName;
    }

    public String getCollectionName() {
        return collectionName;
    }
}
