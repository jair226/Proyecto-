package fes.aragon.mysql;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import fes.aragon.modelo.Clientes;
import fes.aragon.modelo.Facturas;
import fes.aragon.modelo.FacturasProductos;
import fes.aragon.modelo.Productos;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Conexion {
	private String url = "jdbc:mysql://127.0.0.1:3306/ventas?serverTimezone=UTC";
	private String usuario = "root";
	private String psw = "datab2021";
	private Connection conexion = null;

	public Conexion() throws ClassNotFoundException, SQLException {

		Class.forName("com.mysql.cj.jdbc.Driver");
		conexion = DriverManager.getConnection(url, usuario, psw);

		// TODO Auto-generated constructor stub
	}
//CLIENTES
	//CONFIGURACION VISTA CLIENTES
	public ObservableList<Clientes> todosClientes() throws SQLException {
		ObservableList<Clientes> lista = FXCollections.observableArrayList();
		String query = "{call todosClientes()}";
		CallableStatement solicitud = conexion.prepareCall(query);
		ResultSet datos = solicitud.executeQuery();
		if (!datos.next()) {
			System.out.println("No hay datos");
		} else {
			do {
				Clientes cl = new Clientes();
				cl.setIdC(Integer.parseInt(datos.getString(1)));
				cl.setNombre(datos.getString(2));
				cl.setApellidoPaterno(datos.getString(3));
				lista.add(cl);

			} while (datos.next());
		}
		datos.close();
		solicitud.close();
		conexion.close();
		return lista;
	}
	
	public void almacenarClientes(Clientes cliente) throws SQLException {
		String query = "{call insertarClientes(?,?)}";
		CallableStatement solicitud = conexion.prepareCall(query);
		solicitud.setString(1, cliente.getNombre());
		solicitud.setString(2, cliente.getApellidoPaterno());
		solicitud.execute();
		solicitud.close();
		conexion.close();
		
		
	}
	public void eliminarClientes(int id) throws SQLException {
		String query = "{call eliminarClientes(?)}";
		CallableStatement solicitud = conexion.prepareCall(query);
		solicitud.setInt(1, id);
		solicitud.execute();
		solicitud.close();
		conexion.close();
	}
	public void modificarClientes(Clientes cliente) throws SQLException {
		String query = "{call 	modificarClientes(?,?,?)}";
		CallableStatement solicitud = conexion.prepareCall(query);
		solicitud.setInt(1, cliente.getIdC());
		solicitud.setString(2, cliente.getNombre());
		solicitud.setString(3, cliente.getApellidoPaterno());
		solicitud.execute();
		solicitud.close();
		conexion.close();
	}
	

	//FACTURAS
		//CONFIGURACION VISTA FACTURAS
	
	
	public ObservableList<Facturas> todasFacturas() throws SQLException {
		ObservableList<Facturas> listaf = FXCollections.observableArrayList();
		String query = "{call todasFacturas()}";
		CallableStatement solicitud = conexion.prepareCall(query);
		ResultSet datos = solicitud.executeQuery();
		if (!datos.next()) {
			System.out.println("No hay datos");
		} else {
			do {
				Facturas ft = new Facturas();
				ft.setId(Integer.parseInt(datos.getString(1)));
				ft.setReferencia(datos.getString(2));				
				ft.setFecha(datos.getDate(3).toLocalDate());
				Clientes cl = new Clientes(Integer.parseInt(datos.getString(4)), datos.getString(5), datos.getString(6));
				ft.setCliente(cl);
				
				listaf.add(ft);
			} while (datos.next());
		}
		datos.close();
		solicitud.close();
		conexion.close();
		return listaf;
	}
	
	public ObservableList<Clientes> buscarClientes(String patron) throws SQLException {
		ObservableList<Clientes> lista = FXCollections.observableArrayList();
		String query = "{call buscarClientes(?)}";
		CallableStatement solicitud = conexion.prepareCall(query);		
		solicitud.setString(1, patron);		
		ResultSet datos=solicitud.executeQuery();
		if (!datos.next()) {
			System.out.println("No hay datos");
		} else {
			do {
				Clientes cl = new Clientes();
				cl.setIdC(Integer.parseInt(datos.getString(1)));
				cl.setNombre(datos.getString(2));
				cl.setApellidoPaterno(datos.getString(3));
				lista.add(cl);
			} while (datos.next());

		}
		datos.close();
		solicitud.close();
		conexion.close();
		return lista;
	}
	
	public void almacenarFacturas(Facturas factura) throws SQLException {
		String query = "{call insertarFacturas(?,?,?)}";
		CallableStatement solicitud = conexion.prepareCall(query);
		solicitud.setInt(1, factura.getCliente().getIdC());
		solicitud.setString(2, factura.getReferencia());
		solicitud.setDate(3, java.sql.Date.valueOf(factura.getFecha()));
		solicitud.execute();		
		solicitud.close();
		conexion.close();
		
	}

	
	public void eliminarFacturas(int id_facturas) throws SQLException {
		String query = "{call eliminarFacturas(?)}";
		CallableStatement solicitud = conexion.prepareCall(query);
		solicitud.setInt(1, id_facturas);
		solicitud.execute();
		solicitud.close();
		conexion.close();
	}
	
	public void modificarFacturas(Facturas factura) throws SQLException {
		String query = "{call 	modificarFacturas(?,?,?,?)}";
		CallableStatement solicitud = conexion.prepareCall(query);
		
		solicitud.setInt(1, factura.getId());
		solicitud.setInt(2, factura.getCliente().getIdC());
		solicitud.setString(3, factura.getReferencia());
		solicitud.setDate(4, java.sql.Date.valueOf(factura.getFecha()));
		
		solicitud.execute();
		solicitud.close();
		conexion.close();
	}
	
	//CLIENTES
		//CONFIGURACION VISTA FacturasProductos
	
	public ObservableList<FacturasProductos> todasFacturasProductos() throws SQLException {
		ObservableList<FacturasProductos> listafp = FXCollections.observableArrayList();
		String query = "{call todasFacturasProductos()}";
		CallableStatement solicitud = conexion.prepareCall(query);
		ResultSet datos = solicitud.executeQuery();
		if (!datos.next()) {
			System.out.println("No hay datos");
		} else {
			do {
				FacturasProductos ftpd = new FacturasProductos();
				Facturas factura = new Facturas();
				Productos producto = new Productos();
				ftpd.setCantidad_facturas_productos(Double.parseDouble(datos.getString(1)));
				factura.setId(Integer.parseInt(datos.getString(2)));
				factura.setReferencia(datos.getString(3));
				factura.setFecha(datos.getDate(4).toLocalDate());
				producto.setId_productos(Integer.parseInt(datos.getString(5)));
				producto.setNombre_productos(datos.getString(6));
				producto.setPrecio_productos(Double.parseDouble(datos.getString(7)));
				ftpd.setFactura(factura);
				ftpd.setProducto(producto);
				listafp.add(ftpd);
			} while (datos.next());
		}
		datos.close();
		solicitud.close();
		conexion.close();
		return listafp;
	}
	
	//Buscar facturas
	
	public ObservableList<Facturas> buscarFacturas(String patron) throws SQLException {
		ObservableList<Facturas> lista = FXCollections.observableArrayList();
		String query = "{call buscarFacturas(?)}";
		CallableStatement solicitud = conexion.prepareCall(query);		
		solicitud.setString(1, patron);		
		ResultSet datos=solicitud.executeQuery();
		if (!datos.next()) {
			System.out.println("No hay datos");
		} else {
			do {
				Facturas fact = new Facturas();
				Clientes cl = new Clientes();
				fact.setId(Integer.parseInt(datos.getString(1)));
				fact.setReferencia(datos.getString(2));
				fact.setFecha(datos.getDate(3).toLocalDate());
				cl.setIdC(Integer.parseInt(datos.getString(4)));
				cl.setNombre(datos.getString(5));
				cl.setApellidoPaterno(datos.getString(6));
				fact.setCliente(cl);
				lista.add(fact);
			} while (datos.next());

		}
		datos.close();
		solicitud.close();
		conexion.close();
		return lista;
	}
	//Buscar Productos
	public ObservableList<Productos> buscarProductos(String patron) throws SQLException {
		ObservableList<Productos> lista = FXCollections.observableArrayList();
		String query = "{call buscarProductos(?)}";
		CallableStatement solicitud = conexion.prepareCall(query);		
		solicitud.setString(1, patron);		
		ResultSet datos=solicitud.executeQuery();
		if (!datos.next()) {
			System.out.println("No hay datos");
		} else {
			do {
				Productos prod = new Productos();
				prod.setId_productos(Integer.parseInt(datos.getString(1)));
				prod.setNombre_productos(datos.getString(2));
				prod.setPrecio_productos(Double.parseDouble(datos.getString(3)));
				lista.add(prod);
			} while (datos.next());

		}
		datos.close();
		solicitud.close();
		conexion.close();
		return lista;
	}
	public void almacenarFacturasProductos(FacturasProductos factprod) throws SQLException {
		String query = "{call insertarFacturasProductos(?,?,?)}";
		CallableStatement solicitud = conexion.prepareCall(query);
		Productos pd = new Productos();
		Facturas ft = new Facturas();
		solicitud.setInt(1,factprod.getFactura().getId());
		solicitud.setInt(2, factprod.getProducto().getId_productos());
		solicitud.setDouble(3, factprod.getCantidad_facturas_productos());
		solicitud.execute();
		solicitud.close();
		conexion.close();
	}
	
	public void eliminarFacturasProductos(int id_facturas , int id_productos) throws SQLException {
		String query = "{call eliminarFacturasProductos(?,?)}";
		CallableStatement solicitud = conexion.prepareCall(query);
		solicitud.setInt(1, id_facturas);
		solicitud.setInt(2, id_productos);
		solicitud.execute();
		solicitud.close();
		conexion.close();
	}
	public void modificarFacturasProductos(FacturasProductos factprod) throws SQLException {
		String query = "{call 	modificarFacturasProductos(?,?,?)}";
		CallableStatement solicitud = conexion.prepareCall(query);
		solicitud.setInt(1,factprod.getFactura().getId());
		solicitud.setInt(2, factprod.getProducto().getId_productos());
		solicitud.setDouble(3, factprod.getCantidad_facturas_productos());
		solicitud.execute();
		solicitud.close();
		conexion.close();
	}
	
	//CLIENTES
		//CONFIGURACION VISTA Productos
	
	
		public ObservableList<Productos> todosProductos() throws SQLException {
			ObservableList<Productos> lista = FXCollections.observableArrayList();
			String query = "{call todosProductos()}";
			CallableStatement solicitud = conexion.prepareCall(query);
			ResultSet datos = solicitud.executeQuery();
			if (!datos.next()) {
				System.out.println("No hay datos");
			} else {
				do {
					Productos prd = new Productos();
					prd.setId_productos(Integer.parseInt(datos.getString(1)));
					prd.setNombre_productos(datos.getString(2));
					prd.setPrecio_productos(Double.parseDouble(datos.getString(3)));
					lista.add(prd);

				} while (datos.next());
			}
			datos.close();
			solicitud.close();
			conexion.close();
			return lista;
		}
		
		public void almacenarProductos(Productos producto) throws SQLException {
			String query = "{call insertarProductos(?,?)}";
			CallableStatement solicitud = conexion.prepareCall(query);
			solicitud.setString(1, producto.getNombre_productos());
			solicitud.setDouble(2, producto.getPrecio_productos());
			solicitud.execute();
			solicitud.close();
			conexion.close();
			
			
		}
		public void eliminarProductos(int id_productos) throws SQLException {
			String query = "{call eliminarProductos(?)}";
			CallableStatement solicitud = conexion.prepareCall(query);
			solicitud.setInt(1, id_productos);
			solicitud.execute();
			solicitud.close();
			conexion.close();
		}
		public void modificarProductos(Productos producto) throws SQLException {
			String query = "{call 	modificarProductos(?,?,?)}";
			CallableStatement solicitud = conexion.prepareCall(query);
			solicitud.setInt(1, producto.getId_productos());
			solicitud.setString(2, producto.getNombre_productos());
			solicitud.setDouble(3, producto.getPrecio_productos());
			solicitud.execute();
			solicitud.close();
			conexion.close();
		}
		

	
}
