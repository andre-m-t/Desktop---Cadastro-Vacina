package poov.controledoacaosangue.Model;

public enum TipoSanguineo {

	A("A"),
	B("B"),
	AB("AB"),
	O("O"),
	DESCONHECIDO("Desconhecida");
	
	private String descricao;
	
	private TipoSanguineo(String descricao) {
		this.descricao = descricao;
	}
	
	public String getDescricao() {
		return descricao;
	}
}
