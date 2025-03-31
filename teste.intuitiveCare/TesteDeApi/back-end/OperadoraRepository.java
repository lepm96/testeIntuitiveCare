package com.example.operadorasapi.repository;

import com.example.operadorasapi.model.Operadora;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OperadoraRepository extends JpaRepository<Operadora, String> {
    @Query("SELECT o FROM Operadora o WHERE " +
            "LOWER(o.razaoSocial) LIKE LOWER(CONCAT('%', :termo, '%')) OR " +
            "LOWER(o.nomeFantasia) LIKE LOWER(CONCAT('%', :termo, '%')) OR " +
            "LOWER(o.cidade) LIKE LOWER(CONCAT('%', :termo, '%')) OR " +
            "LOWER(o.uf) LIKE LOWER(CONCAT('%', :termo, '%'))")
    List<Operadora> buscarPorTermo(@Param("termo") String termo);
}