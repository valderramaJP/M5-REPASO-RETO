package co.bancolombia.sistemaprestamos.DTO;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class GestionPrestamoDTO {

    @NotNull(message = "El id del prestamo es obligatorio!")
    @Positive(message = "El id del prestamo debe ser mayor a cero!")
    @Digits(integer = 20, fraction = 0, message = "El id del prestamo debe tener un máximo de 20 dígitos!")
    private Long id;

    public GestionPrestamoDTO(Long id) {
        this.id = id;
    }

    public GestionPrestamoDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
