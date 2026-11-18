package com.example.centroveterinarioapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class TurnoActivity extends AppCompatActivity {

    public static final String PREFS_NAME = "turnos_prefs";
    public static final String KEY_TURNOS = "turnos";
    public static final String SEPARADOR_TURNOS = ";;";
    private static final String SEPARADOR_CAMPOS = "::";

    public static final String EXTRA_SERVICIO_TURNO = "servicio_turno";

    private EditText inputMascota;
    private EditText inputDuenio;
    private RadioGroup grupoEspecie;
    private LinearLayout contenedorTurnos;
    private View root;

    private String servicioElegido;
    private final ArrayList<String> turnos = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_turno);

        root = findViewById(R.id.root_turno);
        inputMascota = findViewById(R.id.input_mascota);
        inputDuenio = findViewById(R.id.input_duenio);
        grupoEspecie = findViewById(R.id.grupo_especie);
        contenedorTurnos = findViewById(R.id.contenedor_turnos_dinamicos);
        TextView textoServicio = findViewById(R.id.texto_servicio_elegido);
        Button botonAgregar = findViewById(R.id.boton_agregar_turno);

        servicioElegido = getString(R.string.servicio_por_defecto);
        Bundle extras = getIntent().getExtras();
        if (extras != null && extras.getString(EXTRA_SERVICIO_TURNO) != null) {
            servicioElegido = extras.getString(EXTRA_SERVICIO_TURNO);
        }
        textoServicio.setText(getString(R.string.servicio_elegido, servicioElegido));

        leerTurnos();
        mostrarTurnos();

        botonAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                agregarTurno();
            }
        });
    }

    private void agregarTurno() {
        String mascota = limpiar(inputMascota.getText().toString());
        String duenio = limpiar(inputDuenio.getText().toString());

        if (mascota.isEmpty() || duenio.isEmpty()) {
            Toast.makeText(this, R.string.error_campos_vacios, Toast.LENGTH_SHORT).show();
            return;
        }

        String especie = getString(R.string.especie_perro);
        if (grupoEspecie.getCheckedRadioButtonId() == R.id.radio_gato) {
            especie = getString(R.string.especie_gato);
        }

        String turno = mascota + SEPARADOR_CAMPOS + especie + SEPARADOR_CAMPOS
                + servicioElegido + SEPARADOR_CAMPOS + duenio;
        turnos.add(turno);
        guardarTurnos();
        mostrarTurnos();

        inputMascota.setText("");
        inputDuenio.setText("");

        Snackbar.make(root, R.string.snack_turno_agregado, Snackbar.LENGTH_LONG)
                .setAction(R.string.snack_deshacer, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        turnos.remove(turnos.size() - 1);
                        guardarTurnos();
                        mostrarTurnos();
                        Toast.makeText(TurnoActivity.this, R.string.toast_turno_deshecho, Toast.LENGTH_SHORT).show();
                    }
                })
                .show();
    }

    private void borrarTurno(final int posicion) {
        final String borrado = turnos.remove(posicion);
        guardarTurnos();
        mostrarTurnos();

        Snackbar.make(root, R.string.snack_turno_borrado, Snackbar.LENGTH_LONG)
                .setAction(R.string.snack_deshacer, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        turnos.add(posicion, borrado);
                        guardarTurnos();
                        mostrarTurnos();
                        Toast.makeText(TurnoActivity.this, R.string.toast_turno_deshecho, Toast.LENGTH_SHORT).show();
                    }
                })
                .show();
    }

    private void mostrarTurnos() {
        contenedorTurnos.removeAllViews();

        for (int i = 0; i < turnos.size(); i++) {
            String[] campos = turnos.get(i).split(SEPARADOR_CAMPOS);
            if (campos.length < 4) {
                continue;
            }

            View tarjeta = LayoutInflater.from(this).inflate(R.layout.item_turno, contenedorTurnos, false);
            TextView textoTurno = tarjeta.findViewById(R.id.texto_turno);
            TextView textoDuenio = tarjeta.findViewById(R.id.texto_duenio);

            textoTurno.setText(getString(R.string.turno_texto, campos[0], campos[1], campos[2]));
            textoDuenio.setText(getString(R.string.turno_duenio, campos[3]));

            final int posicion = i;
            tarjeta.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    borrarTurno(posicion);
                    return true;
                }
            });

            contenedorTurnos.addView(tarjeta);
        }
    }

    private void guardarTurnos() {
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        String texto = "";
        for (int i = 0; i < turnos.size(); i++) {
            if (i > 0) {
                texto = texto + SEPARADOR_TURNOS;
            }
            texto = texto + turnos.get(i);
        }
        editor.putString(KEY_TURNOS, texto);
        editor.apply();
    }

    private void leerTurnos() {
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        String guardados = prefs.getString(KEY_TURNOS, "");
        turnos.clear();
        if (!guardados.isEmpty()) {
            for (String turno : guardados.split(SEPARADOR_TURNOS)) {
                turnos.add(turno);
            }
        }
    }

    private String limpiar(String texto) {
        return texto.trim().replace(SEPARADOR_TURNOS, " ").replace(SEPARADOR_CAMPOS, " ");
    }
}
