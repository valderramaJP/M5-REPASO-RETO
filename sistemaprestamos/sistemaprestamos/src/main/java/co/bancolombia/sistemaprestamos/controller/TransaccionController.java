package co.bancolombia.sistemaprestamos.controller;

import co.bancolombia.sistemaprestamos.DTO.GestionPrestamoDTO;
import co.bancolombia.sistemaprestamos.model.Transaccion;
import co.bancolombia.sistemaprestamos.service.TransaccionService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/transaccion")
public class TransaccionController {

    private final TransaccionService transaccionService;

    public TransaccionController(TransaccionService transaccionService) {
        this.transaccionService = transaccionService;
    }

    //Consulta de transacciones.
    @GetMapping("/consultar")
    public ResponseEntity<List<Transaccion>> consultarTransacciones(@Valid @RequestBody GestionPrestamoDTO gestionPrestamoDTO) {
        List<Transaccion> transacciones = transaccionService.transaccionesPrestamo(gestionPrestamoDTO);
        return new ResponseEntity<>(transacciones, HttpStatus.OK);
    }
}
