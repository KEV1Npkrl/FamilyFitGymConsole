package com.familyfitgym.console;

import com.familyfitgym.console.modelo.Administrador;
import com.familyfitgym.console.modelo.Membresia;
import com.familyfitgym.console.modelo.TipoEmpleado;
import com.familyfitgym.console.modelo.Usuario;
import com.familyfitgym.console.modelo.UsuarioMembresia;
import com.familyfitgym.console.repositorio.RepositorioAdministrador;
import com.familyfitgym.console.repositorio.RepositorioMembresia;
import com.familyfitgym.console.repositorio.RepositorioUsuario;
import com.familyfitgym.console.repositorio.RepositorioUsuarioMembresia;
import com.familyfitgym.console.repositorio.impl.RepositorioAdministradorEnMemoria;
import com.familyfitgym.console.repositorio.impl.RepositorioMembresiaEnMemoria;
import com.familyfitgym.console.repositorio.impl.RepositorioUsuarioEnMemoria;
import com.familyfitgym.console.repositorio.impl.RepositorioUsuarioMembresiaEnMemoria;
import com.familyfitgym.console.servicio.ServicioAutenticacion;
import com.familyfitgym.console.servicio.ServicioMembresia;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

/**
 * Aplicación de consola para Family Fit Gym
 * Versión independiente sin dependencias de JavaFX
 */
public class ConsoleApp {

    private static ServicioAutenticacion servicioAutenticacion;
    private static ServicioMembresia servicioMembresia;
    private static Scanner scanner;
    private static final DateTimeFormatter FORMATEADOR_FECHA = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public static void main(String[] args) {
        // Inicializar repositorios
        RepositorioUsuario repoUsuario = new RepositorioUsuarioEnMemoria();
        RepositorioAdministrador repoAdministrador = new RepositorioAdministradorEnMemoria();
        RepositorioMembresia repoMembresia = new RepositorioMembresiaEnMemoria();
        RepositorioUsuarioMembresia repoUsuarioMembresia = new RepositorioUsuarioMembresiaEnMemoria();
        
        servicioAutenticacion = new ServicioAutenticacion(repoUsuario, repoAdministrador);
        servicioMembresia = new ServicioMembresia(repoMembresia, repoUsuarioMembresia);
        scanner = new Scanner(System.in);

        mostrarBanner();
        menuPrincipal();
        
        scanner.close();
    }

    private static void mostrarBanner() {
        System.out.println("\n╔═══════════════════════════════════════════╗");
        System.out.println("║                                           ║");
        System.out.println("║      FAMILY FIT GYM - VERSIÓN CONSOLA     ║");
        System.out.println("║                                           ║");
        System.out.println("║     Sistema de Gestión de Gimnasio        ║");
        System.out.println("║                                           ║");
        System.out.println("╚═══════════════════════════════════════════╝");
    }

    private static void menuPrincipal() {
        while (true) {
            System.out.println("\n════════════════════════════════════════════");
            System.out.println("              MENÚ PRINCIPAL");
            System.out.println("════════════════════════════════════════════");
            System.out.println("  1. Login como Usuario (Socio)");
            System.out.println("  2. Login como Empleado");
            System.out.println("  0. Salir");
            System.out.println("════════════════════════════════════════════");
            System.out.print("Seleccione una opción: ");

            String opcion = scanner.nextLine().trim();

            switch (opcion) {
                case "1":
                    loginUsuario();
                    break;
                case "2":
                    loginEmpleado();
                    break;
                case "0":
                    System.out.println("\n¡Hasta pronto! Gracias por usar Family Fit Gym.");
                    return;
                default:
                    System.out.println("Opción inválida. Intente nuevamente.");
            }
        }
    }

    private static void loginUsuario() {
        limpiarPantalla();
        System.out.println("\n");
        System.out.println("           LOGIN USUARIO (SOCIO)");
        System.out.println("════════════════════════════════════════════");
        System.out.print("Documento: ");
        String username = scanner.nextLine().trim();
        
        if (username.isEmpty()) {
            System.out.println("Documento no puede estar vacío.");
            presioneTecla();
            return;
        }
        
        System.out.print("Contraseña: ");
        String password = scanner.nextLine();

        if (servicioAutenticacion.iniciarSesionUsuario(username, password)) {
            Usuario user = servicioAutenticacion.obtenerUsuarioActual();
            System.out.println("\nLogin exitoso. ¡Bienvenido " + user.getNombres() + " " + user.getApellidos() + "!");
            presioneTecla();
            menuUsuario(user);
        } else {
            System.out.println("Usuario o contraseña incorrectos.");
            presioneTecla();
        }
    }

    private static void loginEmpleado() {
        limpiarPantalla();
        System.out.println("\n");
        System.out.println("              LOGIN EMPLEADO");
        System.out.println("════════════════════════════════════════════");
        System.out.print("Documento: ");
        String username = scanner.nextLine().trim();
        
        if (username.isEmpty()) {
            System.out.println("Documento no puede estar vacío.");
            presioneTecla();
            return;
        }
        
        System.out.print("Contraseña: ");
        String password = scanner.nextLine();

        if (servicioAutenticacion.iniciarSesionAdministrador(username, password)) {
            Administrador admin = servicioAutenticacion.obtenerAdministradorActual();
            System.out.println("\nLogin exitoso!");
            System.out.println(admin.getNombres() + " " + admin.getApellidos());
            System.out.println("Rol: " + admin.getTipoEmpleado().getNombreMostrar());
            presioneTecla();
            
            menuEmpleado(admin);
        } else {
            System.out.println("Credenciales incorrectas.");
            presioneTecla();
        }
    }

    private static void menuUsuario(Usuario user) {
        while (true) {
            limpiarPantalla();
            System.out.println("\n");
            System.out.println("    PANEL DE USUARIO - " + user.getNombres());
            System.out.println("════════════════════════════════════════════");
            System.out.println("  1. Ver Mi Perfil");
            System.out.println("  2. Marcar Asistencia");
            System.out.println("  3. Ver Mi Suscripción");
            System.out.println("  4. Ver Eventos Disponibles");
            System.out.println("  5. Cambiar Mi Contraseña");
            System.out.println("  0. Cerrar Sesión");
            System.out.println("════════════════════════════════════════════");
            System.out.print("Seleccione una opción: ");

            String opcion = scanner.nextLine().trim();

            switch (opcion) {
                case "1":
                    verPerfilUsuario(user);
                    break;
                case "2":
                    System.out.println("\nFuncionalidad de marcar asistencia próximamente...");
                    presioneTecla();
                    break;
                case "3":
                    System.out.println("\nFuncionalidad de ver suscripción próximamente...");
                    presioneTecla();
                    break;
                case "4":
                    System.out.println("\nFuncionalidad de eventos próximamente...");
                    presioneTecla();
                    break;
                case "5":
                    cambiarContrasenaUsuario(user);
                    break;
                case "0":
                    servicioAutenticacion.cerrarSesion();
                    System.out.println("\nSesión cerrada.");
                    presioneTecla();
                    return;
                default:
                    System.out.println("Opción inválida.");
                    presioneTecla();
            }
        }
    }

