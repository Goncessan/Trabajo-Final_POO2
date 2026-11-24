package com.esenciayaroma.cobranzas;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Locale;

@Service
public class AuditoriaCobranzaService {

    private final AuditoriaCobranzaRepository repository;

    public AuditoriaCobranzaService(
            AuditoriaCobranzaRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public void registrarAbono(Abono abono) {

        Authentication autenticacion =
                SecurityContextHolder.getContext()
                        .getAuthentication();

        String usuario = "Sistema";

        if (autenticacion != null
                && autenticacion.isAuthenticated()
                && !"anonymousUser".equals(
                autenticacion.getName())) {

            usuario = autenticacion.getName();
        }

        AuditoriaCobranza auditoria =
                new AuditoriaCobranza(
                        usuario,
                        "REGISTRO DE ABONO",
                        abono.getVenta().getId(),
                        abono.getId(),
                        abono.getMonto(),
                        abono.getMedioPago(),
                        abono.getReferenciaOperacion()
                );

        repository.save(auditoria);
    }

    @Transactional(readOnly = true)
    public List<AuditoriaCobranza> listar() {

        return repository
                .findAllByOrderByFechaRegistroDescIdDesc();
    }

    @Transactional(readOnly = true)
    public List<AuditoriaCobranza> listarPorVenta(
            Long ventaId) {

        return repository
                .findAllByVentaIdOrderByFechaRegistroDescIdDesc(
                        ventaId
                );
    }

    // FILTRAR AUDITORÍA
    @Transactional(readOnly = true)
    public List<AuditoriaCobranza> filtrar(
            LocalDate desde,
            LocalDate hasta,
            String usuario,
            String medioPago,
            Long ventaId) {

        String usuarioFiltro = usuario == null
                ? ""
                : usuario.trim().toLowerCase(Locale.ROOT);

        String medioFiltro = medioPago == null
                ? ""
                : medioPago.trim();

        return listar().stream()

                .filter(registro ->
                        desde == null
                                || !registro.getFechaRegistro()
                                .toLocalDate().isBefore(desde)
                )

                .filter(registro ->
                        hasta == null
                                || !registro.getFechaRegistro()
                                .toLocalDate().isAfter(hasta)
                )

                .filter(registro ->
                        usuarioFiltro.isEmpty()
                                || registro.getUsuario()
                                .toLowerCase(Locale.ROOT)
                                .contains(usuarioFiltro)
                )

                .filter(registro ->
                        medioFiltro.isEmpty()
                                || String.valueOf(registro.getMedioPago())
                                .equalsIgnoreCase(medioFiltro)
                )

                .filter(registro ->
                        ventaId == null
                                || ventaId.equals(registro.getVentaId())
                )

                .toList();
    }
}

