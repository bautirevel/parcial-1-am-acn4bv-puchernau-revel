package com.example.centroveterinarioapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

public class DetalleServicioActivity extends AppCompatActivity {

    public static final String EXTRA_SERVICIO = "servicio";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_servicio);

        final Servicio servicio = (Servicio) getIntent().getSerializableExtra(EXTRA_SERVICIO);

        if (servicio == null) {
            Toast.makeText(this, R.string.error_sin_servicio, Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        ImageView imagen = findViewById(R.id.imagen_servicio);
        TextView titulo = findViewById(R.id.titulo_detalle);
        TextView precio = findViewById(R.id.precio_detalle);
        TextView descripcion = findViewById(R.id.descripcion_detalle);
        Button botonTurno = findViewById(R.id.boton_turno_servicio);

        titulo.setText(servicio.getTitulo());
        precio.setText(servicio.getPrecio());
        descripcion.setText(servicio.getDetalle());

        Glide.with(this)
                .load(servicio.getImagenUrl())
                .into(imagen);

        botonTurno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString(TurnoActivity.EXTRA_SERVICIO_TURNO, servicio.getTitulo());

                Intent intent = new Intent(DetalleServicioActivity.this, TurnoActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }
}