    private static void verPerfilUsuario(Usuario user) {
        limpiarPantalla();
        System.out.println("\n");
        System.out.println("               MI PERFIL");
        System.out.println("════════════════════════════════════════════");
        System.out.println("Tipo Doc:       " + (user.getTipoDocumento() != null ? user.getTipoDocumento() : "N/A"));
        System.out.println("Documento:      " + user.getNumDocumento());
        System.out.println("Nombres:        " + user.getNombres());
        System.out.println("Apellidos:      " + user.getApellidos());
        System.out.println("Correo:         " + (user.getCorreo() != null ? user.getCorreo() : "N/A"));
        System.out.println("Celular:        " + (user.getCelular() != null ? user.getCelular() : "N/A"));
        System.out.println("Género:         " + (user.getGenero() != null ? user.getGenero() : "N/A"));
        System.out.println("Fecha Nac:      " + (user.getFechaNacimiento() != null ? user.getFechaNacimiento().format(FORMATEADOR_FECHA) : "N/A"));
        System.out.println("Dirección:      " + (user.getDireccion() != null ? user.getDireccion() : "N/A"));
        System.out.println("Registro:       " + user.getFechaRegistro().format(FORMATEADOR_FECHA));
        System.out.println("════════════════════════════════════════════");
        presioneTecla();
    }

    private static void menuEmpleado(Administrador admin) {
        TipoEmpleado rol = admin.getTipoEmpleado();

        switch (rol) {
            case PROPIETARIO:
                menuPropietario(admin);
                break;
            case CAJERO_RECEPCIONISTA:
                menuCajero(admin);
                break;
            case INSTRUCTOR:
                menuInstructor(admin);
                break;
        }
    }

    private static void menuPropietario(Administrador admin) {
        while (true) {
            limpiarPantalla();
            System.out.println("\n");
            System.out.println("         PANEL DEL PROPIETARIO");
            System.out.println("════════════════════════════════════════════");
            System.out.println("  1. Gestionar Empleados");
            System.out.println("  2. Gestionar Socios");
            System.out.println("  3. Registrar Nuevo Empleado");
            System.out.println("  4. Registrar Nuevo Socio");
            System.out.println("  5. Ver Reportes");
            System.out.println("  6. Configuración");
            System.out.println("  7. Cambiar Contraseñas");
            System.out.println("  0. Cerrar Sesión");
            System.out.println("════════════════════════════════════════════");
            System.out.print("Seleccione una opción: ");

            String opcion = scanner.nextLine().trim();

            switch (opcion) {
                case "1":
                    gestionarEmpleados(admin);
                    break;
                case "2":
                    gestionarSocios(admin);
                    break;
                case "3":
                    registrarEmpleado();
                    break;
                case "4":
                    registrarSocio();
                    break;
                case "5":
                    System.out.println("\nReportes próximamente...");
                    presioneTecla();
                    break;
                case "6":
                    menuMaestrosConfiguracion(admin);
                    break;
                case "7":
                    menuCambiarContrasenasPropietario(admin);
                    break;
                case "0":
                    servicioAutenticacion.cerrarSesion();
                    System.out.println("\nSesión cerrada.");
                    presioneTecla();
                    return;
                default:
                    System.out.println("Opción inválida.");
                    presioneTecla();
            }
        }
    }

    private static void menuCajero(Administrador admin) {
        while (true) {
            limpiarPantalla();
            System.out.println("\n");
            System.out.println("       PANEL CAJERO/RECEPCIONISTA");
            System.out.println("════════════════════════════════════════════");
            System.out.println("  1. Registrar Nuevo Socio");
            System.out.println("  2. Gestionar Socios");
            System.out.println("  3. Registrar Pago");
            System.out.println("  4. Registrar Asistencia");
            System.out.println("  5. Ver Estadísticas del Día");
            System.out.println("  6. Cambiar Contraseñas");
            System.out.println("  0. Cerrar Sesión");
            System.out.println("════════════════════════════════════════════");
            System.out.print("Seleccione una opción: ");

            String opcion = scanner.nextLine().trim();

            switch (opcion) {
                case "1":
                    registrarSocio();
                    break;
                case "2":
                    gestionarSocios(admin);
                    break;
                case "3":
                    System.out.println("\nRegistrar pago próximamente...");
                    presioneTecla();
                    break;
                case "4":
                    System.out.println("\nRegistrar asistencia próximamente...");
                    presioneTecla();
                    break;
                case "5":
                    System.out.println("\nEstadísticas próximamente...");
                    presioneTecla();
                    break;
                case "6":
                    menuCambiarContrasenasCajero(admin);
                    break;
                case "0":
                    servicioAutenticacion.cerrarSesion();
                    System.out.println("\nSesión cerrada.");
                    presioneTecla();
                    return;
                default:
                    System.out.println("Opción inválida.");
                    presioneTecla();
            }
        }
    }

    private static void menuInstructor(Administrador admin) {
        while (true) {
            limpiarPantalla();
            System.out.println("\n");
            System.out.println("            PANEL DEL INSTRUCTOR");
            System.out.println("════════════════════════════════════════════");
            System.out.println("  1. Gestionar Eventos");
            System.out.println("  2. Ver Programación");
            System.out.println("  3. Registrar Asistencia a Evento");
            System.out.println("  4. Ver Mis Clases");
            System.out.println("  0. Cerrar Sesión");
            System.out.println("════════════════════════════════════════════");
            System.out.print("Seleccione una opción: ");

            String opcion = scanner.nextLine().trim();

            switch (opcion) {
                case "1":
                    System.out.println("\nGestionar eventos próximamente...");
                    presioneTecla();
                    break;
                case "2":
                    System.out.println("\nVer programación próximamente...");
                    presioneTecla();
                    break;
                case "3":
                    System.out.println("\nRegistrar asistencia a evento próximamente...");
                    presioneTecla();
                    break;
                case "4":
                    System.out.println("\nVer mis clases próximamente...");
                    presioneTecla();
                    break;
                case "0":
                    servicioAutenticacion.cerrarSesion();
                    System.out.println("\nSesión cerrada.");
                    presioneTecla();
                    return;
                default:
                    System.out.println("Opción inválida.");
                    presioneTecla();
            }
        }
    }

    private static void gestionarEmpleados(Administrador adminActual) {
        while (true) {
            limpiarPantalla();
            System.out.println("\n");
            System.out.println("           GESTIONAR EMPLEADOS");
            System.out.println("════════════════════════════════════════════");
            
            var empleados = servicioAutenticacion.obtenerRepositorioAdministrador().buscarTodos();
            
            if (empleados.isEmpty()) {
                System.out.println("\nNo hay empleados registrados.");
            } else {
                System.out.println("\nLista de Empleados:");
                System.out.println("");
                int i = 1;
                for (Administrador emp : empleados) {
                    System.out.printf("%d. %s - %s %s%n", 
                        i++,
                        emp.getNumDocumento(), 
                        emp.getNombres(), 
                        emp.getApellidos());
                    System.out.println("   Rol: " + emp.getTipoEmpleado().getNombreMostrar());
                    System.out.println("   " + (emp.getCorreo() != null ? emp.getCorreo() : "Sin correo"));
                    System.out.println("────────────────────────────────────────────");
                }
            }
            
            System.out.println("\n  1. Buscar Empleado");
            System.out.println("  2. Editar Empleado");
            System.out.println("  3. Eliminar Empleado");
            System.out.println("  0. Volver");
            System.out.println("════════════════════════════════════════════");
            System.out.print("Seleccione una opción: ");
            
            String opcion = scanner.nextLine().trim();
            
            switch (opcion) {
                case "1":
                    buscarEmpleado();
                    break;
                case "2":
                    System.out.println("\nEditar empleado próximamente...");
                    presioneTecla();
                    break;
                case "3":
                    eliminarEmpleado(adminActual);
                    break;
                case "0":
                    return;
                default:
                    System.out.println("Opción inválida.");
                    presioneTecla();
            }
        }
    }

