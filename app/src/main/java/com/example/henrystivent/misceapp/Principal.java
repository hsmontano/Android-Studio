package com.example.henrystivent.misceapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Principal extends AppCompatActivity implements View.OnClickListener {

    EditText email, clave;
    Button ingresar;

    private ProgressDialog pDialog;

    // Clase JSONParser
    JSONParser jsonParser = new JSONParser();


    private static final String LOGIN_URL = "http://192.168.1.42/prueba/login.php";

    // La respuesta del JSON es
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";
    private static final String TAG_ROL = "datos";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        email = (EditText) findViewById(R.id.txt_email);
        clave = (EditText) findViewById(R.id.txt_clave);

        ingresar = (Button) findViewById(R.id.btn_ingresar);

        ingresar.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String username =  email.getText().toString();
        String password =  clave.getText().toString();

        new AttemptLogin().execute(username, password);
    }
    class AttemptLogin extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(Principal.this);
            pDialog.setMessage("Iniciando sesión...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... args) {

            int success;

            try {
                // construyendo los  Parametros
                String username = args[0],
                        password = args[1];
                //declaramos una variable de tipo List
                List<NameValuePair> params = new ArrayList<NameValuePair>();
                //agregamos los parametros a nuestra variable params...
                params.add(new BasicNameValuePair("email", username));
                params.add(new BasicNameValuePair("clave", password));
                Log.d("request!", "starting");
                // getting product details by making HTTP request
                JSONObject json = jsonParser.makeHttpRequest(LOGIN_URL, "POST", params);

                // check your log for json response
                Log.d("Login attempt", json.toString());


                JSONObject rol = json.getJSONObject(TAG_ROL);

                String Rol = rol.getString("rol");
                // json success tag
                success = json.getInt(TAG_SUCCESS);

                Intent i;

                        if (success == 1 && Rol.equals("administrador") ) {

                            Log.d("Login Successful!", json.toString());
                            // save user data
                            SharedPreferences spa = PreferenceManager.getDefaultSharedPreferences(Principal.this);
                            SharedPreferences.Editor edit = spa.edit();
                            edit.putString("email", username);
                            edit.apply();

                            i = new Intent(Principal.this, Administrador.class);
                            finish();
                            startActivity(i);
                            return json.getString(TAG_MESSAGE);

                        } else if (success == 1 && Rol.equals("empleado")){

                            Log.d("Login Successful!", json.toString());
                            // save user data
                            SharedPreferences spe = PreferenceManager.getDefaultSharedPreferences(Principal.this);
                            SharedPreferences.Editor edit = spe.edit();
                            edit.putString("email", username);
                            edit.apply();

                            i = new Intent(Principal.this, Empleado.class);
                            finish();
                            startActivity(i);
                            return json.getString(TAG_MESSAGE);

                        }else {

                        Log.d("Login Failure!", json.getString(TAG_MESSAGE));
                        return json.getString(TAG_MESSAGE);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;

        }

        protected void onPostExecute(String file_url) {
            // dismiss the dialog once product deleted
            pDialog.dismiss();
            if (file_url != null) {
                Toast.makeText(Principal.this, file_url, Toast.LENGTH_LONG).show();
            }
        }
    }
}
