package org.Api_Inventario.Modelos;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Categoria")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IdCategoria")
    private Integer idCategoria;

    @Column(name = "Nombre", nullable = false, unique = true, length = 50)
    private String nombre;

    @Column(name = "Descripcion", length = 255)
    private String descripcion;
}