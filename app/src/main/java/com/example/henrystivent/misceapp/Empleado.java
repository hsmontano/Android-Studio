package com.example.henrystivent.misceapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.Button;
import android.widget.RadioButton;

public class Empleado extends AppCompatActivity  {

    RadioButton persona, producto, categoria, factura, reporte;
    Button next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empleado);

        persona = (RadioButton)findViewById(R.id.rbtn_persona);
        producto = (RadioButton)findViewById(R.id.rbtn_producto);
        factura = (RadioButton)findViewById(R.id.rbtn_factura);
        categoria = (RadioButton)findViewById(R.id.rbtn_categoria);
        reporte = (RadioButton)findViewById(R.id.rbtn_reporte);

        next = (Button) findViewById(R.id.btn_ok);

        //next.setOnClickListener(this);
    }

   /* @Override
    public void onClick(View v) {
        Intent ir;

        switch (v.getId()){
            case R.id.rbtn_productos:
                break;
        }
    }*/
    public void empleado(View view){
        Intent i;
        if (persona.isChecked()==true){
            i = new Intent(this,Persona.class);
            startActivity(i);
        }else if (producto.isChecked()==true){
            i = new Intent(this, Productos.class);
            startActivity(i);
        }else if (factura.isChecked()==true){
            i = new Intent(this, Factura.class);
            startActivity(i);
        }else if (categoria.isChecked()==true){
            i = new Intent(this, Categoria.class);
            startActivity(i);
        }else if (reporte.isChecked()==true){
            i = new Intent(this, Reporte.class);
            startActivity(i);
        }
    }
}
