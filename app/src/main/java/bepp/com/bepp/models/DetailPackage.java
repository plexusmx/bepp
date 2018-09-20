package bepp.com.bepp.models;

public class DetailPackage {

    private int id_producto;
    private String nombre_comercial;
    private String concentracion_medicamento;
    private String marca_generico;
    private String precio_producto;
    private String cantidad;
    private String subtotal_por_producto;

    public int getId_producto() {
        return id_producto;
    }

    public void setId_producto(int id_producto) {
        this.id_producto = id_producto;
    }

    public String getNombre_comercial() {
        return nombre_comercial;
    }

    public void setNombre_comercial(String nombre_comercial) {
        this.nombre_comercial = nombre_comercial;
    }

    public String getConcentracion_medicamento() {
        return concentracion_medicamento;
    }

    public void setConcentracion_medicamento(String concentracion_medicamento) {
        this.concentracion_medicamento = concentracion_medicamento;
    }

    public String getMarca_generico() {
        return marca_generico;
    }

    public void setMarca_generico(String marca_generico) {
        this.marca_generico = marca_generico;
    }

    public String getPrecio_producto() {
        return precio_producto;
    }

    public void setPrecio_producto(String precio_producto) {
        this.precio_producto = precio_producto;
    }

    public String getCantidad() {
        return cantidad;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }

    public String getSubtotal_por_producto() {
        return subtotal_por_producto;
    }

    public void setSubtotal_por_producto(String subtotal_por_producto) {
        this.subtotal_por_producto = subtotal_por_producto;
    }
}
