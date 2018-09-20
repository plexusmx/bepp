package bepp.com.bepp.models;

/**
 * Created by admin on 20/09/17.
 */

public class PaymentCard extends BasicResponse {


    private Integer id_pago;
    private String nombre_titular;
    private String predeterminada;
    private String tipo;
    private String vencimiento_mes;
    private String vencimiento_ano;
    private String nro_tarjeta;


    public PaymentCard(String nombre_titular, String predeterminada,
                       String tipo, String vencimiento_mes, String vencimiento_ano,
                       String nro_tarjeta) {

        this.nombre_titular = nombre_titular;
        this.predeterminada = predeterminada;
        this.tipo = tipo;
        this.vencimiento_mes = vencimiento_mes;
        this.vencimiento_ano = vencimiento_ano;
        this.nro_tarjeta = nro_tarjeta;
    }


    public Integer getId_pago() {
        return id_pago;
    }

    public void setId_pago(Integer id_pago) {
        this.id_pago = id_pago;
    }

    public String getNombre_titular() {
        return nombre_titular;
    }

    public void setNombre_titular(String nombre_titular) {
        this.nombre_titular = nombre_titular;
    }

    public String getPredeterminada() {
        return predeterminada;
    }

    public void setPredeterminada(String predeterminada) {
        this.predeterminada = predeterminada;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getVencimiento_mes() {
        return vencimiento_mes;
    }

    public void setVencimiento_mes(String vencimiento_mes) {
        this.vencimiento_mes = vencimiento_mes;
    }

    public String getVencimiento_ano() {
        return vencimiento_ano;
    }

    public void setVencimiento_ano(String vencimiento_ano) {
        this.vencimiento_ano = vencimiento_ano;
    }

    public String getNro_tarjeta() {
        return nro_tarjeta;
    }

    public void setNro_tarjeta(String nro_tarjeta) {
        this.nro_tarjeta = nro_tarjeta;
    }
}
