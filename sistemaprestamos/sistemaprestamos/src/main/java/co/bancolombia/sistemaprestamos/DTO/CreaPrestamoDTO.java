package co.bancolombia.sistemaprestamos.DTO;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import org.hibernate.validator.constraints.Range;

import java.math.BigDecimal;

public class CreaPrestamoDTO {

    @NotNull(message = "El monto es obligatorio")
    @Positive(message = "El monto debe ser mayor a cero")
    @Digits(integer = 13, fraction = 2, message = "El monto debe tener un máximo de 15 dígitos en total, con 2 decimales")
    private BigDecimal monto;

    @NotNull(message = "El plazo es obligatorio")
    @Positive(message = "El plazo debe ser mayor a cero")
    @Range(min = 1, max = 120, message = "El plazo debe estar entre 1 y 120")
    private Integer plazo;

    @NotNull(message = "El id del cliente es obligatorio")
    @Positive(message = "El id del cliente debe ser mayor a cero")
    @Digits(integer = 20, fraction = 0, message = "El id del cliente debe tener un máximo de 20 dígitos")
    private Long idCliente;

    public CreaPrestamoDTO(BigDecimal monto, Integer plazo, Long idCliente) {
        this.monto = monto;
        this.plazo = plazo;
        this.idCliente = idCliente;
    }

    public BigDecimal getMonto() {
        return monto;
    }

    public void setMonto(BigDecimal monto) {
        this.monto = monto;
    }

    public Integer getPlazo() {
        return plazo;
    }

    public void setPlazo(Integer plazo) {
        this.plazo = plazo;
    }

    public Long getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Long idCliente) {
        this.idCliente = idCliente;
    }

}
