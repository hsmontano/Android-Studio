package com.example.henrystivent.misceapp;

import android.os.AsyncTask;
import android.provider.Settings;
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

public class Productos extends AppCompatActivity implements View.OnClickListener {

    EditText pnombre, pdescripcion, pvalor_c, pvalor_v, pcantidad, pestado, pcategoria, pusuario;
    Button Buscar, Registrar, Actualizar, Borrar;
    TextView resultad;

    ObtenerProducto ob_producto;

    JSONParser jsonParser = new JSONParser();

    private static final String URL = "http://192.168.1.42/prueba";
    private static final String CONSULTAR_PRODUCTO = URL + "/consultar_producto.php";
    private static final String REGISTRAR_PRODUCTO = URL + "/register_producto.php";
    private static final String MODIFICAR_PRODUCTO = URL + "/modificar_producto.php";
    private static final String ELIMINAR_PRODUCTO = URL + "/eliminar_producto.php";

    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";
    private static final String TAG_PRODUCTO = "producto";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_productos);

        //enlazo los EditText que tenemos en el xml por medio del id
        pnombre = (EditText)findViewById(R.id.txt_nomproducto);
        pdescripcion = (EditText)findViewById(R.id.txt_desproducto);
        pvalor_c = (EditText)findViewById(R.id.txt_valorcompra);
        pvalor_v = (EditText)findViewById(R.id.txt_valorventa);
        pcantidad = (EditText)findViewById(R.id.txt_cantproducto);
        pestado = (EditText)findViewById(R.id.txt_estado);
        pcategoria = (EditText)findViewById(R.id.txt_categoria);
        pusuario = (EditText)findViewById(R.id.txt_usuario);

        resultad = (TextView) findViewById(R.id.lbl_resultado);

        //enlazo los botones que tenemos en el xml por medio del id
        Buscar = (Button)findViewById(R.id.btn_pBuscar);
        Registrar = (Button)findViewById(R.id.btn_pRegistrar);
        Actualizar = (Button)findViewById(R.id.btn_pActualizar);
        Borrar = (Button)findViewById(R.id.btn_pBorrar);

        Buscar.setOnClickListener(this);
        Registrar.setOnClickListener(this);
        Actualizar.setOnClickListener(this);
        Borrar.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.btn_pBuscar:
                String nombre_p = pnombre.getText().toString();
                ob_producto = new ObtenerProducto();
                ob_producto.execute(CONSULTAR_PRODUCTO, "1", nombre_p);
                break;
            case R.id.btn_pRegistrar:
                String nombre_r = pnombre.getText().toString();
                String descripcion_r = pdescripcion.getText().toString();
                String valor_rC = pvalor_c.getText().toString();
                String valor_rV = pvalor_v.getText().toString();
                String cantidad_r = pcantidad.getText().toString();
                String estado_r = pestado.getText().toString();
                String categoria_r = pcategoria.getText().toString();
                String usuario_r = pusuario.getText().toString();

                ob_producto = new ObtenerProducto();
                ob_producto.execute(REGISTRAR_PRODUCTO, "2", nombre_r, descripcion_r, valor_rC, valor_rV, cantidad_r, estado_r, categoria_r, usuario_r);
                break;
            case R.id.btn_pActualizar:
                String nombre_a = pnombre.getText().toString();
                String descripcion_a = pdescripcion.getText().toString();
                String valor_aC = pvalor_c.getText().toString();
                String valor_aV = pvalor_v.getText().toString();
                String cantidad_a = pcantidad.getText().toString();
                String estado_a = pestado.getText().toString();
                String categoria_a = pcategoria.getText().toString();
                String usuario_a = pusuario.getText().toString();

                ob_producto = new ObtenerProducto();
                ob_producto.execute(MODIFICAR_PRODUCTO, "3", nombre_a, descripcion_a, valor_aC, valor_aV, cantidad_a, estado_a, categoria_a, usuario_a);
                break;
            case R.id.btn_pBorrar:
                String nombre_b = pnombre.getText().toString();
                ob_producto = new ObtenerProducto();
                ob_producto.execute(ELIMINAR_PRODUCTO, "4", nombre_b);
                break;
        }
    }
    class ObtenerProducto extends AsyncTask<String, String, String>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... args) {

            int success;
            String resultado = "";
            String resultado0 = "";
            String resultado1 = "";
            String resultado2 = "";
            String resultado3 = "";
            String resultado4 = "";
            String resultado5 = "";
            String resultado6 = "";
            String resultado7 = "";

            String cadena = args[0];

            if (args[1].equals("1")){

                String nombre_p = args[2];

                List<NameValuePair> params = new ArrayList<NameValuePair>();

                try{
                    params.add(new BasicNameValuePair("nombre", nombre_p));

                    Log.d("request!", "starting");

                    JSONObject json = jsonParser.makeHttpRequest(
                            cadena,"POST", params);

                    Log.d("Registering attempt", json.toString());

                    success = json.getInt(TAG_SUCCESS);

                    if (success == 1){

                        JSONArray jsonArray = json.getJSONArray(TAG_PRODUCTO);

                        for (int i = 0; i < jsonArray.length(); i++){

                            JSONObject Json = jsonArray.getJSONObject(i);

                            resultado  = Json.getString("idProductos");
                            resultado0 = Json.getString("nombre");
                            resultado1 = Json.getString("descripcion");
                            resultado2 = Json.getString("valor_compra");
                            resultado3 = Json.getString("valor_venta");
                            resultado4 = Json.getString("cantidad");
                            resultado5 = Json.getString("usuario");
                            resultado6 = Json.getString("estado");
                            resultado7 = Json.getString("categoria");
                        }
                    }else{
                        resultado = "no hay registros";
                    }

                }catch (JSONException e){
                    e.getStackTrace();
                }

                return resultado + " | " + resultado0 + " | " + resultado1 + "\n" + resultado2 + " | " + resultado3 + "\n" + resultado4 + " | " + resultado5 + "\n" + resultado6 + " | " + resultado7;

            }else if (args[1].equals("2")){

                String nombre_r = args[2],
                        descripcion_r = args[3],
                        valor_rC = args[4],
                        valor_rV = args[5],
                        cantidad_r = args[6],
                        estado_r = args[7],
                        categoria_r = args[8],
                        usuario_r = args[9];

                List<NameValuePair> params = new ArrayList<NameValuePair>();

                    try{
                        params.add(new BasicNameValuePair("nombre", nombre_r));
                        params.add(new BasicNameValuePair("descripcion", descripcion_r));
                        params.add(new BasicNameValuePair("valor_compra", valor_rC));
                        params.add(new BasicNameValuePair("valor_venta", valor_rV));
                        params.add(new BasicNameValuePair("cantidad", cantidad_r));
                        params.add(new BasicNameValuePair("estado_id", estado_r));
                        params.add(new BasicNameValuePair("categorias_id", categoria_r));
                        params.add(new BasicNameValuePair("usuario_id", usuario_r));

                        Log.d("request!", "starting");

                        JSONObject json = jsonParser.makeHttpRequest(
                                cadena, "POST", params);

                        Log.d("Registering attempt", json.toString());

                        success = json.getInt(TAG_SUCCESS);

                        if (success == 1){
                            Log.d("Product Created!", json.toString());
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

                String nombre_a = args[2],
                        descripcion_a = args[3],
                        valor_aC = args[4],
                        valor_aV = args[5],
                        cantidad_a = args[6],
                        estado_a = args[7],
                        categoria_a = args[8],
                        usuario_a = args[9];

                List<NameValuePair> params = new ArrayList<NameValuePair>();

                    try{
                        params.add(new BasicNameValuePair("nombre",nombre_a ));
                        params.add(new BasicNameValuePair("descripcion",descripcion_a ));
                        params.add(new BasicNameValuePair("valor_compra",valor_aC ));
                        params.add(new BasicNameValuePair("valor_venta",valor_aV ));
                        params.add(new BasicNameValuePair("cantidad",cantidad_a ));
                        params.add(new BasicNameValuePair("estado_id",estado_a ));
                        params.add(new BasicNameValuePair("categorias_id",categoria_a ));
                        params.add(new BasicNameValuePair("usuario_id",usuario_a ));

                        Log.d("request!", "starting");

                        JSONObject json = jsonParser.makeHttpRequest(
                                cadena, "POST", params);

                        Log.d("Registering attempt", json.toString());

                        success = json.getInt(TAG_SUCCESS);

                        if (success == 1){
                            Log.d("Product Update!", json.toString());
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

                String nombre_b = args[2];

                List<NameValuePair> params = new ArrayList<NameValuePair>();

                    try{
                        params.add(new BasicNameValuePair("nombre", nombre_b));

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
        protected void onPostExecute(String d) {
            super.onPostExecute(d);

            resultad.setText(d);
        }

    };
}
