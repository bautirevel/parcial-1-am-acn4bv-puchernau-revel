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

### Pantalla 4 - Pedir turno (`TurnoActivity`)
**Objetivo:** que el usuario pida turnos y vea los que ya pidió.

**Componentes:** servicio elegido, dos **EditText** (mascota y dueño), **RadioButton** para elegir perro o gato, botón **Agregar turno** y una lista de turnos dentro de un **ScrollView**.

**Funcionalidades:**
* Si entra desde el detalle, muestra el servicio elegido; si entra desde el inicio, usa *Clínica Médica*.
* Valida que los campos no estén vacíos (Toast de error).
* Cada turno se agrega **dinámicamente desde Java** al ScrollView, inflando el layout `item_turno.xml` como tarjeta.
* Al agregar aparece un **Snackbar** con la opción **Deshacer**.
* Manteniendo apretado un turno se borra, también con Snackbar y Deshacer.
* Los turnos se guardan con **SharedPreferences**, así siguen estando aunque se cierre la app.

### Pantalla 5 - Pacientes felices (`GaleriaActivity`)
**Objetivo:** mostrar fotos de perros que pasaron por la veterinaria.

**Componentes:** encabezado, lista de tarjetas con foto y raza (`item_paciente.xml`), **ProgressBar** y botón **Cargar más**.

**Funcionalidades:**
* Pide las fotos a la API pública *Dog CEO* (`https://dog.ceo/api/breeds/image/random/6`) con **OkHttp**, en segundo plano.
* Lee el JSON de respuesta, infla una tarjeta por cada foto, carga la imagen con **Glide** y saca la raza de la URL.
* Mientras carga se ve la barra de progreso y el botón se deshabilita. Si falla, muestra un Toast.
* Si se cierra la pantalla, se cancela la petición en `onDestroy()`.

---

## 4. Requisitos de la consigna y dónde se cumplen

| Requisito | Dónde |
|---|---|
| Varias pantallas con navegación | 5 activities conectadas con intents |
| Informe por pantalla y flujo | Este documento |
| ConstraintLayout | Raíz de Inicio, Servicios, Turno y Galería |
| LinearLayout vertical / horizontal | Barra superior y botonera (horizontal), contenidos (vertical) |
| Button y TextView | Todas las pantallas |
| Contenido real con imágenes | Fotos reales de servicios y pacientes |
| Evento | Clicks en botones, click en ítems de la lista y click largo en turnos |
| Comportamiento dinámico | Turnos agregados al ScrollView desde Java (`item_turno.xml`) |
| Variables de String, Dimensiones y Colores | `strings.xml`, `dimens.xml`, `colors.xml` |
| Pasaje de datos entre activities | Objeto `Servicio` (Serializable) y Bundle con el servicio |
| Descarga de imágenes desde URL | Glide en Inicio, Detalle y Galería |
| Contenido desde URL con una biblioteca | OkHttp en Galería |
| Conventional commits | Historial del repositorio |

---

## 5. Bibliotecas usadas
* **Glide**: carga de imágenes desde URL.
* **OkHttp**: peticiones HTTP a la API.

Permiso agregado en el `AndroidManifest.xml`: `android.permission.INTERNET`.

## 6. Créditos de imágenes
* Fotos de servicios: [Pexels](https://www.pexels.com) (uso libre).
* Fotos de pacientes: [Dog CEO API](https://dog.ceo/dog-api/).
