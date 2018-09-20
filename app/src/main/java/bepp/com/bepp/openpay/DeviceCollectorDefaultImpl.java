package bepp.com.bepp.openpay;

import android.app.Activity;
import android.provider.Settings;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.util.Date;
import java.util.UUID;

/**
 * Created by charlie on 04/05/18.
 */

public class DeviceCollectorDefaultImpl {

    private String baseUrl;
    private String merchantId;
    private String errorMessage;

    public DeviceCollectorDefaultImpl(final String baseUrl, final String merchantId) {
        this.baseUrl = baseUrl;
        this.merchantId = merchantId;
    }

    public String setup(final Activity activity) {
        try {
            // Generamos sessionId
            String sessionId = UUID.randomUUID().toString();
            sessionId = sessionId.replace("-", "");

            // Obtenemos identifierForVendor
            String identifierForVendor = Settings.Secure.getString(activity.getContentResolver(), Settings.Secure.ANDROID_ID);

            // Inicializamos WebView con el identifierForVendor
            WebView webViewDF = new WebView(activity);
            webViewDF.setWebViewClient(new WebViewClient());
            webViewDF.getSettings().setJavaScriptEnabled(true);
            String identifierForVendorScript = String.format("var identifierForVendor = '%s';", identifierForVendor);
            webViewDF.evaluateJavascript(identifierForVendorScript, null);

            // Ejecutamos OpenControl
            String urlAsString = String.format("%s/oa/logo.htm?m=%s&s=%s", this.baseUrl, this.merchantId, sessionId);
            webViewDF.loadUrl(urlAsString);

            return sessionId;
        } catch (final Exception e) {
            this.logError(this.getClass().getName(), e.getMessage());
            this.errorMessage = e.getMessage();
            return null;
        }
    }

    private void logError(final String tag, final String content) {
        if (content.length() > 4000) {
            Log.e(tag, content.substring(0, 4000));
            this.logError(tag, content.substring(4000));
        } else {
            Log.e(tag, content);
        }
    }

    public String getErrorMessage() {
        return this.errorMessage;
    }

}