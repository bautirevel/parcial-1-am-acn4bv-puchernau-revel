package com.example.centroveterinarioapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private LinearLayout contenedorTurnos;
    private int contadorTurnos = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        contenedorTurnos = findViewById(R.id.contenedor_turnos_dinamicos);

        Button botonLlamar = findViewById(R.id.btn_llamar);
        Button botonTurno = findViewById(R.id.boton_solicitar_turno);

        botonLlamar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                llamarVeterinaria();
            }
        });

        botonTurno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                agregarTurnoDinamico();
            }
        });
    }

    public void llamarVeterinaria() {
        Uri uri = Uri.parse("tel:1144558899");
        Intent intent = new Intent(Intent.ACTION_DIAL, uri);
        startActivity(intent);

        Toast.makeText(this, "Marcando teléfono de guardia...", Toast.LENGTH_SHORT).show();
    }

    public void agregarTurnoDinamico() {
        contadorTurnos++;

        TextView nuevoTurno = new TextView(this);
        nuevoTurno.setText("Turno #" + contadorTurnos + " solicitado");

        contenedorTurnos.addView(nuevoTurno);

        Toast.makeText(this, "Turno registrado en pantalla", Toast.LENGTH_SHORT).show();
    }
}