package com.algaworks.algafood.api.v1.assembler;

import com.algaworks.algafood.api.v1.AlgaLinks;
import com.algaworks.algafood.api.v1.controller.PedidoController;
import com.algaworks.algafood.api.v1.model.PedidoModel;
import com.algaworks.algafood.domain.model.Pedido;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class PedidoModelAssembler extends RepresentationModelAssemblerSupport<Pedido, PedidoModel> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private AlgaLinks algaLinks;

    public PedidoModelAssembler() {
        super(PedidoController.class, PedidoModel.class);
    }

    @Override
    public PedidoModel toModel(Pedido pedido) {

        PedidoModel pedidoModel = createModelWithId(pedido.getCodigo(), pedido);

        modelMapper.map(pedido, pedidoModel);

        pedidoModel.add(algaLinks.linkToPedidos("pedidos"));

        if (pedido.podeSerConfirmado())
            pedidoModel.add(algaLinks.linkToConfirmacaoPedido(pedido.getCodigo(), "confirmar"));

        if (pedido.podeSerCancelado())
            pedidoModel.add(algaLinks.linkToCancelamentoPedido(pedido.getCodigo(), "cancelar"));

        if (pedido.podeSerEntregue())
            pedidoModel.add(algaLinks.linkToEntregaPedido(pedido.getCodigo(), "entregar"));

        Long restauranteId = pedidoModel.getRestaurante().getId();
        pedidoModel.getRestaurante()
                .add(algaLinks.linkToRestaurante(restauranteId));

        Long usuarioId = pedidoModel.getCliente().getId();
        pedidoModel.getCliente()
                .add(algaLinks.linkToUsuario(usuarioId));

        Long formaPagamentoId = pedidoModel.getFormaPagamento().getId();
        pedidoModel.getFormaPagamento()
                .add(algaLinks.linkToFormaPagamento(formaPagamentoId));

        Long cidadeId = pedidoModel.getEnderecoEntrega().getCidade().getId();
        pedidoModel.getEnderecoEntrega().getCidade()
                .add(algaLinks.linkToCidade(cidadeId));

        pedidoModel.getItens().forEach(item ->
                item.add(algaLinks.linkToProduto(restauranteId, item.getProdutoId(), "produto")));

        return pedidoModel;
    }
}