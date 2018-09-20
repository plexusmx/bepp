package bepp.com.bepp.utils;

import bepp.com.bepp.services.RecoverPassService;
import bepp.com.bepp.services.RetrofitClientService;

/**
 * Created by juancarlosromero on 11/09/17.
 */

public class RecoverPassUtil {

    private RecoverPassUtil() {}

    public static final String BASE_URL = "http://201.150.45.17/servicio/src/public/index.php/usuario/";

    public static RecoverPassService getRetrofitService() {

        return RetrofitClientService.getClient(BASE_URL).create(RecoverPassService.class);
    }

}

