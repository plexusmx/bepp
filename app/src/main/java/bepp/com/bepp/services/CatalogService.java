package bepp.com.bepp.services;

import java.util.List;

import bepp.com.bepp.models.MedicalSpeciality;
import bepp.com.bepp.models.Relationship;
import bepp.com.bepp.models.ResponseList;
import bepp.com.bepp.utils.HttpClient;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;

/**
 * Created by charlie on 04/10/17.
 */

public interface CatalogService {


    @GET("especialidades")
    Call<List<MedicalSpeciality>> getMedicalSpeciality();

    @GET("parentesco")
    Call<List<Relationship>> getRelationship();



    public static final CatalogService CATALOG_CLIENT_RETROFIT = new Retrofit.Builder()
            .baseUrl("http://201.150.35.211/servicio/src/public/index.php/catalogo/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(HttpClient.getINSTANCE().getClient())
            .build()
            .create(CatalogService.class);
}

