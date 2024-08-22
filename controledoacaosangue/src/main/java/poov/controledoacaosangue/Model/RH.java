package poov.controledoacaosangue.Model;

public enum RH {

	POSITIVO("Rh+"),
	NEGATIVO("Rh-"),
	DESCONHECIDO("Desconhecido");
	
	private String descricao;
	
	private RH(String descricao) {
		this.descricao = descricao;
	}
	
	public String getDescricao() {
		return descricao;
	}
	
}
