package br.com.ecommerce.common.resource;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ServiceResponse<T extends Object>  {
    @Getter
    @Setter
    private T result;

    @JsonProperty("mensagens")
    private List<Mensagem> mensagens = new ArrayList<>();

    @JsonIgnore
    @Setter
    private HttpStatus status;

    @Data
    @AllArgsConstructor
    public static class Mensagem {
        private String codigo;
        private String descricao;
    }

    @JsonIgnore
    public List<Mensagem> getAll() {
        return this.mensagens;
    }

    @JsonIgnore
    public Boolean isEmpty() {
        return this.mensagens.isEmpty();
    }

    @JsonIgnore
    public HttpStatus getStatus() {
        return status;
    }

    public ServiceResponse<T> addMensagem(String codigo, String mensagem) {
        mensagens.add(new Mensagem(codigo, mensagem));
        return this;
    }
}
