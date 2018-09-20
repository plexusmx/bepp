package bepp.com.bepp.models;

/**
 * Created by admin on 21/09/17.
 */

public class ClinicalStudy {
    private String nombre_estudio;
    private String laboratorio_sugerido;
    private String comentarios;

    public String getNombre_estudio() {
        return nombre_estudio;
    }

    public void setNombre_estudio(String nombre_estudio) {
        this.nombre_estudio = nombre_estudio;
    }

    public String getLaboratorio_sugerido() {
        return laboratorio_sugerido;
    }

    public void setLaboratorio_sugerido(String laboratorio_sugerido) {
        this.laboratorio_sugerido = laboratorio_sugerido;
    }

    public String getComentarios() {
        return comentarios;
    }

    public void setComentarios(String comentarios) {
        this.comentarios = comentarios;
    }
}
