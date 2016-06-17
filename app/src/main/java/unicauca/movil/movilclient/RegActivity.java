package unicauca.movil.movilclient;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import unicauca.movil.movilclient.databinding.ActivityRegBinding;
import unicauca.movil.movilclient.net.HttpAsyncTask;

public class RegActivity extends AppCompatActivity implements View.OnClickListener, HttpAsyncTask.OnHttpResponse {

    ActivityRegBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.setOnClick(this);
    }

    @Override
    public void onClick(View v) {
        String url = getString(R.string.url)+getString(R.string.url_post);
        JsonObject json = new JsonObject();
        json.addProperty("nombre", binding.nombre.getText().toString());
        json.addProperty("correo", binding.correo.getText().toString());
        json.addProperty("usuario", binding.usuario.getText().toString());
        json.addProperty("password", binding.pass.getText().toString());

        HttpAsyncTask task = new HttpAsyncTask(HttpAsyncTask.POST, this);
        task.execute(url, json.toString());
    }

    @Override
    public void onResponse(String response, int error) {
        try {
            JSONObject json = new JSONObject(response);
            boolean success = json.getBoolean("success");
            if(success){
                Toast.makeText(this, "Registro Exitoso", Toast.LENGTH_SHORT).show();
                finish();
            }else{
                Toast.makeText(this, "Error al registrarse", Toast.LENGTH_SHORT).show();
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
