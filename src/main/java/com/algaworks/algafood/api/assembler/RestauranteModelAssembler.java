package com.algaworks.algafood.api.assembler;

import com.algaworks.algafood.api.AlgaLinks;
import com.algaworks.algafood.api.controller.RestauranteController;
import com.algaworks.algafood.api.model.EnderecoModel;
import com.algaworks.algafood.api.model.RestauranteModel;
import com.algaworks.algafood.domain.model.Restaurante;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class RestauranteModelAssembler extends RepresentationModelAssemblerSupport<Restaurante, RestauranteModel> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private AlgaLinks algaLinks;

    public RestauranteModelAssembler() {
        super(RestauranteController.class, RestauranteModel.class);
    }

    @Override
    public RestauranteModel toModel(Restaurante restaurante) {

        RestauranteModel restauranteModel = createModelWithId(restaurante.getId(), restaurante);

        modelMapper.map(restaurante, restauranteModel);

        restauranteModel.add(algaLinks.linkToRestaurantes("restaurantes"));

        Long cozinhaId = restauranteModel.getCozinha().getId();
        restauranteModel.getCozinha()
                .add(algaLinks.linkToCozinha(cozinhaId));

        EnderecoModel enderecoModel = restauranteModel.getEndereco();
        if ((enderecoModel != null)
                && (enderecoModel.getCidade() != null)) {
            Long cidadeId = enderecoModel.getCidade().getId();
            enderecoModel.getCidade()
                    .add(algaLinks.linkToCidade(cidadeId));
        }

        restauranteModel.add(algaLinks.linkToRestauranteFormasPagamento(restauranteModel.getId(), "formas-pagamento"));

        restauranteModel.add(algaLinks.linkToResponsaveisRestaurante(restauranteModel.getId(), "responsaveis"));

        restauranteModel.add(algaLinks.linkToProdutos(restauranteModel.getId(), "produtos"));

        if (restaurante.ativacaoPermitida())
            restauranteModel.add(algaLinks.linkToAtivacaoRestaurante(restauranteModel.getId(), "ativar"));

        if (restaurante.inativacaoPermitida())
            restauranteModel.add(algaLinks.linkToInativacaoRestaurante(restauranteModel.getId(), "inativar"));

        if (restaurante.aberturaPermitida())
            restauranteModel.add(algaLinks.linkToAberturaRestaurante(restauranteModel.getId(), "abrir"));

        if (restaurante.fechamentoPermitido())
            restauranteModel.add(algaLinks.linkToFechamentoRestaurante(restauranteModel.getId(), "fechar"));

        return restauranteModel;
    }

    @Override
    public CollectionModel<RestauranteModel> toCollectionModel(Iterable<? extends Restaurante> entities) {
        return super.toCollectionModel(entities)
                .add(algaLinks.linkToRestaurantes());
    }
}
