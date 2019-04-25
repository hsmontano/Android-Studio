package com.example.henrystivent.misceapp;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Categoria extends AppCompatActivity implements View.OnClickListener {

    EditText cNombre, cDescripcion, cFecha;
    Button cBuscar, cRegistrar, cActualizar, cBorrar;
    TextView cResultado;

    ObtenerCategoria categoria;

    JSONParser jsonParser = new JSONParser();

    private static final String URL = "http://192.168.1.42/prueba";
    private static final String CONSULTAR_CATEGORIA = URL + "/consultar_categoria.php";
    private static final String REGISTRAR_CATEGORIA = URL + "/register_categoria.php";
    private static final String MODIFICAR_CATEGORIA = URL + "/modificar_categoria.php";
    private static final String ELIMINAR_CATEGORIA  = URL + "/eliminar_categoria.php";

    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";
    private static final String TAG_CATEGORIA = "categoria";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categoria);

        cNombre = (EditText)findViewById(R.id.txt_nombreC);
        cDescripcion = (EditText)findViewById(R.id.txt_descripcionC);
        cFecha = (EditText)findViewById(R.id.txt_fechaC);

        cResultado = (TextView)findViewById(R.id.lbl_cResultado);

        cBuscar = (Button)findViewById(R.id.btn_aBuscar);
        cRegistrar = (Button)findViewById(R.id.btn_aRegistrar);
        cActualizar = (Button)findViewById(R.id.btn_aActualizar);
        cBorrar = (Button)findViewById(R.id.btn_aBorrar);

        cBuscar.setOnClickListener(this);
        cRegistrar.setOnClickListener(this);
        cActualizar.setOnClickListener(this);
        cBorrar.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_aBuscar:
                String nombre_b = cNombre.getText().toString();
                categoria = new ObtenerCategoria();
                categoria.execute(CONSULTAR_CATEGORIA, "1", nombre_b);
                break;
            case R.id.btn_aRegistrar:
                String nombre_r = cNombre.getText().toString();
                String descripcion_r = cDescripcion.getText().toString();
                String fecha_r = cFecha.getText().toString();

                categoria = new ObtenerCategoria();
                categoria.execute(REGISTRAR_CATEGORIA, "2", nombre_r, descripcion_r, fecha_r);
                break;
            case R.id.btn_aActualizar:
                String nombre_a = cNombre.getText().toString();
                String descripcion_a = cDescripcion.getText().toString();
                String fecha_a = cFecha.getText().toString();

                categoria = new ObtenerCategoria();
                categoria.execute(MODIFICAR_CATEGORIA,"3", nombre_a, descripcion_a, fecha_a);
                break;
            case R.id.btn_aBorrar:
                String nombre_d = cNombre.getText().toString();

                categoria = new ObtenerCategoria();
                categoria.execute(ELIMINAR_CATEGORIA,"4", nombre_d);
                break;
        }
    }class ObtenerCategoria extends AsyncTask<String, String, String>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... args) {

            int success;

            String resultado0 = "";
            String resultado1 = "";
            String resultado2 = "";

            String cadena = args[0];

            if (args[1].equals("1")){

                String nombre_c = args[2];

                List<NameValuePair> params = new ArrayList<NameValuePair>();

                try{
                    params.add(new BasicNameValuePair("nombre", nombre_c));

                    Log.d("request!", "starting");

                    JSONObject json = jsonParser.makeHttpRequest(
                            cadena,"POST", params);

                    Log.d("Registering attempt", json.toString());

                    success = json.getInt(TAG_SUCCESS);

                    if (success == 1){

                        JSONArray jsonArray = json.getJSONArray(TAG_CATEGORIA);

                        for (int i = 0; i < jsonArray.length(); i++){

                            JSONObject Json = jsonArray.getJSONObject(i);

                            resultado0 = Json.getString("nombre");
                            resultado1 = Json.getString("descripcion");
                            resultado2 = Json.getString("fecha_creacion");
                        }
                    }else {
                        resultado0 = "no hay registros";
                    }
                }catch (JSONException e){
                    e.getStackTrace();
                }

                return " | " + resultado0 + " | " + resultado1 + " | " + resultado2;

            }else if (args[1].equals("2")){
                String nombre_c = args[2],
                        descripcion_c = args[3],
                        fecha_c = args[4];

                List<NameValuePair> params = new ArrayList<NameValuePair>();

                try{
                    params.add(new BasicNameValuePair("nombre",nombre_c));
                    params.add(new BasicNameValuePair("descripcion",descripcion_c));
                    params.add(new BasicNameValuePair("fecha_creacion",fecha_c));

                    Log.d("request!", "starting");

                    JSONObject json = jsonParser.makeHttpRequest(
                            cadena, "POST", params);

                    Log.d("Registering attempt", json.toString());

                    success = json.getInt(TAG_SUCCESS);

                    if (success == 1){
                        Log.d("Category Created!", json.toString());
                        finish();
                        return json.getString(TAG_MESSAGE);
                    } else{
                        Log.d("Registering Failure!", json.getString(TAG_MESSAGE));
                        return json.getString(TAG_MESSAGE);
                    }
                }catch (JSONException e){
                    e.getStackTrace();
                }

            }else if (args[1].equals("3")){

                String nombre_c = args[2],
                        descripcion_c = args[3],
                        fecha_c = args[4];

                List<NameValuePair> params = new ArrayList<NameValuePair>();

                try{
                    params.add(new BasicNameValuePair("nombre",nombre_c));
                    params.add(new BasicNameValuePair("descripcion",descripcion_c));
                    params.add(new BasicNameValuePair("fecha_creacion",fecha_c));

                    Log.d("request!", "starting");

                    JSONObject json = jsonParser.makeHttpRequest(
                            cadena, "POST", params);

                    Log.d("Registering attempt", json.toString());

                    success = json.getInt(TAG_SUCCESS);

                    if (success == 1){
                        Log.d("Category Modificada!", json.toString());
                        finish();
                        return json.getString(TAG_MESSAGE);
                    } else{
                        Log.d("Registering Failure!", json.getString(TAG_MESSAGE));
                        return json.getString(TAG_MESSAGE);
                    }
                }catch (JSONException e){
                    e.getStackTrace();
                }
            }else if (args[1].equals("4")){

                String nombre_c = args[2];

                List<NameValuePair> params = new ArrayList<NameValuePair>();

                try{
                    params.add(new BasicNameValuePair("nombre", nombre_c));

                    Log.d("request!", "starting");

                    JSONObject json = jsonParser.makeHttpRequest(
                            cadena,"POST", params);

                    Log.d("Registering attempt", json.toString());

                    success = json.getInt(TAG_SUCCESS);

                    if (success == 1){
                        Log.d("Delete!", json.toString());
                        return json.getString(TAG_MESSAGE);
                    }else {
                        Log.d("Not register!", json.getString(TAG_MESSAGE));
                        return json.getString(TAG_MESSAGE);
                    }
                }catch (JSONException e){
                    e.getStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            cResultado.setText(s);
        }
    }
}
