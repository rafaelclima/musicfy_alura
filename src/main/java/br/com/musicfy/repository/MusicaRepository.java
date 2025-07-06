package br.com.musicfy.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.musicfy.model.Musica;

public interface MusicaRepository extends JpaRepository<Musica, Long> {

}
