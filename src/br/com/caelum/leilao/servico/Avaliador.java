package br.com.caelum.leilao.servico;

import br.com.caelum.leilao.dominio.Lance;
import br.com.caelum.leilao.dominio.Leilao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Avaliador {
    private double maiorDeTodos = Double.NEGATIVE_INFINITY;
    private double menorDeTodos = Double.POSITIVE_INFINITY;
    private double media = new Double(0.0);
    private List<Lance> top3 = new ArrayList<>();

    public void avalia(Leilao leilao) {

        if(leilao.getLances().size() ==0)
            throw new RuntimeException("Não é possível avaliar um leilão sem lances");

        //Pega o maior e o menor
        for(Lance lance : leilao.getLances()) {
            if(lance.getValor() > maiorDeTodos) maiorDeTodos = lance.getValor();
            if(lance.getValor() < menorDeTodos) menorDeTodos = lance.getValor();
        }

        //Calcula a média
        if(leilao.getValorLances().isEmpty()) {
            media = 0;
        }
        else {
            media = leilao.getValorLances().stream().mapToDouble(Double::doubleValue).average().getAsDouble();
        }

        //Pega o top3
        selecionaTop3(leilao);
    }

    private void selecionaTop3(Leilao leilao) {
        top3 = new ArrayList<Lance>(leilao.getLances());
        Collections.sort(top3, (o1, o2) -> {
            if (o1.getValor() < o2.getValor()) return 1;
            if (o1.getValor() > o2.getValor()) return -1;
            return 0;
        });
        top3 = top3.subList(0, top3.size() > 3 ? 3 : top3.size());
    }

    public double getMaiorLance() { return maiorDeTodos; }
    public double getMenorLance() { return menorDeTodos; }
    public double getMedia() { return media; }
    public List<Lance> getTop3() { return this.top3; }
}
