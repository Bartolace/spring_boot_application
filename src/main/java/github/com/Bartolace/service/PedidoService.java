package github.com.Bartolace.service;

import github.com.Bartolace.domain.entity.Pedido;
import github.com.Bartolace.domain.enums.StatusPedido;
import github.com.Bartolace.rest.dto.PedidoDTO;

import java.util.Optional;

public interface PedidoService {

    Pedido salvar(PedidoDTO dto); // faz a assinatura do método

    Optional<Pedido> obterPedidoCompleto (Integer id);
    void atualizaStatus(Integer id, StatusPedido statusPedido);
}
