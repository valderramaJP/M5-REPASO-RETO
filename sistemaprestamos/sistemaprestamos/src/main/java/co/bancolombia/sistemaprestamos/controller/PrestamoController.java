package co.bancolombia.sistemaprestamos.controller;

import co.bancolombia.sistemaprestamos.DTO.CreaPrestamoDTO;
import co.bancolombia.sistemaprestamos.DTO.GestionClienteDTO;
import co.bancolombia.sistemaprestamos.DTO.GestionPrestamoDTO;
import co.bancolombia.sistemaprestamos.model.DatosPrestamo;
import co.bancolombia.sistemaprestamos.model.PlanCuotas;
import co.bancolombia.sistemaprestamos.model.Prestamo;
import co.bancolombia.sistemaprestamos.model.PrestamosCliente;
import co.bancolombia.sistemaprestamos.service.PrestamoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/prestamo")
public class PrestamoController {

    private final PrestamoService prestamoService;

    public PrestamoController(PrestamoService prestamoService) {
        this.prestamoService = prestamoService;
    }

    //Crear un prestamo.
    @PostMapping("/solicitar")
    public ResponseEntity<String> crearPrestamo(@Valid @RequestBody CreaPrestamoDTO crearDTO) {
        Prestamo prestamo = prestamoService.grabarPrestamo(crearDTO);
        return new ResponseEntity<>("Prestamo creado exitosamente. \nNúmero de prestamo: " + prestamo.getIdPrestamo(), HttpStatus.CREATED);
    }

    //Aprobar prestamo.
    @PutMapping("/aprobar")
    public ResponseEntity<String> aprobarPrestamo(@Valid @RequestBody GestionPrestamoDTO gestionPrestamoDTO) {
        Prestamo prestamo = prestamoService.aprobarPrestamo(gestionPrestamoDTO);
        return new ResponseEntity<>("Prestamo Aprobado. \nNúmero de prestamo: " + prestamo.getIdPrestamo(), HttpStatus.CREATED);
    }

    //Rechazar prestamo.
    @PutMapping("/rechazar")
    public ResponseEntity<String> rechazarPrestamo(@Valid @RequestBody GestionPrestamoDTO gestionPrestamoDTO) {
        Prestamo prestamo = prestamoService.rechazarPrestamo(gestionPrestamoDTO);
        return new ResponseEntity<>("Prestamo Rechazado. \nNúmero de prestamo: " + prestamo.getIdPrestamo(), HttpStatus.CREATED);
    }

    //Simular prestamo.
    @GetMapping("/simular")
    public ResponseEntity<List<PlanCuotas>> simularPrestamo(@Valid @RequestBody GestionPrestamoDTO gestionPrestamo) {
        List<PlanCuotas> plan = prestamoService.SimularPlan(gestionPrestamo);
        return new ResponseEntity<>(plan, HttpStatus.OK);
    }

    //Consultar estado prestamo.
    @GetMapping("/consultas/estado")
    public ResponseEntity<String> consultaEstado(@Valid @RequestBody GestionPrestamoDTO gestionPrestamo) {
        String estado = prestamoService.consultarEstadoPrestamo(gestionPrestamo);
        return new ResponseEntity<>("Id prestamo: " +gestionPrestamo.getId() + "\n Estado prestamo: " +estado, HttpStatus.OK);
    }

    //Consultar prestamo.
    @GetMapping("/consultas/prestamo")
    public ResponseEntity<DatosPrestamo> consultaPrestamo(@Valid @RequestBody GestionPrestamoDTO gestionPrestamo) {
        DatosPrestamo datosprestamo = prestamoService.consultarPrestamo(gestionPrestamo);
        return new ResponseEntity<>(datosprestamo, HttpStatus.OK);
    }







}
