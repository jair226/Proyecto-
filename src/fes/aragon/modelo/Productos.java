package fes.aragon.modelo;

public class Productos {
	private Integer id_productos;
	private String nombre_productos;
	private Double precio_productos;
	public Productos() {
		// TODO Auto-generated constructor stub
	}
	public Productos(Integer id_productos, String nombre_productos, Double precio_productos) {
		super();
		this.id_productos = id_productos;
		this.nombre_productos = nombre_productos;
		this.precio_productos = precio_productos;
	}
	public Integer getId_productos() {
		return id_productos;
	}
	public void setId_productos(Integer id_productos) {
		this.id_productos = id_productos;
	}
	public String getNombre_productos() {
		return nombre_productos;
	}
	public void setNombre_productos(String nombre_productos) {
		this.nombre_productos = nombre_productos;
	}
	public Double getPrecio_productos() {
		return precio_productos;
	}
	public void setPrecio_productos(Double precio_productos) {
		this.precio_productos = precio_productos;
	}
	
}
