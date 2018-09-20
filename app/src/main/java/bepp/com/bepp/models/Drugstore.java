package bepp.com.bepp.models;

/**
 * Created by admin on 21/09/17.
 */

public class Drugstore {

    private Integer id_farmacia;
    private String nombre_farmacia;
    private String logoo_farmacia;
    private String calificacion_farmacia;
    private int numero_medicamentos_receta;
    private int numero_medicamentos_farmacia;
    private float subtotal;
    private int ofertas;

    public Integer getId_farmacia() {
        return id_farmacia;
    }

    public void setId_farmacia(Integer id_farmacia) {
        this.id_farmacia = id_farmacia;
    }

    public String getNombre_farmacia() {
        return nombre_farmacia;
    }

    public void setNombre_farmacia(String nombre_farmacia) {
        this.nombre_farmacia = nombre_farmacia;
    }

    public String getLogoo_farmacia() {
        return logoo_farmacia;
    }

    public void setLogoo_farmacia(String logoo_farmacia) {
        this.logoo_farmacia = logoo_farmacia;
    }

    public String getCalificacion_farmacia() {
        return calificacion_farmacia;
    }

    public void setCalificacion_farmacia(String calificacion_farmacia) {
        this.calificacion_farmacia = calificacion_farmacia;
    }

    public int getNumero_medicamentos_receta() {
        return numero_medicamentos_receta;
    }

    public void setNumero_medicamentos_receta(int numero_medicamentos_receta) {
        this.numero_medicamentos_receta = numero_medicamentos_receta;
    }

    public int getNumero_medicamentos_farmacia() {
        return numero_medicamentos_farmacia;
    }

    public void setNumero_medicamentos_farmacia(int numero_medicamentos_farmacia) {
        this.numero_medicamentos_farmacia = numero_medicamentos_farmacia;
    }

    public float getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(float subtotal) {
        this.subtotal = subtotal;
    }

    public int getOfertas() {
        return ofertas;
    }

    public void setOfertas(int ofertas) {
        this.ofertas = ofertas;
    }
}