    private static void buscarEmpleado() {
        limpiarPantalla();
        System.out.println("\n");
        System.out.println("             BUSCAR EMPLEADO");
        System.out.println("════════════════════════════════════════════");
        System.out.print("Ingrese documento del empleado: ");
        String doc = scanner.nextLine().trim();
        
        var empleado = servicioAutenticacion.obtenerRepositorioAdministrador().buscarPorNombreUsuario(doc);
        
        if (empleado.isPresent()) {
            Administrador emp = empleado.get();
            System.out.println("\nEmpleado encontrado:");
            System.out.println("");
            System.out.println("Documento:      " + emp.getNumDocumento());
            System.out.println("Nombres:        " + emp.getNombres());
            System.out.println("Apellidos:      " + emp.getApellidos());
            System.out.println("Rol:            " + emp.getTipoEmpleado().getNombreMostrar());
            System.out.println("Correo:         " + (emp.getCorreo() != null ? emp.getCorreo() : "N/A"));
            System.out.println("Celular:        " + (emp.getCelular() != null ? emp.getCelular() : "N/A"));
            System.out.println("Salario:        " + (emp.getSalario() != null ? "S/ " + emp.getSalario() : "N/A"));
            System.out.println("Contratación:   " + (emp.getFechaContratacion() != null ? emp.getFechaContratacion().format(FORMATEADOR_FECHA) : "N/A"));
            System.out.println("════════════════════════════════════════════");
        } else {
            System.out.println("\nNo se encontró empleado con ese documento.");
        }
        presioneTecla();
    }

    private static void eliminarEmpleado(Administrador adminActual) {
        limpiarPantalla();
        System.out.println("\n");
        System.out.println("            ELIMINAR EMPLEADO");
        System.out.println("════════════════════════════════════════════");
        System.out.print("Ingrese documento del empleado a eliminar: ");
        String doc = scanner.nextLine().trim();
        
        var empleado = servicioAutenticacion.obtenerRepositorioAdministrador().buscarPorNombreUsuario(doc);
        
        if (empleado.isEmpty()) {
            System.out.println("\nNo se encontró empleado con ese documento.");
            presioneTecla();
            return;
        }
        
        Administrador emp = empleado.get();
        
        System.out.println("\nEstá a punto de eliminar:");
        System.out.println(emp.getNumDocumento() + " - " + emp.getNombres() + " " + emp.getApellidos());
        System.out.print("\n¿Está seguro? (S/N): ");
        String confirmacion1 = scanner.nextLine().trim().toUpperCase();
        
        if (!confirmacion1.equals("S")) {
            System.out.println("Operación cancelada.");
            presioneTecla();
            return;
        }
        
        System.out.print("\nConfirmación final. ¿Eliminar empleado? (S/N): ");
        String confirmacion2 = scanner.nextLine().trim().toUpperCase();
        
        if (!confirmacion2.equals("S")) {
            System.out.println("Operación cancelada.");
            presioneTecla();
            return;
        }
        
        // Verificar contraseña del propietario
        System.out.print("\nIngrese su contraseña para confirmar: ");
        String password = scanner.nextLine();
        
        if (!servicioAutenticacion.verificarPassword(adminActual.getNumDocumento(), password)) {
            System.out.println("Contraseña incorrecta. Operación cancelada.");
            presioneTecla();
            return;
        }
        
        servicioAutenticacion.obtenerRepositorioAdministrador().eliminarPorNombreUsuario(doc);
        System.out.println("\nEmpleado eliminado exitosamente.");
        presioneTecla();
    }

    private static void gestionarSocios(Administrador admin) {
        while (true) {
            limpiarPantalla();
            System.out.println("\n");
            System.out.println("             GESTIONAR SOCIOS");
            System.out.println("════════════════════════════════════════════");
            
            var socios = servicioAutenticacion.obtenerRepositorioUsuario().buscarTodos();
            
            if (socios.isEmpty()) {
                System.out.println("\nNo hay socios registrados.");
            } else {
                System.out.println("\nLista de Socios:");
                System.out.println("");
                int i = 1;
                for (Usuario socio : socios) {
                    // Verificar estado de membresía
                    Optional<UsuarioMembresia> membresiaOpt = 
                        servicioMembresia.obtenerMembresiaActivaDeUsuario(socio.getNumDocumento());
                    
                    String estadoMembresia = "[X] Sin membresía";
                    if (membresiaOpt.isPresent()) {
                        UsuarioMembresia um = membresiaOpt.get();
                        if (um.estaActiva()) {
                            estadoMembresia = "[OK] Activa hasta " + 
                                            um.getFechaVencimiento().format(FORMATEADOR_FECHA);
                        } else if (um.estaVencida()) {
                            estadoMembresia = "[!] Vencida desde " + 
                                            um.getFechaVencimiento().format(FORMATEADOR_FECHA);
                        } else {
                            estadoMembresia = "[PAUSA] " + um.getEstado();
                        }
                    }
                    
                    System.out.printf("%d. %s - %s %s%n", 
                        i++,
                        socio.getNumDocumento(), 
                        socio.getNombres(), 
                        socio.getApellidos());
                    System.out.println("   " + (socio.getCorreo() != null ? socio.getCorreo() : "Sin correo"));
                    System.out.println("   " + estadoMembresia);
                    System.out.println("────────────────────────────────────────────");
                }
            }
            
            System.out.println("\n  1. Buscar Socio (ver detalle)");
            System.out.println("  2. Asignar/Renovar Membresía");
            System.out.println("  3. Editar Datos de Socio");
            if (admin.getTipoEmpleado() == TipoEmpleado.PROPIETARIO) {
                System.out.println("  4. Eliminar Socio");
            }
            System.out.println("  0. Volver");
            System.out.println("════════════════════════════════════════════");
            System.out.print("Seleccione una opción: ");
            
            String opcion = scanner.nextLine().trim();
            
            switch (opcion) {
                case "1":
                    buscarSocio();
                    break;
                case "2":
                    asignarRenovarMembresia();
                    break;
                case "3":
                    editarSocio();
                    break;
                case "4":
                    if (admin.getTipoEmpleado() == TipoEmpleado.PROPIETARIO) {
                        eliminarSocio(admin);
                    } else {
                        System.out.println("Opción inválida.");
                        presioneTecla();
                    }
                    break;
                case "0":
                    return;
                default:
                    System.out.println("Opción inválida.");
                    presioneTecla();
            }
        }
    }

