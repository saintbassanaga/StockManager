package tech.saintbassanaga.stockmanager.configs.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Created by  {saintbassanaga}
 * In the Project StockManager at Mon - 9/16/24
 */

@Getter
public class ResourceNotFound extends RuntimeException {
    private final ErrorCode errorCode;
    private final ErrorStatus errorStatus;

    /**
     * Constructs a new runtime exception with the specified detail message.
     * The cause is not initialized, and may subsequently be initialized by a
     * call to {@link #initCause}.
     *
     * @param message the detail message. The detail message is saved for
     *                later retrieval by the {@link #getMessage()} method.
     */
    public ResourceNotFound(String message, ErrorCode errorCode, ErrorStatus errorStatus) {
        super(message);
        this.errorCode = errorCode;
        this.errorStatus = errorStatus;
    }
}
