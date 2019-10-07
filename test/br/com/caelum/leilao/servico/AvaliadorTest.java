package br.com.caelum.leilao.servico;

import br.com.caelum.leilao.builder.CriadorDeLeilao;
import br.com.caelum.leilao.dominio.Lance;
import br.com.caelum.leilao.dominio.Leilao;
import br.com.caelum.leilao.dominio.Usuario;
import br.com.caelum.leilao.servico.Avaliador;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;


public class AvaliadorTest {

    private Avaliador leiloeiro;
    private Usuario joao;
    private Usuario jose;
    private Usuario maria;
    private Usuario bruna;

    @Before
    public void criaAvaliador() {
        this.leiloeiro = new Avaliador();
        this.joao = new Usuario("Joao");
        this.jose = new Usuario("José");
        this.maria = new Usuario("Maria");
        this.bruna = new Usuario("Bruna");
    }

    @Test(expected=RuntimeException.class)
    public void naoDeveAvaliarLeiloesSemNenhumLanceDado() {
        Leilao leilao = new CriadorDeLeilao()
                .para("Playstation 3 Novo")
                .constroi();

        leiloeiro.avalia(leilao);
    }

    @Test
    public void deveEntenderLancesEmOrdemCrescente() {
        // cenario: 3 lances em ordem crescente
        Leilao leilao = new CriadorDeLeilao().para("Playstation 3 Novo")
                .lance(joao, 100.0)
                .lance(maria, 200.0)
                .lance(joao, 300.0)
                .lance(maria, 400.0)
                .constroi();

        // executando a acao
        leiloeiro.avalia(leilao);

        // comparando a saida com o esperado
        double maiorEsperado = 400;
        double menorEsperado = 100;

        assertEquals(maiorEsperado, leiloeiro.getMaiorLance(), 0.0001);
        assertEquals(menorEsperado, leiloeiro.getMenorLance(), 0.0001);
    }

    @Test
    public void deveEntenderLancesEmOrdemDecrescente() {
        // cenario: 3 lances em ordem decrescente
        Leilao leilao = new CriadorDeLeilao().para("Playstation 3 Novo")
                .lance(joao, 400.0)
                .lance(maria, 300.0)
                .lance(joao, 200.0)
                .lance(maria, 100.0)
                .constroi();

        // executando a acao
        leiloeiro.avalia(leilao);

        // comparando a saida com o esperado
        double maiorEsperado = 400;
        double menorEsperado = 100;

        assertEquals(maiorEsperado, leiloeiro.getMaiorLance(), 0.0001);
        assertEquals(menorEsperado, leiloeiro.getMenorLance(), 0.0001);
    }

    @Test
    public void deveEntenderLancesEmOrdemAleatoria() {
        // cenario: lances em ordem aleatoria
        Leilao leilao = new CriadorDeLeilao().para("Playstation 3 Novo")
                .lance(joao, 400.0)
                .lance(maria, 200.0)
                .lance(jose, 250.0)
                .lance(maria, 300.0)
                .lance(bruna, 700.0)
                .lance(joao, 100.0)
                .lance(maria, 200.0)
                .lance(jose, 650.0)
                .lance(maria, 900.0)
                .lance(bruna, 600.0)
                .constroi();

        // executando a acao
        leiloeiro.avalia(leilao);

        // comparando a saida com o esperado
        double maiorEsperado = 900;
        double menorEsperado = 100;

        assertEquals(maiorEsperado, leiloeiro.getMaiorLance(), 0.0001);
        assertEquals(menorEsperado, leiloeiro.getMenorLance(), 0.0001);
    }

    @Test
    public void deveEntenderLeilaoComUmLance() {
        // cenario: 1 lance
        Leilao leilao = new CriadorDeLeilao().para("Playstation 3 Novo")
                .lance(joao, 300.0)
                .constroi();

        // executando a acao
        leiloeiro.avalia(leilao);

        // comparando a saida com o esperado
        double valorLance = 300;

        assertEquals(valorLance, leiloeiro.getMaiorLance(), 0.0001);
        assertEquals(valorLance, leiloeiro.getMenorLance(), 0.0001);
    }

    @Test
    public void deveCalcularMediaDosLances() {
        // cenario: 3 lances em ordem crescente
        Leilao leilao = new CriadorDeLeilao().para("Playstation 3 Novo")
                .lance(joao, 400.0)
                .lance(maria, 200.0)
                .lance(maria, 300.0)
                .constroi();

        // executando a acao
        leiloeiro.avalia(leilao);

        // comparando a saida com o esperado
        double mediaEsperada = 300;

        assertEquals(mediaEsperada, leiloeiro.getMedia(), 0.0001);
    }

    @Test
    public void devePegarMaioresComMaisDe3Elementos() {
        // cenario: pegar os maiores de lista com mais de 3 elementos
        Leilao leilao = new CriadorDeLeilao().para("Playstation 3 Novo")
                .lance(joao, 400.0)
                .lance(maria, 200.0)
                .lance(jose, 250.0)
                .lance(maria, 300.0)
                .lance(bruna, 780.0)
                .lance(joao, 100.0)
                .lance(maria, 200.0)
                .lance(jose, 600.0)
                .lance(bruna, 800.0)
                .constroi();

        // executando a acao
        leiloeiro.avalia(leilao);

        // comparando a saida com o esperado
        assertEquals(3, leiloeiro.getTop3().size());
        assertEquals(800.0, leiloeiro.getTop3().get(0).getValor(), 0.0001);
        assertEquals(780.0, leiloeiro.getTop3().get(1).getValor(), 0.0001);
        assertEquals(600.0, leiloeiro.getTop3().get(2).getValor(), 0.0001);
    }

    @Test
    public void devePegarMaioresComMenosDe3Elementos() {
        // cenario: pegar os maiores de lista com menos de 3 elementos
        Leilao leilao = new CriadorDeLeilao().para("Playstation 3 Novo")
                .lance(joao, 400.0)
                .lance(bruna, 600.0)
                .constroi();

        // executando a acao
        leiloeiro.avalia(leilao);

        // comparando a saida com o esperado
        assertEquals(2, leiloeiro.getTop3().size());
        assertEquals(600.0, leiloeiro.getTop3().get(0).getValor(), 0.0001);
        assertEquals(400.0, leiloeiro.getTop3().get(1).getValor(), 0.0001);
    }
}
