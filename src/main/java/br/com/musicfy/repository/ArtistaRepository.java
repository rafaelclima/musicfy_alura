package br.com.musicfy.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.musicfy.model.Artista;

public interface ArtistaRepository extends JpaRepository<Artista, Long> {

  @Query("SELECT a FROM Artista a LEFT JOIN FETCH a.musicas WHERE a.id = :id")
  Optional<Artista> findByIdComMusicas(Long id);

}
