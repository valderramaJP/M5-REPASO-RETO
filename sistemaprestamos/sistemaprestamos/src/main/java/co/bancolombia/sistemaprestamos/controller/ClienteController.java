package co.bancolombia.sistemaprestamos.controller;

import co.bancolombia.sistemaprestamos.DTO.CreaClienteDTO;
import co.bancolombia.sistemaprestamos.DTO.GestionClienteDTO;
import co.bancolombia.sistemaprestamos.model.Cliente;
import co.bancolombia.sistemaprestamos.model.DatosPrestamo;
import co.bancolombia.sistemaprestamos.model.PrestamosCliente;
import co.bancolombia.sistemaprestamos.service.ClienteService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cliente")
public class ClienteController {

    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    //crear clientes
    @PostMapping("/crear")
    public ResponseEntity<String> crearCliente(@Valid @RequestBody CreaClienteDTO creaClienteDTO) {
        Cliente cliente = clienteService.grabarCliente(creaClienteDTO);
        return new ResponseEntity<>("Cliente creado exitosamente. \nNúmero de cliente: " + cliente.getId(), HttpStatus.CREATED);
    }

    //Ultimos 3 prestamos.
    @GetMapping("/consultas/ultimos3")
    public ResponseEntity<List<PrestamosCliente>> ultimos3(@Valid @RequestBody GestionClienteDTO gestionCliente) {
        List<PrestamosCliente> prestamos = clienteService.ultimos3Prestamos(gestionCliente);
        return new ResponseEntity<>(prestamos, HttpStatus.OK);
    }

    //Prestamos de un cliente.
    @GetMapping("/consultas/prestamoscliente")
    public ResponseEntity<List<DatosPrestamo>> prestamosClientes(@Valid @RequestBody GestionClienteDTO gestionCliente) {
        List<DatosPrestamo> prestamos = clienteService.prestamosCliente(gestionCliente);
        return new ResponseEntity<>(prestamos, HttpStatus.OK);
    }



}
