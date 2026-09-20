package org.Api_Inventario.dtos.MovimientoInventario;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record MovimientoInventarioGuardar (

    @NotNull(message = "El tipo de movimiento es obligatorio.")
    @Positive(message = "El tipo de movimiento debe ser mayor a 0.")
    Integer idTipoMovimiento,

    @NotNull(message = "La cantidad es obligatoria.")
    @Positive(message = "La cantidad debe ser mayor a 0.")
    Integer cantidad,

    @NotNull(message = "El costo unitario es obligatorio.")
    @DecimalMin(value = "0.00", inclusive = true,
            message = "El costo no puede ser negativo")
    BigDecimal costoUnitario,

    @Size(max = 255,
    message = "Las notas no pueden exeder los 255 caracteres.")
    String notas,

    @NotNull(message = "El usuario es obligatorio.")
    @Positive(message = "El usuario debe ser mayot a 0.")
    Integer creadoPorUsuario,

    @NotNull(message = "El inventario es obligatorio.")
    @Positive(message = "El inventario debe ser mayor a 0")
    Integer idInventario,

    @Positive(message = "El detalle de documento debe ser mayor a 0.")
    Integer idDetalleDocumento
) {
    @JsonCreator
    public MovimientoInventarioGuardar(

            @JsonProperty("movementTypeId")
            Integer idTipoMovimiento,

            @JsonProperty("quantity")
            Integer cantidad,

            @JsonProperty("unitCost")
            BigDecimal costoUnitario,

            @JsonProperty("notes")
            String notas,

            @JsonProperty("createdByUser")
            Integer creadoPorUsuario,

            @JsonProperty("InventoryId")
            Integer idInventario,

            @JsonProperty("documentDetailId")
            Integer idDetalleDocumento ){

        this.idTipoMovimiento = idTipoMovimiento;
        this.cantidad = cantidad;
        this.costoUnitario = costoUnitario;
        this.notas = notas;
        this.creadoPorUsuario = creadoPorUsuario;
        this.idInventario = idInventario;
        this.idDetalleDocumento =  idDetalleDocumento;
    }
}