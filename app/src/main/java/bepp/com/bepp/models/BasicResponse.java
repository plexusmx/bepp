package bepp.com.bepp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by admin on 21/09/17.
 */

public class BasicResponse {

    @SerializedName("resultado")
    @Expose
    private boolean resultado;

    @SerializedName("token")
    @Expose
    private String token;

    @SerializedName("mensaje")
    @Expose
    private String mensaje;

    public boolean isResultado() {
        return resultado;
    }

    public void setResultado(boolean resultado) {
        this.resultado = resultado;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
}
