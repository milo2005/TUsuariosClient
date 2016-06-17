package unicauca.movil.movilclient;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import unicauca.movil.movilclient.databinding.ActivityLoginBinding;
import unicauca.movil.movilclient.net.HttpAsyncTask;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener, HttpAsyncTask.OnHttpResponse {

    ActivityLoginBinding  binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.setOnClick(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_login:
                login();
                break;
            case R.id.btn_reg:
                Intent intent = new Intent(this, RegActivity.class);
                startActivity(intent);
                break;
        }
    }

    private void login() {
        String url = getString(R.string.url)+getString(R.string.url_login);
        HttpAsyncTask task = new HttpAsyncTask(HttpAsyncTask.POST, this);

        JsonObject json = new JsonObject();
        json.addProperty("usuario", binding.usr.getText().toString());
        json.addProperty("password", binding.pass.getText().toString());

        task.execute(url, json.toString());
    }

    @Override
    public void onResponse(String response, int error) {

        try {
            JSONObject json = new JSONObject(response);
            boolean success = json.getBoolean("success");
            if(success){
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
            }else{
                Toast.makeText(this, "Usuario o password incorrectos", Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
