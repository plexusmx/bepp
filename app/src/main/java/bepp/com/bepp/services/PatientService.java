package bepp.com.bepp.services;

import bepp.com.bepp.models.BasicResponse;
import bepp.com.bepp.models.Patient;
import bepp.com.bepp.utils.HttpClient;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by admin on 05/10/17.
 */

public interface PatientService {

    @POST("paciente")
    Call<Void> createPatient(@Body Patient patient);

    public static final PatientService PATIENT_CLIENT_RETROFIT = new Retrofit.Builder()
            .baseUrl("http://201.150.35.211/servicio/src/public/index.php/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(HttpClient.getINSTANCE().getClient())
            .build()
            .create(PatientService.class);
}
