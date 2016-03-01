package com.pgm.controller;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.pgm.model.Grupo;
import com.pgm.repository.Grupos;
import com.pgm.util.jpa.Transacional;
import com.pgm.util.message.FacesMessages;

@Named
@ViewScoped
public class GrupoBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private Grupo grupoEdicao;
	private List<Grupo> todosGrupos;

	@Inject
	private Grupos grupos;

	@Inject
	private FacesMessages messages;

	@PostConstruct
	private void iniciar() {
		this.grupoEdicao = new Grupo();
	}

	@Transacional
	public void gravar() {
		this.grupos.salvar(this.grupoEdicao);
		this.grupoEdicao = new Grupo();
		this.consulta();

		this.messages.info("Grupo salvo com sucesso!");
	}

	@Transacional
	public void excluir(Grupo grupo) {
		this.grupos.excluir(grupo);
		this.consulta();

		this.messages.info("Grupo excluído com sucesso!");
	}

	public void consulta() {
		this.todosGrupos = grupos.todos();
	}

	public Grupo getGrupoEdicao() {
		return grupoEdicao;
	}

	public void setGrupoEdicao(Grupo grupoEdicao) {
		this.grupoEdicao = grupoEdicao;
	}

	public List<Grupo> getTodosGrupos() {
		return todosGrupos;
	}

}