    private static void buscarSocio() {
        limpiarPantalla();
        System.out.println("\n");
        System.out.println("               BUSCAR SOCIO");
        System.out.println("════════════════════════════════════════════");
        System.out.print("Ingrese documento del socio: ");
        String doc = scanner.nextLine().trim();
        
        var socio = servicioAutenticacion.obtenerRepositorioUsuario().buscarPorNombreUsuario(doc);
        
        if (socio.isPresent()) {
            Usuario user = socio.get();
            System.out.println("\nSocio encontrado:");
            System.out.println("");
            System.out.println("Documento:      " + user.getNumDocumento());
            System.out.println("Nombres:        " + user.getNombres());
            System.out.println("Apellidos:      " + user.getApellidos());
            System.out.println("Correo:         " + (user.getCorreo() != null ? user.getCorreo() : "N/A"));
            System.out.println("Celular:        " + (user.getCelular() != null ? user.getCelular() : "N/A"));
            System.out.println("Género:         " + (user.getGenero() != null ? user.getGenero() : "N/A"));
            System.out.println("Fecha Nac:      " + (user.getFechaNacimiento() != null ? user.getFechaNacimiento().format(FORMATEADOR_FECHA) : "N/A"));
            System.out.println("Dirección:      " + (user.getDireccion() != null ? user.getDireccion() : "N/A"));
            System.out.println("════════════════════════════════════════════");
            
            // Mostrar información de membresía
            Optional<UsuarioMembresia> membresiaOpt = 
                servicioMembresia.obtenerMembresiaActivaDeUsuario(doc);
            
            if (membresiaOpt.isPresent()) {
                UsuarioMembresia um = membresiaOpt.get();
                Optional<Membresia> planOpt = 
                    servicioMembresia.obtenerMembresiaPorId(um.getIdMembresia());
                
                System.out.println("\nINFORMACIÓN DE MEMBRESÍA:");
                System.out.println("════════════════════════════════════════════");
                if (planOpt.isPresent()) {
                    Membresia plan = planOpt.get();
                    System.out.println("Plan:          " + plan.getNombre());
                    System.out.printf("Precio:        $%.2f\n", plan.getPrecio());
                }
                System.out.println("Inicio:        " + um.getFechaInicio().format(FORMATEADOR_FECHA));
                System.out.println("Vencimiento:   " + um.getFechaVencimiento().format(FORMATEADOR_FECHA));
                
                String estado;
                if (um.estaActiva()) {
                    estado = "[OK] ACTIVA";
                } else if (um.estaVencida()) {
                    estado = "[X] VENCIDA";
                } else {
                    estado = "[PAUSA] " + um.getEstado();
                }
                System.out.println("Estado:        " + estado);
                System.out.println("════════════════════════════════════════════");
            } else {
                System.out.println("\nEste socio no tiene membresía activa.");
                System.out.println("════════════════════════════════════════════");
            }
        } else {
            System.out.println("\nNo se encontró socio con ese documento.");
        }
        presioneTecla();
    }

    private static void eliminarSocio(Administrador adminActual) {
        limpiarPantalla();
        System.out.println("\n");
        System.out.println("              ELIMINAR SOCIO");
        System.out.println("════════════════════════════════════════════");
        System.out.print("Ingrese documento del socio a eliminar: ");
        String doc = scanner.nextLine().trim();
        
        var socio = servicioAutenticacion.obtenerRepositorioUsuario().buscarPorNombreUsuario(doc);
        
        if (socio.isEmpty()) {
            System.out.println("\nNo se encontró socio con ese documento.");
            presioneTecla();
            return;
        }
        
        Usuario user = socio.get();
        
        System.out.println("\nEstá a punto de eliminar:");
        System.out.println(user.getNumDocumento() + " - " + user.getNombres() + " " + user.getApellidos());
        System.out.print("\n¿Está seguro? (S/N): ");
        String confirmacion1 = scanner.nextLine().trim().toUpperCase();
        
        if (!confirmacion1.equals("S")) {
            System.out.println("Operación cancelada.");
            presioneTecla();
            return;
        }
        
        System.out.print("\nConfirmación final. ¿Eliminar socio? (S/N): ");
        String confirmacion2 = scanner.nextLine().trim().toUpperCase();
        
        if (!confirmacion2.equals("S")) {
            System.out.println("Operación cancelada.");
            presioneTecla();
            return;
        }
        
        // Verificar contraseña del propietario
        System.out.print("\nIngrese su contraseña para confirmar: ");
        String password = scanner.nextLine();
        
        if (!servicioAutenticacion.verificarPassword(adminActual.getNumDocumento(), password)) {
            System.out.println(" Contraseña incorrecta. Operación cancelada.");
            presioneTecla();
            return;
        }
        
        servicioAutenticacion.obtenerRepositorioUsuario().eliminarPorNombreUsuario(doc);
        System.out.println("\n Socio eliminado exitosamente.");
        presioneTecla();
    }

    private static void registrarEmpleado() {
        limpiarPantalla();
        System.out.println("\n");
        System.out.println("        REGISTRAR NUEVO EMPLEADO");
        System.out.println("════════════════════════════════════════════");
        
        try {
            System.out.print(" Tipo Documento (DNI/CE): ");
            String tipoDoc = scanner.nextLine().trim().toUpperCase();
            
            System.out.print(" Número de Documento: ");
            String numDoc = scanner.nextLine().trim();
            
            if (numDoc.isEmpty()) {
                System.out.println(" El documento no puede estar vacío.");
                presioneTecla();
                return;
            }
            
            // Verificar si ya existe
            if (servicioAutenticacion.obtenerRepositorioAdministrador().buscarPorNombreUsuario(numDoc).isPresent()) {
                System.out.println(" Ya existe un empleado con ese documento.");
                presioneTecla();
                return;
            }
            
            System.out.print(" Nombres: ");
            String nombres = scanner.nextLine().trim();
            
            System.out.print(" Apellidos: ");
            String apellidos = scanner.nextLine().trim();
            
            System.out.print(" Fecha Nacimiento (dd/MM/yyyy): ");
            String fechaStr = scanner.nextLine().trim();
            LocalDate fechaNac = LocalDate.parse(fechaStr, FORMATEADOR_FECHA);
            
            System.out.println("\n Tipo de Empleado:");
            System.out.println("  1. Propietario");
            System.out.println("  2. Cajero/Recepcionista");
            System.out.println("  3. Instructor");
            System.out.print("Seleccione: ");
            String tipoEmpStr = scanner.nextLine().trim();
            
            TipoEmpleado tipoEmp;
            switch (tipoEmpStr) {
                case "1": tipoEmp = TipoEmpleado.PROPIETARIO; break;
                case "2": tipoEmp = TipoEmpleado.CAJERO_RECEPCIONISTA; break;
                case "3": tipoEmp = TipoEmpleado.INSTRUCTOR; break;
                default:
                    System.out.println(" Tipo de empleado inválido.");
                    presioneTecla();
                    return;
            }
            
            System.out.print(" Celular: ");
            String celular = scanner.nextLine().trim();
            
            System.out.print(" Correo: ");
            String correo = scanner.nextLine().trim();
            
            System.out.print(" Dirección: ");
            String direccion = scanner.nextLine().trim();
            
            System.out.print(" Salario: ");
            String salarioStr = scanner.nextLine().trim();
            
            System.out.print(" Fecha Contratación (dd/MM/yyyy): ");
            String fechaContStr = scanner.nextLine().trim();
            LocalDate fechaCont = LocalDate.parse(fechaContStr, FORMATEADOR_FECHA);
            
            System.out.print(" Contraseña: ");
            String password = scanner.nextLine();
            
            if (password.length() < 4) {
                System.out.println(" La contraseña debe tener al menos 4 caracteres.");
                presioneTecla();
                return;
            }
            
            // Crear empleado
            Administrador nuevoEmpleado = new Administrador(numDoc, nombres, apellidos, password, tipoEmp);
            nuevoEmpleado.setTipoDocumento(tipoDoc);
            nuevoEmpleado.setFechaNacimiento(fechaNac);
            nuevoEmpleado.setCelular(celular);
            nuevoEmpleado.setCorreo(correo);
            nuevoEmpleado.setDireccion(direccion);
            if (!salarioStr.isEmpty()) {
                nuevoEmpleado.setSalario(new BigDecimal(salarioStr));
            }
            nuevoEmpleado.setFechaContratacion(fechaCont);
            
            servicioAutenticacion.obtenerRepositorioAdministrador().guardar(nuevoEmpleado);
            
            System.out.println("\n Empleado registrado exitosamente.");
            System.out.println(" " + numDoc + " - " + nombres + " " + apellidos);
            System.out.println(" " + tipoEmp.getNombreMostrar());
            
        } catch (DateTimeParseException e) {
            System.out.println(" Formato de fecha inválido. Use dd/MM/yyyy");
        } catch (NumberFormatException e) {
            System.out.println(" Formato de salario inválido.");
        } catch (Exception e) {
            System.out.println(" Error al registrar empleado: " + e.getMessage());
        }
        presioneTecla();
    }

