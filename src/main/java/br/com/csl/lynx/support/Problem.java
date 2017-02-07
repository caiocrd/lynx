package br.com.csl.lynx.support;

public enum Problem {

	LAMP_OFF ("Lâmpada Apagada"),
	LAMP_ON ("Lâmpada Acesa"),
	LAMP_BLINK ("Lâmpada Piscando"),
	BROKEN_MOUNT ("Luminária Quebrada"),
	FALLING_MOUNT ("Luminária Caindo"),
	TURNED_MOUNT ("Luminária Virada"),
	FULL_MAINTENANCE ("Manutenção Geral"),
	NEW_MOUNTS ("Implantação de Luminárias"),
	SPORT_SQUARE_MAINTENANCE ("Manutenção Quadra de Esportes"),
	SQUARE_MAINTENANCE ("Manutenção de Praças"),
	PETAL_LIGHTPOLE ("Poste de Pétalas (poste de canteiro central)"),
	POWER_RAISE ("Aumento de Potência"),
	REFLECTOR_ON ("Refletor Aceso"),
	REFLECTOR_OFF ("Refletor Apagado"),
	REFLECTOR_BROKEN ("Refletor Quebrado"),
	NETWORK_EXPANSION ("Expansão da Rede (Implantação de Poste)"),
	SOCCER_FIELD_MAINTENANCE ("Manutenção em campo de futebol");
	
	private String label;

    private Problem(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
