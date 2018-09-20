package bepp.com.bepp.models;

import java.util.List;

/**
 * Created by admin on 21/09/17.
 */

public class ResponseList<T> extends BasicResponse {
    private List<T> listado;

    public List<T> getListado() {
        return listado;
    }

    public void setListado(List<T> listado) {
        this.listado = listado;
    }
}
