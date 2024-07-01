package com.codigo.msregistro.infraestructure.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Entity
@Table(name = "empleado")
@Getter
@Setter
public class PersonaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "nombre", length = 150)
    private String nombre;

    @Column(name = "apellido", length = 150)
    private String apellido;

    @Column(name = "edad", length = 15)
    private Integer edad;

    @Column(name = "cargo", length = 150)
    private String cargo;


    @Column(name = "tipo_doc", length = 150)
    private String tipoDoc;

    @Column(name = "num_doc", length = 15)
    private String numDoc;

    @Column(name = "departamento")
    private String departamento;

    @Column(name = "salario")
    private Double salario;

    @Column(name = "telefono")
    private String telefono;

    @Column(name = "correo")
    private String correo;

    @Column(name = "estado", nullable = false)
    private Integer estado;

    @Column(name = "direccion")
    private String direccion;

    @Column(name = "usua_crea", length = 45)
    private String usuaCrea;

    @Column(name = "date_create")
    private Timestamp dateCrea;

    @Column(name = "usua_update", length = 45)
    private String usuaUpdate;

    @Column(name = "date_update")
    private Timestamp dateUpdate;

    @Column(name = "usua_delete", length = 45)
    private String usuaDelete;

    @Column(name = "date_delete")
    private Timestamp dateDelete;
}
