package service;

import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;
import model.Usuario;
import repository.UsuarioRepository;

public class UsuarioService {

    private static final Pattern PADRAO_EMAIL =
            Pattern.compile(
                    "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$"
            );

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(
            UsuarioRepository usuarioRepository) {

        if (usuarioRepository == null) {
            throw new IllegalArgumentException(
                    "O repositório de usuários é obrigatório."
            );
        }

        this.usuarioRepository = usuarioRepository;
    }

    public Usuario cadastrar(Usuario usuario) {
        validarUsuario(usuario);

        Optional<Usuario> usuarioExistente =
                usuarioRepository.buscarPorEmail(
                        usuario.getEmail()
                );

        if (usuarioExistente.isPresent()) {
            throw new ServiceException(
                    "Já existe um usuário cadastrado com esse e-mail."
            );
        }

        usuario.setIdUsuario(0);

        return usuarioRepository.salvar(usuario);
    }

    public List<Usuario> listarTodos() {
        return usuarioRepository.listarTodos();
    }

    public Optional<Usuario> buscarPorId(
            int idUsuario) {

        validarId(idUsuario);

        return usuarioRepository.buscarPorId(
                idUsuario
        );
    }

    public Optional<Usuario> buscarPorEmail(
            String email) {

        validarEmail(email);

        return usuarioRepository.buscarPorEmail(
                email.trim().toLowerCase()
        );
    }

    public Usuario autenticar(
            String email,
            String senha) {

        validarEmail(email);

        if (senha == null || senha.isBlank()) {
            throw new ServiceException(
                    "A senha é obrigatória."
            );
        }

        return usuarioRepository
                .autenticar(
                        email.trim().toLowerCase(),
                        senha
                )
                .orElseThrow(() ->
                        new ServiceException(
                                "E-mail ou senha inválidos."
                        )
                );
    }

    public boolean atualizar(Usuario usuario) {
        if (usuario == null) {
            throw new ServiceException(
                    "O usuário informado é obrigatório."
            );
        }

        validarId(usuario.getIdUsuario());
        validarUsuario(usuario);

        Usuario usuarioSalvo = usuarioRepository
                .buscarPorId(usuario.getIdUsuario())
                .orElseThrow(() ->
                        new ServiceException(
                                "Usuário não encontrado."
                        )
                );

        Optional<Usuario> usuarioComMesmoEmail =
                usuarioRepository.buscarPorEmail(
                        usuario.getEmail()
                );

        if (usuarioComMesmoEmail.isPresent()
                && usuarioComMesmoEmail
                        .get()
                        .getIdUsuario()
                        != usuarioSalvo.getIdUsuario()) {

            throw new ServiceException(
                    "O e-mail já pertence a outro usuário."
            );
        }

        return usuarioRepository.atualizar(usuario);
    }

    public boolean excluir(int idUsuario) {
        validarId(idUsuario);

        if (usuarioRepository
                .buscarPorId(idUsuario)
                .isEmpty()) {

            throw new ServiceException(
                    "Usuário não encontrado."
            );
        }

        return usuarioRepository.excluir(idUsuario);
    }

    private void validarUsuario(Usuario usuario) {
        if (usuario == null) {
            throw new ServiceException(
                    "O usuário informado é obrigatório."
            );
        }

        if (usuario.getNome() == null
                || usuario.getNome().isBlank()) {

            throw new ServiceException(
                    "O nome do usuário é obrigatório."
            );
        }

        if (usuario.getNome().trim().length() < 3) {
            throw new ServiceException(
                    "O nome deve possuir pelo menos 3 caracteres."
            );
        }

        validarEmail(usuario.getEmail());

        if (usuario.getSenha() == null
                || usuario.getSenha().isBlank()) {

            throw new ServiceException(
                    "A senha é obrigatória."
            );
        }

        if (usuario.getSenha().length() < 6) {
            throw new ServiceException(
                    "A senha deve possuir pelo menos 6 caracteres."
            );
        }

        if (usuario.getTipo() == null
                || usuario.getTipo().isBlank()) {

            throw new ServiceException(
                    "O tipo do usuário é obrigatório."
            );
        }

        usuario.setNome(
                usuario.getNome().trim()
        );

        usuario.setEmail(
                usuario.getEmail()
                        .trim()
                        .toLowerCase()
        );

        usuario.setTipo(
                usuario.getTipo().trim()
        );
    }

    private void validarEmail(String email) {
        if (email == null || email.isBlank()) {
            throw new ServiceException(
                    "O e-mail é obrigatório."
            );
        }

        String emailTratado =
                email.trim().toLowerCase();

        if (!PADRAO_EMAIL
                .matcher(emailTratado)
                .matches()) {

            throw new ServiceException(
                    "O e-mail informado é inválido."
            );
        }
    }

    private void validarId(int idUsuario) {
        if (idUsuario <= 0) {
            throw new ServiceException(
                    "O código do usuário deve ser maior que zero."
            );
        }
    }
}