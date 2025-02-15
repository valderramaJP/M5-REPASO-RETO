package co.bancolombia.sistemaprestamos.repository;

import co.bancolombia.sistemaprestamos.model.DatosPrestamo;
import co.bancolombia.sistemaprestamos.model.Prestamo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PrestamoRepository extends JpaRepository<Prestamo, Long> {

    @Query(value = "SELECT e.descripcion FROM prestamo p JOIN estado_prestamo e ON e.id_estado = p.estado WHERE p.id_prestamo = :idPrestamo", nativeQuery = true)
    String findEstadoById(@Param("idPrestamo") Long idPrestamo);

    @Query(value = "SELECT  p.id_prestamo , p.monto , p.interes , p.plazo , e.descripcion , " +
            "c.id_cliente, c.nombre , c.email , c.telefono , c.direccion  from prestamo p " +
            " JOIN estado_prestamo e ON e.id_estado = p.estado" +
            " JOIN cliente c on c.id_cliente = p.cliente" +
            " Where p.id_prestamo = :idPrestamo", nativeQuery = true)
    DatosPrestamo findPrestamoById(@Param("idPrestamo") Long idPrestamo);


}
