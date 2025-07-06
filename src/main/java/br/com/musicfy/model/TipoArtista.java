package br.com.musicfy.model;

public enum TipoArtista {
  SOLO("solo"),
  BANDA("banda"),
  DUPLA("dupla");

  private final String descricao;

  TipoArtista(String descricao) {
    this.descricao = descricao;
  }

  public String getDescricao() {
    return descricao;
  }

  @Override
  public String toString() {
    return descricao;
  }
}
