package fes.aragon.modelo;

public class Clientes {
	private Integer id_clientes;
	private String nombre;
	private String apellidoPaterno;
	public Clientes() {
		// TODO Auto-generated constructor stub
	}
	public Clientes(Integer id_clientes, String nombre, String apellidoPaterno) {
		super();
		this.id_clientes = id_clientes;
		this.nombre = nombre;
		this.apellidoPaterno = apellidoPaterno;
	}
	
	public Integer getIdC() {
		return id_clientes;
	}
	public void setIdC(Integer id_clientes) {
		this.id_clientes = id_clientes;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getApellidoPaterno() {
		return apellidoPaterno;
	}
	public void setApellidoPaterno(String apellidoPaterno) {
		this.apellidoPaterno = apellidoPaterno;
	}


}
