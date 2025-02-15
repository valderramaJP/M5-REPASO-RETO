package co.bancolombia.sistemaprestamos.repository;

import co.bancolombia.sistemaprestamos.model.Transaccion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TransaccionRepository extends JpaRepository<Transaccion, Long> {

    @Query("SELECT t FROM Transaccion t WHERE t.prestamoAsociado.id = :prestamoId")
    List<Transaccion> transaccionesPrestamo(@Param("prestamoId") Long prestamoId);
}
