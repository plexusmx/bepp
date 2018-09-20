package bepp.com.bepp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Receta {

    private int id_receta;

    @SerializedName("nombre_medico")
    @Expose
    private String medico;
    private String fecha_registro;

    private String fecha_vencimiento;
    private String nombre_paciente ;
    private String diagnostico ;
    private int total_medicamentos ;
    @SerializedName("detalle")
    @Expose
    private List<Medicine> detalle ;
    private String tipo_receta ;



    public Receta(int id_receta, String medico, String fecha_registro, String fecha_vencimiento) {
        this.id_receta = id_receta;
        this.medico = medico;
        this.fecha_registro = fecha_registro;
        this.fecha_vencimiento = fecha_vencimiento;
    }


    public int getId_receta() {
        return id_receta;
    }

    public void setId_receta(int id_receta) {
        this.id_receta = id_receta;
    }

    public String getMedico() {
        return medico;
    }

    public void setMedico(String medico) {
        this.medico = medico;
    }

    public String getFecha_registro() {
        return fecha_registro;
    }

    public void setFecha_registro(String fecha_registro) {
        this.fecha_registro = fecha_registro;
    }

    public String getFecha_vencimiento() {
        return fecha_vencimiento;
    }

    public void setFecha_vencimiento(String fecha_vencimiento) {
        this.fecha_vencimiento = fecha_vencimiento;
    }

    public String getNombre_paciente() {
        return nombre_paciente;
    }

    public void setNombre_paciente(String nombre_paciente) {
        this.nombre_paciente = nombre_paciente;
    }

    public String getDiagnostico() {
        return diagnostico;
    }

    public void setDiagnostico(String diagnostico) {
        this.diagnostico = diagnostico;
    }

    public int getTotal_medicamentos() {
        return total_medicamentos;
    }

    public void setTotal_medicamentos(int total_medicamentos) {
        this.total_medicamentos = total_medicamentos;
    }

    public List<Medicine> getDetalle() {
        return detalle;
    }

    public void setDetalle(List<Medicine> detalle) {
        this.detalle = detalle;
    }

    public String getTipo_receta() {
        return tipo_receta;
    }

    public void setTipo_receta(String tipo_receta) {
        this.tipo_receta = tipo_receta;
    }
}



