package co.bancolombia.sistemaprestamos.service;

import co.bancolombia.sistemaprestamos.DTO.CreaPrestamoDTO;
import co.bancolombia.sistemaprestamos.DTO.GestionClienteDTO;
import co.bancolombia.sistemaprestamos.DTO.GestionPrestamoDTO;
import co.bancolombia.sistemaprestamos.model.*;
import co.bancolombia.sistemaprestamos.repository.ClienteRepository;
import co.bancolombia.sistemaprestamos.repository.PrestamoRepository;
import co.bancolombia.sistemaprestamos.repository.TransaccionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class PrestamoService {

    private final PrestamoRepository prestamoRepository;
    private final ClienteRepository clienteRepository;
    private final TransaccionRepository transaccionRepository;

    public PrestamoService(PrestamoRepository prestamoRepository, ClienteRepository clienteRepository, TransaccionRepository transaccionRepository) {
        this.prestamoRepository = prestamoRepository;
        this.clienteRepository = clienteRepository;
        this.transaccionRepository = transaccionRepository;
    }

    //Crear un prestamo
    @Transactional
    public Prestamo grabarPrestamo(CreaPrestamoDTO crearDTO){
        //Verificar si el cliente ya está creado en base de datos.
        Cliente cliente = clienteRepository.findById(crearDTO.getIdCliente()).orElseThrow(() ->
                new NoSuchElementException("Cliente no existe en el sistema."));

        //Se crea prestamo.
        Prestamo prestamo = new Prestamo();

        prestamo.setMonto(crearDTO.getMonto());
        prestamo.setInteres(BigDecimal.valueOf(2.5));
        prestamo.setPlazo(crearDTO.getPlazo());
        prestamo.setEstado(1);
        prestamo.setCliente(cliente);

        //Grabar prestamo en la base de datos.
        Prestamo prestamoNuevo = prestamoRepository.save(prestamo);

        if(prestamoNuevo == null){
            throw new RuntimeException("¡No se pudo crear el prestamo.!");
        }

        //Se crea transacción.
        Transaccion transaccion = new Transaccion();

        transaccion.setPrestamoAsociado(prestamoNuevo);
        transaccion.setTipoNovedad("CREACION NUEVO PRESTAMO");
        transaccion.setValor(crearDTO.getMonto());
        transaccion.setFecha(LocalDate.now());
        transaccion.setHora(LocalTime.now());

        //Grabar transacción en la base de datos.
        transaccionRepository.save(transaccion);
        return prestamoNuevo;

    }

    //Aprobar prestamos
    @Transactional
    public Prestamo aprobarPrestamo(GestionPrestamoDTO gestionDTO) {
        //Verificar si el prestamo ya está creado en base de datos.
        Prestamo prestamo = prestamoRepository.findById(gestionDTO.getId()).orElseThrow(() ->
                new NoSuchElementException("Prestamo no existe en el sistema."));

        if (!prestamo.getEstado().equals(1))
        {
            throw new RuntimeException("¡El prestamo no está en estado pendiente.!");
        }

        //Cambiar estado del prestamo a 2 (aprobado).
        prestamo.setEstado(2);

        //Grabar prestamo en la base de datos.
        Prestamo prestamoAprobado = prestamoRepository.save(prestamo);

        if (prestamoAprobado == null) {
            throw new RuntimeException("¡No se pudo aprobar el prestamo.!");
        }

        //Se crea transacción.
        Transaccion transaccion = new Transaccion();
        transaccion.setPrestamoAsociado(prestamoAprobado);
        transaccion.setTipoNovedad("PRESTAMO APROBADO");
        transaccion.setValor(prestamoAprobado.getMonto());
        transaccion.setFecha(LocalDate.now());
        transaccion.setHora(LocalTime.now());

        //Grabar transacción en la base de datos.
        transaccionRepository.save(transaccion);
        return prestamoAprobado;

    }

    //Rechazar prestamos
    @Transactional
    public Prestamo rechazarPrestamo(GestionPrestamoDTO gestionDTO) {
        //Verificar si el prestamo ya está creado en base de datos.
        Prestamo prestamo = prestamoRepository.findById(gestionDTO.getId()).orElseThrow(() ->
                new NoSuchElementException("Prestamo no existe en el sistema."));

        if (!prestamo.getEstado().equals(1))
        {
            throw new RuntimeException("¡El prestamo no está en estado pendiente.!");
        }

        //Cambiar estado del prestamo a 3 (Rechazado).
        prestamo.setEstado(3);

        //Grabar prestamo en la base de datos.
        Prestamo prestamoRechazado = prestamoRepository.save(prestamo);

        if (prestamoRechazado == null) {
            throw new RuntimeException("¡No se pudo rechazar el prestamo.!");
        }

        //Se crea transacción.
        Transaccion transaccion = new Transaccion();
        transaccion.setPrestamoAsociado(prestamoRechazado);
        transaccion.setTipoNovedad("PRESTAMO RECHAZADO");
        transaccion.setValor(prestamoRechazado.getMonto());
        transaccion.setFecha(LocalDate.now());
        transaccion.setHora(LocalTime.now());

        //Grabar transacción en la base de datos.
        transaccionRepository.save(transaccion);
        return prestamoRechazado;

    }

    //Simular plan de pagos.
    @Transactional
    public List<PlanCuotas> SimularPlan(GestionPrestamoDTO gestionDTO) {
        //Verificar si el prestamo ya está creado en base de datos.
        Prestamo prestamo = prestamoRepository.findById(gestionDTO.getId()).orElseThrow(() ->
                new NoSuchElementException("Prestamo no existe en el sistema, simulación fallida."));

        //Se crea plan de cuotas.
        List<PlanCuotas> planCuotas = new ArrayList<>();
        BigDecimal capital = prestamo.getMonto().divide(BigDecimal.valueOf(prestamo.getPlazo()), 2, BigDecimal.ROUND_HALF_UP);
        BigDecimal interes;
        BigDecimal capitalAjustado;
        BigDecimal cuota;
        BigDecimal saldo = prestamo.getMonto();
        for (int i = 1; i <= prestamo.getPlazo(); i++) {
            interes = saldo.multiply(prestamo.getInteres().divide(BigDecimal.valueOf(100)));
            cuota = capital.add(interes);
            saldo = saldo.subtract(capital);

            if(i==prestamo.getPlazo()-1){
                capitalAjustado = saldo.subtract(capital);
                saldo = saldo.subtract(capitalAjustado);
            }

            PlanCuotas planCuota = new PlanCuotas(i, capital, interes, cuota, saldo);
            planCuotas.add(planCuota);
        }
        return planCuotas;

    }

    //Consultar estado de un prestamos.
    @Transactional
    public String consultarEstadoPrestamo(GestionPrestamoDTO gestionDTO) {
        //Verificar si el prestamo ya está creado en base de datos.
        Prestamo prestamo = prestamoRepository.findById(gestionDTO.getId()).orElseThrow(() ->
                new NoSuchElementException("Prestamo no existe en el sistema."));

        return prestamoRepository.findEstadoById(gestionDTO.getId());
    }

    //Consultar un prestamo por ID.
    @Transactional
    public DatosPrestamo consultarPrestamo(GestionPrestamoDTO gestionDTO){
        Prestamo prestamo= prestamoRepository.findById(gestionDTO.getId()).orElseThrow(() ->
                new NoSuchElementException("Prestamo no existe en el sistema."));

       return prestamoRepository.findPrestamoById(gestionDTO.getId());
    }



}
