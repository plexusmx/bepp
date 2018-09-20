package bepp.com.bepp.openpay;

import bepp.com.bepp.openpay.exceptions.OpenpayServiceException;
import bepp.com.bepp.openpay.exceptions.ServiceUnavailableException;

/**
 * Created by charlie on 04/05/18.
 */

public interface OperationCallBack<T> {

    /**
     * On error.
     *
     * @param error the error
     */
    void onError(final OpenpayServiceException error);

    /**
     * On communication error.
     *
     * @param error the error
     */
    void onCommunicationError(final ServiceUnavailableException error);

    /**
     * On success.
     *
     * @param operationResult the operation result
     */
    void onSuccess(final OperationResult<T> operationResult);
}