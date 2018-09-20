package bepp.com.bepp.utils;

import bepp.com.bepp.services.LoginUserService;
import bepp.com.bepp.services.RetrofitClientService;

/**
 * Created by juancarlosromero on 11/09/17.
 */

public class LoginUserUtil {

    private LoginUserUtil() {}

    public static final String BASE_URL = "http://201.150.45.17/servicio/src/public/index.php/usuario/";

    public static LoginUserService getRetrofitService() {

        return RetrofitClientService.getClient(BASE_URL).create(LoginUserService.class);
    }

}

