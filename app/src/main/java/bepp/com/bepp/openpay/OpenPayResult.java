package bepp.com.bepp.openpay;

import bepp.com.bepp.openpay.exceptions.OpenpayServiceException;
import bepp.com.bepp.openpay.exceptions.ServiceUnavailableException;

/**
 * Created by charlie on 04/05/18.
 */

public class OpenPayResult<T> {
    private OpenpayServiceException openpayServiceException;

    private OperationResult<T> operationResult;

    private ServiceUnavailableException serviceUnavailableException;

    public OpenpayServiceException getOpenpayServiceException() {
        return this.openpayServiceException;
    }

    public void setOpenpayServiceException(final OpenpayServiceException openpayServiceException) {
        this.openpayServiceException = openpayServiceException;
    }

    public OperationResult<T> getOperationResult() {
        return this.operationResult;
    }

    public void setOperationResult(final OperationResult<T> operationResult) {
        this.operationResult = operationResult;
    }

    public ServiceUnavailableException getServiceUnavailableException() {
        return this.serviceUnavailableException;
    }

    public void setServiceUnavailableException(final ServiceUnavailableException serviceUnavailableException) {
        this.serviceUnavailableException = serviceUnavailableException;
    }
}