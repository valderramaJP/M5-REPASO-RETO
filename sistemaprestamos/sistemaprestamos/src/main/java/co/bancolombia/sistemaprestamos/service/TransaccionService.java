package co.bancolombia.sistemaprestamos.service;

import co.bancolombia.sistemaprestamos.DTO.GestionPrestamoDTO;
import co.bancolombia.sistemaprestamos.model.Prestamo;
import co.bancolombia.sistemaprestamos.model.Transaccion;
import co.bancolombia.sistemaprestamos.repository.PrestamoRepository;
import co.bancolombia.sistemaprestamos.repository.TransaccionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class TransaccionService {

    private final TransaccionRepository transaccionRepository;
    private final PrestamoRepository prestamoRepository;

    public TransaccionService(TransaccionRepository transaccionRepository, PrestamoRepository prestamoRepository) {
        this.transaccionRepository = transaccionRepository;
        this.prestamoRepository = prestamoRepository;
    }

    //Consultar transacciones de un prestamo.
    @Transactional
    public List<Transaccion> transaccionesPrestamo(GestionPrestamoDTO gestionPrestamoDTO) {
        Prestamo prestamo = prestamoRepository.findById(gestionPrestamoDTO.getId()).orElseThrow(() ->
                new NoSuchElementException("Prestamo no existe en el sistema."));

        List<Transaccion> transacciones = transaccionRepository.transaccionesPrestamo(gestionPrestamoDTO.getId());

        if (transacciones.isEmpty()) {
            throw new RuntimeException("Prestamo no tiene transacciones asociadas.");
        }

        return transacciones;
    }

}
