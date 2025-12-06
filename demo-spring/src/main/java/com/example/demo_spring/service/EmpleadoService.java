package com.example.demo_spring.service; // Servicio para la entidad Empleado

import com.example.demo_spring.model.Empleado;
import com.example.demo_spring.repository.EmpleadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;
// Importa dependencias necesarias
@Service // Anotación de servicio de Spring
@Transactional // Manejo de transacciones
public class EmpleadoService { // Servicio para operaciones CRUD de Empleado

    @Autowired// Inyección de dependencia del repositorio de Empleado
    private EmpleadoRepository repository; // Repositorio de Empleado

    // Listar todos los empleados
    public List<Empleado> listarTodos() {// Lista todos los empleados
        return repository.findAll();// Retorna la lista de todos los empleados
    }

    // Guardar (con validación de correo único)
    public Empleado guardar(Empleado empleado) {// Guarda un nuevo empleado con validación de correo único
        if (repository.existsByCorreo(empleado.getCorreo())) {// Verifica si el correo ya existe
            throw new RuntimeException("El correo ya está registrado: " + empleado.getCorreo());// Lanza excepción si el correo ya existe
        }
        return repository.save(empleado);// Guarda y retorna el nuevo empleado
    }

    // Buscar por ID de empleado
    public Optional<Empleado> buscarPorId(Long id) {// Busca un empleado por su ID
        return repository.findById(id);// Retorna un Optional con el empleado si se encuentra
    }// Fin del método buscarPorId

    // Eliminar por ID de empleado
    public void eliminar(Long id) {// Elimina un empleado por su ID
        repository.deleteById(id);// Elimina el empleado del repositorio
    }// Fin del método eliminar

    // Actualizar por ID de empleado
    public Empleado actualizar(Long id, Empleado empleadoActualizado) {// Actualiza un empleado por su ID
        Empleado empleado = repository.findById(id)// Busca el empleado por ID
            .orElseThrow(() -> new RuntimeException("Empleado no encontrado con ID: " + id));// Lanza excepción si no se encuentra

        // Validar si el nuevo correo ya existe (y no es el mismo)
        if (!empleado.getCorreo().equals(empleadoActualizado.getCorreo()) &&// Si el correo ha cambiado
            repository.existsByCorreo(empleadoActualizado.getCorreo())) {// Verifica si el nuevo correo ya existe
            throw new RuntimeException("El correo ya está en uso: " + empleadoActualizado.getCorreo());// Lanza excepción si el nuevo correo ya existe
        }

        empleado.setNombre(empleadoActualizado.getNombre());// Actualiza el nombre
        empleado.setCorreo(empleadoActualizado.getCorreo());// Actualiza el correo
        empleado.setSalario(empleadoActualizado.getSalario());// Actualiza el salario
        return repository.save(empleado);// Guarda y retorna el empleado actualizado
    }// Fin del método actualizar
}