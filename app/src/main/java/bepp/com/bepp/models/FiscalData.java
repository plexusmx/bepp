package bepp.com.bepp.models;

/**
 * Created by admin on 21/09/17.
 */

public class FiscalData {


    public FiscalData(String razon_social,
                      String rfc,
                      String calle,
                      String nroext,
                      String nroint,
                      String colonia,
                      String delegacion,
                      String estado,
                      String pais,
                      Integer tipo,
                      String cp,
                      Integer id_dato,
                      String predeterminada) {
        this.razon_social = razon_social;
        this.rfc = rfc;
        this.calle = calle;
        this.nroext = nroext;
        this.nroint = nroint;
        this.colonia = colonia;
        this.delegacion = delegacion;
        this.estado = estado;
        this.pais = pais;
        this.tipo = tipo;
        this.cp = cp;
        this.id_dato = id_dato;
        this.predeterminada = predeterminada;
    }

    private String razon_social;
    private String rfc;
    private String calle;
    private String nroext;
    private String nroint;
    private String colonia;
    private String delegacion;
    private String estado;
    private String pais;
    private Integer tipo;
    private String cp;
    private Integer id_dato;
    private String predeterminada;


    public String getRazon_social() {
        return razon_social;
    }

    public void setRazon_social(String razon_social) {
        this.razon_social = razon_social;
    }

    public String getRfc() {
        return rfc;
    }

    public void setRfc(String rfc) {
        this.rfc = rfc;
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

    public String getNroint() {
        return nroint;
    }

    public void setNroint(String nroint) {
        this.nroint = nroint;
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

    public Integer getTipo() {
        return tipo;
    }

    public void setTipo(Integer tipo) {
        this.tipo = tipo;
    }

    public String getCp() {
        return cp;
    }

    public void setCp(String cp) {
        this.cp = cp;
    }

    public Integer getId_dato() {
        return id_dato;
    }

    public void setId_dato(Integer id_dato) {
        this.id_dato = id_dato;
    }

    public String getPredeterminada() {
        return predeterminada;
    }

    public void setPredeterminada(String predeterminada) {
        this.predeterminada = predeterminada;
    }
}
