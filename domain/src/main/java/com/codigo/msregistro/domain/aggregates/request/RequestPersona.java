package com.codigo.msregistro.domain.aggregates.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RequestPersona {
    private String numDoc;
    private int edad;
    private String cargo;
    private double salario;
    private String telefono;
    private String correo;
    private String departamento;
}
