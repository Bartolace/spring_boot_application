package github.com.Bartolace.service.impl;

import github.com.Bartolace.domain.entity.Cliente;
import github.com.Bartolace.domain.entity.ItemPedido;
import github.com.Bartolace.domain.entity.Pedido;
import github.com.Bartolace.domain.entity.Produto;
import github.com.Bartolace.domain.enums.StatusPedido;
import github.com.Bartolace.domain.repository.Clientes;
import github.com.Bartolace.domain.repository.ItemsPedidos;
import github.com.Bartolace.domain.repository.Pedidos;
import github.com.Bartolace.domain.repository.Produtos;
import github.com.Bartolace.exception.RegraNegocioException;
import github.com.Bartolace.rest.dto.ItemPedidoDTO;
import github.com.Bartolace.rest.dto.PedidoDTO;
import github.com.Bartolace.service.PedidoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
// um mock da verdadeira PedidoService// boa pratica e facilita fazer os testes, pois trabalha com objetos mocks


@Service
@RequiredArgsConstructor // gera constrotor com os campos, com final, obrigatorios
public class PedidoServiceImpl implements PedidoService {

    private final Pedidos repository;
    private final Clientes clientesRepository;
    private final Produtos produtosRepository;
    private final ItemsPedidos itemsPedidosRepository;


    @Override
    @Transactional
    public Pedido salvar(PedidoDTO dto) {
        Integer idCliente =dto.getCliente();
        Cliente cliente = clientesRepository
                .findById(idCliente) // pega o Id dto e prepara para id cliente
                .orElseThrow( () -> new RegraNegocioException("Código de cliente inválido"));

        Pedido pedido = new Pedido();
        pedido.setTotal(dto.getTotal());
        pedido.setDataPedido(LocalDate.now());
        pedido.setCliente(cliente);
        pedido.setStatus(StatusPedido.REALIZADO);

        List<ItemPedido> itemsPedido = converterItems(pedido, dto.getItems() );
        repository.save(pedido);
        itemsPedidosRepository.saveAll(itemsPedido);
        pedido.setItens(itemsPedido);
        return pedido;
    }

    private List<ItemPedido> converterItems (Pedido pedido, List<ItemPedidoDTO> items){
        if (items.isEmpty()){
            throw new RegraNegocioException("Não é possível realizar um pedido sem itens");
        }

        return items //comeca com  uma stream dto
                .stream()
                .map( dto -> {
                    Integer idProduto = dto.getProduto();
                    Produto produto = produtosRepository
                            .findById(idProduto)
                            .orElseThrow(() ->
                                    new RegraNegocioException(
                                            "Código de Produto inválido: " + idProduto));

                    ItemPedido itemPedido = new ItemPedido();
                    itemPedido.setQuantidade(dto.getQuantidade());
                    itemPedido.setPedido(pedido);
                    itemPedido.setProduto(produto);
                    return itemPedido;  //sai como class itemPedido

                }).collect(Collectors.toList());
    }

    @Override
    public Optional<Pedido> obterPedidoCompleto(Integer id) {
        return repository.findByIdFetchItens(id);
    }

    @Override
    public void atualizaStatus(Integer id, StatusPedido statusPedido) {

    }

}
