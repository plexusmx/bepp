package bepp.com.bepp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by juancarlosromero on 11/09/17.
 */

public class LoginUserModel {
    @SerializedName("Resultado")
    @Expose
    private boolean resultado;

    @SerializedName("Token")
    @Expose
    private String token;

    @SerializedName("Mensaje")
    @Expose
    private String mensaje;


    private String usuario;
    private String password;
    private String tipo;

    public LoginUserModel() {

    }

    public LoginUserModel(String usuario, String password,String tipo) {
        this.usuario = usuario;
        this.password = password;
        this.tipo = tipo;
    }

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