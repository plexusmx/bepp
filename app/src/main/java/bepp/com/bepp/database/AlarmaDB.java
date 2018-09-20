package bepp.com.bepp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import bepp.com.bepp.modelDB.Alarma;
import bepp.com.bepp.models.Alarm;

/**
 * Created by charlie on 30/11/17.
 */

public class AlarmaDB extends SQLiteOpenHelper{

    public static final String dbName = "AlarmaDB";
    public static final  String tableName = "alarma";
    public static final  String idColumn = "idAlarma";
    public static final  String idRequestCode = "requestCode";
    public static final  String medicamentoColumn = "nombreMedicamento";
    public static final String cantidadColumn = "cantidad";
    public static final String fecha = "fecha";
    public static final String  FECHA_TERMINO= "fechaTermino";
    public static final String col_hora  = "hora";
    private Context context;

    public AlarmaDB(Context context){
        super(context, dbName, null,1);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(" create table " + tableName + "(" +
                idColumn + " integer primary key autoincrement, " +
                medicamentoColumn + " text , " +
                idRequestCode + " integer, " +
                cantidadColumn + " TEXT, "+
                fecha + " DATE, "+
                col_hora  + " TIME, "+
                FECHA_TERMINO+ " long "+


                ")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + tableName);
        onCreate(db);

    }

    public List<Alarma> findAll (){
        try{
            List<Alarma> alarms = new ArrayList<Alarma>();
            SQLiteDatabase db = getWritableDatabase();
            Cursor cursor = db.rawQuery("select * from " +
            tableName, null);
            if (cursor.moveToFirst()){
                do{

                    Alarma alarm = new Alarma();
                    alarm.setIdAlarma(cursor.getInt(0));
                    alarm.setNombreMedicamento(cursor.getString(1));
                    alarm.setRequestCode(cursor.getInt(2));
                    alarm.setCantidad(cursor.getString(3));
                    alarm.setFecha(cursor.getString(4));
                    alarm.setTiempo(cursor.getString(5));
                    alarm.setFechaTemino(cursor.getLong(6));


                    alarms.add(alarm);


                }while (cursor.moveToNext());


            }
            db.close();
            return alarms;
        } catch (Exception e){
            return  null;
        }

    }

    public  boolean create(Alarma alarm){

        try{
            SQLiteDatabase db = getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(medicamentoColumn, alarm.getNombreMedicamento());
            Log.v("BeppError", "alarm.getRequestCode() : " + alarm.getRequestCode());

            contentValues.put(idRequestCode, alarm.getRequestCode());
            contentValues.put(cantidadColumn, alarm.getCantidad());
            contentValues.put(fecha, alarm.getFecha());
            contentValues.put(col_hora, alarm.getTiempo());
            contentValues.put(FECHA_TERMINO, alarm.getFechaTemino());
            long rows = db.insert(tableName,null,contentValues);



            db.close();
            return rows > 0;
        }catch (Exception e){
            Log.e("BeppError",e.getMessage(),e);


            return false;
        }



    }

    public  boolean delete (int id ){
        try{
            SQLiteDatabase db = getWritableDatabase();
            int rows = db.delete(tableName, idColumn +" = ?", new String[]{ String.valueOf(id)});

            db.close();
            return rows > 0;
        }catch (Exception e){
            return false;
        }

    }





    public int maxIdAlarms (){

        int maxId = 0;
        //List<Alarm> alarms = new ArrayList<Alarm>();



        try(SQLiteDatabase db = getWritableDatabase();  Cursor cursor = db.rawQuery("select * from " +
                tableName, null)){


            if (cursor.moveToFirst()){
                maxId = cursor.getInt(0);
                //cursor.close();


            }
            //db.close();

        } catch (Exception e){
            e.printStackTrace();
        }

        return maxId + 1;
    }


    public boolean actualizarFechaVencimiento (int requestCode, long nuevaFechaVencimiento){

        boolean success = false;
        //List<Alarm> alarms = new ArrayList<Alarm>();



        String strSQL = "UPDATE "+tableName+" SET "+ FECHA_TERMINO +" = " +nuevaFechaVencimiento+"  WHERE "+idRequestCode+" = "+ requestCode;





        try(SQLiteDatabase db = getWritableDatabase()){

            db.execSQL(strSQL);
            success = true;


        } catch (Exception e){
            e.printStackTrace();
        }

        return success;
    }













    public Alarma findByRequestCode (int requestCode ){


        Alarma alarm = new Alarma();
        int countAlarms = 0;
        //List<Alarm> alarms = new ArrayList<Alarm>();



        try(SQLiteDatabase db = getWritableDatabase();  Cursor cursor = db.rawQuery("select * from " +
                tableName + " where requestCode = " + requestCode, null)){


            if (cursor.moveToFirst()){



                alarm.setIdAlarma(cursor.getInt(0));
                alarm.setNombreMedicamento(cursor.getString(1));
                alarm.setRequestCode(cursor.getInt(2));
                alarm.setCantidad(cursor.getString(3));
                alarm.setFecha(cursor.getString(4));
                alarm.setTiempo(cursor.getString(5));
                alarm.setFechaTemino(cursor.getLong(6));


            }
            //db.close();

        } catch (Exception e){
            e.printStackTrace();
        }

        return alarm;
    }


    public Alarma findById (int id ){


        Alarma alarm = new Alarma();
        int countAlarms = 0;
        //List<Alarm> alarms = new ArrayList<Alarm>();


        try(SQLiteDatabase db = getWritableDatabase();  Cursor cursor = db.rawQuery("select * from " +
                tableName + " where idAlarma = " + id, null)){

            if (cursor.moveToFirst()){
                alarm.setIdAlarma(cursor.getInt(0));
                alarm.setNombreMedicamento(cursor.getString(1));
                alarm.setRequestCode(cursor.getInt(2));
                alarm.setCantidad(cursor.getString(3));
                alarm.setFecha(cursor.getString(4));
                alarm.setTiempo(cursor.getString(5));
                alarm.setFechaTemino(cursor.getLong(6));

            }
            //db.close();

        } catch (Exception e){
            e.printStackTrace();
        }

        return alarm;
    }







}
