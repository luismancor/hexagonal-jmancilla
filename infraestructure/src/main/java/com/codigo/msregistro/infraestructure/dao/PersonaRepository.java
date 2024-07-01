package com.codigo.msregistro.infraestructure.dao;

import com.codigo.msregistro.infraestructure.entity.PersonaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PersonaRepository extends JpaRepository<PersonaEntity,Long> {

    PersonaEntity findByNumDoc(@Param("numDoc") String numDoc);
    List<PersonaEntity> findByEstado(@Param("estado") Integer estado);
    boolean existsByNumDoc(String numDoc);

}
