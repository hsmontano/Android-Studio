package com.example.henrystivent.misceapp;

import android.content.Entity;
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

public class Factura extends AppCompatActivity implements View.OnClickListener {

    EditText fIdfactura, fTotal, fPago, fDescuento, fFecha, fUsuario, fCaja, fPersona, fOperacion;
    Button fBuscar, fRegistrar, fActualizar, fBorrar;
    TextView resultado;

    ObtenerFactura ob_factura;

    JSONParser jsonParser = new JSONParser();

    private static final String URL = "http://192.168.1.42/prueba";
    private static final String CONSULTAR_FACTURA = URL + "/consultar_factura.php";
    private static final String REGISTRAR_FACTURA = URL + "/register_factura.php";
    private static final String MODIFICAR_FACTURA = URL + "/modificar_factura.php";
    private static final String ELIMINAR_FACTURA  = URL + "/eliminar_factura.php";

    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";
    private static final String TAG_FACTURA = "factura";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_factura);

        fIdfactura = (EditText)findViewById(R.id.txt_idFactura);
        fTotal = (EditText)findViewById(R.id.txt_tpagar);
        fPago = (EditText)findViewById(R.id.txt_pfactura);
        fDescuento = (EditText)findViewById(R.id.txt_descuento);
        fFecha = (EditText)findViewById(R.id.txt_fecha);
        fUsuario = (EditText)findViewById(R.id.txt_usuario);
        fCaja = (EditText)findViewById(R.id.txt_caja);
        fPersona = (EditText)findViewById(R.id.txt_persona);
        fOperacion = (EditText)findViewById(R.id.txt_operacion);

        resultado = (TextView)findViewById(R.id.lbl_resultado);

        fBuscar = (Button)findViewById(R.id.btn_buscar);
        fRegistrar = (Button)findViewById(R.id.btn_registrar);
        fActualizar = (Button)findViewById(R.id.btn_actualizar);
        fBorrar = (Button)findViewById(R.id.btn_borrar);

        fBuscar.setOnClickListener(this);
        fRegistrar.setOnClickListener(this);
        fActualizar.setOnClickListener(this);
        fBorrar.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_buscar:
                    String id_f = fIdfactura.getText().toString();
                    ob_factura = new ObtenerFactura();
                    ob_factura.execute(CONSULTAR_FACTURA,"1", id_f);
                break;
            case R.id.btn_registrar:
                    String id_r = fIdfactura.getText().toString();
                    String total_r = fTotal.getText().toString();
                    String pago_r = fPago.getText().toString();
                    String descuento_r = fDescuento.getText().toString();
                    String fecha_r = fFecha.getText().toString();
                    String usuario_r = fUsuario.getText().toString();
                    String caja_r = fCaja.getText().toString();
                    String persona_r = fPersona.getText().toString();
                    String operacion_r = fOperacion.getText().toString();

                    ob_factura = new ObtenerFactura();
                    ob_factura.execute(REGISTRAR_FACTURA, "2", id_r,total_r,pago_r,descuento_r, fecha_r, usuario_r, caja_r, persona_r, operacion_r);
                break;
            case R.id.btn_actualizar:
                String id_a = fIdfactura.getText().toString();
                String total_a = fTotal.getText().toString();
                String pago_a = fPago.getText().toString();
                String descuento_a = fDescuento.getText().toString();
                String fecha_a = fFecha.getText().toString();
                String usuario_a = fUsuario.getText().toString();
                String caja_a = fCaja.getText().toString();
                String persona_a = fPersona.getText().toString();
                String operacion_a = fOperacion.getText().toString();

                ob_factura = new ObtenerFactura();
                ob_factura.execute(MODIFICAR_FACTURA, "3", id_a, total_a, pago_a, descuento_a, fecha_a, usuario_a, caja_a, persona_a, operacion_a);
                break;
            case R.id.btn_borrar:
                String id_b = fIdfactura.getText().toString();

                ob_factura = new ObtenerFactura();
                ob_factura.execute(ELIMINAR_FACTURA, "4", id_b);
                break;
        }
    }
    class ObtenerFactura extends AsyncTask<String, String, String>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
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

                String id_f = args[2];

                List<NameValuePair> params = new ArrayList<NameValuePair>();

                    try{
                        params.add(new BasicNameValuePair("idFactura", id_f));

                        Log.d("request!", "starting");

                        JSONObject json = jsonParser.makeHttpRequest(
                                cadena,"POST", params);

                        Log.d("Registering attempt", json.toString());

                        success = json.getInt(TAG_SUCCESS);

                        if (success == 1){

                            JSONArray jsonArray = json.getJSONArray(TAG_FACTURA);

                            for (int i = 0; i < jsonArray.length(); i++){

                                JSONObject Json = jsonArray.getJSONObject(i);

                                resultados  = Json.getString("idFactura");
                                resultado0 = Json.getString("total");
                                resultado1 = Json.getString("pago");
                                resultado2 = Json.getString("descuento");
                                resultado3 = Json.getString("fecha_creacion");
                                resultado4 = Json.getString("persona");
                                resultado5 = Json.getString("usuario");
                                resultado6 = Json.getString("operacion");
                            }
                        }else {
                            resultados = "no hay registros";
                        }
                    }catch (JSONException e){
                    e.getStackTrace();
                }

                return resultados + " | " + resultado0 + " | " + resultado1 + "\n" + resultado2 + " | " + resultado3 + "\n" + resultado4 + " | " + resultado5 + "\n" + resultado6;

            }else if (args[1].equals("2")){
                String  id_r = args[2],
                        total_r = args[3],
                        pago_r = args[4],
                        descuento_r = args[5],
                        fecha_r = args[6],
                        usuario_r = args[7],
                        caja_r = args[8],
                        persona_r = args[9],
                        operacion_r = args[10];

                List<NameValuePair> params = new ArrayList<NameValuePair>();

                    try{
                        params.add(new BasicNameValuePair("idFatura", id_r));
                        params.add(new BasicNameValuePair("total", total_r));
                        params.add(new BasicNameValuePair("pago", pago_r));
                        params.add(new BasicNameValuePair("descuento", descuento_r));
                        params.add(new BasicNameValuePair("fecha_creacion", fecha_r));
                        params.add(new BasicNameValuePair("usuario_id", usuario_r));
                        params.add(new BasicNameValuePair("caja_id", caja_r));
                        params.add(new BasicNameValuePair("persona_id", persona_r));
                        params.add(new BasicNameValuePair("tipo_operacion_id", operacion_r));

                        Log.d("request!", "starting");

                        JSONObject json = jsonParser.makeHttpRequest(
                                cadena, "POST", params);

                        Log.d("Registering attempt", json.toString());

                        success = json.getInt(TAG_SUCCESS);

                        if (success == 1){
                            Log.d("Factura Created!", json.toString());
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
                String id_a = args[2],
                        total_a = args[3],
                        pago_a = args[4],
                        descuento_a = args[5],
                        fecha_a = args[6],
                        usuario_a = args[7],
                        caja_a = args[8],
                        persona_a = args[9],
                        operacion_a = args[10];

                List<NameValuePair> params = new ArrayList<NameValuePair>();

                    try{
                        params.add(new BasicNameValuePair("idFactura",id_a));
                        params.add(new BasicNameValuePair("total",total_a));
                        params.add(new BasicNameValuePair("pago",pago_a));
                        params.add(new BasicNameValuePair("descuento",descuento_a));
                        params.add(new BasicNameValuePair("fecha_creacion",fecha_a));
                        params.add(new BasicNameValuePair("usuario_id",usuario_a));
                        params.add(new BasicNameValuePair("caja_id",caja_a));
                        params.add(new BasicNameValuePair("persona_id",persona_a));
                        params.add(new BasicNameValuePair("tipo_operacion_id",operacion_a));

                        Log.d("request!", "starting");

                        JSONObject json = jsonParser.makeHttpRequest(
                                cadena, "POST", params);

                        Log.d("Registering attempt", json.toString());

                        success = json.getInt(TAG_SUCCESS);

                        if (success == 1){
                            Log.d("Factura Modificada!", json.toString());
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

                String id_b = args[2];

                List<NameValuePair> params = new ArrayList<NameValuePair>();

                    try{
                        params.add(new BasicNameValuePair("idFactura", id_b));

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

            resultado.setText(s);
        }

    }
}
