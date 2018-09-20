package bepp.com.bepp.services;

import bepp.com.bepp.models.LoginUserModel;
import bepp.com.bepp.models.ResponseLoginUserModel;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by juancarlosromero on 11/09/17.
 */

public interface LoginUserService {
    @POST("login")
    Call<ResponseLoginUserModel> savePost(@Body LoginUserModel userLogin);
}


