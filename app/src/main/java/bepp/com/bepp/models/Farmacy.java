package bepp.com.bepp.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Farmacy {

    private int id_farmacia;
    private String nombre_cadena;
    private String url_logotipo;
    private String id_sucursal;
    private String nombre_sucursal;
    private String cantidad_medicamentos;
    private String subtotalpedido ;

    @SerializedName("detalle")
    private List<DetailPackage> detailPackageList;

    public int getId_farmacia() {
        return id_farmacia;
    }

    public void setId_farmacia(int id_farmacia) {
        this.id_farmacia = id_farmacia;
    }

    public String getNombre_cadena() {
        return nombre_cadena;
    }

    public void setNombre_cadena(String nombre_cadena) {
        this.nombre_cadena = nombre_cadena;
    }

    public String getUrl_logotipo() {
        return url_logotipo;
    }

    public void setUrl_logotipo(String url_logotipo) {
        this.url_logotipo = url_logotipo;
    }

    public String getId_sucursal() {
        return id_sucursal;
    }

    public void setId_sucursal(String id_sucursal) {
        this.id_sucursal = id_sucursal;
    }

    public String getNombre_sucursal() {
        return nombre_sucursal;
    }

    public void setNombre_sucursal(String nombre_sucursal) {
        this.nombre_sucursal = nombre_sucursal;
    }

    public String getCantidad_medicamentos() {
        return cantidad_medicamentos;
    }

    public void setCantidad_medicamentos(String cantidad_medicamentos) {
        this.cantidad_medicamentos = cantidad_medicamentos;
    }

    public String getSubtotalpedido() {
        return subtotalpedido;
    }

    public void setSubtotalpedido(String subtotalpedido) {
        this.subtotalpedido = subtotalpedido;
    }

    public List<DetailPackage> getDetailPackageList() {
        return detailPackageList;
    }

    public void setDetailPackageList(List<DetailPackage> detailPackageList) {
        this.detailPackageList = detailPackageList;
    }
}