    private static void registrarSocio() {
        limpiarPantalla();
        System.out.println("\n");
        System.out.println("         REGISTRAR NUEVO SOCIO");
        System.out.println("════════════════════════════════════════════");
        
        try {
            System.out.print(" Tipo Documento (DNI/CE): ");
            String tipoDoc = scanner.nextLine().trim().toUpperCase();
            
            System.out.print(" Número de Documento: ");
            String numDoc = scanner.nextLine().trim();
            
            if (numDoc.isEmpty()) {
                System.out.println(" El documento no puede estar vacío.");
                presioneTecla();
                return;
            }
            
            // Verificar si ya existe
            if (servicioAutenticacion.obtenerRepositorioUsuario().buscarPorNombreUsuario(numDoc).isPresent()) {
                System.out.println(" Ya existe un socio con ese documento.");
                presioneTecla();
                return;
            }
            
            System.out.print(" Nombres: ");
            String nombres = scanner.nextLine().trim();
            
            System.out.print(" Apellidos: ");
            String apellidos = scanner.nextLine().trim();
            
            System.out.print(" Fecha Nacimiento (dd/MM/yyyy): ");
            String fechaStr = scanner.nextLine().trim();
            LocalDate fechaNac = LocalDate.parse(fechaStr, FORMATEADOR_FECHA);
            
            System.out.print(" Género (Masculino/Femenino/Otro): ");
            String genero = scanner.nextLine().trim();
            
            System.out.print(" Celular: ");
            String celular = scanner.nextLine().trim();
            
            System.out.print(" Correo: ");
            String correo = scanner.nextLine().trim();
            
            System.out.print(" Dirección: ");
            String direccion = scanner.nextLine().trim();
            
            System.out.print(" Contraseña: ");
            String password = scanner.nextLine();
            
            if (password.length() < 4) {
                System.out.println(" La contraseña debe tener al menos 4 caracteres.");
                presioneTecla();
                return;
            }
            
            // Crear socio
            Usuario nuevoSocio = new Usuario(numDoc, nombres, apellidos, password);
            nuevoSocio.setTipoDocumento(tipoDoc);
            nuevoSocio.setFechaNacimiento(fechaNac);
            nuevoSocio.setGenero(genero);
            nuevoSocio.setCelular(celular);
            nuevoSocio.setCorreo(correo);
            nuevoSocio.setDireccion(direccion);
            
            servicioAutenticacion.obtenerRepositorioUsuario().guardar(nuevoSocio);
            
            System.out.println("\n Socio registrado exitosamente.");
            System.out.println(" " + numDoc + " - " + nombres + " " + apellidos);
            
            // Preguntar si desea asignar membresía
            System.out.print("\n ¿Desea asignar una membresía ahora? (S/N): ");
            String asignar = scanner.nextLine().trim().toUpperCase();
            
            if (asignar.equals("S") || asignar.equals("SI")) {
                asignarMembresiaASocio(numDoc);
            }
            
        } catch (DateTimeParseException e) {
            System.out.println(" Formato de fecha inválido. Use dd/MM/yyyy");
        } catch (Exception e) {
            System.out.println(" Error al registrar socio: " + e.getMessage());
        }
        presioneTecla();
    }

    private static void asignarMembresiaASocio(String documentoSocio) {
        System.out.println("\n");
        System.out.println("       ASIGNAR MEMBRESÍA A SOCIO");
        System.out.println("════════════════════════════════════════════");
        
        // Listar membresías disponibles
        List<Membresia> membresias = servicioMembresia.listarMembresiasActivas();
        
        if (membresias.isEmpty()) {
            System.out.println("\n  No hay membresías activas disponibles.");
            System.out.println("   Solicite al propietario crear membresías en Configuración.");
            presioneTecla();
            return;
        }
        
        System.out.println("\n Membresías disponibles:");
        System.out.println("════════════════════════════════════════════");
        for (int i = 0; i < membresias.size(); i++) {
            Membresia m = membresias.get(i);
            System.out.printf("%d. %s - $%.2f (%d días)\n", 
                            i + 1, m.getNombre(), m.getPrecio(), m.getDuracionDias());
            System.out.printf("   %s\n", m.getDescripcion());
        }
        System.out.println("════════════════════════════════════════════");
        
        System.out.print("\n Seleccione el número de membresía (0 para cancelar): ");
        String opcion = scanner.nextLine().trim();
        
        try {
            int seleccion = Integer.parseInt(opcion);
            
            if (seleccion == 0) {
                System.out.println(" Asignación cancelada.");
                presioneTecla();
                return;
            }
            
            if (seleccion < 1 || seleccion > membresias.size()) {
                System.out.println(" Opción inválida.");
                presioneTecla();
                return;
            }
            
            Membresia membresiaSeleccionada = membresias.get(seleccion - 1);
            
            // Asignar membresía
            UsuarioMembresia asignacion = servicioMembresia.asignarMembresiaAUsuario(
                documentoSocio, membresiaSeleccionada.getId()
            );
            
            System.out.println("\n Membresía asignada exitosamente.");
            System.out.printf("   Plan: %s\n", membresiaSeleccionada.getNombre());
            System.out.printf("   Válida desde: %s\n", 
                            asignacion.getFechaInicio().format(FORMATEADOR_FECHA));
            System.out.printf("   Válida hasta: %s\n", 
                            asignacion.getFechaVencimiento().format(FORMATEADOR_FECHA));
            
        } catch (NumberFormatException e) {
            System.out.println(" Debe ingresar un número válido.");
        } catch (Exception e) {
            System.out.println(" Error al asignar membresía: " + e.getMessage());
        }
        
        presioneTecla();
    }

    private static void asignarRenovarMembresia() {
        limpiarPantalla();
        System.out.println("\n");
        System.out.println("       ASIGNAR/RENOVAR MEMBRESÍA");
        System.out.println("════════════════════════════════════════════");
        
        System.out.print(" Ingrese el documento del socio: ");
        String documento = scanner.nextLine().trim();
        
        Optional<Usuario> socioOpt = servicioAutenticacion.obtenerRepositorioUsuario().buscarPorNombreUsuario(documento);
        
        if (!socioOpt.isPresent()) {
            System.out.println(" Socio no encontrado.");
            presioneTecla();
            return;
        }
        
        Usuario socio = socioOpt.get();
        System.out.printf("\n Socio: %s %s\n", socio.getNombres(), socio.getApellidos());
        
        // Verificar si tiene membresía activa
        Optional<UsuarioMembresia> membresiaActualOpt = 
            servicioMembresia.obtenerMembresiaActivaDeUsuario(documento);
        
        if (membresiaActualOpt.isPresent()) {
            UsuarioMembresia membresiaActual = membresiaActualOpt.get();
            Optional<Membresia> planOpt = 
                servicioMembresia.obtenerMembresiaPorId(membresiaActual.getIdMembresia());
            
            System.out.println("\n  Este socio ya tiene una membresía activa:");
            if (planOpt.isPresent()) {
                System.out.printf("   Plan: %s\n", planOpt.get().getNombre());
            }
            System.out.printf("   Estado: %s\n", membresiaActual.getEstado());
            System.out.printf("   Vence: %s\n", 
                            membresiaActual.getFechaVencimiento().format(FORMATEADOR_FECHA));
            
            System.out.print("\n¿Desea renovar/reemplazar esta membresía? (S/N): ");
            String confirma = scanner.nextLine().trim().toUpperCase();
            
            if (!confirma.equals("S") && !confirma.equals("SI")) {
                System.out.println(" Operación cancelada.");
                presioneTecla();
                return;
            }
        }
        
        asignarMembresiaASocio(documento);
    }

