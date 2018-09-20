package bepp.com.bepp.services;

import bepp.com.bepp.models.RegisterUserModel;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by juancarlosromero on 11/09/17.
 */

public interface RegisterUserService {
    //REGISTRO
    @POST("paciente")
    Call<RegisterUserModel> createAccount(@Body RegisterUserModel user);
}
