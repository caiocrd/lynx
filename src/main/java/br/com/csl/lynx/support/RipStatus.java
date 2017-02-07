package br.com.csl.lynx.support;

public enum RipStatus {

	OPEN ("Aberta"),
	EXECUTING ("Em execução"),
	EVALUATING ("Sob avaliação"),
	ADEQUATING ("Aguardando adequação"),
	REVERSING ("Aguardando estorno"),
	REVERSED ("Estornada"),
	EVALUATING_FEEDBACK ("Com feedback negativo"),
	DONE ("Aguardando finalização"),
	CLOSED ("Finalizada"),
	PAYED ("Enviada para pagamento"),
	CANCELED ("Cancelada");
	
	private String label;

    private RipStatus(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
