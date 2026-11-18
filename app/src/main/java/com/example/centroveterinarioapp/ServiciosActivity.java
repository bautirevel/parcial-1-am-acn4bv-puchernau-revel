package com.example.centroveterinarioapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class ServiciosActivity extends AppCompatActivity {

    private ArrayList<Servicio> servicios;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_servicios);

        cargarServicios();

        ArrayList<String> titulos = new ArrayList<>();
        for (Servicio servicio : servicios) {
            titulos.add(servicio.getTitulo() + "\n" + servicio.getDescripcion());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.list_item, titulos);
        ListView listView = findViewById(R.id.lista_servicios);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Servicio elegido = servicios.get(position);
                Toast.makeText(ServiciosActivity.this,
                        getString(R.string.toast_servicio_elegido, elegido.getTitulo()),
                        Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(ServiciosActivity.this, DetalleServicioActivity.class);
                intent.putExtra(DetalleServicioActivity.EXTRA_SERVICIO, elegido);
                startActivity(intent);
            }
        });
    }

    private void cargarServicios() {
        servicios = new ArrayList<>();
        servicios.add(new Servicio(
                getString(R.string.service_clinica_title),
                getString(R.string.service_clinica_desc),
                getString(R.string.service_clinica_detalle),
                getString(R.string.service_clinica_precio),
                getString(R.string.service_clinica_img)));
        servicios.add(new Servicio(
                getString(R.string.service_vacunacion_title),
                getString(R.string.service_vacunacion_desc),
                getString(R.string.service_vacunacion_detalle),
                getString(R.string.service_vacunacion_precio),
                getString(R.string.service_vacunacion_img)));
        servicios.add(new Servicio(
                getString(R.string.service_cirugia_title),
                getString(R.string.service_cirugia_desc),
                getString(R.string.service_cirugia_detalle),
                getString(R.string.service_cirugia_precio),
                getString(R.string.service_cirugia_img)));
        servicios.add(new Servicio(
                getString(R.string.service_peluqueria_title),
                getString(R.string.service_peluqueria_desc),
                getString(R.string.service_peluqueria_detalle),
                getString(R.string.service_peluqueria_precio),
                getString(R.string.service_peluqueria_img)));
    }
}
