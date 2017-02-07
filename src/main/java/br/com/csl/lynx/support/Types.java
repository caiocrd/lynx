package br.com.csl.lynx.support;

public enum Types {

	OPEN ("Abertas"),
	CANCEL ("Canceladas"),
	RECEIVE ("Recebidas"),
	EXECUTE ("Executadas"),
	EVALUATE ("Avaliadas"),
	ADEQUATE ("Adequadas"),
	NEGATIVE_FEEDBACK ("Com Fdbk negativo"),
	REVERSE ("Estornadas"),
	CLOSE ("Finalizadas");
	
	private String label;

    private Types(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
