package bepp.com.bepp.openpay;

/**
 * Created by charlie on 04/05/18.
 */

public class OperationResult<T> {

    /** The card. */
    private T result;


    /**
     * Instantiates a new operation result.
     *
     * @param card the card
     */
    public OperationResult(final T result) {
        this.result = result;
    }

    /**
     * Gets the card.
     *
     * @return the card
     */
    public T getResult() {
        return this.result;
    }
}