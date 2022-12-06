package github.com.Bartolace.rest.controller;

import github.com.Bartolace.domain.entity.ItemPedido;
import github.com.Bartolace.domain.entity.Pedido;
import github.com.Bartolace.domain.enums.StatusPedido;
import github.com.Bartolace.rest.dto.AtualizacaoStatusPedidoDTO;
import github.com.Bartolace.rest.dto.InformacaoItemPedidoDTO;
import github.com.Bartolace.rest.dto.InformacoesPedidoDTO;
import github.com.Bartolace.rest.dto.PedidoDTO;
import github.com.Bartolace.service.PedidoService;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

    private PedidoService service;

    public PedidoController (PedidoService service) {
        this.service = service;
    }


    @PostMapping
    @ResponseStatus(CREATED)
    public Integer save ( @RequestBody @Valid PedidoDTO dto){
        Pedido pedido = service.salvar(dto); //recebe um pedido dto
        return pedido.getId(); // salva como um verdadeiro pedido
    }

    @GetMapping("{id}")
    public InformacoesPedidoDTO getById(@PathVariable Integer id){
        return service
                .obterPedidoCompleto(id)
                .map( p -> converter(p))
                .orElseThrow( () ->
                    new ResponseStatusException(NOT_FOUND, "Pedido não encontrado"));
    }

    @PatchMapping("{id}") // em vez de usar o putMapping, para atualizar apenas uma parte da entidade.
    @ResponseStatus(NO_CONTENT) // nao retorna nenhum obj.
    public void updateStatus( @PathVariable @Valid Integer id ,
            @RequestBody AtualizacaoStatusPedidoDTO dto){
        String novoStatus = dto.getNovoStatus();
        service.atualizaStatus(id, StatusPedido.valueOf(novoStatus));
    }

    //metodo que ajuda no getById de InformacoesPedidoDTO
    public InformacoesPedidoDTO converter (Pedido pedido){
       return InformacoesPedidoDTO
                .builder()
                .codigo(pedido.getId())
                .dataPedido(pedido.getDataPedido().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))
                .cpf(pedido.getCliente().getCpf())
                .nomeCliente(pedido.getCliente().getNome())
                .total(pedido.getTotal())
                .status(pedido.getStatus().name())
                .items(converter(pedido.getItens()))
                .build();
    }

    private List<InformacaoItemPedidoDTO> converter (List<ItemPedido> itens){
        if (CollectionUtils.isEmpty(itens)) {
            return Collections.emptyList();
        }

        return itens.stream().map(
                item -> InformacaoItemPedidoDTO
                        .builder()
                        .descricaoProduto(item.getProduto().getDescricao())
                        .precoUnitario(item.getProduto().getPreco())
                        .quantidade(item.getQuantidade())
                        .build()
        ).collect(Collectors.toList());// de stream para list
    }


}
