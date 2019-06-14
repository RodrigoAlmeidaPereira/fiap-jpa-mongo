package br.com.fiap.rodrigo;


import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@SpringBootApplication
@EnableMongoRepositories(basePackageClasses = PessoaRepository.class)
public class Application implements CommandLineRunner {

    public PessoaRepository repository;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        repository.deleteAll();

        repository.save(Pessoa.builder().nome("Rafael").build());

        Endereco residencial = Endereco.builder().rua("Avenida Morumbi 2000").cidade("SP").build();
        Endereco comercial = Endereco.builder().rua("Avenida Paulista 301").cidade("SP").build();
        List<Endereco> enderecos = new ArrayList<>();
        enderecos.add(residencial);
        enderecos.add(comercial);

        repository.save(Pessoa.builder().nome("Paulo").endereco(enderecos).build());
        repository.save(Pessoa.builder().nome("Julia").endereco(enderecos).build());

        for (Pessoa pessoa : repository.findAll()) {
            System.out.println(pessoa);
        }
        System.out.println();

        // find by nome
        System.out.println("findByNome('Julia'):");
        System.out.println("--------------------------------");
        System.out.println(repository.findByNome("Julia"));
        System.out.println();

        // find by nome usando like
        System.out.println("findByNomeLike('Pa'):");
        System.out.println("--------------------------------");
        System.out.println(repository.findByNomeLike("Pa"));
        System.out.println();
    }
}
