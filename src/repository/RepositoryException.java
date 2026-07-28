package repository;

public class RepositoryException extends RuntimeException {

    public RepositoryException(String mensagem) {
        super(mensagem);
    }

    public RepositoryException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }
}