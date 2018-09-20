package bepp.com.bepp.services;

import bepp.com.bepp.models.RecoverPassModel;
import bepp.com.bepp.models.ResponseRecoverPassModel;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by juancarlosromero on 11/09/17.
 */

public interface RecoverPassService {
    @POST("recuperar_password")
    Call<ResponseRecoverPassModel> savePost(@Body RecoverPassModel recoverPassModel);
}
