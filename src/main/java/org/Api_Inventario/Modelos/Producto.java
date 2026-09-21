package org.Api_Inventario.Modelos;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "ProductList")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ProductId")
    private Integer idProducto;

    @Column(name = "Name", nullable = false, length = 150)
    private String nombre;

    @Column(name = "Description", length = 255)
    private String descripcion;

    @Column(name = "Barcode", nullable = false, length = 100)
    private String codigoBarras;

    @Column(name = "ImageUrl", length = 500)
    private String imagenUrl;

    @Column(name = "CreatedAt", nullable = false)
    private LocalDateTime fechaCreacion;

    // Referencias simples (igual que en Inventario) mientras Status,
    // Category y Users no se relacionen como entidades.
    @Column(name = "StatusId", nullable = false)
    private Integer idEstado;

    @Column(name = "CategoryId", nullable = false)
    private Integer idCategoria;

    @Column(name = "CreatedByUser", nullable = false)
    private Integer creadoPorUsuario;

    // La fecha se genera desde la API y no se recibe desde el cliente.
    @PrePersist
    public void asignarFechaCreacion() {
        if (fechaCreacion == null) {
            fechaCreacion = LocalDateTime.now();
        }
    }
}