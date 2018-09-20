package bepp.com.bepp.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by admin on 20/09/17.
 */

public class Login {
    private String usuario;
    private String password;
    private Integer id_usuario;
    private Integer tipo;
    private String nuevo_password;


    public Login(String usuario, String password, Integer tipo, String nuevo_password) {
        this.usuario = usuario;
        this.password = password;
        this.tipo = tipo;
        this.nuevo_password = nuevo_password;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNuevoPassword() {
        return nuevo_password;
    }

    public void setNuevoPassword(String nuevo_password) {
        this.nuevo_password = nuevo_password;
    }

    public Integer getTipo() {
        return tipo;
    }

    public void setTipo(Integer tipo) {
        this.tipo = tipo;
    }

    public Integer getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(Integer id_usuario) {
        this.id_usuario = id_usuario;
    }
}
