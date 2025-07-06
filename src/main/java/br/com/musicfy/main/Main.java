package br.com.musicfy.main;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import br.com.musicfy.model.Artista;
import br.com.musicfy.model.GeneroMusical;
import br.com.musicfy.model.Musica;
import br.com.musicfy.model.TipoArtista;
import br.com.musicfy.repository.ArtistaRepository;
import br.com.musicfy.repository.MusicaRepository;
import br.com.musicfy.service.ApiService;

public class Main {

  private final ArtistaRepository repositorio;
  private final MusicaRepository musicaRepository;

  public Main(ArtistaRepository repositorio, MusicaRepository musicaRepository) {
    this.repositorio = repositorio;
    this.musicaRepository = musicaRepository;
  }

  Scanner leitura = new Scanner(System.in);

  public void exibeMenu() {
    var opcao = -1;
    while (opcao != 0) {

      var menu = """
          \n***********************************
          *** My MusicFy - Menu Principal ***
          ***********************************
          Escolha uma opção:
          1 - Cadastrar Artistas
          2 - Cadastrar Músicas
          3 - Listar músicas
          4 - Buscar músicas por artista
          5 - Pesquisar dados sobre um artista\n
          0 - Sair
          ***********************************
          """;

      System.out.println(menu);
      System.out.print("Digite a opção desejada: ");
      try {
        opcao = leitura.nextInt();
        leitura.nextLine();
      } catch (Exception e) {
        System.out.println("Opção inválida, por favor digite um número válido.");
        leitura.nextLine();
        opcao = -1;
        continue;
      }
      switch (opcao) {
        case 1:
          cadastrarArtistas();
          break;
        case 2:
          cadastrarMusicas();
          break;
        case 3:
          listarMusicas();
          break;
        case 4:
          buscarMusicasPorArtista();
          break;
        case 5:
          pesquisarArtista();
          break;
        case 0:
          System.out.println("\nSaindo do My MusicFy... Até logo!");
          break;
        default:
          System.out.println("Opção inválida, por favor escolha uma opção válida.");
      }
    }

    leitura.close();
  }

  private void cadastrarArtistas() {
    var sairDoCadastro = false;
    while (!sairDoCadastro) {
      System.out.println("\n****************************");
      System.out.println("*** Cadastro de Artistas ***");
      System.out.println("****************************");
      System.out.print("Digite o nome do artista: ");
      String nome = leitura.nextLine();

      GeneroMusical genero = null;
      while (genero == null) {
        System.out.print("Digite o gênero do artista: ");
        System.out.println("Gêneros disponíveis:");
        for (GeneroMusical generoMusical : GeneroMusical.values()) {
          System.out.println("- " + generoMusical);
        }
        String generoInput = leitura.nextLine().toUpperCase();
        try {
          genero = GeneroMusical.valueOf(generoInput);
        } catch (IllegalArgumentException e) {
          System.out.println("Gênero inválido. Por favor, digite um gênero válido.");
        }
      }

      TipoArtista tipo = null;
      while (tipo == null) {
        System.out.print("\nDigite o tipo do artista (SOLO, BANDA, DUPLA): ");
        String tipoInput = leitura.nextLine().toUpperCase();
        try {
          tipo = TipoArtista.valueOf(tipoInput);
        } catch (IllegalArgumentException e) {
          System.out.println("Tipo inválido. Por favor, digite um tipo válido.");
        }
      }

      Artista novoArtista = new Artista(nome, tipo, genero);
      repositorio.save(novoArtista);

      System.out.println("\n********************************");
      System.out.println("\nArtista cadastrado com sucesso!");
      System.out.println("\n********************************\n");

      System.out.println("\nDeseja cadastrar mais artistas? (S/N)");
      String resposta = leitura.nextLine().toUpperCase();
      if (!resposta.equals("S")) {
        sairDoCadastro = true;
      }

    }

  }

