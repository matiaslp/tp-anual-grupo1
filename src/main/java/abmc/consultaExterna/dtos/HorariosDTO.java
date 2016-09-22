package abmc.consultaExterna.dtos;

class HorariosDTO {
	int diaSemana;
	int horaDesde;
	int minutosDesde;
	int horaHasta;
	int minutosHasta;

	public int getDiaSemana() {
		return diaSemana;
	}

	public void setDiaSemana(int diaSemana) {
		this.diaSemana = diaSemana;
	}

	public int getHoraDesde() {
		return horaDesde;
	}

	public void setHoraDesde(int horaDesde) {
		this.horaDesde = horaDesde;
	}

	public int getMinutosDesde() {
		return minutosDesde;
	}

	public void setMinutosDesde(int minutosDesde) {
		this.minutosDesde = minutosDesde;
	}

	public int getHoraHasta() {
		return horaHasta;
	}

	public void setHoraHasta(int horaHasta) {
		this.horaHasta = horaHasta;
	}

	public int getMinutosHasta() {
		return minutosHasta;
	}

	public void setMinutosHasta(int minutosHasta) {
		this.minutosHasta = minutosHasta;
	}

}
