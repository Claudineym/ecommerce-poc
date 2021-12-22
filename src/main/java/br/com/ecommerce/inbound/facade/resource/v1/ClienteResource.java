package br.com.ecommerce.inbound.facade.resource.v1;

import br.com.ecommerce.business.domain.entity.Cliente;
import br.com.ecommerce.business.domain.repository.ClienteRepository;
import br.com.ecommerce.common.message.Mensagem;
import br.com.ecommerce.common.resource.ServiceResponse;
import br.com.ecommerce.inbound.dto.ClienteRequest;
import br.com.ecommerce.inbound.dto.ClienteResponse;
import lombok.Data;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@Data
@RestController
public class ClienteResource implements ClienteApi{

    private final ClienteRepository clienteRepository;


    @Override
    public ServiceResponse<ClienteResponse> criar(ClienteRequest requisicao) {

        Cliente  cliente = clienteRepository.save(Cliente
                .builder()
                .nome(requisicao.getNome())
                .email(requisicao.getEmail())
                .celular(requisicao.getCelular())
                .dtNascimento(requisicao.getDtNascimento())
                .build());

        return ServiceResponse.<ClienteResponse>builder()
                .result(ClienteResponse.builder()
                        .nome(cliente.getNome())
                        .celular(cliente.getCelular())
                        .email(cliente.getEmail())
                        .build()
                ).mensagens(
                        Collections.singletonList(new ServiceResponse.Mensagem(
                                Mensagem.SUCESSO.getCodigo(), Mensagem.SUCESSO.getDescricao())))
                .build();
    }
}
