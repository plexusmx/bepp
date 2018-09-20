package bepp.com.bepp.openpay.exceptions;

import android.annotation.TargetApi;
import android.os.Build;

import java.io.IOException;

/**
 * Created by charlie on 04/05/18.
 */

public class ServiceUnavailableException extends IOException {

    private static final long serialVersionUID = -7388627000694002585L;

    public ServiceUnavailableException(final String message) {
        super(message);
    }

    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
    public ServiceUnavailableException(final Throwable cause) {
        super(cause);
    }

    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
    public ServiceUnavailableException(final String message, final Throwable cause) {
        super(message, cause);
    }

}