package tech.saintbassanaga.stockmanager.configs.exception;

/**
 * Created by saintbassanaga {saintbassanaga}
 * In the Project StockManager at Mon - 9/16/24
 */
public class PdfGenerationException extends RuntimeException {
    public PdfGenerationException(String message, Throwable cause) {
        super(message, cause);
    }
}
