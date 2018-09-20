package bepp.com.bepp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by juancarlosromero on 11/09/17.
 */

public class ResponseRecoverPassModel {




    @SerializedName("resultado")
    @Expose
    private boolean resultado;

    @SerializedName("mensaje")
    @Expose
    private String mensaje;


    public boolean isResultado() {
        return resultado;
    }

    public void setResultado(boolean resultado) {
        this.resultado = resultado;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
}
