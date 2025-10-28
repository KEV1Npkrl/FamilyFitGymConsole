## Credenciales de ejemplo

Estas credenciales son solo para propósitos de desarrollo y pruebas. No uses estas contraseñas en producción.

### EMPLEADOS (Login como Empleado):

- **PROPIETARIO** (Acceso completo)
  - Documento (DNI): **45678912**
  - Contraseña: **propietario123**
  - Nombre: Carlos Mendoza

- **CAJERO / RECEPCIONISTA**
  - Documento (DNI): **78945612**
  - Contraseña: **cajero123**
  - Nombre: Ana López

- **INSTRUCTOR**
  - Documento (DNI): **32165498**
  - Contraseña: **instructor123**
  - Nombre: Luis Ramírez

### SOCIOS / USUARIOS (Login como Usuario):

- **Usuario 1**
  - Documento (DNI): **12345678**
  - Contraseña: **user123**
  - Nombre: Juan Pérez

- **Usuario 2**
  - Documento (DNI): **87654321**
  - Contraseña: **user123**
  - Nombre: María García

---

## 🔑 Cómo usar las credenciales:
1. Ejecuta la aplicación
2. En el menú principal elige:
   - Opción 1: "Login como Usuario (Socio)" → usa documentos de SOCIOS
   - Opción 2: "Login como Empleado" → usa documentos de EMPLEADOS
3. Ingresa el **número de documento** (sin guiones ni puntos)
4. Ingresa la **contraseña**

---

##  Notas importantes:

- Las contraseñas están almacenadas con hash BCrypt en el sistema
- Los documentos son el **username** para login
- El Propietario tiene acceso a todas las funcionalidades
- El Cajero puede gestionar socios y registrar pagos/asistencias
- El Instructor puede gestionar clases y eventos
- Los Usuarios/Socios tienen acceso limitado a su perfil y servicios