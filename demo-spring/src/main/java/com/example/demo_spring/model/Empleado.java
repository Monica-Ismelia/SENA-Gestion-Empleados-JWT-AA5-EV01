package com.example.demo_spring.model; // src/main/java/com/example/demo_spring/model/Empleado.java

import jakarta.persistence.*; // Importa las anotaciones de JPA
import java.math.BigDecimal;// Importa BigDecimal para el salario
import java.time.LocalDate; // Importa LocalDate para la fecha de ingreso

@Entity// Define que esta clase es una entidad JPA
@Table(name = "empleados") // Nombre de la tabla en la base de datos
public class Empleado {// Clase modelo para Empleado

    @Id// Define la clave primaria
    @GeneratedValue(strategy = GenerationType.IDENTITY)//Id autoincremental
    private Long id;// ID del empleado

    @Column(nullable = false, length = 100)// caracteres de nombre del empleado
    private String nombre;// Nombre del empleado

    @Column(nullable = false, unique = true, length = 150)// caracteres de correo electrónico único
    private String correo;// Correo electrónico del empleado

    @Column(nullable = false, precision = 12, scale = 2)// Salario con precisión y escala
    private BigDecimal salario;// Salario del empleado

    // ----------------------------------------------------
    // Fecha de Ingreso
    // @Column(nullable = false) podría ser necesario si es obligatorio
    @Column
    private LocalDate fechaIngreso;// Fecha de ingreso del empleado
    // ----------------------------------------------------

    public Empleado() {}// Constructor por defecto

    // Constructor con parámetros
    public Empleado(String nombre, String correo, BigDecimal salario, LocalDate fechaIngreso) {// Constructor con todos los campos
        this.nombre = nombre;// crea el nombre
        this.correo = correo;// crea el correo
        this.salario = salario;// crea el salario
        this.fechaIngreso = fechaIngreso;// crea la fecha de ingreso
    }

    // Getters y Setters
    public Long getId() { return id; } // Obtiene el ID
    public void setId(Long id) { this.id = id; }// Establece el ID

    public String getNombre() { return nombre; }// Obtiene el nombre
    public void setNombre(String nombre) { this.nombre = nombre; }// Establece el nombre

    public String getCorreo() { return correo; }// Obtiene el correo
    public void setCorreo(String correo) { this.correo = correo; }// Establece el correo
    public BigDecimal getSalario() { return salario; }// Obtiene el salario
    public void setSalario(BigDecimal salario) { this.salario = salario; }// Establece el salario

    // ----------------------------------------------------
    // NUEVOS GETTER Y SETTER
    // ----------------------------------------------------
    public LocalDate getFechaIngreso() { return fechaIngreso; }// Obtiene la fecha de ingreso
    public void setFechaIngreso(LocalDate fechaIngreso) { this.fechaIngreso = fechaIngreso; }// Establece la fecha de ingreso
}// Fin de la clase Empleado