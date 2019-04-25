package com.example.henrystivent.misceapp;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Usuario extends AppCompatActivity implements View.OnClickListener {

    EditText uCedula, uNombre, uApellido, uEmail, uTelefono, uClave, uEstado, uRol;
    Button uBuscar, uRegistrar, uActualizar, uBorrar;
    private TextView result;
    // Progress Dialog
    private ProgressDialog pDialog;

    // JSON parser class
    JSONParser jsonParser = new JSONParser();

    //si lo trabajan de manera local en xxx.xxx.x.x va su ip local
    // private static final String REGISTER_URL = "http://xxx.xxx.x.x:1234/cas/register.php";

    //testing on Emulator:
    private static final String URL = "http://192.168.1.42/prueba";
    //direcciones de los servicios
    private  static final String CONSULTAR_USER = URL + "/consultar_user.php";
    private  static final String REGISTRAR_USER = URL + "/register_user.php";
    private  static final String MODIFICAR_USER = URL + "/modificar_user.php";
    private  static final String ELIMINAR_USER  = URL + "/eliminar_user.php";
    //objeto de la clase obtener servicio
    ObtenerService obtener;
    //ids del objeto json
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";
    private static final String TAG_NOMBRE = "usuario";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuario);

        //enlazamos los componentes de tipo EditText
        uCedula = (EditText)findViewById(R.id.txt_cedula);
        uNombre = (EditText)findViewById(R.id.txt_nombre);
        uApellido = (EditText)findViewById(R.id.txt_apellido);
        uEmail = (EditText)findViewById(R.id.txt_email);
        uTelefono = (EditText)findViewById(R.id.txt_telefono);
        uClave = (EditText)findViewById(R.id.txt_clave);
        uEstado = (EditText)findViewById(R.id.txt_estado);
        uRol = (EditText)findViewById(R.id.txt_rol);
        //enlazamos componete de tipo TextView
        result = (TextView)findViewById(R.id.lbl_registros);
        //enlazamos los componentes de tipo Button
        uBuscar = (Button)findViewById(R.id.btn_buscar);
        uRegistrar = (Button)findViewById(R.id.btn_registrar);
        uActualizar = (Button)findViewById(R.id.btn_actualizar);
        uBorrar = (Button)findViewById(R.id.btn_borrar);
        //ponemos en escucha los botones...
        uBuscar.setOnClickListener(this);
        uRegistrar.setOnClickListener(this);
        uActualizar.setOnClickListener(this);
        uBorrar.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            //Opcion buscar usuario que envia como parametros la cedula del usuario
            //la URL y el argumento
            case R.id.btn_buscar:
                String cc = uCedula.getText().toString();
                obtener = new ObtenerService();
                obtener.execute(CONSULTAR_USER, "1", cc);
                break;
            //Opcion registrar usuario que envia como parametros los datos del usuario que ingreso desde el formulario
            //la URL y el argumento
            case R.id.btn_registrar:
                String id = null;
                String cedula   =  uCedula.getText().toString();
                String nombre = uNombre.getText().toString();
                String apellido =  uApellido.getText().toString();
                String email =  uEmail.getText().toString();
                String telefono =  uTelefono.getText().toString();
                String clave =  uClave.getText().toString();
                String estado =  uEstado.getText().toString();
                String rol =  uRol.getText().toString();

                obtener = new ObtenerService();
                obtener.execute(REGISTRAR_USER , "2", id, cedula, nombre, apellido, email, telefono, clave, estado, rol);
                break;
            //Opcion actualizar datos del usuario que envia como parametros la cedula del usuario
            //la URL y el argumento
            case R.id.btn_actualizar:
                String A_id = null;
                String A_cedula   =  uCedula.getText().toString();
                String A_nombre = uNombre.getText().toString();
                String A_apellido =  uApellido.getText().toString();
                String A_email =  uEmail.getText().toString();
                String A_telefono =  uTelefono.getText().toString();
                String A_clave =  uClave.getText().toString();
                String A_estado =  uEstado.getText().toString();
                String A_rol =  uRol.getText().toString();

                obtener = new ObtenerService();
                obtener.execute(MODIFICAR_USER , "3", A_id, A_cedula, A_nombre, A_apellido, A_email, A_telefono, A_clave, A_estado, A_rol);
                break;
            //Opcion borrar usuario que envia como parametros la cedula del usuario
            //la URL y el argumento
            case R.id.btn_borrar:
                String ced = uCedula.getText().toString();
                obtener = new ObtenerService();
                obtener.execute(ELIMINAR_USER, "4", ced);
                break;
        }
    }
    class ObtenerService extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            /*pDialog = new ProgressDialog(Usuario.this);
            pDialog.setMessage("Procesando...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();*/
        }
