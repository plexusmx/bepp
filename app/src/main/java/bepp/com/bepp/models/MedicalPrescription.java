package bepp.com.bepp.models;

import java.util.Date;
import java.util.List;

/**
 * Created by admin on 21/09/17.
 */

public class MedicalPrescription {

    private String medico;
    private Integer id_receta;
    private Integer estatus_receta;
    private Date fecha_registro;
    private Date fecha_vencimiento;
    private String nombre_paciente;
    private String diagnostico;
    private int estatus_estudios_laboratorios;
    private String observaciones;
    private Date tratamiento;
    private List<ClinicalStudy> listado1;
    private List<MedicalPrescription> listado2;

    public List<ClinicalStudy> getListado1() {
        return listado1;
    }

    public void setListado1(List<ClinicalStudy> listado1) {
        this.listado1 = listado1;
    }

    public List<MedicalPrescription> getListado2() {
        return listado2;
    }

    public void setListado2(List<MedicalPrescription> listado2) {
        this.listado2 = listado2;
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

    public int getEstatus_estudios_laboratorios() {
        return estatus_estudios_laboratorios;
    }

    public void setEstatus_estudios_laboratorios(int estatus_estudios_laboratorios) {
        this.estatus_estudios_laboratorios = estatus_estudios_laboratorios;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public Date getTratamiento() {
        return tratamiento;
    }

    public void setTratamiento(Date tratamiento) {
        this.tratamiento = tratamiento;
    }

    public String getMedico() {
        return medico;
    }

    public void setMedico(String medico) {
        this.medico = medico;
    }

    public Integer getId_receta() {
        return id_receta;
    }

    public void setId_receta(Integer id_receta) {
        this.id_receta = id_receta;
    }

    public Integer getEstatus_receta() {
        return estatus_receta;
    }

    public void setEstatus_receta(Integer estatus_receta) {
        this.estatus_receta = estatus_receta;
    }

    public Date getFecha_registro() {
        return fecha_registro;
    }

    public void setFecha_registro(Date fecha_registro) {
        this.fecha_registro = fecha_registro;
    }

    public Date getFecha_vencimiento() {
        return fecha_vencimiento;
    }

    public void setFecha_vencimiento(Date fecha_vencimiento) {
        this.fecha_vencimiento = fecha_vencimiento;
    }
}
