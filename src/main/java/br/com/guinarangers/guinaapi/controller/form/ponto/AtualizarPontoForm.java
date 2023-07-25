package br.com.guinarangers.guinaapi.controller.form.ponto;

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
        Ponto ponto = pontoRepository.findByusuario_id(id).get();
        ponto.setValor(this.valor + ponto.getValor());
        return ponto;
    }

    public Ponto remover (Long id, PontoRepository pontoRepository){
        Ponto ponto = pontoRepository.findByusuario_id(id).get();
        if (ponto.getValor() == 0 || ponto.getValor() < this.valor){
            return null;
        }
        ponto.setValor(ponto.getValor() - this.valor );
        return ponto;
    }
}
