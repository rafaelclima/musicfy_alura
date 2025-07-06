package br.com.musicfy.model;

public enum GeneroMusical {
  ROCK("rock"),
  SERTANEJO("sertanejo"),
  POP("pop"),
  MPB("mpb"),
  SAMBA("samba"),
  JAZZ("jazz"),
  ELETRONICA("eletronica"),
  CLASSICO("classic"),
  RAP("rap"),
  HIPHOP("hiphop"),
  FUNK("funk"),
  REGGAE("reggae");

  private final String descricao;

  GeneroMusical(String descricao) {
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
