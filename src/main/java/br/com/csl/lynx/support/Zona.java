package br.com.csl.lynx.support;

public enum Zona {
	
	ZONA_NORTE ("Zona Norte"),
	ZONA_SUL ("Zona Sul"),
	ZONA_LESTE ("Zona Leste"),
	ZONA_OESTE ("Zona Oeste");
	
	private String label;

    private Zona(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
