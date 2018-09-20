package bepp.com.bepp.models;

/**
 * Created by admin on 20/09/17.
 */

public class RecoverPassword {
    private String usuario;
    private int tipo;

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }
}
