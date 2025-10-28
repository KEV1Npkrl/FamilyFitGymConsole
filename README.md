# Family Fit Gym - Versión Consola

Sistema de gestión de gimnasio en modo consola (sin interfaz gráfica).

# Requisitos

- Java 8 o superior
- Biblioteca BCrypt para hashing de contraseñas

## Compilación y Ejecución

### Descargar BCrypt

Descarga el archivo JAR de BCrypt desde:
https://repo1.maven.org/maven2/org/mindrot/jbcrypt/0.4/jbcrypt-0.4.jar

Colócalo en una carpeta `lib/` en la raíz del proyecto.

### Compilar

```bash
javac -cp ".;lib/jbcrypt-0.4.jar" -d bin src/com/familyfitgym/console/**/*.java
