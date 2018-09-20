package bepp.com.bepp.models;

/**
 * Created by charlie on 28/11/17.
 */

public class Alarm {

    private int idAlarma;
    private String nombreMedicamento;

    public int getRequestCode() {
        return requestCode;
    }

    public void setRequestCode(int requestCode) {
        this.requestCode = requestCode;
    }

    private int requestCode;
    private String cantidad;
    private String tiempoToma;
    private String dias;


    public int getIdAlarma() {
        return idAlarma;
    }

    public void setIdAlarma(int idAlarma) {
        this.idAlarma = idAlarma;
    }

    public String getNombreMedicamento() {
        return nombreMedicamento;
    }

    public void setNombreMedicamento(String nombreMedicamento) {
        this.nombreMedicamento = nombreMedicamento;
    }

    public String getTiempoToma() {
        return tiempoToma;
    }

    public void setTiempoToma(String tiempoToma) {
        this.tiempoToma = tiempoToma;
    }

    public String getDias() {
        return dias;
    }

    public void setDias(String dias) {
        this.dias = dias;
    }

    public String getCantidad() {
        return cantidad;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }
}
