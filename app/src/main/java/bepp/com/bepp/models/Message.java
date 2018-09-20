package bepp.com.bepp.models;

import java.util.Date;

/**
 * Created by admin on 21/09/17.
 */

public class Message {
    private String titulo;
    private String fecha;
    private String contenido;
    private Integer id_mensaje;

    public Integer getId_mensaje() {
        return id_mensaje;
    }

    public void setId_mensaje(Integer id_mensaje) {
        this.id_mensaje = id_mensaje;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }
}
