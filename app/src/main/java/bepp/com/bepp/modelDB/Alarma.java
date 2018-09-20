package bepp.com.bepp.modelDB;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import bepp.com.bepp.database.AlarmaDB;

/**
 * Created by charlie on 30/11/17.
 */

public class Alarma extends Application{

    public static AlarmaDB alarmaDB;
    public static SQLiteDatabase db;

    private int idAlarma;
    private String nombreMedicamento;
    private String cantidad;
    private int requestCode;
    private String fecha;
    private long fechaTemino;
    private String tiempo;



    public Alarma() {

    }

    public Alarma(int idAlarma) {
        this.idAlarma = idAlarma;
    }


    @Override
    public void onCreate() {
        super.onCreate();


        alarmaDB = new AlarmaDB(this);
        db = alarmaDB.getWritableDatabase();
    }


    public Alarma(int idAlarma, String nombreMedicamento, int requestCode, String cantidad, String fecha, String tiempo,long fechaTemino) {
        this.idAlarma = idAlarma;
        this.nombreMedicamento = nombreMedicamento;
        this.requestCode = requestCode;
        this.cantidad = cantidad;
        this.fecha = fecha;
        this.tiempo = tiempo;
        this.fechaTemino = fechaTemino;
    }

    public int getIdAlarma() {
        return idAlarma;
    }

    public void setIdAlarma(int idAlarma) {
        this.idAlarma = idAlarma;
    }

    public String getNombreMedicamento() {
        return nombreMedicamento;
    }

    public void setNombreMedicamento(String nombreMedicamento) {
        this.nombreMedicamento = nombreMedicamento;
    }

    public String getCantidad() {
        return cantidad;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }

    public int getRequestCode() {
        return requestCode;
    }

    public void setRequestCode(int requestCode) {
        this.requestCode = requestCode;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getTiempo() {
        return tiempo;
    }

    public void setTiempo(String tiempo) {
        this.tiempo = tiempo;
    }

    public long getFechaTemino() {
        return fechaTemino;
    }

    public void setFechaTemino(long fechaTemino) {
        this.fechaTemino = fechaTemino;
    }
}
