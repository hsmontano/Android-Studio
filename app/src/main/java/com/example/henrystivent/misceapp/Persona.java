package com.example.henrystivent.misceapp;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class Persona extends AppCompatActivity implements View.OnClickListener {

    Button buscar, registrar, actualizar, borrar;
    EditText pCc, pNombre, pApellido, pFecha_nac, pTelefono, pEmail, pDireccion, pTipo;
    TextView pResult;

    private ProgressDialog pDialog;

    // JSON parser class
    JSONParser jsonParser = new JSONParser();
    //testing on Emulator:
    private static final String URL = "http://192.168.1.42/prueba";
    private static final String CONSULTAR_PERSONA = URL + "/consultar_person.php";
    private static final String REGISTRAR_PERSONA = URL + "/register_person.php";
    private static final String MODIFICAR_PERSONA = URL + "/modificar_person.php";
    private static final String ELIMINAR_PERSONA  = URL + "/eliminar_person.php";

    CreatePersona obtener;
    //ids
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";
    private static final String TAG_PERSONA = "persona";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_persona);

        //enlaso los editText por medio del id
        pCc = (EditText)findViewById(R.id.txt_cedula);
        pNombre = (EditText)findViewById(R.id.txt_nombre);
        pApellido = (EditText)findViewById(R.id.txt_apellido);
        pFecha_nac = (EditText)findViewById(R.id.txt_fecha);
        pTelefono = (EditText)findViewById(R.id.txt_telefono);
        pEmail = (EditText)findViewById(R.id.txt_email);
        pDireccion = (EditText)findViewById(R.id.txt_direccion);
        pTipo = (EditText)findViewById(R.id.txt_tipo);

        //enlazamos el componente de tipo TextView
        pResult = (TextView) findViewById(R.id.lbl_Pregistros);

        //Enlaso los botones por medio del id
        buscar = (Button)findViewById(R.id.btn_buscar);
        registrar = (Button)findViewById(R.id.btn_registrar);
        actualizar = (Button)findViewById(R.id.btn_actualizar);
        borrar = (Button)findViewById(R.id.btn_borrar);

        //ponemos en escucha los botones
        buscar.setOnClickListener(this);
        registrar.setOnClickListener(this);
        actualizar.setOnClickListener(this);
        borrar.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_buscar:
                String bIden = pCc.getText().toString();
                obtener = new CreatePersona();
                obtener.execute(CONSULTAR_PERSONA, "1", bIden);
                break;
            case R.id.btn_registrar:
                String id = null;
                String identificacion = pCc.getText().toString();
                String nombre = pNombre.getText().toString();
                String apellido = pApellido.getText().toString();
                String fecha = pFecha_nac.getText().toString();
                String telefono = pTelefono.getText().toString();
                String email = pEmail.getText().toString();
                String direccion = pDireccion.getText().toString();
                String tipo = pTipo.getText().toString();

                obtener = new CreatePersona();
                obtener.execute(REGISTRAR_PERSONA, "2", id, identificacion, nombre, apellido, fecha, telefono, email, direccion, tipo);
                break;
            case R.id.btn_actualizar:
                String P_id = null;
                String P_cedula   =  pCc.getText().toString();
                String P_nombre = pNombre.getText().toString();
                String P_apellido =  pApellido.getText().toString();
                String P_fecha =  pFecha_nac.getText().toString();
                String P_telefono =  pTelefono.getText().toString();
                String P_email =  pEmail.getText().toString();
                String P_direccion =  pDireccion.getText().toString();
                String P_tipo =  pTipo.getText().toString();

                obtener = new CreatePersona();
                obtener.execute(MODIFICAR_PERSONA , "3", P_id, P_cedula, P_nombre, P_apellido, P_fecha, P_telefono, P_email, P_direccion, P_tipo);
                break;
            case R.id.btn_borrar:
                String iden = pCc.getText().toString();
                obtener = new CreatePersona();
                obtener.execute(ELIMINAR_PERSONA, "4", iden);
                break;
        }
    }
    class CreatePersona extends AsyncTask<String, String, String>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            /*pDialog = new ProgressDialog(Persona.this);
            pDialog.setMessage("Procesando...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();*/
        }
        @Override
        protected String doInBackground(String... args) {

            int success;

            String resultados = "";
            String resultado0 = "";
            String resultado1 = "";
            String resultado2 = "";
            String resultado3 = "";
            String resultado4 = "";
            String resultado5 = "";
            String resultado6 = "";

            String cadena = args[0];

            if (args[1].equals("1")){

                String bIden = args[2];
                List<NameValuePair> params = new ArrayList<NameValuePair>();
                    try {
                        params.add(new BasicNameValuePair("identificacion", bIden));

                        Log.d("request!", "starting");

                        JSONObject json = jsonParser.makeHttpRequest(
                                cadena,"POST", params);
                        Log.d("Registering attempt", json.toString());

                        success = json.getInt(TAG_SUCCESS);
                        if (success == 1){
                            JSONArray jsonArray = json.getJSONArray(TAG_PERSONA);

                            for (int i = 0; i < jsonArray.length(); i++){

                                JSONObject Json = jsonArray.getJSONObject(i);

                                resultados = Json.getString("idPersona");
                                resultado0  = Json.getString("nombre");
                                resultado1 = Json.getString("apellido");
                                resultado2 = Json.getString("fecha_nac");
                                resultado3 = Json.getString("telefono");
                                resultado4 = Json.getString("email");
                                resultado5 = Json.getString("direccion");
                                resultado6 = Json.getString("tipo");
                            }
                        }else{
                            resultados = "no hay registros";
                        }
                    }catch (JSONException e){
                    e.getStackTrace();
                }

                return resultados + " | " + resultado0 + " \n " + resultado1 + " | " + resultado2 + " \n " + resultado3 + " | " + resultado4 + " \n " + resultado5 + " | " + resultado6;

            } else if (args[1].equals("2")){

                String  id = args[2],
                        identificacion = args[3],
                        nombre = args[4],
                        apellido = args[5],
                        fecha_nac = args[6],
                        telefono = args[7],
                        email = args[8],
                        direccion = args[9],
                        tipo = args[10];

                List<NameValuePair> params = new ArrayList<NameValuePair>();

                try{
                    params.add(new BasicNameValuePair("idPersona", id));
                    params.add(new BasicNameValuePair("identificacion", identificacion));
                    params.add(new BasicNameValuePair("nombre", nombre));
                    params.add(new BasicNameValuePair("apellido", apellido));
                    params.add(new BasicNameValuePair("fecha_nac", fecha_nac));
                    params.add(new BasicNameValuePair("telefono", telefono));
                    params.add(new BasicNameValuePair("email", email));
                    params.add(new BasicNameValuePair("direccion", direccion));
                    params.add(new BasicNameValuePair("tipo_id", tipo));

                    Log.d("request!", "starting");

                    //Posting user data to script
                    JSONObject json = jsonParser.makeHttpRequest(
                            cadena, "POST", params);

                    // full json response
                    Log.d("Registering attempt", json.toString());

                    // json success element
                    success = json.getInt(TAG_SUCCESS);

                    if (success == 1) {
                        Log.d("Person Created!", json.toString());
                        finish();
                        return json.getString(TAG_MESSAGE);
                    }else{
                        Log.d("Registering Failure!", json.getString(TAG_MESSAGE));
                        return json.getString(TAG_MESSAGE);

                    }
                }catch (JSONException e){
                    e.getStackTrace();
                }
            }else if (args[1].equals("3")){

                String  P_id = args[2],
                        P_cedula = args[3],
                        P_nombre = args[4],
                        P_apellido = args[5],
                        P_fecha = args[6],
                        P_telefono = args[7],
                        P_email = args[8],
                        P_direccion = args[9],
                        P_tipo = args[10];
                List<NameValuePair> params = new ArrayList<NameValuePair>();

                try{
                    params.add(new BasicNameValuePair("idPersona", P_id));
                    params.add(new BasicNameValuePair("identificacion", P_cedula));
                    params.add(new BasicNameValuePair("nombre", P_nombre));
                    params.add(new BasicNameValuePair("apellido", P_apellido));
                    params.add(new BasicNameValuePair("fecha_nac", P_fecha));
                    params.add(new BasicNameValuePair("telefono", P_telefono));
                    params.add(new BasicNameValuePair("email", P_email));
                    params.add(new BasicNameValuePair("direccion", P_direccion));
                    params.add(new BasicNameValuePair("tipo_id", P_tipo));

                    Log.d("request!", "starting");

                    //Posting user data to script
                    JSONObject json = jsonParser.makeHttpRequest(
                            cadena, "POST", params);

                    // full json response
                    Log.d("Registering attempt", json.toString());

                    // json success element
                    success = json.getInt(TAG_SUCCESS);
                    if (success == 1) {
                        Log.d("User Update!", json.toString());
                        finish();
                        return json.getString(TAG_MESSAGE);
                    }else{
                        Log.d("Registering Failure!", json.getString(TAG_MESSAGE));
                        return json.getString(TAG_MESSAGE);

                    }
                }catch (JSONException e){
                    e.getStackTrace();
                }

            }else if (args[1].equals("4")){
                String pIde = args[2];
                List<NameValuePair> params = new ArrayList<NameValuePair>();
                try {
                    params.add(new BasicNameValuePair("identificacion", pIde));

                    Log.d("request!", "starting");

                    JSONObject json = jsonParser.makeHttpRequest(
                            cadena,"POST", params);
                    Log.d("Registering attempt", json.toString());

                    success = json.getInt(TAG_SUCCESS);

                    if (success == 1){
                        Log.d("Delete!", json.toString());
                        return json.getString(TAG_MESSAGE);
                    }else{
                        Log.d("Not register!", json.getString(TAG_MESSAGE));
                        return json.getString(TAG_MESSAGE);
                    }
                }catch (JSONException e){
                    e.getStackTrace();
                }
            }
            return null;
        }
        protected void onPostExecute(String p) {
            // dismiss the dialog once product deleted
           /* pDialog.dismiss();
            if (file_url != null) {
                Toast.makeText(Persona.this, file_url, Toast.LENGTH_LONG).show();
            }*/
           pResult.setText(p);
        }
    }
}
