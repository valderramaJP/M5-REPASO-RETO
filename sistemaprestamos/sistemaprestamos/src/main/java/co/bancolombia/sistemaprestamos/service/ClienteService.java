package co.bancolombia.sistemaprestamos.service;

import co.bancolombia.sistemaprestamos.DTO.CreaClienteDTO;
import co.bancolombia.sistemaprestamos.DTO.GestionClienteDTO;
import co.bancolombia.sistemaprestamos.model.Cliente;
import co.bancolombia.sistemaprestamos.model.DatosPrestamo;
import co.bancolombia.sistemaprestamos.model.PrestamosCliente;
import co.bancolombia.sistemaprestamos.repository.ClienteRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ClienteService {

    private final ClienteRepository clienteRepository;

    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    //crear clientes.
    @Transactional
    public Cliente grabarCliente(CreaClienteDTO creaClienteDTO){

        //Se valida que el cliente no exista.
        if(clienteRepository.existsById(creaClienteDTO.getId())){
            throw new RuntimeException("¡El cliente ya existe.!");
        }

        //Se crea cliente.
        Cliente cliente = new Cliente();
        cliente.setId(creaClienteDTO.getId());
        cliente.setNombre(creaClienteDTO.getNombre());
        cliente.setEmail(creaClienteDTO.getEmail());
        cliente.setTelefono(creaClienteDTO.getTelefono());
        cliente.setDireccion(creaClienteDTO.getDireccion());

        //se graba cliente en la base de datos.
        Cliente clienteNuevo = clienteRepository.save(cliente);

        if(clienteNuevo == null){
            throw new RuntimeException("¡No se pudo crear el cliente.!");
        }

        return clienteNuevo;
    }

    //Consultar ultimo 3 prestamos de un cliente.
    @Transactional
    public List<PrestamosCliente> ultimos3Prestamos(GestionClienteDTO gestionClienteDTO){
        Cliente cliente = clienteRepository.findById(gestionClienteDTO.getId()).orElseThrow(() ->
                new NoSuchElementException("Cliente no registrado en el sistema."));

        List<PrestamosCliente> prestamos = clienteRepository.ultimos3Prestamos(gestionClienteDTO.getId());

        if (prestamos.isEmpty()){
            throw new RuntimeException("Cliente no tiene Prestamos asociados.");
        }
        return prestamos;
    }

    //Consultar prestamos de un cliente.
    @Transactional
    public List<DatosPrestamo> prestamosCliente(GestionClienteDTO gestionClienteDTO){
        Cliente cliente = clienteRepository.findById(gestionClienteDTO.getId()).orElseThrow(() ->
                new NoSuchElementException("Cliente no registrado en el sistema."));

        List<DatosPrestamo> prestamos = clienteRepository.prestamosCliente(gestionClienteDTO.getId());

        if (prestamos.isEmpty()){
            throw new RuntimeException("Cliente no tiene Prestamos asociados.");
        }
        return prestamos;
    }

}
