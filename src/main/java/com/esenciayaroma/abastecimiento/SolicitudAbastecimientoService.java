package com.esenciayaroma.abastecimiento;

import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;

@Service
public class SolicitudAbastecimientoService {

    private final SolicitudAbastecimientoRepository repository;

    public SolicitudAbastecimientoService(
            SolicitudAbastecimientoRepository repository) {
        this.repository = repository;
    }

    public List<SolicitudAbastecimiento> listar() {
        return repository.findAll();
    }

    public SolicitudAbastecimiento registrar(
            SolicitudAbastecimientoFormulario formulario) {

        SolicitudAbastecimiento solicitud =
                new SolicitudAbastecimiento();

        solicitud.setMotivo(formulario.getMotivo());
        solicitud.setSolicitante(formulario.getSolicitante());
        solicitud.setCantidad(formulario.getCantidad());
        solicitud.setFechaSolicitud(LocalDate.now());

        solicitud.setEstado(
                EstadoSolicitudAbastecimiento.PENDIENTE
        );

        return repository.save(solicitud);
    }

    public void cambiarEstado(
            Long id,
            EstadoSolicitudAbastecimiento estado) {

        SolicitudAbastecimiento solicitud =
                repository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException("Solicitud no encontrada"));

        solicitud.setEstado(estado);

        repository.save(solicitud);
    }
}
