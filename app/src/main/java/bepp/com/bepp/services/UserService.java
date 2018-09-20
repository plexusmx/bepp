package bepp.com.bepp.services;

import java.util.List;

import bepp.com.bepp.models.Address;
import bepp.com.bepp.models.BasicResponse;
import bepp.com.bepp.models.Doctor;
import bepp.com.bepp.models.EmptyRequest;
import bepp.com.bepp.models.Familiar;
import bepp.com.bepp.models.FiscalData;
import bepp.com.bepp.models.Login;
import bepp.com.bepp.models.MedicalSpeciality;
import bepp.com.bepp.models.Message;
import bepp.com.bepp.models.PackageSend;
import bepp.com.bepp.models.Patient;
import bepp.com.bepp.models.PaymentCard;
import bepp.com.bepp.models.Receta;
import bepp.com.bepp.models.ResponseList;
import bepp.com.bepp.utils.HttpClient;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

/**
 * Created by admin on 22/09/17.
 */
@SuppressWarnings("all")
public interface UserService {

    @POST("login")
    Call<BasicResponse> login(@Body Login login);

    @POST("recuperar_password")
    Call<Void> recoverPassword(@Body Login login);


    @POST("tarjeta")
    Call<Void> createPaymentCard(@Body PaymentCard card);

    @GET("tarjeta")
    Call<List<PaymentCard>> getPaymentCards();

    @DELETE("tarjeta/{id_pago}")
    Call<Void> deletePaymentCard(@Path("id_pago") int id_pago);

    @POST("logout")
    Call<Void> logout(@Body EmptyRequest req);

    @POST("direccion")
    Call<Void> createAddress(@Body Address req);

    @GET("direccion")
    Call<List<Address>> getAddress();

    @DELETE("direccion/{id_direccion}")
    Call<Void> deleteAddress(@Path("id_direccion") int id_direccion);

    @POST("fiscal")
    Call<Void> createFiscalData(@Body FiscalData fiscalData);

    @GET("fiscal")
    Call<List<FiscalData>> getFiscalData();

    @PUT("fiscal")
    Call<Void> updateFiscalData(@Body FiscalData fiscalData);

    @DELETE("fiscal/{id_dato}")
    Call<Void> deleteFiscalData(@Path("id_dato") int id_dato);

    @POST("registrar_mensajes")
    Call<BasicResponse> createMessage(@Body Message message);

    @GET("mensaje")
    Call<List<Message>> getMessages();

    @DELETE("mensaje/{id_mensaje}")
    Call<Void> deleteMessage(@Path("id_mensaje") int id_mensaje);

    @POST("listado_especialidades_medicas")
    Call<ResponseList<MedicalSpeciality>> deleteMessage(@Body MedicalSpeciality medicalSpeciality);

    @POST("relacion_paciente_medico")
    Call<BasicResponse> createPatientMedicRelationship(@Body Doctor patient);

    @POST("bloqueo_paciente_medico")
    Call<BasicResponse> removePatientMedicRelationship(@Body Doctor patient);

    @GET("mis_medicos")
    Call<List<Doctor>> getDoctors();

    @PUT("paciente")
    Call<Void> updatePatient(@Body Patient patient);

    @POST("password")
    Call<Void> updatePassword(@Body Patient patient);

    @POST("datosgenerales")
    Call<Patient> generalInfo();

    @Headers("Content-Type:application/x-www-form-urlencoded; charset=utf-8")
    @GET("mis_medicos/{id_usuario}")
    Call<List<Doctor>> getDoctor(@Path("id_usuario") int id_usuario);

    @Headers("Content-Type:application/x-www-form-urlencoded; charset=utf-8")
    @GET("detalle_familiar/{id_familiar}")
    Call<Familiar> getFamiliar(@Path("id_familiar") int id_familiar);

    @POST("desvincular_familiares")
    Call<Void> desvincularFamiliar(@Body Familiar familiar);



    @Headers("Content-Type:application/x-www-form-urlencoded; charset=utf-8")
    @GET("medico/especialidad/{id_especialidad}")
    Call<List<Doctor>> getDoctorEspecialidad(@Path("id_especialidad") int id_especialidad);


    @GET("medico/listado_por_nombre/{nombre}")
    Call<List<Doctor>> getDoctorsInfo(@Path("nombre") String nombre);

    @Headers("Content-Type:application/x-www-form-urlencoded; charset=utf-8")
    @GET("medico/{id_usuario}")
    Call<Doctor> addDoctor(@Path("id_usuario") int id_usuario);

    @DELETE("medico/{id_usuario}")
    Call<Void> desvincularDoctor(@Path("id_usuario") int id_usuario);

    @GET("medico/{id_usuario}")
    Call<Void> vincularDoctor(@Path("id_usuario") int id_usuario);


    @GET("ficha_medica")
    Call<List<Patient>> obtenerFichaMedica();

    @POST("ficha_medica")
    Call<Void> fichaMedica(@Body Patient patient );

    @POST("familiares")
    Call<Void> addFamiliar(@Body Familiar familiar);

    @GET("familiares")
    Call<List<Familiar>> getFamily();

    @DELETE("eliminar_familiares/{id_familiar}")
    Call<Void> deletefamiliar(@Path("id_familiar") int id_familiar);

    @GET("paciente/recetas_listado/")
    Call<List<Receta>> listarRecetas();

    @GET("paciente/recetas/{id_receta}")
    Call<List<Receta>> detalleReceta(@Path("id_receta") int id_receta);

    @POST("paciente/recetas")
    Call<Void> formaEnvio(@Body PackageSend packageSend);

    @GET("paciente/receta_farmacia/{id_receta}")
    Call<List<PackageSend>> infoFarmaciaReceta(@Path("id_receta") int id_receta);




    @POST("paciente/recetas/{id_receta}")
    Call<Void> direccionReceta(@Body FiscalData fiscalData, @Path("id_receta") int id_receta);











    public static final UserService USER_CLIENT_RETROFIT = new Retrofit.Builder()
            .baseUrl("http://201.150.35.211/servicio/src/public/index.php/usuario/")

            .addConverterFactory(GsonConverterFactory.create())
            .client(HttpClient.getINSTANCE().getClient())
            .build()
            .create(UserService.class);


    public static final UserService USER_CLIENT_RETROFIT_PACIENT = new Retrofit.Builder()
            .baseUrl("http://201.150.35.211/servicio/src/public/index.php/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(HttpClient.getINSTANCE().getClient())
            .build()
            .create(UserService.class);

}


