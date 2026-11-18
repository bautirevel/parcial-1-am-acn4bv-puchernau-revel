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

        servicioElegido = getString(R.string.servicio_por_defecto);
        Bundle extras = getIntent().getExtras();
        if (extras != null && extras.getString(EXTRA_SERVICIO_TURNO) != null) {
            servicioElegido = extras.getString(EXTRA_SERVICIO_TURNO);
        }
        textoServicio.setText(getString(R.string.servicio_elegido, servicioElegido));
    }
}
