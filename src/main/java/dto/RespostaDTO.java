package dto;

import java.util.List;
import java.util.Map;

public class RespostaDTO<T> {
    private T dados;
    private Boolean status;
    private Map<String, List<String>> erros;

    public RespostaDTO(T dados, Boolean status, Map<String, List<String>> erros) {
        this.dados = dados;
        this.status = status;
        this.erros = erros;
    }

    public T getDados() {
        return dados;
    }
    public void setDados(T dados) {
        this.dados = dados;
    }
    public Boolean getStatus() {
        return status;
    }
    public void setStatus(Boolean status) {
        this.status = status;
    }
    public Map<String, List<String>> getErros() {
        return erros;
    }
    public void setErros(Map<String, List<String>> erros) {
        this.erros = erros;
    }
}
