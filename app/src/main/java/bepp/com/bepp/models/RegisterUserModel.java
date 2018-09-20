package bepp.com.bepp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by juancarlosromero on 11/09/17.
 */

public class RegisterUserModel {
    private long id;
    private String nombre;
    private String apellidop;
    private String apellidom;
    private String sexo;
    private String usuario;
    private String tel_movil;
    private String password;
    private String fotografia;


    @SerializedName("Resultado")
    @Expose
    private boolean resultado;

    @SerializedName("Token")
    @Expose
    private String token;

    @SerializedName("Mensaje")
    @Expose
    private String mensaje;

    public RegisterUserModel(){

    }

    public RegisterUserModel(String nombre,
                        String apellidop, String apellidom, String sexo,
                        String usuario, String tel_movil, String password, String fotografia) {

        this.nombre = nombre;
        this.apellidop = apellidop;
        this.apellidom = apellidom;
        this.sexo = sexo;
        this.usuario = usuario;
        this.tel_movil = tel_movil;
        this.password = password;
        this.fotografia = fotografia;

    }

    public long getId() {
        return id;
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