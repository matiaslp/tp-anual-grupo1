package ar.edu.utn.dds.grupouno.abmc.consultaExterna.dtos;

import java.util.ArrayList;

class Banco_DTO {
	String banco;
	long x;
	long y;
	String sucursal;
	String gerente;
	ArrayList<String> servicios;

	public String getBanco() {
		return banco;
	}

	public void setBanco(String banco) {
		this.banco = banco;
	}

	public long getX() {
		return x;
	}

	public void setX(long x) {
		this.x = x;
	}

	public long getY() {
		return y;
	}

	public void setY(long y) {
		this.y = y;
	}

	public String getSucursal() {
		return sucursal;
	}

	public void setSucursal(String sucursal) {
		this.sucursal = sucursal;
	}

	public String getGerente() {
		return gerente;
	}

	public void setGerente(String gerente) {
		this.gerente = gerente;
	}

	public ArrayList<String> getServicios() {
		return servicios;
	}

	public void setServicios(ArrayList<String> servicios) {
		this.servicios = servicios;
	}

}