//
        @Override
        protected String doInBackground(String... args){

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
            //valido el argumento correspondiente a la opcion que deseo procesar en este caso seria la opcion 1
            if (args[1].equals("1")){
            //
                String cec = args[2];
                List<NameValuePair> params = new ArrayList<NameValuePair>();
            try {
                params.add(new BasicNameValuePair("cedula", cec));

                Log.d("request!", "starting");

                JSONObject json = jsonParser.makeHttpRequest(
                        cadena,"POST", params);
                Log.d("Registering attempt", json.toString());


                success = json.getInt(TAG_SUCCESS);
                if (success == 1){
                    JSONArray jsonArray = json.getJSONArray(TAG_NOMBRE);

                    for (int i = 0; i < jsonArray.length(); i++){
                        JSONObject Json = jsonArray.getJSONObject(i);

                        resultados = Json.getString("idUsuario");
                        resultado0 = Json.getString("nombre");
                        resultado1 = Json.getString("apellido");
                        resultado3 = Json.getString("email");
                        resultado2 = Json.getString("telefono");
                        resultado4 = Json.getString("clave");
                        resultado5 = Json.getString("estado");
                        resultado6 = Json.getString("nombre_rol");
                    }
                }else{
                        resultados = "no hay registros";
                }
                    }catch (JSONException e){
                        e.getStackTrace();
                }

                return resultado0 + " | " + resultado0 + " \n " + resultado1 + " | " + resultado2 + " \n " + resultado3 + " | " + resultado4 + " \n " + resultado5 + " | " + resultado6;
                //valido el argumento correspondiente a la opcion que deseo procesar en este caso seria la opcion 2
            } else if (args[1].equals("2")){

                String  id = args[2],
                        cedula = args[3],
                        nombre = args[4],
                        apellido = args[5],
                        email = args[6],
                        telefono = args[7],
                        clave = args[8],
                        estado = args[9],
                        rol = args[10];
                List<NameValuePair> params = new ArrayList<NameValuePair>();

                try{
                    params.add(new BasicNameValuePair("idUsuario", id));
                    params.add(new BasicNameValuePair("cedula", cedula));
                    params.add(new BasicNameValuePair("nombre", nombre));
                    params.add(new BasicNameValuePair("apellido", apellido));
                    params.add(new BasicNameValuePair("email", email));
                    params.add(new BasicNameValuePair("telefono", telefono));
                    params.add(new BasicNameValuePair("clave", clave));
                    params.add(new BasicNameValuePair("estado", estado));
                    params.add(new BasicNameValuePair("rol_id", rol));

                    Log.d("request!", "starting");

                    //Posting user data to script
                    JSONObject json = jsonParser.makeHttpRequest(
                            cadena, "POST", params);

                    // full json response
                    Log.d("Registering attempt", json.toString());

                    // json success element
                    success = json.getInt(TAG_SUCCESS);
                    if (success == 1) {
                        Log.d("User Created!", json.toString());
                        finish();
                        return json.getString(TAG_MESSAGE);
                    }else{
                        Log.d("Registering Failure!", json.getString(TAG_MESSAGE));
                        return json.getString(TAG_MESSAGE);

                    }
                }catch (JSONException e){
                    e.getStackTrace();
                }
                //valido el argumento correspondiente a la opcion que deseo procesar en este caso seria la opcion 3
            }else if(args [1].equals("3")){
                String  A_id = args[2],
                        A_cedula = args[3],
                        A_nombre = args[4],
                        A_apellido = args[5],
                        A_email = args[6],
                        A_telefono = args[7],
                        A_clave = args[8],
                        A_estado = args[9],
                        A_rol = args[10];
                List<NameValuePair> params = new ArrayList<NameValuePair>();

                try{
                    params.add(new BasicNameValuePair("idUsuario", A_id));
                    params.add(new BasicNameValuePair("cedula", A_cedula));
                    params.add(new BasicNameValuePair("nombre", A_nombre));
                    params.add(new BasicNameValuePair("apellido", A_apellido));
                    params.add(new BasicNameValuePair("email", A_email));
                    params.add(new BasicNameValuePair("telefono", A_telefono));
                    params.add(new BasicNameValuePair("clave", A_clave));
                    params.add(new BasicNameValuePair("estado", A_estado));
                    params.add(new BasicNameValuePair("rol_id", A_rol));

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

                //valido el argumento correspondiente a la opcion que deseo procesar en este caso seria la opcion 4
            }else if (args [1].equals("4")){
                String cec = args[2];
                List<NameValuePair> params = new ArrayList<NameValuePair>();
                try {
                    params.add(new BasicNameValuePair("cedula", cec));

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
        protected void onPostExecute(String s) {
            // dismiss the dialog once product deleted
            /*
            pDialog.dismiss();
            if (file_url != null) {
                Toast.makeText(Usuario.this, file_url, Toast.LENGTH_LONG).show();
            }*/
            result.setText(s);
        }
    }
}
