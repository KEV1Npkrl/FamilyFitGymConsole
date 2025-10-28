package com.familyfitgym.console.service;

import com.familyfitgym.console.model.Membresia;
import com.familyfitgym.console.model.UsuarioMembresia;
import com.familyfitgym.console.repository.MembresiaRepository;
import com.familyfitgym.console.repository.UsuarioMembresiaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Servicio para gestionar membresías y asignaciones a usuarios
 */
public class MembresiaService {
    
    private final MembresiaRepository membresiaRepository;
    private final UsuarioMembresiaRepository usuarioMembresiaRepository;

    public MembresiaService(MembresiaRepository membresiaRepository, 
                           UsuarioMembresiaRepository usuarioMembresiaRepository) {
        this.membresiaRepository = membresiaRepository;
        this.usuarioMembresiaRepository = usuarioMembresiaRepository;
    }

    // Gestión de Planes de Membresía
    public void crearMembresia(Membresia membresia) {
        if (membresia.getId() == null || membresia.getId().isEmpty()) {
            membresia.setId("MEM-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase());
        }
        membresiaRepository.save(membresia);
    }

    public Optional<Membresia> obtenerMembresiaPorId(String id) {
        return membresiaRepository.findById(id);
    }

    public List<Membresia> listarTodasMembresias() {
        return membresiaRepository.findAll();
    }

    public List<Membresia> listarMembresiasActivas() {
        return membresiaRepository.findAllActivas();
    }

    public void actualizarMembresia(Membresia membresia) {
        membresiaRepository.save(membresia);
    }

    public void desactivarMembresia(String id) {
        Optional<Membresia> membresiaOpt = membresiaRepository.findById(id);
        if (membresiaOpt.isPresent()) {
            Membresia membresia = membresiaOpt.get();
            membresia.setActivo(false);
            membresiaRepository.save(membresia);
        }
    }

    public void eliminarMembresia(String id) {
        membresiaRepository.deleteById(id);
    }

    // Asignación de Membresías a Usuarios
    public UsuarioMembresia asignarMembresiaAUsuario(String usuarioDocumento, String membresiaId) {
        Optional<Membresia> membresiaOpt = membresiaRepository.findById(membresiaId);
        if (!membresiaOpt.isPresent()) {
            throw new IllegalArgumentException("Membresía no encontrada");
        }

        Membresia membresia = membresiaOpt.get();
        LocalDate fechaInicio = LocalDate.now();
        LocalDate fechaVencimiento = fechaInicio.plusDays(membresia.getDuracionDias());

        UsuarioMembresia usuarioMembresia = new UsuarioMembresia(
            "UM-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase(),
            usuarioDocumento,
            membresiaId,
            fechaInicio,
            fechaVencimiento
        );

        usuarioMembresiaRepository.save(usuarioMembresia);
        return usuarioMembresia;
    }

    public Optional<UsuarioMembresia> obtenerMembresiaActivaDeUsuario(String usuarioDocumento) {
        return usuarioMembresiaRepository.findActivaByUsuarioDocumento(usuarioDocumento);
    }

    public List<UsuarioMembresia> obtenerHistorialMembresiasUsuario(String usuarioDocumento) {
        return usuarioMembresiaRepository.findByUsuarioDocumento(usuarioDocumento);
    }

    public boolean usuarioTieneMembresiaActiva(String usuarioDocumento) {
        Optional<UsuarioMembresia> membresiaActiva = 
            usuarioMembresiaRepository.findActivaByUsuarioDocumento(usuarioDocumento);
        return membresiaActiva.isPresent() && membresiaActiva.get().estaActiva();
    }

    public void renovarMembresia(String usuarioDocumento, String membresiaId) {
        // Desactivar membresía actual si existe
        Optional<UsuarioMembresia> membresiaActual = 
            usuarioMembresiaRepository.findActivaByUsuarioDocumento(usuarioDocumento);
        
        if (membresiaActual.isPresent()) {
            UsuarioMembresia actual = membresiaActual.get();
            actual.setActivo(false);
            actual.setEstado("FINALIZADA");
            usuarioMembresiaRepository.save(actual);
        }

        // Crear nueva membresía
        asignarMembresiaAUsuario(usuarioDocumento, membresiaId);
    }

    public void suspenderMembresia(String usuarioDocumento) {
        Optional<UsuarioMembresia> membresiaActiva = 
            usuarioMembresiaRepository.findActivaByUsuarioDocumento(usuarioDocumento);
        
        if (membresiaActiva.isPresent()) {
            UsuarioMembresia membresia = membresiaActiva.get();
            membresia.setEstado("SUSPENDIDA");
            usuarioMembresiaRepository.save(membresia);
        }
    }

    public void reactivarMembresia(String usuarioDocumento) {
        Optional<UsuarioMembresia> membresiaActiva = 
            usuarioMembresiaRepository.findActivaByUsuarioDocumento(usuarioDocumento);
        
        if (membresiaActiva.isPresent()) {
            UsuarioMembresia membresia = membresiaActiva.get();
            if (!membresia.estaVencida()) {
                membresia.setEstado("ACTIVA");
                usuarioMembresiaRepository.save(membresia);
            }
        }
    }

    public void cancelarMembresia(String usuarioDocumento) {
        Optional<UsuarioMembresia> membresiaActiva = 
            usuarioMembresiaRepository.findActivaByUsuarioDocumento(usuarioDocumento);
        
        if (membresiaActiva.isPresent()) {
            UsuarioMembresia membresia = membresiaActiva.get();
            membresia.setActivo(false);
            membresia.setEstado("CANCELADA");
            usuarioMembresiaRepository.save(membresia);
        }
    }

    public void actualizarEstadosMembresias() {
        List<UsuarioMembresia> todasMembresias = usuarioMembresiaRepository.findAll();
        for (UsuarioMembresia membresia : todasMembresias) {
            if (membresia.isActivo() && membresia.estaVencida() && "ACTIVA".equals(membresia.getEstado())) {
                membresia.setEstado("VENCIDA");
                membresia.setActivo(false);
                usuarioMembresiaRepository.save(membresia);
            }
        }
    }
}
