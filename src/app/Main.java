package app;

import model.Usuario;
import repository.AuditoriaRepository;
import repository.NaoConformidadeRepository;
import repository.ProjetoRepository;
import repository.RepositoryException;
import repository.UsuarioRepository;
import repository.jdbc.AuditoriaRepositoryJdbc;
import repository.jdbc.NaoConformidadeRepositoryJdbc;
import repository.jdbc.ProjetoRepositoryJdbc;
import repository.jdbc.UsuarioRepositoryJdbc;
import service.AuditoriaService;
import service.NaoConformidadeService;
import service.ProjetoService;
import service.ServiceException;
import service.UsuarioService;

public class Main {

    public static void main(String[] args) {

        try {
            ProjetoRepository projetoRepository =
                    new ProjetoRepositoryJdbc();

            UsuarioRepository usuarioRepository =
                    new UsuarioRepositoryJdbc();

            AuditoriaRepository auditoriaRepository =
                    new AuditoriaRepositoryJdbc();

            NaoConformidadeRepository naoConformidadeRepository =
                    new NaoConformidadeRepositoryJdbc();

            ProjetoService projetoService =
                    new ProjetoService(projetoRepository);

            UsuarioService usuarioService =
                    new UsuarioService(usuarioRepository);

            AuditoriaService auditoriaService =
                    new AuditoriaService(auditoriaRepository);

            NaoConformidadeService naoConformidadeService =
                    new NaoConformidadeService(
                            naoConformidadeRepository
                    );

            System.out.println("==================================");
            System.out.println("TESTE DO SISTEMA REFATORADO");
            System.out.println("==================================");

            System.out.println();
            System.out.println("1. Teste de usuários");

            usuarioService.listarTodos()
                    .forEach(System.out::println);

            System.out.println();
            System.out.println("2. Teste de autenticação");

            Usuario usuarioAutenticado =
                    usuarioService.autenticar(
                            "christian@empresa.com",
                            "senha123"
                    );

            System.out.println(
                    "Usuário autenticado: "
                    + usuarioAutenticado.getNome()
            );

            System.out.println();
            System.out.println("3. Teste de projetos");

            projetoService.listarTodos()
                    .forEach(System.out::println);

            System.out.println();
            System.out.println("4. Teste de auditorias");

            auditoriaService.listarTodos()
                    .forEach(System.out::println);

            System.out.println();
            System.out.println(
                    "5. Teste de não conformidades"
            );

            naoConformidadeService.listarTodos()
                    .forEach(System.out::println);
            
            System.out.println();
            System.out.println("6. Teste de validação");

            try {
            projetoService.cadastrar(new model.Projeto());

            System.out.println(
                "Falha: o projeto inválido foi aceito."
               );

            } catch (ServiceException erroValidacao) {

            System.out.println(
                "Validação funcionando corretamente: "
                + erroValidacao.getMessage()
               );
            }
            System.out.println();
            System.out.println("==================================");
            System.out.println(
                    "TODOS OS TESTES FORAM CONCLUÍDOS!"
            );
            System.out.println("==================================");

        } catch (ServiceException erro) {

            System.out.println(
                    "Erro de regra de negócio: "
                    + erro.getMessage()
            );

        } catch (RepositoryException erro) {

            System.out.println(
                    "Erro de acesso ao banco: "
                    + erro.getMessage()
            );

            if (erro.getCause() != null) {
                System.out.println(
                        "Detalhes: "
                        + erro.getCause().getMessage()
                );
            }
        }
    }
}