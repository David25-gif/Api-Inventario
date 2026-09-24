package org.Api_Inventario.Seguridad.Servicios;

import jakarta.persistence.EntityNotFoundException;
import org.Api_Inventario.Seguridad.Modelos.Rol;
import org.Api_Inventario.Seguridad.Repositorio.RolRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RolService {

    @Autowired
    private RolRepositorio rolRepositorio;

    public List<Rol> obtenerRoles() {
        return rolRepositorio.findAll();
    }

    public Rol obtenerPorId(Integer rolId) {
        return rolRepositorio.findById(rolId)
                .orElseThrow(() ->
                        new EntityNotFoundException(
                                "El rol con id " + rolId + " no existe"
                        )
                );
    }
}