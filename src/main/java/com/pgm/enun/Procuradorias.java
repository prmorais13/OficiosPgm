package com.pgm.enun;

public enum Procuradorias {

	GABINETE("GABINETE"),
	SUBSTITUTO("PROC SUBSTITUTO"),
	ADMINISTRATIVA("PROC. ADMINISTRATIVA"),
	FISCAL("PROC. FISCAL"),
	RDA("PROC. RDA"),
	JUDICIAL("PROC. JUDICIAL"),
	AMBIENTE("PROC. MEIO AMBIENTE"),
	PATRIMONIAL("PROC. PATRIMONIAL"),
	ASSESSORIA("ASSESSORIA JURÍDICA");
	
	Procuradorias(String descricao){
		this.descricao = descricao;
	}
	
	private String descricao;
	
	public String getDescricao() {
		return descricao;
	}
	
}
