# Esencia & Aroma – cómo ejecutar el proyecto

## Requisitos (lo único que hay que instalar)
1. **JDK 21** (Temurin 21 recomendado). Spring Boot 4.1.2 exige como mínimo Java 17.
2. **IntelliJ IDEA** (Community sirve). Ya trae Maven incluido.
3. **Conexión a internet** la primera vez: Maven descarga solo las dependencias del `pom.xml`.

No hay que instalar librerías a mano: todas están declaradas en `pom.xml`.

## Abrir el proyecto en IntelliJ
1. `File > Open...` y elige el archivo **`pom.xml`** de esta carpeta (o la carpeta que lo contiene).
   Si pregunta, elige **Open as Project** y **Trust Project**.
2. `File > Project Structure > Project > SDK`: elige un **JDK 21**
   (si no aparece: `Add SDK > Download JDK > Temurin 21`).
3. Abre la pestaña **Maven** (borde derecho) y pulsa **Reload All Maven Projects** (icono de flechas circulares).
   Espera a que termine la barra de progreso inferior.
4. Arriba a la derecha elige la configuración **EsenciaAromaApplication** y pulsa Run.
5. Abre http://localhost:8080

## Usuario del sistema
La app NO arranca si faltan estas variables de entorno (están definidas en la configuración
de ejecución `.run/EsenciaAromaApplication.run.xml`):

- `APP_ADMIN_USERNAME`
- `APP_ADMIN_PASSWORD` (mínimo 12 caracteres)

Cámbialas ahí (o en `Run > Edit Configurations > Environment variables`) por las tuyas.
No subas contraseñas reales a Git.

## Ejecutar desde la terminal (opcional)
PowerShell:
    $env:APP_ADMIN_USERNAME="tu_usuario"; $env:APP_ADMIN_PASSWORD="tu_clave_de_12+"
    .\mvnw.cmd spring-boot:run

## Pruebas
`EsenciaAromaApplicationTests` levanta toda la aplicación, así que también necesita esas
variables. Si compilas con Maven sin ellas usa `-DskipTests`.

## Base de datos
H2 en archivo: `./data/esenciaaroma.mv.db` (se crea sola si no existe).
La tabla nueva `alertas_stock` se crea automáticamente al arrancar.

## Módulo de ventas (50 clases)
- `ventas/` (11 originales) · `observador/` (8) · `alerta/` (9) · `concurrencia/` (6)
- `regla/` (8) · `puerto/` (2) · `controlador/` (4) · `soporte/` (2)
- Nueva pantalla: http://localhost:8080/ventas/alertas
