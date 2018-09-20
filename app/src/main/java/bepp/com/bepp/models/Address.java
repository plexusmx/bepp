package bepp.com.bepp.models;

/**
 * Created by admin on 21/09/17.
 */

public class Address {
    private String nombre;
    private String telefono;
    private Integer cp;
    private String calle;
    private String nroext;
    private String norint;
    private String colonia;
    private String delegacion;
    private String estado;
    private String pais;
    private String referencia;
    private String longitud;
    private String latitud;
    private Integer id_direccion;
    private String predeterminada;


    public Address(String nombre, String telefono, Integer cp,
                   String calle, String nroext, String norint,
                   String colonia, String delegacion, String estado,
                   String pais, String referencia, String longitud,
                   String latitud, Integer id_direccion, String predeterminada) {

        this.nombre = nombre;
        this.telefono = telefono;
        this.cp = cp;
        this.calle = calle;
        this.nroext = nroext;
        this.norint = norint;
        this.colonia = colonia;
        this.delegacion = delegacion;
        this.estado = estado;
        this.pais = pais;
        this.referencia = referencia;
        this.longitud = longitud;
        this.latitud = latitud;
        this.predeterminada = predeterminada;
        this.id_direccion = id_direccion;
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

    public Integer getCp() {
        return cp;
    }

    public void setCp(Integer cp) {
        this.cp = cp;
    }

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public String getNroext() {
        return nroext;
    }

    public void setNroext(String nroext) {
        this.nroext = nroext;
    }

    public String getNorint() {
        return norint;
    }

    public void setNorint(String norint) {
        this.norint = norint;
    }

    public String getColonia() {
        return colonia;
    }

    public void setColonia(String colonia) {
        this.colonia = colonia;
    }

    public String getDelegacion() {
        return delegacion;
    }

    public void setDelegacion(String delegacion) {
        this.delegacion = delegacion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public String getLongitud() {
        return longitud;
    }

    public void setLongitud(String longitud) {
        this.longitud = longitud;
    }

    public String getLatitud() {
        return latitud;
    }

    public void setLatitud(String latitud) {
        this.latitud = latitud;
    }

    public Integer getId_direccion() {
        return id_direccion;
    }

    public void setId_direccion(Integer id_direccion) {
        this.id_direccion = id_direccion;
    }

    @Override
    public String toString() {
        return "Address{" +
                "nombre='" + nombre + '\'' +
                ", telefono='" + telefono + '\'' +
                ", cp=" + cp +
                ", calle='" + calle + '\'' +
                ", nroext='" + nroext + '\'' +
                ", norint='" + norint + '\'' +
                ", colonia='" + colonia + '\'' +
                ", delegacion='" + delegacion + '\'' +
                ", estado='" + estado + '\'' +
                ", pais='" + pais + '\'' +
                ", referencia='" + referencia + '\'' +
                ", longitud='" + longitud + '\'' +
                ", latitud='" + latitud + '\'' +
                ", id_direccion=" + id_direccion +
                '}';
    }
}
