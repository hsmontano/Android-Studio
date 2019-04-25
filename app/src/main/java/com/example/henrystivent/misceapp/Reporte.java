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

import java.awt.font.TextAttribute;
import java.util.ArrayList;
import java.util.List;

public class Reporte extends AppCompatActivity implements View.OnClickListener {

    EditText fecha_ini, fecha_fi;
    Button reportar;
    TextView reporte_ver;

    GenerarReporte reporte_g;

    JSONParser jsonParser = new JSONParser();

    private static final String URL = "http://192.168.1.42/prueba/consultar_reporte.php";

    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";
    private static final String TAG_REPORTE = "reporte";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reporte);

        fecha_ini = (EditText)findViewById(R.id.txt_fechaInicio);
        fecha_fi =(EditText)findViewById(R.id.txt_fechaFin);

        reporte_ver = (TextView)findViewById(R.id.lbl_reporteR);

        reportar = (Button)findViewById(R.id.btn_reporteR);

        reportar.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String fecha_inicio = fecha_ini.getText().toString();
        String fecha_fin = fecha_fi.getText().toString();

        reporte_g = new GenerarReporte();
        reporte_g.execute(fecha_inicio, fecha_fin);
    }
    class GenerarReporte extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... args) {

            int success;

            String resultado1 = "";
            String resultado2 = "";
            String resultado3 = "";
            String resultado4 = "";
            String resultado5 = "";
            String resultado6 = "";
            String resultado7 = "";
            String resultado8 = "";
            String resultado9 = "";


            String fecha_i = args[0],
                    fecha_f = args[1];

            List<NameValuePair> params = new ArrayList<NameValuePair>();

            try{
                params.add(new BasicNameValuePair("fecha_inicio",fecha_i));
                params.add(new BasicNameValuePair("fecha_fin",fecha_f));

                Log.d("request!", "starting");
                // getting product details by making HTTP request
                JSONObject json = jsonParser.makeHttpRequest(URL, "POST", params);

                // check your log for json response
                Log.d("Login attempt", json.toString());

                success = json.getInt(TAG_SUCCESS);

                if (success == 1){

                    JSONArray jsonArray = json.getJSONArray(TAG_REPORTE);

                    for (int i = 0; i < jsonArray.length(); i++){

                        JSONObject Json = jsonArray.getJSONObject(i);

                        resultado1 = Json.getString("idFactura");
                        resultado2  = Json.getString("total");
                        resultado3 = Json.getString("pago");
                        resultado4 = Json.getString("descuento");
                        resultado5 = Json.getString("empleado");
                        resultado6 = Json.getString("cedula");
                        resultado7 = Json.getString("persona");
                        resultado8 = Json.getString("apellido");
                        resultado9 = Json.getString("identificacion");
                        resultado9 = Json.getString("operacion");

                    }
                }else{
                    resultado1 = "no se encontraron registros";
                }

            }catch (JSONException e){
                e.getStackTrace();
            }
            return  " | " + resultado1 + " | " + resultado2 + " | " + resultado3 + " \n " + resultado4 + " | " + resultado5 + " | " + resultado6 + " \n " + resultado7 + " | " + resultado8 + " | " + resultado9 + " | ";
        }

        @Override
        protected void onPostExecute(String s) {
            reporte_ver.setText(s);
        }
    }
}
