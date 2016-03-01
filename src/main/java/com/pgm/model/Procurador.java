package com.pgm.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import com.pgm.enun.Procuradorias;

@Entity
public class Procurador implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	private String nome;
	private String oab;
	private Procuradorias procuradoria;
	private String email;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@NotEmpty(message = "O Nome deve ser informado!")
	@Column(length = 120, nullable = false)
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getOab() {
		return oab;
	}

	public void setOab(String oab) {
		this.oab = oab;
	}

	@NotNull(message = "A Procuradoria deve ser informada!")
	@Enumerated(EnumType.STRING)
	public Procuradorias getProcuradoria() {
		return procuradoria;
	}

	public void setProcuradoria(Procuradorias procuradoria) {
		this.procuradoria = procuradoria;
	}

	@NotEmpty(message = "Um email deve ser informado!")
	@Email(message = "Email não é válido. Informe outro!")
	@Column(length = 100, nullable = false)
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Procurador other = (Procurador) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
