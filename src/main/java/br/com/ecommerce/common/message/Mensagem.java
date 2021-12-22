package br.com.ecommerce.common.message;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Mensagem {
    SUCESSO("0", "Operação realizada com sucesso."),
    CRIADO("1", "${0} criada com sucesso."),
    SEM_CONTEUDO("3", "Não existem resultados a serem exibidos para a pesquisa informada."),
    DUPLICADA("-3", "A pessoa informada já existe no emissor."),
    OBRIGATORIO("-4", "${0}: preenchimento obrigatório."),
    MINIMO_CARACTERES("-5", "${0}: informar ao menos 3 caracteres."),
    INVALIDO("-5", "${0}: o valor informado está inválido."),
    CAMPO_CHAVE_ALTERACAO("-6", "${0}: não pode ser alterado."),
    REQUEST_INVALIDO("-5", "${0}: campo não esperado."),
    PARAMETROS_INVALIDOS("-5", "Parâmetros inválidos para pesquisa."),
    NAO_ENCONTRADO("-9", "${0} não encontrada."),
    INTERNO("-99", "Erro Interno."),
    CONDITIONAL_CHECK_FAILED(
            "-98",
            "Problema de concorrencia ao salvar o registro. Operação não realizada. O registro enviado foi salvo por outro processo."),
    REPOSITORIO_INVALIDO("-100", "${0}: Repositório inválido."),
    DOC_JA_EXISTE("-101", "${0}: Documento já existe."),
    DOC_NAO_ENCONTRADO("-102", "${0}: Documento não existe."),
    UNKNOWN("9999", "Mensagem desconhecida ou não informada ou não de negócio.");

    @Getter private final String codigo;

    @Getter private final String descricao;

    public String show(String... params) {
        var i = 0;
        String retorno = this.descricao;

        for (String param : params) {
            retorno = retorno.replace("${" + i + "}", param);
            i += 1;
        }

        return retorno;
    }

    public String showCode(String... params) {
        final var delimitador = "||";

        return this.codigo + delimitador + this.show(params);
    }
}