  private void cadastrarMusicas() {
    var sairDoCadastro = false;
    while (!sairDoCadastro) {
      System.out.println("\n****************************");
      System.out.println("*** Cadastro de Músicas ***");
      System.out.println("****************************");

      System.out.println("\nEssa música será cadastrada para qual artista?");
      System.out.println("Artistas cadastrados no banco de dados:");
      for (Artista artista : repositorio.findAll()) {
        System.out.println("- [" + artista.getId() + "] " + artista.getNome());
      }

      Long idArtista = null;
      while (idArtista == null) {
        System.out.print("\nDigite o ID do artista: ");
        try {
          idArtista = Long.parseLong(leitura.nextLine());
        } catch (NumberFormatException e) {
          System.out.println("ID inválido! Digite um número.");
        }
      }

      Optional<Artista> artistaOptional = repositorio.findById(idArtista);
      if (artistaOptional.isEmpty()) {
        System.out.println("Artista não encontrado.");
        return;
      }

      System.out.print("Digite o nome da música: ");
      String nome = leitura.nextLine();

      System.out.print("Digite o nome do álbum: ");
      String album = leitura.nextLine();

      Integer duracaoInt = null;
      while (duracaoInt == null) {
        System.out.print("Digite a duração da música (em segundos): ");
        String input = leitura.nextLine();
        try {
          duracaoInt = Integer.parseInt(input);
        } catch (NumberFormatException e) {
          System.out.println("Duração inválida! Digite apenas números.");
        }
      }

      Artista artista = artistaOptional.get();
      Musica novaMusica = new Musica(nome, album, duracaoInt, artista);
      musicaRepository.save(novaMusica);

      System.out.println("\n********************************");
      System.out.println("Música cadastrada com sucesso!");
      System.out.println("********************************");

      System.out.println("Deseja cadastrar mais músicas? (S/N)");
      String resposta = leitura.nextLine();
      if (!resposta.equalsIgnoreCase("S")) {
        sairDoCadastro = true;
      }

    }
  }

  private void listarMusicas() {
    System.out.println("\n************************************");
    System.out.println("*** Lista de Músicas Cadastradas ***");
    System.out.println("**************************************");
    List<Musica> musicas = musicaRepository.findAll();
    if (musicas.isEmpty()) {
      System.out.println("Nenhuma música encontrada.");
      return;
    }
    for (Musica musica : musicas) {
      System.out.println("\n**********************************************");
      System.out.println("Título: " + musica.getTitulo());
      System.out.println("Álbum: " + musica.getAlbum());
      System.out.println("Duração: " + musica.getDuracao() + " segundos");
      System.out.println("Artista: " + musica.getArtista().getNome());
      System.out.println("**********************************************");
    }
  }

  private void buscarMusicasPorArtista() {
    System.out.println("\n************************************");
    System.out.println("*** Buscar músicas por artista ***");
    System.out.println("**************************************");
    System.out.println("Artistas cadastrados:");
    for (Artista artista : repositorio.findAll()) {
      System.out.println("- [" + artista.getId() + "] " + artista.getNome());
    }

    Long idArtista = null;
    while (idArtista == null) {
      System.out.print("Digite o ID do artista: ");
      try {
        idArtista = Long.parseLong(leitura.nextLine());
      } catch (NumberFormatException e) {
        System.out.println("ID inválido! Digite um número.");
      }
    }

    Optional<Artista> artistaOptional = repositorio.findByIdComMusicas(idArtista);
    if (artistaOptional.isEmpty()) {
      System.out.println("Artista não encontrado.");
      return;
    }

    Artista artista = artistaOptional.get();

    if (artista.getMusicas().isEmpty()) {
      System.out
          .println("Nenhuma música encontrada para o artista " + artista.getNome() + ".");
      return;
    }

    System.out.println("************************************");
    System.out.println("Músicas de " + artista.getNome());
    System.out.println("************************************");

    artista.getMusicas().forEach(musica -> {
      System.out.println("Título: " + musica.getTitulo());
    });

  }

  private void pesquisarArtista() {
    System.out.println("\n**************************************");
    System.out.println("*** Buscar dados sobre um artista ***");
    System.out.println("**************************************");
    System.out.println("Sobre qual artista deseja pesquisar?");
    String artista = leitura.nextLine();

    System.out.println("\nConsultando dados sobre o artista " + artista + "...\n");

    ApiService apiService = new ApiService();
    String resumo = apiService.gerarResumo(artista);

    System.out.println("************************************");
    System.out.println("Resumo sobre o artista " + artista + ":");
    System.out.println("************************************");
    System.out.println(resumo);

  }

  // Hugging Face Token: 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

}
