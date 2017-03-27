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
	ZONA_OESTE ("Gerente da Zona Oeste"),
	CALLCENTER_SIEP ("Gerente de CallCenter SIEP"),
	ATENDENTE_SIEP ("Atendente de CallCenter SIEP"),
	PRESTADORA_SIEP ("Gerente da Prestadora SIEP"),
	EXECUTOR_SIEP ("Executor da Prestadora SIEP"),
	CALLCENTER_SVPA ("Gerente de CallCenter SVPA"),
	ATENDENTE_SVPA ("Atendente de CallCenter SVPA"),
	PRESTADORA_SVPA ("Gerente da Prestadora SVPA"),
	EXECUTOR_SVPA ("Executor da Prestadora SVPA"),
	CALLCENTER_SLP ("Gerente de CallCenter SLP"),
	ATENDENTE_SLP ("Atendente de CallCenter SLP"),
	PRESTADORA_SLP ("Gerente da Prestadora SLP"),
	EXECUTOR_SLP ("Executor da Prestadora SLP"),
	CALLCENTER_SLC ("Gerente de CallCenter SLC"),
	ATENDENTE_SLC ("Atendente de CallCenter SLC"),
	PRESTADORA_SLC ("Gerente da Prestadora SLC"),
	EXECUTOR_SLC ("Executor da Prestadora SLC");
	
	private String label;

    private Permission(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
