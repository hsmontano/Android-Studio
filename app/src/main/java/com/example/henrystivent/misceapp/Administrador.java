package com.example.henrystivent.misceapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;

public class Administrador extends AppCompatActivity {

    RadioButton personas, productos, categorias, usuarios, facturas, reportes;
    Button next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_administrador);

        personas = (RadioButton) findViewById(R.id.rbtn_personas);
        productos = (RadioButton) findViewById(R.id.rbtn_productos);
        usuarios = (RadioButton) findViewById(R.id.rbtn_usuarios);
        facturas = (RadioButton) findViewById(R.id.rbtn_facturas);
        categorias = (RadioButton) findViewById(R.id.rbtn_categorias);
        reportes = (RadioButton) findViewById(R.id.rbtn_reportes);

        next = (Button) findViewById(R.id.btn_aOk);

       // next.setOnClickListener(this);
    }
/*
    @Override
    public void onClick(View v) {
        Intent i;
        switch (v.getId()) {
            case R.id.rbtn_persona:
                i = new Intent(this, Persona.class);
                startActivity(i);
                break;
            case R.id.rbtn_productos:
                i = new Intent(this, Productos.class);
                startActivity(i);
                break;
            case R.id.rbtn_abastecer:
                i = new Intent(this, Abastecer.class);
                startActivity(i);
                break;
            case R.id.rbtn_venta:
                i = new Intent(this, Venta.class);
                startActivity(i);
                break;
            case R.id.rbtn_usuarios:
                i = new Intent(this, Usuario.class);
                startActivity(i);
                break;
            case R.id.rbtn_factura:
                i = new Intent(this, Factura.class);
                startActivity(i);
                break;
            case R.id.rbtn_reporte:
                i = new Intent(this, Reporte.class);
                startActivity(i);
        }
    }*/
    public void continuar(View view){
        Intent i;

        if (personas.isChecked()){
            i = new Intent(this, Persona.class);
            startActivity(i);
        }else if (productos.isChecked()){
            i = new Intent(this, Productos.class);
            startActivity(i);
        }else if (usuarios.isChecked()){
            i = new Intent(this, Usuario.class);
            startActivity(i);
        }else if (facturas.isChecked()){
            i = new Intent(this, Factura.class);
            startActivity(i);
        }else if (categorias.isChecked()){
            i = new Intent(this, Categoria.class);
            startActivity(i);
        }else if (reportes.isChecked()){
            i = new Intent(this, Reporte.class);
            startActivity(i);
        }
    }
}
