package com.example.demo_spring.controller; // src/main/java/com/example/demo_spring/controller/EmpleadoController.java

import com.example.demo_spring.model.Empleado;
import com.example.demo_spring.repository.EmpleadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.time.LocalDate; // <-- ¡NUEVO IMPORT NECESARIO!
import java.util.List;
import java.util.Map;
import java.util.Optional;
// importa dependencias necesarias

@RestController// Anotación de controlador REST de Spring
@RequestMapping("/api/empleados")// Mapea las solicitudes a /api/empleados
public class EmpleadoController { // Controlador para manejar operaciones CRUD de Empleado

    @Autowired// Inyección de dependencia del repositorio de Empleado
    private EmpleadoRepository repository; // Repositorio de Empleado

    // ----------------------------------------------
    // GET → Listar todos
    // ----------------------------------------------
    @GetMapping
    public List<Empleado> listar() { // Lista todos los empleados
        return repository.findAll();// Retorna la lista de todos los empleados
    }

    // ----------------------------------------------
    // GET → Buscar por ID de empleado
    // ----------------------------------------------
    @GetMapping("/{id}")// Mapea las solicitudes GET a /api/empleados/{id}
    public ResponseEntity<Empleado> buscarPorId(@PathVariable Long id) {// Busca un empleado por su ID
        return repository.findById(id) // Busca el empleado por ID
                .map(ResponseEntity::ok) // Retorna 200 OK si se encuentra
                .orElse(ResponseEntity.notFound().build());// Retorna 404 Not Found si no se encuentra
    }

    // ----------------------------------------------
    // POST → Crear empleado
    // Se recomienda usar 201 CREATED para nuevas entidades.
    // ----------------------------------------------
    @PostMapping
    public ResponseEntity<?> crear(@RequestBody Empleado empleado) { // Crea un nuevo empleado
        if (repository.existsByCorreo(empleado.getCorreo())) {// Verifica si el correo ya existe
            // Retorna 400 Bad Request si el correo ya existe
            return ResponseEntity.badRequest().body("El correo ya está registrado: " + empleado.getCorreo());// Retorna 400 Bad Request
        }
        // Retorna 201 Created para la creación exitosa
        return ResponseEntity.status(HttpStatus.CREATED).body(repository.save(empleado));// Guarda y retorna el nuevo empleado
    }

