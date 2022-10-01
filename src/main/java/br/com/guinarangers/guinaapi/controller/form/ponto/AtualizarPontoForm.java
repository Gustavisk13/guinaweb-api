package br.com.guinarangers.guinaapi.controller.form.ponto;

import java.util.Optional;

import javax.validation.constraints.NotNull;

import br.com.guinarangers.guinaapi.model.Ponto;
import br.com.guinarangers.guinaapi.repository.PontoRepository;

public class AtualizarPontoForm {
    @NotNull
    private Long valor;

    public Long getValor() {
        return valor;
    }

    public void setValor(Long valor) {
        this.valor = valor;
    }

    public Ponto update (Long id, PontoRepository pontoRepository){
        Ponto ponto = pontoRepository.findById(id).get();
        ponto.setValor(this.valor);

        return ponto;
    }

    public Ponto adicionar (Long id, PontoRepository pontoRepository){
        Ponto ponto = pontoRepository.findById(id).get();
        ponto.setValor(this.valor + ponto.getValor());
        return ponto;
    }

    public Optional<Object> remover (Long id, PontoRepository pontoRepository){
        Ponto ponto = pontoRepository.findById(id).get();
        if (ponto.getValor().SIZE == 0) {
            return null;
        }
        ponto.setValor(ponto.getValor() - this.valor );
        return ponto;
    }
}
