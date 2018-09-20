package bepp.com.bepp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by juancarlosromero on 11/09/17.
 */

public class RecoverPassModel {

    @SerializedName("usuario")
    @Expose
    private String usuario;

        @SerializedName("tipo")
        @Expose
        private String tipo;


        public RecoverPassModel() {

        }

        public RecoverPassModel(String usuario, String tipo) {
            this.usuario = usuario;
            this.tipo = tipo;
        }

        public String getUsuario() {
            return usuario;
        }

        public void setUsuario(String usuario) {
            this.usuario = usuario;
        }

        public String getTipo() {
            return tipo;
        }

        public void setTipo(String tipo) {
            this.tipo = tipo;
        }
}
