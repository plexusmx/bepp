package bepp.com.bepp.models;

public class Medicine {
    private String id_medicamento;
    private String nombre_comercial;
    private String concentracion;
    private String sustancia_activa ;
    private String cada;
    private String por;
    private String tipo;
    private String observaciones;





    public String getId_medicamento() {
        return id_medicamento;
    }

    public void setId_medicamento(String id_medicamento) {
        this.id_medicamento = id_medicamento;
    }

    public String getNombre_comercial() {
        return nombre_comercial;
    }

    public void setNombre_comercial(String nombre_comercial) {
        this.nombre_comercial = nombre_comercial;
    }

    public String getConcentracion() {
        return concentracion;
    }

    public void setConcentracion(String concentracion) {
        this.concentracion = concentracion;
    }

    public String getSustancia_activa() {
        return sustancia_activa;
    }

    public void setSustancia_activa(String sustancia_activa) {
        this.sustancia_activa = sustancia_activa;
    }

    public String getCada() {
        return cada;
    }

    public void setCada(String cada) {
        this.cada = cada;
    }

    public String getPor() {
        return por;
    }

    public void setPor(String por) {
        this.por = por;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }
}
