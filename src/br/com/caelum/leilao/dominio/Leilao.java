package br.com.caelum.leilao.dominio;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Leilao {

	private String descricao;
	private List<Lance> lances;
	
	public Leilao(String descricao) {
		this.descricao = descricao;
		this.lances = new ArrayList<Lance>();
	}
	
	public void propoe(Lance lance) {
		int total = qtdDeLancesDo(lance.getUsuario());

		if(lances.isEmpty() || (!ultimoLanceDado().getUsuario().equals(lance.getUsuario()) &&  total < 5)) {
			lances.add(lance);
		}
	}

	private boolean podeDarLance(Usuario usuario) {
		return !ultimoLanceDado().getUsuario().equals(usuario)
				&& qtdDeLancesDo(usuario) < 5;
	}

	private int qtdDeLancesDo(Usuario usuario) {
		int total = 0;
		for(Lance l : lances) {
			if(l.getUsuario().equals(usuario)) total++;
		}
		return total;
	}

	private Lance ultimoLanceDado() {
		return lances.get(lances.size()-1);
	}

	public String getDescricao() {
		return descricao;
	}

	public List<Lance> getLances() {
		return Collections.unmodifiableList(lances);
	}

	public List<Double> getValorLances() {
		List<Double> valores = new ArrayList<>();
		lances.forEach(lance -> valores.add(lance.getValor()));
		return valores;
	}

	public void dobraLance(Usuario usuario){
		if(jaDeuLance(usuario) && podeDarLance(usuario)){
			propoe(new Lance(usuario, lances.get(ultimoLanceDo(usuario)).getValor()*2));
		}
	}

	private int ultimoLanceDo(Usuario usuario) {
		int ultimoLance = 0;
		for(Lance l : lances) {
			if(l.getUsuario().equals(usuario)){
				ultimoLance = lances.indexOf(l);
			}
		}
		return ultimoLance;
	}

	private boolean jaDeuLance(Usuario usuario) {
		for(Lance l : lances) {
			if(l.getUsuario().equals(usuario)){
				return true;
			}
		}
		return false;
	}
}
