# Parcial 2 - Aplicaciones Móviles

## Integrantes
* Puchernau Baffa Theo
* Revel Chion Bautista

Comisión: ACN4BV

---

## 1. Temática
Continuamos con la app de la **Veterinaria Puchernau-Chion** del Parcial 1. En el Parcial 1 desarrollamos solo la pantalla de inicio (landing). Para esta entrega la app pasa a tener **5 pantallas** conectadas entre sí, siguiendo el roadmap que habíamos planteado en el primer informe (servicios, turnos y contacto).

La app está hecha en **Java** con un enfoque **multi-activity**: cada pantalla es una Activity y la navegación se hace con Intents.

---

## 2. Mapa de pantallas y flujo de uso

```
                 ┌──────────────────────┐
                 │  1. Inicio (Landing) │
                 └──────────┬───────────┘
        ┌───────────────────┼────────────────────┐
        ▼                   ▼                    ▼
┌───────────────┐   ┌───────────────┐   ┌─────────────────────┐
│ 2. Servicios  │   │ 4. Pedir turno│   │ 5. Pacientes felices│
└───────┬───────┘   └───────▲───────┘   └─────────────────────┘
        ▼                   │
┌───────────────────────┐   │
│ 3. Detalle de servicio│───┘  (pasa el servicio elegido)
└───────────────────────┘
```

**Flujo principal:** el usuario entra a la app → toca *Ver servicios* → elige un servicio de la lista → ve el detalle con foto, precio y descripción → toca *Pedir turno para este servicio* → completa el nombre de la mascota, del dueño y la especie → el turno aparece en la lista y queda guardado → al volver al inicio ve cuántos turnos tiene pedidos.

---

## 3. Descripción de cada pantalla

### Pantalla 1 - Inicio (`MainActivity`)
**Objetivo:** presentar la veterinaria y dar acceso al resto de la app.

**Componentes:**
* Barra superior (LinearLayout horizontal) con el nombre y un botón **Llamar**.
* Foto principal descargada desde internet con **Glide**.
* Título y descripción de la veterinaria.
* Botón **Ver servicios** y una fila (LinearLayout horizontal) con **Pedir turno** y **Pacientes felices**.
* Tarjeta **Mis turnos** con un resumen de cuántos turnos pidió el usuario.

**Funcionalidades:**
* *Llamar* abre el marcador del teléfono con el número de guardia (intent implícito `ACTION_DIAL`) y muestra un Toast.
* Cada botón abre su pantalla con un intent explícito.
* El resumen de turnos se lee de SharedPreferences en `onResume()`, así se actualiza cada vez que el usuario vuelve a esta pantalla.

### Pantalla 2 - Servicios (`ServiciosActivity`)
**Objetivo:** mostrar todos los servicios de la veterinaria.

**Componentes:** encabezado y un **ListView** cargado con un **ArrayAdapter** (cada ítem usa el layout `list_item.xml`).

**Funcionalidades:**
* Al tocar un servicio aparece un Toast con el nombre y se abre el detalle.
* Se le pasa a la pantalla de detalle el objeto `Servicio` completo (la clase implementa `Serializable`).

### Pantalla 3 - Detalle del servicio (`DetalleServicioActivity`)
**Objetivo:** mostrar la información completa del servicio elegido.

**Componentes:** foto del servicio (Glide), título, precio, descripción y botón **Pedir turno para este servicio**.

**Funcionalidades:**
* Recibe el `Servicio` por el intent y valida que haya llegado; si no llegó, muestra un error y cierra la pantalla.
* El botón abre *Pedir turno* mandando el nombre del servicio dentro de un **Bundle**.
