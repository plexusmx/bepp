package bepp.com.bepp.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by charlie on 27/11/17.
 */

public class Especialidad {

    @SerializedName("especialidad")
    private String nombreEpecialidad ;
    @SerializedName("sub")
    private String sub;


    public String getNombreEpecialidad() {
        return nombreEpecialidad;
    }

    public void setNombreEpecialidad(String nombreEpecialidad) {
        this.nombreEpecialidad = nombreEpecialidad;
    }

    public String getSub() {
        return sub;
    }

    public void setSub(String sub) {
        this.sub = sub;
    }
}
