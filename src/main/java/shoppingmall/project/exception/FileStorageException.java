package shoppingmall.project.exception;

import java.io.IOException;

public class FileStorageException extends IOException {

    public FileStorageException(String message) {
        super(message);
    }

    public FileStorageException(String message, Throwable cause) {
        super(message, cause);
    }


}
