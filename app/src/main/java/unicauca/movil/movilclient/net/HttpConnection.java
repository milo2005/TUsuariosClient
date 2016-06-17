package unicauca.movil.movilclient.net;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Dario Chamorro on 24/05/2016.
 */
public class HttpConnection {

    static final int TIMEOUT_READ = 10000;
    static final int TIMEOUT_CON = 7000;

    public HttpResponse get(String url) throws IOException {
        URL u = new URL(url);
        HttpURLConnection con = (HttpURLConnection) u.openConnection();

        con.setRequestMethod("GET");
        con.setDoInput(true);

        con.setReadTimeout(TIMEOUT_READ);
        con.setConnectTimeout(TIMEOUT_CON);

        con.connect();

        InputStream in = con.getInputStream();

        HttpResponse response = new HttpResponse();
        response.setStatus(con.getResponseCode());
        response.setMsg(streamToString(in));

        return response;
    }

    public HttpResponse post(String url, String json) throws IOException {
        return request("POST", url, json);
    }

    public HttpResponse put(String url, String json) throws IOException {
        return request("PUT", url, json);
    }

    public HttpResponse delete(String url, String json) throws IOException {
        return request("DELETE", url, json);
    }

    private HttpResponse request(String method, String url, String json) throws IOException {

        URL u = new URL(url);
        HttpURLConnection con = (HttpURLConnection) u.openConnection();

        con.setRequestMethod(method);
        con.setDoInput(true);

        con.setReadTimeout(TIMEOUT_READ);
        con.setConnectTimeout(TIMEOUT_CON);

        if(json!=null)
            con.setDoOutput(true);

        con.setRequestProperty("Content-Type","application/json");
        con.connect();

        if(json!=null){
            DataOutputStream out = new DataOutputStream(con.getOutputStream());
            out.write(json.getBytes());
            out.flush();
            out.close();
        }

        InputStream in = con.getInputStream();

        HttpResponse response = new HttpResponse();
        response.setStatus(con.getResponseCode());
        response.setMsg(streamToString(in));
        return response;
    }

    private String streamToString(InputStream in) throws IOException {
        InputStreamReader reader = new InputStreamReader(in);
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        int ch;
        while((ch = reader.read())!= -1){
            out.write(ch);
        }

        String rta = new String(out.toByteArray());

        return rta;
    }


}
