package bepp.com.bepp.models;

import java.util.Date;

/**
 * Created by admin on 21/09/17.
 */

public class MedicamentPrescription {

    private int idMedicamentPrescription;
    private String nombre_medicamento;
    private String nombre;
    private int cantidad;
    private int seleccionado;
    private String via;
    private String frecuencia;
    private Date tratamiento;

    public String getNombre_medicamento() {
        return nombre_medicamento;
    }

    public void setNombre_medicamento(String nombre_medicamento) {
        this.nombre_medicamento = nombre_medicamento;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }


    public int getSeleccionado() {
        return seleccionado;
    }

    public void setSeleccionado(int seleccionado) {
        this.seleccionado = seleccionado;
    }

    public String getVia() {
        return via;
    }

    public void setVia(String via) {
        this.via = via;
    }

    public String getFrecuencia() {
        return frecuencia;
    }

    public void setFrecuencia(String frecuencia) {
        this.frecuencia = frecuencia;
    }

    public Date getTratamiento() {
        return tratamiento;
    }

    public void setTratamiento(Date tratamiento) {
        this.tratamiento = tratamiento;
    }

    public int getIdMedicamentPrescription() {
        return idMedicamentPrescription;
    }

    public void setIdMedicamentPrescription(int idMedicamentPrescription) {
        this.idMedicamentPrescription = idMedicamentPrescription;
    }
}