    // ----------------------------------------------
    // PUT → Actualización completa de empleado
    // ----------------------------------------------
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Long id, @RequestBody Empleado emp) {// Actualiza un empleado existente
        return repository.findById(id)// Busca el empleado por ID
                .map(empleado -> {
                    // Validación de correo único: solo verifica si el correo que viene (emp.getCorreo())
                    // es diferente al correo actual (empleado.getCorreo()) Y ya existe en BD.
                    if (!empleado.getCorreo().equals(emp.getCorreo()) &&
                            repository.existsByCorreo(emp.getCorreo())) {
                        return ResponseEntity.badRequest().body("El correo ya está en uso: " + emp.getCorreo());
                    } // Retorna 400 Bad Request si el correo ya existe

                    empleado.setNombre(emp.getNombre());//Actualiza el nombre
                    empleado.setCorreo(emp.getCorreo());// Actualiza el correo
                    empleado.setSalario(emp.getSalario()); // BigDecimal viene del JSON como número
                    // La fecha de ingreso también se actualiza si viene en el JSON 
                    empleado.setFechaIngreso(emp.getFechaIngreso());// Actualiza la fecha de ingreso

                    return ResponseEntity.ok(repository.save(empleado));// Guarda y retorna el empleado actualizado
                })
                .orElse(ResponseEntity.notFound().build());// Retorna 404 Not Found si no se encuentra el empleado
    }

    // ----------------------------------------------
    // PATCH → Actualización  de empleado (parcial)
    // ----------------------------------------------
    @PatchMapping("/{id}")
    public ResponseEntity<?> actualizarParcial(
            @PathVariable Long id,// ID del empleado a actualizar
            @RequestBody Map<String, Object> cambios) {// Mapa de campos a actualizar

        Optional<Empleado> optionalEmp = repository.findById(id);// Busca el empleado por ID

        if (optionalEmp.isEmpty()) {// Verifica si el empleado existe
            return ResponseEntity.notFound().build();// Retorna 404 Not Found si no se encuentra el empleado
        }

        Empleado empleado = optionalEmp.get();// Obtiene el empleado existente

        // Variable para manejar la respuesta si hay un error de unicidad
        String correoDuplicadoError = null;// Variable para error de correo duplicado

         // Itera sobre los cambios y actualiza los campos correspondientes

        for (Map.Entry<String, Object> entry : cambios.entrySet()) {// Itera sobre los cambios
            String campo = entry.getKey();// Nombre del campo a actualizar
            Object valor = entry.getValue();// Nuevo valor del campo

            switch (campo) {// Switch para cada campo posible
                case "nombre":// Actualiza el nombre
                    empleado.setNombre(valor.toString());// Convierte el valor a String y actualiza el nombre
                    break;// Fin del case nombre

                case "correo":// Actualiza el correo
                    String nuevoCorreo = valor.toString();// Convierte el valor a String y actualiza el correo
                    // Chequea si el nuevo correo ya existe y es diferente al actual
                    if (repository.existsByCorreo(nuevoCorreo) && !nuevoCorreo.equals(empleado.getCorreo())) {// Verifica si el correo ya existe
                        correoDuplicadoError = "El correo ya está registrado: " + nuevoCorreo;// Mensaje de error de correo duplicado
                        break; // Salir del switch
                    }
                    empleado.setCorreo(nuevoCorreo);// Actualiza el correo
                    break;// Fin del case correo

                case "salario":// Actualiza el salario
                    try {
                        // Asegurarse de que el valor sea un número válido antes de la conversión
                        empleado.setSalario(new BigDecimal(valor.toString()));// Convierte el valor a BigDecimal y actualiza el salario
                    } catch (NumberFormatException e) {// Manejo de error si el formato no es válido
                        return ResponseEntity.badRequest().body("Formato de salario inválido: " + valor);// Retorna 400 Bad Request
                    }
                    break;// Fin del case salario
                
                // ----------------------------------------------
                // LÓGICA PARA FECHA DE INGRESO
                // ----------------------------------------------
                case "fechaIngreso": // Actualiza la fecha de ingreso
                    try {
                        // Asume el formato ISO 8601 (AAAA-MM-DD)
                        empleado.setFechaIngreso(LocalDate.parse(valor.toString()));// Convierte el valor a LocalDate y actualiza la fecha de ingreso
                    } catch (Exception e) {// Manejo de error si el formato no es válido
                        return ResponseEntity.badRequest().body("Formato de fecha de ingreso inválido. Use AAAA-MM-DD.");// Retorna 400 Bad Request
                    }
                    break;// Fin del case fechaIngreso
            }

            // Si se detectó un error de correo duplicado, retorna 400 inmediatamente.
            if (correoDuplicadoError != null) { // Verifica si hay un error de correo duplicado
                return ResponseEntity.badRequest().body(correoDuplicadoError); // Retorna 400 Bad Request
            }
        }

        return ResponseEntity.ok(repository.save(empleado));// Guarda y retorna el empleado actualizado
    }

    // ----------------------------------------------
    // DELETE → Eliminar empleado
    // ----------------------------------------------
    @DeleteMapping("/{id}")// Mapea las solicitudes DELETE a /api/empleados/{id}
    public ResponseEntity<String> eliminar(@PathVariable Long id) {// Elimina un empleado por su ID
        if (!repository.existsById(id)) {// Verifica si el empleado existe
            return ResponseEntity.notFound().build();// Retorna 404 Not Found si no se encuentra el empleado
        }
        repository.deleteById(id);// Elimina el empleado por ID
        return ResponseEntity.ok("Empleado eliminado");// Retorna 200 OK con mensaje de éxito
    }
}// Fin de la clase EmpleadoController