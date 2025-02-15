package co.bancolombia.sistemaprestamos.DTO;

import jakarta.validation.constraints.*;

public class CreaClienteDTO {

    @NotNull(message = "El id del cliente es obligatorio")
    @Positive(message = "El id del cliente debe ser mayor a cero")
    @Digits(integer = 20, fraction = 0, message = "El id del cliente debe tener un máximo de 20 dígitos")
    private Long id;

    @NotNull(message = "El nombre es obligatorio")
    @NotEmpty(message = "El nombre no puede estar vacío")
    private String nombre;

    @NotNull(message = "El email es obligatorio")
    @NotEmpty(message = "El email no puede estar vacío")
    @Email(message = "El email debe ser válido")
    private String email;

    @NotNull(message = "El teléfono es obligatorio")
    @NotEmpty(message = "El teléfono no puede estar vacío")
    private String telefono;

    @NotNull(message = "La dirección es obligatoria")
    @NotEmpty(message = "La dirección no puede estar vacía")
    private String direccion;

    public CreaClienteDTO(Long id, String nombre, String email, String telefono, String direccion) {
        this.id = id;
        this.nombre = nombre;
        this.email = email;
        this.telefono = telefono;
        this.direccion = direccion;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDireccion(){
        return direccion;
    }

    public void setDireccion(String direccion){
        this.direccion = direccion;
    }

}
