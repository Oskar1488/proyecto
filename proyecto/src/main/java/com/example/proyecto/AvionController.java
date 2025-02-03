package com.example.proyecto;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Controller
public class AvionController {
    private final List<Avion> aviones = new ArrayList<>();

    @Autowired
    private AvionServicio servicio;

    @GetMapping("/")
    public String home(HttpSession session, Model model) {
        if (session.getAttribute("bienvenida") == null) {
            model.addAttribute("bienvenida", "¡Bienvenido!");
            session.setAttribute("bienvenida", true);
        }
        model.addAttribute("aviones", servicio.listarAviones());
        return "index";
    }

    @GetMapping("/nuevo")
    public String nuevoAnuncio(HttpSession session, Model model) {
        String nombre = (String) session.getAttribute("nombre");
        model.addAttribute("nombre", nombre != null ? nombre : "");
        return "nuevo";
    }


    @GetMapping("/anuncio/{index}")
    public String verAnuncio(@PathVariable int index, Model model) {
        if (index >= 0 && index < aviones.size()) {
            model.addAttribute("avion", aviones.get(index));
            return "anuncio";
        }
        return "redirect:/";
    }

    public String subirImagen(MultipartFile imagen) {
        // Ruta base para guardar las imágenes
        String uploadDir = System.getProperty("user.dir") + "/src/main/resources/static/img/";

        try {
            // Crear el directorio si no existe
            Path uploadPath = Paths.get(uploadDir);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            // Guardar el archivo
            String fileName = imagen.getOriginalFilename();
            String rutaArchivo = uploadDir + fileName;
            imagen.transferTo(new File(rutaArchivo));

            return fileName;  // Retornamos el nombre del archivo
        } catch (IOException e) {
            e.printStackTrace();
            return null;  // En caso de error, retornamos null
        }
    }


    @PostMapping("/nuevo")
    public String agregarAvion(@RequestParam("nombre") String nombre,
                               @RequestParam("descripcion") String descripcion,
                               @RequestParam("imagen") MultipartFile imagen,
                               HttpSession session, Model model) {
        // Subir la imagen y obtener el nombre del archivo
        String fileName = subirImagen(imagen);

        if (fileName != null) {
            // Si la imagen se subió correctamente, agregamos el avión

            aviones.add(new Avion(nombre, fileName, descripcion));
            session.setAttribute("nombre", nombre);
            Avion avion = new Avion(nombre, fileName, descripcion);
            servicio.guardarAvion(avion);

            model.addAttribute("mensaje", "Avión añadido correctamente con la imagen!");
        } else {
            // Si hubo un error en la subida de la imagen, mostramos un mensaje
            model.addAttribute("mensaje", "Error al subir la imagen.");
        }

        return "resultado";
    }



}



