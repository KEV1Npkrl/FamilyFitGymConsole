package com.familyfitgym.console.servicio;

import com.familyfitgym.console.modelo.Membresia;
import com.familyfitgym.console.modelo.UsuarioMembresia;
import com.familyfitgym.console.repositorio.RepositorioMembresia;
import com.familyfitgym.console.repositorio.RepositorioUsuarioMembresia;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Servicio para gestionar membresías y asignaciones a usuarios
 */
public class ServicioMembresia {
    
    private final RepositorioMembresia repositorioMembresia;
    private final RepositorioUsuarioMembresia repositorioUsuarioMembresia;

    public ServicioMembresia(RepositorioMembresia repositorioMembresia, 
                           RepositorioUsuarioMembresia repositorioUsuarioMembresia) {
        this.repositorioMembresia = repositorioMembresia;
        this.repositorioUsuarioMembresia = repositorioUsuarioMembresia;
    }

    // Gestión de Planes de Membresía
    public void crearMembresia(Membresia membresia) {
        if (membresia.getId() == null || membresia.getId().isEmpty()) {
            membresia.setId("MEM-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase());
        }
        repositorioMembresia.guardar(membresia);
    }

    public Optional<Membresia> obtenerMembresiaPorId(String id) {
        return repositorioMembresia.buscarPorId(id);
    }

    public List<Membresia> listarTodasMembresias() {
        return repositorioMembresia.buscarTodas();
    }

    public List<Membresia> listarMembresiasActivas() {
        return repositorioMembresia.buscarTodasActivas();
    }

    public void actualizarMembresia(Membresia membresia) {
        repositorioMembresia.guardar(membresia);
    }

    public void desactivarMembresia(String id) {
        Optional<Membresia> membresiaOpt = repositorioMembresia.buscarPorId(id);
        if (membresiaOpt.isPresent()) {
            Membresia membresia = membresiaOpt.get();
            membresia.setActivo(false);
            repositorioMembresia.guardar(membresia);
        }
    }

    public void eliminarMembresia(String id) {
        repositorioMembresia.eliminarPorId(id);
    }

    // Asignación de Membresías a Usuarios
    public UsuarioMembresia asignarMembresiaAUsuario(String documentoUsuario, String idMembresia) {
        Optional<Membresia> membresiaOpt = repositorioMembresia.buscarPorId(idMembresia);
        if (!membresiaOpt.isPresent()) {
            throw new IllegalArgumentException("Membresía no encontrada");
        }

        Membresia membresia = membresiaOpt.get();
        LocalDate fechaInicio = LocalDate.now();
        LocalDate fechaVencimiento = fechaInicio.plusDays(membresia.getDuracionDias());

        UsuarioMembresia usuarioMembresia = new UsuarioMembresia(
            "UM-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase(),
            documentoUsuario,
            idMembresia,
            fechaInicio,
            fechaVencimiento
        );

        repositorioUsuarioMembresia.guardar(usuarioMembresia);
        return usuarioMembresia;
    }

    public Optional<UsuarioMembresia> obtenerMembresiaActivaDeUsuario(String documentoUsuario) {
        return repositorioUsuarioMembresia.buscarActivaPorDocumentoUsuario(documentoUsuario);
    }

    public List<UsuarioMembresia> obtenerHistorialMembresiasUsuario(String documentoUsuario) {
        return repositorioUsuarioMembresia.buscarPorDocumentoUsuario(documentoUsuario);
    }

    public boolean usuarioTieneMembresiaActiva(String documentoUsuario) {
        Optional<UsuarioMembresia> membresiaActiva = 
            repositorioUsuarioMembresia.buscarActivaPorDocumentoUsuario(documentoUsuario);
        return membresiaActiva.isPresent() && membresiaActiva.get().estaActiva();
    }

    public void renovarMembresia(String documentoUsuario, String idMembresia) {
        // Desactivar membresía actual si existe
        Optional<UsuarioMembresia> membresiaActual = 
            repositorioUsuarioMembresia.buscarActivaPorDocumentoUsuario(documentoUsuario);
        
        if (membresiaActual.isPresent()) {
            UsuarioMembresia actual = membresiaActual.get();
            actual.setActivo(false);
            actual.setEstado("FINALIZADA");
            repositorioUsuarioMembresia.guardar(actual);
        }

        // Crear nueva membresía
        asignarMembresiaAUsuario(documentoUsuario, idMembresia);
    }

    public void suspenderMembresia(String documentoUsuario) {
        Optional<UsuarioMembresia> membresiaActiva = 
            repositorioUsuarioMembresia.buscarActivaPorDocumentoUsuario(documentoUsuario);
        
        if (membresiaActiva.isPresent()) {
            UsuarioMembresia membresia = membresiaActiva.get();
            membresia.setEstado("SUSPENDIDA");
            repositorioUsuarioMembresia.guardar(membresia);
        }
    }

    public void reactivarMembresia(String documentoUsuario) {
        Optional<UsuarioMembresia> membresiaActiva = 
            repositorioUsuarioMembresia.buscarActivaPorDocumentoUsuario(documentoUsuario);
        
        if (membresiaActiva.isPresent()) {
            UsuarioMembresia membresia = membresiaActiva.get();
            if (!membresia.estaVencida()) {
                membresia.setEstado("ACTIVA");
                repositorioUsuarioMembresia.guardar(membresia);
            }
        }
    }

    public void cancelarMembresia(String documentoUsuario) {
        Optional<UsuarioMembresia> membresiaActiva = 
            repositorioUsuarioMembresia.buscarActivaPorDocumentoUsuario(documentoUsuario);
        
        if (membresiaActiva.isPresent()) {
            UsuarioMembresia membresia = membresiaActiva.get();
            membresia.setActivo(false);
            membresia.setEstado("CANCELADA");
            repositorioUsuarioMembresia.guardar(membresia);
        }
    }

    public void actualizarEstadosMembresias() {
        List<UsuarioMembresia> todasMembresias = repositorioUsuarioMembresia.buscarTodas();
        for (UsuarioMembresia membresia : todasMembresias) {
            if (membresia.isActivo() && membresia.estaVencida() && "ACTIVA".equals(membresia.getEstado())) {
                membresia.setEstado("VENCIDA");
                membresia.setActivo(false);
                repositorioUsuarioMembresia.guardar(membresia);
            }
        }
    }
}
