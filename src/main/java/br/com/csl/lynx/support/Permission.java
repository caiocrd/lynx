package br.com.csl.lynx.support;

public enum Permission {

	CALLCENTER ("Gerente de CallCenter"),
	ATENDENTE ("Atendente de CallCenter"),
	PRESTADORA ("Gerente da Prestadora"),
	EXECUTOR ("Executor da Prestadora"),
	DIRECAO ("Diretor"),
	REGIAO ("Gerente das Regiões"),
	ZONA_NORTE ("Gerente da Zona Norte"),
	ZONA_SUL ("Gerente da Zona Sul"),
	ZONA_LESTE ("Gerente da Zona Leste"),
	ZONA_OESTE ("Gerente da Zona Oeste");
	
	private String label;

    private Permission(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
