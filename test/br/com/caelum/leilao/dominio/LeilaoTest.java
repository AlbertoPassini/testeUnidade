package br.com.caelum.leilao.dominio;

import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class LeilaoTest {

    @Test
    public void deveReceberUmLance() {
        Leilao leilao = new Leilao("Macbook Pro 15");
        assertEquals(0, leilao.getLances().size());

        leilao.propoe(new Lance(new Usuario("Steve Jobs"), 2000));

        assertEquals(1, leilao.getLances().size());
        assertEquals(2000.0, leilao.getLances().get(0).getValor(), 0.00001);
    }

    @Test
    public void deveReceberVariosLances() {
        Leilao leilao = new Leilao("Macbook Pro 15");
        leilao.propoe(new Lance(new Usuario("Steve Jobs"), 2000));
        leilao.propoe(new Lance(new Usuario("Steve Wozniak"), 3000));

        assertEquals(2, leilao.getLances().size());
        assertEquals(2000.0, leilao.getLances().get(0).getValor(), 0.00001);
        assertEquals(3000.0, leilao.getLances().get(1).getValor(), 0.00001);
    }

    @Test
    public void naoDeveAceitarDoisLancesSeguidosDoMesmoUsuario() {
        Leilao leilao = new Leilao("Macbook Pro 15");
        Usuario steveJobs = new Usuario("Steve Jobs");

        leilao.propoe(new Lance(steveJobs, 2000.0));
        leilao.propoe(new Lance(steveJobs, 3000.0));

        assertEquals(1, leilao.getLances().size());
        assertEquals(2000.0, leilao.getLances().get(0).getValor(), 0.00001);
    }

    @Test
    public void naoDeveAceitarMaisDoQue5LancesDeUmMesmoUsuario() {
        Leilao leilao = new Leilao("Macbook Pro 15");
        Usuario steveJobs = new Usuario("Steve Jobs");
        Usuario billGates = new Usuario("Bill Gates");

        leilao.propoe(new Lance(steveJobs, 2000));
        leilao.propoe(new Lance(billGates, 3000));
        leilao.propoe(new Lance(steveJobs, 3000));
        leilao.propoe(new Lance(billGates, 3000));
        leilao.propoe(new Lance(steveJobs, 4000));
        leilao.propoe(new Lance(billGates, 3000));
        leilao.propoe(new Lance(steveJobs, 5000));
        leilao.propoe(new Lance(billGates, 3000));
        leilao.propoe(new Lance(steveJobs, 6000));
        leilao.propoe(new Lance(billGates, 999));
        leilao.propoe(new Lance(steveJobs, 7000));

        assertEquals(10, leilao.getLances().size());
        int ultimo = leilao.getLances().size() - 1;
        assertEquals(999, leilao.getLances().get(ultimo).getValor(), 0.00001);
    }
/*

    @Test
    public void verificaPosicaoDoUltimoLanceUsuario() {
        Leilao leilao = new Leilao("Macbook Pro 15");

        Usuario steveJobs = new Usuario("Steve Jobs");
        Usuario steveWozniak = new Usuario("Steve Wozniak");
        Usuario billGates = new Usuario("Bill Gates");

        leilao.propoe(new Lance(steveJobs, 2000));
        leilao.propoe(new Lance(billGates, 3000));
        leilao.propoe(new Lance(steveJobs, 3000));
        leilao.propoe(new Lance(steveWozniak, 3000));
        leilao.propoe(new Lance(steveJobs, 4000));
        leilao.propoe(new Lance(steveWozniak, 3000));
        leilao.propoe(new Lance(billGates, 5000));
        leilao.propoe(new Lance(steveWozniak, 6000));

        assertEquals(4, leilao.ultimoLanceDo(steveJobs));
    }
*/

    @Test
    public void verificaDobroUltimoLance() {
        Leilao leilao = new Leilao("Macbook Pro 15");

        Usuario steveJobs = new Usuario("Steve Jobs");
        Usuario steveWozniak = new Usuario("Steve Wozniak");

        leilao.propoe(new Lance(steveJobs, 2000));
        leilao.propoe(new Lance(steveWozniak, 3000));

        leilao.dobraLance(steveJobs);

        assertEquals(3, leilao.getLances().size());
        assertEquals(4000.0, leilao.getLances().get(2).getValor(), 0.00001);
    }

    @Test
    public void verificaDobroQuandoNaoHaLance() {
        Leilao leilao = new Leilao("Macbook Pro 15");

        Usuario steveJobs = new Usuario("Steve Jobs");
        Usuario steveWozniak = new Usuario("Steve Wozniak");

        leilao.propoe(new Lance(steveWozniak, 3000));

        leilao.dobraLance(steveJobs);

        assertEquals(1, leilao.getLances().size());
    }
}
