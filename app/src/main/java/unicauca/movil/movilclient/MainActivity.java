package unicauca.movil.movilclient;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import unicauca.movil.movilclient.adapters.UsuarioAdapter;
import unicauca.movil.movilclient.databinding.ActivityMainBinding;
import unicauca.movil.movilclient.models.Usuario;
import unicauca.movil.movilclient.net.HttpAsyncTask;

public class MainActivity extends AppCompatActivity implements HttpAsyncTask.OnHttpResponse {

    List<Usuario> data;
    UsuarioAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        data = new ArrayList<>();
        adapter = new UsuarioAdapter(getLayoutInflater(), data);
        binding.setAdapter(adapter);

        String url = getString(R.string.url)+getString(R.string.url_get);
        HttpAsyncTask task = new HttpAsyncTask(HttpAsyncTask.GET,this);
        task.execute(url);

    }


    @Override
    public void onResponse(String response, int error) {
        Gson gson = new Gson();
        //Usuario u = gson.fromJson(response,Usuario.class);

        Type type = new TypeToken<List<Usuario>>(){}.getType();

        List<Usuario> usrs = gson.fromJson(response, type);

        for(Usuario u : usrs){
            data.add(u);
        }

        adapter.notifyDataSetChanged();
    }
}
