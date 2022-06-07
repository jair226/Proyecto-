package fes.aragon.modelo;

import java.time.LocalDate;

public class Facturas {
	private Integer id;
	private String referencia;
	private LocalDate fecha;
	Clientes cliente = new Clientes();

	public Facturas() {
// TODO Auto-generated constructor stub
		 cliente = new Clientes();

	}

	public Facturas(Integer id, String referencia, LocalDate fecha, Clientes cliente) {
		super();
		this.id = id;
		this.referencia = referencia;
		this.fecha = fecha;
		this.cliente = cliente;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getReferencia() {
		return referencia;
	}

	public void setReferencia(String referencia) {
		this.referencia = referencia;
	}

	public LocalDate getFecha() {
		return fecha;
	}

	public void setFecha(LocalDate fecha) {
		this.fecha = fecha;
	}

	public Clientes getCliente() {
		return cliente;
	}

	public void setCliente(Clientes cliente) {
		this.cliente = cliente;
	}
	
	public Integer getIdC() {
		return cliente.getIdC();
	}
	
	public String getNombre() {
		return cliente.getNombre();
	}

	public String getApellido() {
		return cliente.getApellidoPaterno();
	}
}
