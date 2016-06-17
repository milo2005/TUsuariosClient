package unicauca.movil.movilclient.net;

/**
 * Created by Dario Chamorro on 13/06/2016.
 */
public class HttpResponse {

    public static final int NO_ERROR=0;
    public static final int ERROR_TIMEOUT=1;
    public static final int ERROR_404=2;
    public static final int ERROR=3;

    String msg;
    int status;
    int error;

    public int getError() {
        return error;
    }

    public void setError(int error) {
        this.error = error;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