    private static void editarSocio() {
        limpiarPantalla();
        System.out.println("\n");
        System.out.println("           EDITAR DATOS DE SOCIO");
        System.out.println("════════════════════════════════════════════");
        
        System.out.print(" Ingrese el documento del socio: ");
        String documento = scanner.nextLine().trim();
        
        Optional<Usuario> socioOpt = servicioAutenticacion.obtenerRepositorioUsuario().buscarPorNombreUsuario(documento);
        
        if (!socioOpt.isPresent()) {
            System.out.println(" Socio no encontrado.");
            presioneTecla();
            return;
        }
        
        Usuario socio = socioOpt.get();
        
        System.out.println("\n Datos actuales:");
        System.out.printf("   Nombres: %s\n", socio.getNombres());
        System.out.printf("   Apellidos: %s\n", socio.getApellidos());
        System.out.printf("   Celular: %s\n", socio.getCelular() != null ? socio.getCelular() : "No registrado");
        System.out.printf("   Correo: %s\n", socio.getCorreo() != null ? socio.getCorreo() : "No registrado");
        System.out.printf("   Dirección: %s\n", socio.getDireccion() != null ? socio.getDireccion() : "No registrado");
        
        System.out.println("\n(Presione ENTER para mantener el valor actual)");
        
        try {
            System.out.print(" Nuevos nombres [" + socio.getNombres() + "]: ");
            String nombres = scanner.nextLine().trim();
            if (!nombres.isEmpty()) {
                socio.setNombres(nombres);
            }
            
            System.out.print(" Nuevos apellidos [" + socio.getApellidos() + "]: ");
            String apellidos = scanner.nextLine().trim();
            if (!apellidos.isEmpty()) {
                socio.setApellidos(apellidos);
            }
            
            System.out.print(" Nuevo celular [" + (socio.getCelular() != null ? socio.getCelular() : "") + "]: ");
            String celular = scanner.nextLine().trim();
            if (!celular.isEmpty()) {
                socio.setCelular(celular);
            }
            
            System.out.print(" Nuevo correo [" + (socio.getCorreo() != null ? socio.getCorreo() : "") + "]: ");
            String correo = scanner.nextLine().trim();
            if (!correo.isEmpty()) {
                socio.setCorreo(correo);
            }
            
            System.out.print(" Nueva dirección [" + (socio.getDireccion() != null ? socio.getDireccion() : "") + "]: ");
            String direccion = scanner.nextLine().trim();
            if (!direccion.isEmpty()) {
                socio.setDireccion(direccion);
            }
            
            servicioAutenticacion.obtenerRepositorioUsuario().guardar(socio);
            
            System.out.println("\n Datos del socio actualizados exitosamente.");
            
        } catch (Exception e) {
            System.out.println(" Error al editar socio: " + e.getMessage());
        }
        
        presioneTecla();
    }

    // 
    // MAESTROS / CONFIGURACIÓN
    // 

    private static void menuMaestrosConfiguracion(Administrador admin) {
        while (true) {
            limpiarPantalla();
            System.out.println("\n");
            System.out.println("        MAESTROS Y CONFIGURACIÓN");
            System.out.println("════════════════════════════════════════════");
            System.out.println("  1.  Gestionar Planes de Membresía");
            System.out.println("  2.  Listar Todas las Membresías");
            System.out.println("  3.  Crear Nueva Membresía");
            System.out.println("  4.   Editar Membresía");
            System.out.println("  5.   Desactivar Membresía");
            System.out.println("  0.   Volver al Menú Principal");
            System.out.println("════════════════════════════════════════════");
            System.out.print("Seleccione una opción: ");

            String opcion = scanner.nextLine().trim();

            switch (opcion) {
                case "1":
                    gestionarMembresias();
                    break;
                case "2":
                    listarMembresias();
                    break;
                case "3":
                    crearMembresia();
                    break;
                case "4":
                    editarMembresia();
                    break;
                case "5":
                    desactivarMembresia();
                    break;
                case "0":
                    return;
                default:
                    System.out.println(" Opción inválida.");
                    presioneTecla();
            }
        }
    }

    private static void gestionarMembresias() {
        limpiarPantalla();
        System.out.println("\n");
        System.out.println("          GESTIÓN DE MEMBRESÍAS");
        System.out.println("════════════════════════════════════════════");
        
        List<Membresia> membresias = servicioMembresia.listarMembresiasActivas();
        
        if (membresias.isEmpty()) {
            System.out.println("\n  No hay membresías activas registradas.");
            presioneTecla();
            return;
        }
        
        System.out.println("\n Membresías Activas:");
        System.out.println("════════════════════════════════════════════");
        for (Membresia m : membresias) {
            System.out.printf("ID: %s | %s\n", m.getId(), m.getNombre());
            System.out.printf("   Precio: $%.2f | Duración: %d días\n", 
                            m.getPrecio(), m.getDuracionDias());
            System.out.printf("   Descripción: %s\n", m.getDescripcion());
            System.out.println("────────────────────────────────────────────");
        }
        
        presioneTecla();
    }

    private static void listarMembresias() {
        limpiarPantalla();
        System.out.println("\n");
        System.out.println("         LISTADO DE MEMBRESÍAS");
        System.out.println("════════════════════════════════════════════");
        
        List<Membresia> membresias = servicioMembresia.listarTodasMembresias();
        
        if (membresias.isEmpty()) {
            System.out.println("\n  No hay membresías registradas.");
            presioneTecla();
            return;
        }
        
        System.out.println("\n Todas las Membresías:");
        System.out.println("════════════════════════════════════════════");
        for (Membresia m : membresias) {
            String estado = m.isActivo() ? " ACTIVA" : " INACTIVA";
            System.out.printf("[%s] ID: %s | %s\n", estado, m.getId(), m.getNombre());
            System.out.printf("   Precio: $%.2f | Duración: %d días\n", 
                            m.getPrecio(), m.getDuracionDias());
            System.out.printf("   Descripción: %s\n", m.getDescripcion());
            System.out.println("────────────────────────────────────────────");
        }
        
        presioneTecla();
    }

    private static void crearMembresia() {
        limpiarPantalla();
        System.out.println("\n");
        System.out.println("         CREAR NUEVA MEMBRESÍA");
        System.out.println("════════════════════════════════════════════");
        
        try {
            System.out.print(" Nombre de la membresía: ");
            String nombre = scanner.nextLine().trim();
            
            if (nombre.isEmpty()) {
                System.out.println(" El nombre no puede estar vacío.");
                presioneTecla();
                return;
            }
            
            System.out.print(" Descripción: ");
            String descripcion = scanner.nextLine().trim();
            
            System.out.print(" Precio: ");
            String precioStr = scanner.nextLine().trim();
            BigDecimal precio = new BigDecimal(precioStr);
            
            if (precio.compareTo(BigDecimal.ZERO) <= 0) {
                System.out.println(" El precio debe ser mayor a cero.");
                presioneTecla();
                return;
            }
            
            System.out.print(" Duración (días): ");
            String diasStr = scanner.nextLine().trim();
            int duracionDias = Integer.parseInt(diasStr);
            
            if (duracionDias <= 0) {
                System.out.println(" La duración debe ser mayor a cero.");
                presioneTecla();
                return;
            }
            
            // Crear membresía
            Membresia nuevaMembresia = new Membresia(null, nombre, descripcion, precio, duracionDias);
            servicioMembresia.crearMembresia(nuevaMembresia);
            
            System.out.println("\n Membresía creada exitosamente.");
            System.out.printf(" %s - $%.2f por %d días\n", nombre, precio, duracionDias);
            
        } catch (NumberFormatException e) {
            System.out.println(" Error: Ingrese valores numéricos válidos.");
        } catch (Exception e) {
            System.out.println(" Error al crear membresía: " + e.getMessage());
        }
        
        presioneTecla();
    }

