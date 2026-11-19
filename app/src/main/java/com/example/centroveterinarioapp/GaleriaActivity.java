package com.example.centroveterinarioapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class GaleriaActivity extends AppCompatActivity {

    private OkHttpClient client;
    private LinearLayout contenedorPacientes;
    private ProgressBar barraProgreso;
    private Button botonCargarMas;
    private Call llamadaActual;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_galeria);

        contenedorPacientes = findViewById(R.id.contenedor_pacientes);
        barraProgreso = findViewById(R.id.barra_progreso);
        botonCargarMas = findViewById(R.id.boton_cargar_mas);

        client = new OkHttpClient();

        botonCargarMas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cargarPacientes();
            }
        });

        cargarPacientes();
    }

    private void cargarPacientes() {
        barraProgreso.setVisibility(View.VISIBLE);
        botonCargarMas.setEnabled(false);

        Request request = new Request.Builder()
                .url(getString(R.string.url_galeria))
                .build();

        llamadaActual = client.newCall(request);
        llamadaActual.enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                mostrarError();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (!response.isSuccessful() || response.body() == null) {
                    mostrarError();
                    return;
                }

                String cuerpo = response.body().string();
                try {
                    JSONObject json = new JSONObject(cuerpo);
                    JSONArray imagenes = json.getJSONArray("message");
                    final ArrayList<String> urls = new ArrayList<>();
                    for (int i = 0; i < imagenes.length(); i++) {
                        urls.add(imagenes.getString(i));
                    }

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            barraProgreso.setVisibility(View.GONE);
                            botonCargarMas.setEnabled(true);
                            for (String url : urls) {
                                agregarPaciente(url);
                            }
                        }
                    });
                } catch (JSONException e) {
                    mostrarError();
                }
            }
        });
    }

    private void agregarPaciente(String imageUrl) {
        View item = LayoutInflater.from(this).inflate(R.layout.item_paciente, contenedorPacientes, false);

        ImageView imagen = item.findViewById(R.id.imagen_paciente);
        TextView textoRaza = item.findViewById(R.id.texto_raza);

        textoRaza.setText(getString(R.string.raza, obtenerRaza(imageUrl)));

        Glide.with(getApplicationContext())
                .load(imageUrl)
                .into(imagen);

        contenedorPacientes.addView(item);
    }

    private String obtenerRaza(String url) {
        String[] partes = url.split("/");
        for (int i = 0; i < partes.length - 1; i++) {
            if (partes[i].equals("breeds")) {
                return partes[i + 1].replace("-", " ");
            }
        }
        return "-";
    }

    private void mostrarError() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                barraProgreso.setVisibility(View.GONE);
                botonCargarMas.setEnabled(true);
                Toast.makeText(GaleriaActivity.this, R.string.error_galeria, Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (llamadaActual != null) {
            llamadaActual.cancel();
        }
    }
}
