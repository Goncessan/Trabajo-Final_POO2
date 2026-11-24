package com.esenciayaroma.abastecimiento;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class CampaniaService {

    private final CampaniaRepository campaniaRepository;

    public CampaniaService(CampaniaRepository campaniaRepository) {
        this.campaniaRepository = campaniaRepository;
    }

    // Listar todas las campañas
    public List<Campania> listarCampanias() {
        return campaniaRepository.findAll();
    }

    // Registrar una campaña
    @Transactional
    public Campania registrarCampania(
            CampaniaFormulario formulario) {

        if (formulario.getFechaFin()
                .isBefore(formulario.getFechaInicio())) {
            throw new IllegalArgumentException(
                    "La fecha de fin no puede ser anterior al inicio."
            );
        }

        Campania campania = new Campania(
                formulario.getNombre(),
                formulario.getMarca(),
                formulario.getFechaInicio(),
                formulario.getFechaFin(),
                EstadoCampania.PROGRAMADA
        );

        return campaniaRepository.save(campania);
    }

    // Buscar campaña por ID
    public Campania buscarCampania(Long id) {
        return campaniaRepository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Campaña no encontrada."
                        )
                );
    }

    // Cambiar estado de campaña
    @Transactional
    public void cambiarEstado(
            Long id, EstadoCampania estado) {

        Campania campania = buscarCampania(id);
        campania.setEstado(estado);

        campaniaRepository.save(campania);
    }
}

