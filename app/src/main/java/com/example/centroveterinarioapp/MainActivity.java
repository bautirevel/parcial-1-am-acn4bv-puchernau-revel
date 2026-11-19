package com.example.centroveterinarioapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

public class MainActivity extends AppCompatActivity {

    private TextView resumenTurnos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        resumenTurnos = findViewById(R.id.texto_resumen_turnos);

        ImageView imagenHero = findViewById(R.id.imagen_hero);
        Glide.with(this)
                .load(getString(R.string.url_imagen_hero))
                .into(imagenHero);

        Button botonLlamar = findViewById(R.id.btn_llamar);
        Button botonServicios = findViewById(R.id.boton_ver_servicios);
        Button botonTurno = findViewById(R.id.boton_pedir_turno);
        Button botonGaleria = findViewById(R.id.boton_galeria);

        botonLlamar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                llamarVeterinaria();
            }
        });

        botonServicios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ServiciosActivity.class);
                startActivity(intent);
            }
        });

        botonTurno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, TurnoActivity.class);
                startActivity(intent);
            }
        });

        botonGaleria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, GaleriaActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        actualizarResumenTurnos();
    }

    private void actualizarResumenTurnos() {
        SharedPreferences prefs = getSharedPreferences(TurnoActivity.PREFS_NAME, MODE_PRIVATE);
        String guardados = prefs.getString(TurnoActivity.KEY_TURNOS, "");
        int cantidad = 0;
        if (!guardados.isEmpty()) {
            cantidad = guardados.split(TurnoActivity.SEPARADOR_TURNOS).length;
        }

        if (cantidad == 0) {
            resumenTurnos.setText(R.string.resumen_turnos_vacio);
        } else {
            resumenTurnos.setText(getString(R.string.resumen_turnos, cantidad));
        }
    }

    public void llamarVeterinaria() {
        Uri uri = Uri.parse(getString(R.string.telefono_guardia));
        Intent intent = new Intent(Intent.ACTION_DIAL, uri);
        startActivity(intent);

        Toast.makeText(this, R.string.toast_llamando, Toast.LENGTH_SHORT).show();
    }
}
