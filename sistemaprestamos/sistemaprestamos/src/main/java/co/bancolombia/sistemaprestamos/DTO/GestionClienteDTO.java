package co.bancolombia.sistemaprestamos.DTO;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class GestionClienteDTO {

    @NotNull(message = "El id del cliente es obligatorio!")
    @Positive(message = "El id del cliente debe ser mayor a cero!")
    @Digits(integer = 20, fraction = 0, message = "El id del cliente debe tener un máximo de 20 dígitos!")
    private Long id;

    public GestionClienteDTO(Long id) {
        this.id = id;
    }

    public GestionClienteDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
