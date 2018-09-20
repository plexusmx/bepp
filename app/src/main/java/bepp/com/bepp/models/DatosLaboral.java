package bepp.com.bepp.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by charlie on 27/11/17.
 */

public class DatosLaboral {

    @SerializedName("hospital")
    private String hopital ;
    @SerializedName("domicilio")
    private String domicilio;
    @SerializedName("horario")
    private  String horario;
    @SerializedName("telefono")
    private String telefono;


    public String getHopital() {
        return hopital;
    }

    public void setHopital(String hopital) {
        this.hopital = hopital;
    }

    public String getDomicilio() {
        return domicilio;
    }

    public void setDomicilio(String domicilio) {
        this.domicilio = domicilio;
    }

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
}
