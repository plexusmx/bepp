package bepp.com.bepp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 21/09/17.
 */

public class Doctor {


    private String nombre;

    @SerializedName("especialidades")
    private List<Especialidad> especialidades = new ArrayList<>();

    @SerializedName("datos_laborales")
    private  List<DatosLaboral> datosLaborales= new ArrayList<>();

    @SerializedName("tel_movil")
    private String telefono;
    private String fotografia;
    private Integer id_usuario;
    private String hostpital;
    private String domicilio;
    private String usuario;
    private String horario;
    private  String conoce_mas;
private  String especialidad;

private  String vinculo;


    public Doctor(String nombre) {
        this.nombre = nombre;
    }

    public String getHostpital() {
        return hostpital;
    }

    public void setHostpital(String hostpital) {
        this.hostpital = hostpital;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String correo) {
        this.usuario = usuario;
    }

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }



    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public Integer getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(Integer id_usuario) {
        this.id_usuario = id_usuario;
    }


    public List<Especialidad> getEspecialidades() {
        return especialidades;
    }

    public void setEspecialidades(List<Especialidad> especialidades) {
        this.especialidades = especialidades;
    }

    public List<DatosLaboral> getDatosLaborales() {
        return datosLaborales;
    }

    public void setDatosLaborales(List<DatosLaboral> datosLaborales) {
        this.datosLaborales = datosLaborales;
    }

    public String getFotografia() {
        return fotografia;
    }

    public void setFotografia(String fotografia) {
        this.fotografia = fotografia;
    }


    public String getDomicilio() {
        return domicilio;
    }

    public void setDomicilio(String domicilio) {
        this.domicilio = domicilio;
    }

    public String getConoce_mas() {
        return conoce_mas;
    }

    public void setConoce_mas(String conoce_mas) {
        this.conoce_mas = conoce_mas;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    public String getVinculo() {
        return vinculo;
    }

    public void setVinculo(String vinculo) {
        this.vinculo = vinculo;
    }
}