    private static void editarMembresia() {
        limpiarPantalla();
        System.out.println("\n");
        System.out.println("           EDITAR MEMBRESÍA");
        System.out.println("════════════════════════════════════════════");
        
        System.out.print(" Ingrese el ID de la membresía a editar: ");
        String id = scanner.nextLine().trim();
        
        Optional<Membresia> membresiaOpt = servicioMembresia.obtenerMembresiaPorId(id);
        
        if (!membresiaOpt.isPresent()) {
            System.out.println(" Membresía no encontrada.");
            presioneTecla();
            return;
        }
        
        Membresia membresia = membresiaOpt.get();
        
        System.out.println("\n Datos actuales:");
        System.out.printf("   Nombre: %s\n", membresia.getNombre());
        System.out.printf("   Descripción: %s\n", membresia.getDescripcion());
        System.out.printf("   Precio: $%.2f\n", membresia.getPrecio());
        System.out.printf("   Duración: %d días\n", membresia.getDuracionDias());
        
        System.out.println("\n(Presione ENTER para mantener el valor actual)");
        
        try {
            System.out.print(" Nuevo nombre [" + membresia.getNombre() + "]: ");
            String nombre = scanner.nextLine().trim();
            if (!nombre.isEmpty()) {
                membresia.setNombre(nombre);
            }
            
            System.out.print(" Nueva descripción [" + membresia.getDescripcion() + "]: ");
            String descripcion = scanner.nextLine().trim();
            if (!descripcion.isEmpty()) {
                membresia.setDescripcion(descripcion);
            }
            
            System.out.print(" Nuevo precio [" + membresia.getPrecio() + "]: ");
            String precioStr = scanner.nextLine().trim();
            if (!precioStr.isEmpty()) {
                BigDecimal precio = new BigDecimal(precioStr);
                if (precio.compareTo(BigDecimal.ZERO) > 0) {
                    membresia.setPrecio(precio);
                } else {
                    System.out.println("  Precio inválido, manteniendo valor actual.");
                }
            }
            
            System.out.print(" Nueva duración en días [" + membresia.getDuracionDias() + "]: ");
            String diasStr = scanner.nextLine().trim();
            if (!diasStr.isEmpty()) {
                int duracionDias = Integer.parseInt(diasStr);
                if (duracionDias > 0) {
                    membresia.setDuracionDias(duracionDias);
                } else {
                    System.out.println("  Duración inválida, manteniendo valor actual.");
                }
            }
            
            servicioMembresia.actualizarMembresia(membresia);
            
            System.out.println("\n Membresía actualizada exitosamente.");
            
        } catch (NumberFormatException e) {
            System.out.println(" Error: Valores numéricos inválidos.");
        } catch (Exception e) {
            System.out.println(" Error al editar membresía: " + e.getMessage());
        }
        
        presioneTecla();
    }

    private static void desactivarMembresia() {
        limpiarPantalla();
        System.out.println("\n");
        System.out.println("         DESACTIVAR MEMBRESÍA");
        System.out.println("════════════════════════════════════════════");
        
        System.out.print(" Ingrese el ID de la membresía a desactivar: ");
        String id = scanner.nextLine().trim();
        
        Optional<Membresia> membresiaOpt = servicioMembresia.obtenerMembresiaPorId(id);
        
        if (!membresiaOpt.isPresent()) {
            System.out.println(" Membresía no encontrada.");
            presioneTecla();
            return;
        }
        
        Membresia membresia = membresiaOpt.get();
        
        System.out.printf("\n Membresía: %s\n", membresia.getNombre());
        System.out.printf("   Precio: $%.2f | Duración: %d días\n", 
                        membresia.getPrecio(), membresia.getDuracionDias());
        
        System.out.print("\n  ¿Está seguro de desactivar esta membresía? (S/N): ");
        String confirma = scanner.nextLine().trim().toUpperCase();
        
        if (confirma.equals("S") || confirma.equals("SI")) {
            servicioMembresia.desactivarMembresia(id);
            System.out.println("\n Membresía desactivada exitosamente.");
        } else {
            System.out.println("\n Operación cancelada.");
        }
        
        presioneTecla();
    }

    // ============================================
    // FUNCIONES DE CAMBIO DE CONTRASEÑA
    // ============================================

    private static void cambiarContrasenaUsuario(Usuario user) {
        limpiarPantalla();
        System.out.println("\n");
        System.out.println("         CAMBIAR MI CONTRASEÑA");
        System.out.println("════════════════════════════════════════════");
        
        System.out.print("Contraseña actual: ");
        String contrasenaActual = scanner.nextLine();
        
        if (!com.familyfitgym.console.utilidad.UtilidadPassword.verificarPassword(contrasenaActual, user.getHashPassword())) {
            System.out.println("\n❌ Contraseña actual incorrecta.");
            presioneTecla();
            return;
        }
        
        System.out.print("Nueva contraseña: ");
        String nuevaContrasena = scanner.nextLine();
        
        if (nuevaContrasena.isEmpty() || nuevaContrasena.length() < 4) {
            System.out.println("\n❌ La contraseña debe tener al menos 4 caracteres.");
            presioneTecla();
            return;
        }
        
        System.out.print("Confirmar nueva contraseña: ");
        String confirmarContrasena = scanner.nextLine();
        
        if (!nuevaContrasena.equals(confirmarContrasena)) {
            System.out.println("\n❌ Las contraseñas no coinciden.");
            presioneTecla();
            return;
        }
        
        user.setHashPassword(com.familyfitgym.console.utilidad.UtilidadPassword.hashPassword(nuevaContrasena));
        servicioAutenticacion.obtenerRepositorioUsuario().guardar(user);
        
        System.out.println("\n✓ Contraseña actualizada exitosamente.");
        presioneTecla();
    }

    private static void menuCambiarContrasenasPropietario(Administrador admin) {
        while (true) {
            limpiarPantalla();
            System.out.println("\n");
            System.out.println("         CAMBIAR CONTRASEÑAS");
            System.out.println("════════════════════════════════════════════");
            System.out.println("  1. Cambiar Mi Contraseña");
            System.out.println("  2. Cambiar Contraseña de Empleado");
            System.out.println("  3. Cambiar Contraseña de Socio");
            System.out.println("  0. Volver");
            System.out.println("════════════════════════════════════════════");
            System.out.print("Seleccione una opción: ");
            
            String opcion = scanner.nextLine().trim();
            
            switch (opcion) {
                case "1":
                    cambiarContrasenaEmpleado(admin, admin);
                    break;
                case "2":
                    cambiarContrasenaEmpleadoPropietario();
                    break;
                case "3":
                    cambiarContrasenaSocioPropietario();
                    break;
                case "0":
                    return;
                default:
                    System.out.println("Opción inválida.");
                    presioneTecla();
            }
        }
    }

    private static void menuCambiarContrasenasCajero(Administrador admin) {
        while (true) {
            limpiarPantalla();
            System.out.println("\n");
            System.out.println("         CAMBIAR CONTRASEÑAS");
            System.out.println("════════════════════════════════════════════");
            System.out.println("  1. Cambiar Mi Contraseña");
            System.out.println("  2. Cambiar Contraseña de Socio");
            System.out.println("  0. Volver");
            System.out.println("════════════════════════════════════════════");
            System.out.print("Seleccione una opción: ");
            
            String opcion = scanner.nextLine().trim();
            
            switch (opcion) {
                case "1":
                    cambiarContrasenaEmpleado(admin, admin);
                    break;
                case "2":
                    cambiarContrasenaSocioCajero();
                    break;
                case "0":
                    return;
                default:
                    System.out.println("Opción inválida.");
                    presioneTecla();
            }
        }
    }

