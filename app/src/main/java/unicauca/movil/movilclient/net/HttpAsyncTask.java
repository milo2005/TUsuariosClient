package unicauca.movil.movilclient.net;

import android.os.AsyncTask;

import java.io.IOException;
import java.net.SocketTimeoutException;

/**
 * Created by Dario Chamorro on 24/05/2016.
 */
public class HttpAsyncTask extends AsyncTask<String, Integer, HttpResponse> {

    public static final int GET = 0;
    public static final int POST = 1;
    public static final int PUT = 2;
    public static final int DELETE = 3;

    public interface OnHttpResponse{
        void onResponse(String response, int error);
    }

    int method;
    OnHttpResponse response;

    public HttpAsyncTask(int method, OnHttpResponse response) {
        this.method = method;
        this.response = response;
    }

    @Override
    protected HttpResponse doInBackground(String... params) {
        HttpConnection con = new HttpConnection();

        HttpResponse rta=null;
        try{
            switch (method){
                case GET:
                    rta = con.get(params[0]);
                    break;
                case POST:
                    rta = con.post(params[0], params[1]);
                    break;
                case PUT:
                    rta = con.put(params[0], params[1]);
                    break;
                case DELETE:
                    rta = con.delete(params[0], params[1]);
                    break;
            }
            if(rta.getStatus() == 404){
                rta.setError(HttpResponse.ERROR_404);
            }
        }catch(SocketTimeoutException e){
            rta = new HttpResponse();
            rta.setError(HttpResponse.ERROR_TIMEOUT);
        }catch(IOException e){
            rta = new HttpResponse();
            rta.setError(HttpResponse.ERROR);
        }
        return rta;
    }

    @Override
    protected void onPostExecute(HttpResponse res) {
        response.onResponse(res.getMsg(), res.getError());
    }
}
