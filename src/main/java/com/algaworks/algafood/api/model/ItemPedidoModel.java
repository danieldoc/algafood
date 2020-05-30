package com.algaworks.algafood.api.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
public class ItemPedidoModel {

    @ApiModelProperty(example = "1")
    private Long produtoId;

    @ApiModelProperty(example = "Petisco de Picanha")
    private String produtoNome;

    @ApiModelProperty(example = "1")
    private Integer quantidade;

    @ApiModelProperty(example = "42.90")
    private BigDecimal precoUnitario;

    @ApiModelProperty(example = "42.90")
    private BigDecimal precoTotal;

    @ApiModelProperty(example = "Bem passada")
    private String observacao;
}