    private static void cambiarContrasenaEmpleado(Administrador admin, Administrador empleadoACambiar) {
        limpiarPantalla();
        System.out.println("\n");
        System.out.println("    CAMBIAR CONTRASEÑA DE EMPLEADO");
        System.out.println("════════════════════════════════════════════");
        System.out.println("Empleado: " + empleadoACambiar.getNombres() + " " + empleadoACambiar.getApellidos());
        System.out.println("Documento: " + empleadoACambiar.getNumDocumento());
        System.out.println("════════════════════════════════════════════");
        
        // Si es el mismo usuario, pedir contraseña actual
        if (admin.getNumDocumento().equals(empleadoACambiar.getNumDocumento())) {
            System.out.print("Contraseña actual: ");
            String contrasenaActual = scanner.nextLine();
            
            if (!com.familyfitgym.console.utilidad.UtilidadPassword.verificarPassword(contrasenaActual, admin.getHashPassword())) {
                System.out.println("\n❌ Contraseña actual incorrecta.");
                presioneTecla();
                return;
            }
        }
        
        System.out.print("Nueva contraseña: ");
        String nuevaContrasena = scanner.nextLine();
        
        if (nuevaContrasena.isEmpty() || nuevaContrasena.length() < 4) {
            System.out.println("\n❌ La contraseña debe tener al menos 4 caracteres.");
            presioneTecla();
            return;
        }
        
        System.out.print("Confirmar nueva contraseña: ");
        String confirmarContrasena = scanner.nextLine();
        
        if (!nuevaContrasena.equals(confirmarContrasena)) {
            System.out.println("\n❌ Las contraseñas no coinciden.");
            presioneTecla();
            return;
        }
        
        empleadoACambiar.setHashPassword(com.familyfitgym.console.utilidad.UtilidadPassword.hashPassword(nuevaContrasena));
        servicioAutenticacion.obtenerRepositorioAdministrador().guardar(empleadoACambiar);
        
        System.out.println("\n✓ Contraseña actualizada exitosamente.");
        presioneTecla();
    }

    private static void cambiarContrasenaEmpleadoPropietario() {
        limpiarPantalla();
        System.out.println("\n");
        System.out.println("  CAMBIAR CONTRASEÑA DE EMPLEADO");
        System.out.println("════════════════════════════════════════════");
        
        var empleados = servicioAutenticacion.obtenerRepositorioAdministrador().buscarTodos();
        
        if (empleados.isEmpty()) {
            System.out.println("\nNo hay empleados registrados.");
            presioneTecla();
            return;
        }
        
        System.out.println("\nLista de Empleados:");
        System.out.println("");
        int i = 1;
        for (Administrador emp : empleados) {
            System.out.printf("%d. %s - %s %s (%s)%n", 
                i++,
                emp.getNumDocumento(), 
                emp.getNombres(), 
                emp.getApellidos(),
                emp.getTipoEmpleado().getNombreMostrar());
        }
        
        System.out.println("════════════════════════════════════════════");
        System.out.print("\nIngrese el número del empleado: ");
        String seleccion = scanner.nextLine().trim();
        
        try {
            int index = Integer.parseInt(seleccion) - 1;
            if (index >= 0 && index < empleados.size()) {
                Administrador empleadoSeleccionado = empleados.get(index);
                cambiarContrasenaEmpleado(servicioAutenticacion.obtenerAdministradorActual(), empleadoSeleccionado);
            } else {
                System.out.println("\n❌ Número inválido.");
                presioneTecla();
            }
        } catch (NumberFormatException e) {
            System.out.println("\n❌ Entrada inválida.");
            presioneTecla();
        }
    }

    private static void cambiarContrasenaSocioPropietario() {
        limpiarPantalla();
        System.out.println("\n");
        System.out.println("    CAMBIAR CONTRASEÑA DE SOCIO");
        System.out.println("════════════════════════════════════════════");
        
        System.out.print("Ingrese el documento del socio: ");
        String documento = scanner.nextLine().trim();
        
        if (documento.isEmpty()) {
            System.out.println("\n❌ Debe ingresar un documento.");
            presioneTecla();
            return;
        }
        
        Optional<Usuario> usuarioOpt = servicioAutenticacion.obtenerRepositorioUsuario().buscarPorNombreUsuario(documento);
        
        if (!usuarioOpt.isPresent()) {
            System.out.println("\n❌ Socio no encontrado.");
            presioneTecla();
            return;
        }
        
        Usuario usuario = usuarioOpt.get();
        cambiarContrasenaSocio(usuario);
    }

    private static void cambiarContrasenaSocioCajero() {
        limpiarPantalla();
        System.out.println("\n");
        System.out.println("    CAMBIAR CONTRASEÑA DE SOCIO");
        System.out.println("════════════════════════════════════════════");
        
        System.out.print("Ingrese el documento del socio: ");
        String documento = scanner.nextLine().trim();
        
        if (documento.isEmpty()) {
            System.out.println("\n❌ Debe ingresar un documento.");
            presioneTecla();
            return;
        }
        
        Optional<Usuario> usuarioOpt = servicioAutenticacion.obtenerRepositorioUsuario().buscarPorNombreUsuario(documento);
        
        if (!usuarioOpt.isPresent()) {
            System.out.println("\n❌ Socio no encontrado.");
            presioneTecla();
            return;
        }
        
        Usuario usuario = usuarioOpt.get();
        cambiarContrasenaSocio(usuario);
    }

    private static void cambiarContrasenaSocio(Usuario usuario) {
        limpiarPantalla();
        System.out.println("\n");
        System.out.println("    CAMBIAR CONTRASEÑA DE SOCIO");
        System.out.println("════════════════════════════════════════════");
        System.out.println("Socio: " + usuario.getNombres() + " " + usuario.getApellidos());
        System.out.println("Documento: " + usuario.getNumDocumento());
        System.out.println("════════════════════════════════════════════");
        
        System.out.print("Nueva contraseña: ");
        String nuevaContrasena = scanner.nextLine();
        
        if (nuevaContrasena.isEmpty() || nuevaContrasena.length() < 4) {
            System.out.println("\n❌ La contraseña debe tener al menos 4 caracteres.");
            presioneTecla();
            return;
        }
        
        System.out.print("Confirmar nueva contraseña: ");
        String confirmarContrasena = scanner.nextLine();
        
        if (!nuevaContrasena.equals(confirmarContrasena)) {
            System.out.println("\n❌ Las contraseñas no coinciden.");
            presioneTecla();
            return;
        }
        
        usuario.setHashPassword(com.familyfitgym.console.utilidad.UtilidadPassword.hashPassword(nuevaContrasena));
        servicioAutenticacion.obtenerRepositorioUsuario().guardar(usuario);
        
        System.out.println("\n✓ Contraseña del socio actualizada exitosamente.");
        presioneTecla();
    }

    private static void limpiarPantalla() {
        // En Windows
        try {
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        } catch (Exception e) {
            // En Linux/Mac o si falla en Windows, imprimir líneas vacías
            for (int i = 0; i < 50; i++) {
                System.out.println();
            }
        }
    }

    private static void presioneTecla() {
        System.out.print("\n⏎ Presione ENTER para continuar...");
        scanner.nextLine();
    }
}
