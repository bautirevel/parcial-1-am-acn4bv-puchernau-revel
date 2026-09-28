# Parcial 1 - Aplicaciones Móviles

## Integrantes
* Puchernau Baffa Theo
* Revel Chion Bautista

---

## 1. Temática y Elección de Pantalla
Para el desarrollo de la aplicación móvil se tomó como base el sistema de gestión y atención de una clínica veterinaria.

De acuerdo con las pautas del parcial, se seleccionó para esta primera entrega la **Pantalla de Inicio / Landing**, ya que constituye el punto de acceso principal del usuario a los servicios del establecimiento y reúne las condiciones estructurales requeridas para la interfaz.

---

## 2. Descripción de la Pantalla (Landing)
La pantalla tiene como objetivo presentar la identidad del negocio, ofrecer accesos directos de contacto inmediato y permitir al usuario explorar y gestionar servicios antes de iniciar un flujo más profundo (como agendar turnos o iniciar sesión).

La estructura visual comprende:
* **Barra Superior (Header):** Contiene el isotipo/logo del local, el título identificador de la app y accesos rápidos de contacto (vía llamada directa e interacción web).
* **Sección Destacada (Hero):** Presentación del establecimiento con texto descriptivo del servicio veterinario y un botón de acción principal para solicitar atención o iniciar una gestión rápida.
* **Sección de Servicios:** Catálogo con tarjetas que combinan imagen descriptiva y texto con los servicios ofrecidos (ej. Consulta Clínica, Vacunación, Cirugía, Peluquería Canina).
* **Área Interactiva y Dinámica:** Espacio dentro del contenedor desplazable donde el usuario puede registrar dinámicamente consultas o solicitudes rápidas en pantalla al interactuar con los botones.

---

## 3. Jerarquía y Componentes de UI
Para cumplir con los estándares de diseño y escalabilidad de Android, la pantalla se organizó de la siguiente forma:
* **ConstraintLayout (Raíz):** Asegura la correcta distribución entre la barra superior fija y el cuerpo de contenido desplazable.
* **ScrollView:** Permite el desplazamiento vertical fluido de todo el cuerpo de información para asegurar adaptabilidad en pantallas de diferentes tamaños.
* **LinearLayout (Vertical):** Contenedor principal hijo del ScrollView, encargado de apilar de manera ordenada los bloques de contenido.
* **LinearLayouts (Horizontales):** Utilizados para estructurar elementos en fila, como la botonera de contacto superior y las filas de tarjetas de servicios.
* **TextViews y Buttons:** Empleados para títulos, descripciones y disparadores de eventos e interacciones.
* **ImageViews:** Utilizados para el logo corporativo y la previsualización visual de los servicios veterinarios.

---

## 4. Flujo de Navegación Esperado (Roadmap de la App)
1. **Landing (Pantalla actual):** El usuario ingresa, visualiza la información de la veterinaria y puede solicitar acciones rápidas o interactuar con los canales de contacto.
2. **Registro / Login:** Desde la barra superior o los accesos de perfil, el usuario podrá identificarse para guardar el historial clínico de sus mascotas.
3. **Agendar Turno:** Al presionar "Solicitar Turno", el flujo conducirá a una pantalla de selección de fecha (calendario), hora y profesional disponible.
4. **Contacto y Sucursales:** Acceso a la vista detallada con geolocalización, horarios de atención discriminados por día y teléfonos de guardia.