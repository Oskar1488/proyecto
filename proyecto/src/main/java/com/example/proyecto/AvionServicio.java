package com.example.proyecto;

import java.util.List;

public interface AvionServicio {
    public List<Avion> listarAviones();

    public Avion guardarAvion (Avion avion);

    public Avion obtenerAvion (Integer id);

    public Avion actualizarAvion (Avion avion);

    public void borrarAvion (Integer id);
}
