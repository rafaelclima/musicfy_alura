package br.com.musicfy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import br.com.musicfy.main.Main;
import br.com.musicfy.repository.ArtistaRepository;
import br.com.musicfy.repository.MusicaRepository;

@SpringBootApplication
public class MusicfyApplication implements CommandLineRunner {
	@Autowired
	private ArtistaRepository repositorio;
	@Autowired
	private MusicaRepository musicaRepository;

	public static void main(String[] args) {
		SpringApplication.run(MusicfyApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Main main = new Main(repositorio, musicaRepository);
		main.exibeMenu();
	}

}
