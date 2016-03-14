package com.pgm.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "oficio")
public class Oficio implements Serializable {

	private static final long serialVersionUID = 1L;

	// GERAÇÃO OFICIO
	private Long id;
	private Secretaria destino;
	private Procurador procurador;
	private Date dataCriacao;
	private String status;

	// CADASTRO OFICIOS
	private Date dataCadastro;
	private String setorOrigem;
	private String interessado;
	private String tipoDocumento;
	private String assunto;

	// ENVIO
	private Date dataEnvio;
	private String responsavelEnvio;

	// RECEBIMENTO
	private Date dataRecebimento;
	private String responsavelRecebimento;
	private Date dataPrazo;
	private int dias;
	private byte[] docPdf;


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@ManyToOne
	@JoinColumn(name = "secretaria_id", nullable = false)
	@NotNull(message = "ATENÇÃO: A Secretaria de destino deve ser informada!")
	public Secretaria getDestino() {
		return destino;
	}

	public void setDestino(Secretaria destino) {
		this.destino = destino;
	}

	@ManyToOne
	@JoinColumn(name = "procurador_id", nullable = false)
	@NotNull(message = "ATENÇÃO: O Procurador responsável deve ser informado!")
	public Procurador getProcurador() {
		return procurador;
	}

	public void setProcurador(Procurador procurador) {
		this.procurador = procurador;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "data_criacao", nullable = false)
	public Date getDataCriacao() {
		return dataCriacao;
	}

	public void setDataCriacao(Date dataCriacao) {
		this.dataCriacao = dataCriacao;
	}

	@Column(length = 15, nullable = false)
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "data_cadastro")
	public Date getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	@Column(name = "setor_origem", length = 60)
	public String getSetorOrigem() {
		return setorOrigem;
	}

	public void setSetorOrigem(String setorOrigem) {
		this.setorOrigem = setorOrigem;
	}

	@Column(length = 60)
	public String getInteressado() {
		return interessado;
	}

	public void setInteressado(String interessado) {
		this.interessado = interessado;
	}

	@Column(name = "tipo_documento", length = 60)
	public String getTipoDocumento() {
		return tipoDocumento;
	}

	public void setTipoDocumento(String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

	public String getAssunto() {
		return assunto;
	}

	public void setAssunto(String assunto) {
		this.assunto = assunto;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "data_envio")
	public Date getDataEnvio() {
		return dataEnvio;
	}

	public void setDataEnvio(Date dataEnvio) {
		this.dataEnvio = dataEnvio;
	}

	@Column(name = "responsavel_envio", length = 60)
	public String getResponsavelEnvio() {
		return responsavelEnvio;
	}

	public void setResponsavelEnvio(String responsavelEnvio) {
		this.responsavelEnvio = responsavelEnvio;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "data_recebimento")
	public Date getDataRecebimento() {
		return dataRecebimento;
	}

	public void setDataRecebimento(Date dataRecebimento) {
		this.dataRecebimento = dataRecebimento;
	}

	@Column(name = "responsavel_recebimento", length = 60)
	public String getResponsavelRecebimento() {
		return responsavelRecebimento;
	}

	public void setResponsavelRecebimento(String responsavelRecebimento) {
		this.responsavelRecebimento = responsavelRecebimento;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "data_prazo")
	public Date getDataPrazo() {
		this.numDias();
		return dataPrazo;
	}

	public void setDataPrazo(Date dataPrazo) {
		this.dataPrazo = dataPrazo;
	}

	@Transient
	public int getDias() {
		return dias;
	}

	public void setDias(int dias) {
		this.dias = dias;
	}

	@Lob
	@Column(name = "doc_pdf", columnDefinition = "longblob")
	public byte[] getDocPdf() {
		return docPdf;
	}

	public void setDocPdf(byte[] docPdf) {
		this.docPdf = docPdf;
	}
	
	public void numDias(){
		
		if(this.dataPrazo != null){
			Long dataPrazoEntrega = this.dataPrazo.getTime();
			Long dataAtual = new Date().getTime();
			Long difData = dataPrazoEntrega - dataAtual;
			this.dias = (int) ((difData/86400000) + 1);
		}
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
		Oficio other = (Oficio) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
