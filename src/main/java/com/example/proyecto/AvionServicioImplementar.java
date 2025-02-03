package com.example.proyecto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AvionServicioImplementar implements AvionServicio {

    @Autowired
    private AvionRepository repositorio;

    @Override
    public List<Avion> listarAviones(){

        return repositorio.findAll();
    }

    @Override
    public Avion guardarAvion (Avion avion) {
        return repositorio.save(avion);
    }

    @Override
    public Avion obtenerAvion (Integer id) {
        return repositorio.findById(id).get();
    }

    @Override
    public Avion actualizarAvion(Avion trabajador) {
        return repositorio.save(trabajador);
    }

    @Override
    public void borrarAvion(Integer id) {
        repositorio.deleteById(id);
    }

}
