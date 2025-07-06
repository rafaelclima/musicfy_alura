package br.com.musicfy.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

import jakarta.persistence.*;

@Entity
@Table(name = "artistas")
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Artista {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String nome;

  @Enumerated(EnumType.STRING)
  private TipoArtista tipo;

  @Enumerated(EnumType.STRING)
  private GeneroMusical genero;

  @OneToMany(mappedBy = "artista", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Musica> musicas;

  public Artista(String nome, TipoArtista tipo, GeneroMusical genero) {
    this.nome = nome;
    this.tipo = tipo;
    this.genero = genero;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getNome() {
    return nome;
  }

  public void setNome(String nome) {
    this.nome = nome;
  }

  public TipoArtista getTipo() {
    return tipo;
  }

  public void setTipo(TipoArtista tipo) {
    this.tipo = tipo;
  }

  public GeneroMusical getGenero() {
    return genero;
  }

  public void setGenero(GeneroMusical genero) {
    this.genero = genero;
  }

  public List<Musica> getMusicas() {
    return musicas;
  }

  public void setMusicas(List<Musica> musicas) {
    this.musicas = musicas;
  }

}
