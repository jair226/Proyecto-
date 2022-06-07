package fes.aragon.modelo;

import java.time.LocalDate;

public class FacturasProductos {
	Facturas factura = new Facturas();
	Productos producto = new Productos();
	private Clientes cliente = new Clientes();
	private Double cantidad_facturas_productos;
	public FacturasProductos() {
		// TODO Auto-generated constructor stub
		factura = new Facturas();
		producto = new Productos();
		cliente = new Clientes();
	}
	
	
	public FacturasProductos(Facturas factura, Productos producto, Double cantidad_facturas_productos) {
		super();
		this.factura = factura;
		this.producto = producto;
		this.cantidad_facturas_productos = cantidad_facturas_productos;
	}

	
	//CLientes
	public Integer getIdC() {
		return cliente.getIdC();
	}
	
	public String getNombre() {
		return cliente.getNombre();
	}

	public String getApellido() {
		return cliente.getApellidoPaterno();
	}
	
	//Productos
	
	public Integer getId_productos() {
		return producto.getId_productos();
	}
	
	public String getNombre_productos() {
		return producto.getNombre_productos();
	}
	

	public Double getPrecio_productos() {
		return producto.getPrecio_productos();
	}
	
	//Facturas
	
	public Integer getId() {
		return factura.getId();
	}
	public String getReferencia() {
		return factura.getReferencia();
	}
	
	public LocalDate getFecha() {
		return factura.getFecha();
	}
	
	//normales
	
	public Facturas getFactura() {
		return factura;
	}
	public void setFactura(Facturas factura) {
		this.factura = factura;
	}
	public Productos getProducto() {
		return producto;
	}
	public void setProducto(Productos producto) {
		this.producto = producto;
	}
	public Double getCantidad_facturas_productos() {
		return cantidad_facturas_productos;
	}
	public void setCantidad_facturas_productos(Double cantidad_facturas_productos) {
		this.cantidad_facturas_productos = cantidad_facturas_productos;
	}
	
	
	
	
}
