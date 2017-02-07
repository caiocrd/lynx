package br.com.csl.lynx.support;

public enum Movement {

	OPEN ("Aberta"),
	RECEIVE ("Recebida para execução"),
	PRINT ("Impressa"),
	EXECUTE ("Executada, aguardando avaliação"),
	EVALUATE ("Avaliada"),
	TO_ADEQUATE ("Enviada para adequação"),
	ADEQUATE ("Adequada"),
	NEGATIVE_FEEDBACK ("Feedback negativo recebido"),
	DONE ("Enviada para finalização"),
	REVERSIBLE ("Passível de estorno"),
	REVERSE ("Estornada, aguardando avaliação"),
	CANCEL ("Cancelada"),
	CLOSE ("Finalizada"),
	PAY ("Enviada para pagamento");
	
	private String label;

    private Movement(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
