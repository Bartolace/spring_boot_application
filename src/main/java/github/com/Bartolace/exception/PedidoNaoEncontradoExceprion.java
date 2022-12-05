package github.com.Bartolace.exception;

public class PedidoNaoEncontradoExceprion extends RuntimeException {
    public PedidoNaoEncontradoExceprion() {
        super("Pedido não encontrado.");
    }
}
