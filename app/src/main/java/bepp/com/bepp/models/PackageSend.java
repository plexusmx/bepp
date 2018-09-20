package bepp.com.bepp.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PackageSend {

    private int id_pedido;
    private String id_receta;
    private String forma_envio;
    private String farmacias_disponibles;
    private String medicamentos_disponibles;
    private String fecha_receta;

    @SerializedName("farmacias")
    private List<Farmacy> farmacy;




    public PackageSend(String id_receta, String forma_envio) {
        this.id_receta = id_receta;
        this.forma_envio = forma_envio;
    }

    public String getId_receta() {
        return id_receta;
    }

    public void setId_receta(String id_receta) {
        this.id_receta = id_receta;
    }

    public String getForma_envio() {
        return forma_envio;
    }

    public void setForma_envio(String forma_envio) {
        this.forma_envio = forma_envio;
    }

    public int getId_pedido() {
        return id_pedido;
    }

    public void setId_pedido(int id_pedido) {
        this.id_pedido = id_pedido;
    }

    public String getFarmacias_disponibles() {
        return farmacias_disponibles;
    }

    public void setFarmacias_disponibles(String farmacias_disponibles) {
        this.farmacias_disponibles = farmacias_disponibles;
    }

    public String getMedicamentos_disponibles() {
        return medicamentos_disponibles;
    }

    public void setMedicamentos_disponibles(String medicamentos_disponibles) {
        this.medicamentos_disponibles = medicamentos_disponibles;
    }

    public String getFecha_receta() {
        return fecha_receta;
    }

    public void setFecha_receta(String fecha_receta) {
        this.fecha_receta = fecha_receta;
    }

    public List<Farmacy> getFarmacy() {
        return farmacy;
    }

    public void setFarmacy(List<Farmacy> farmacy) {
        this.farmacy = farmacy;
    }
}
