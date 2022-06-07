package fes.aragon.controlador;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import fes.aragon.modelo.Clientes;
import fes.aragon.modelo.Productos;
import fes.aragon.mysql.Conexion;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;

public class ProductoController implements Initializable {

    @FXML // fx:id="tblTablaProducto"
    private TableView<Productos> tblTablaProducto; // Value injected by FXMLLoader

    @FXML // fx:id="productoID"
    private TableColumn<Productos, Integer> productoID; // Value injected by FXMLLoader

    @FXML // fx:id="productoNombre"
    private TableColumn<Productos, String> productoNombre; // Value injected by FXMLLoader

    @FXML // fx:id="productoPrecio"
    private TableColumn<Productos, Double> productoPrecio; // Value injected by FXMLLoader

    @FXML // fx:id="comandoProducto"
    private TableColumn<Productos, String> comandoProducto; // Value injected by FXMLLoader

    @FXML
    void nuevoProducto(MouseEvent event) {
    	try {
			Parent parent = FXMLLoader.load(getClass().getResource("/fes/aragon/vista/NuevoProducto.fxml"));
			Scene escena= new Scene(parent);
			Stage escenario=new Stage();
			escenario.initModality(Modality.APPLICATION_MODAL);
			escenario.initOwner(tblTablaProducto.getScene().getWindow());
			escenario.setScene(escena);
			escenario.initStyle(StageStyle.UTILITY);
			escenario.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}

    @FXML
    void refrescarProducto(MouseEvent event) {
    	this.traerDatosProd();
    }
    
    @Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		this.productoID.setCellValueFactory(new PropertyValueFactory<>("id_productos"));
		this.productoNombre.setCellValueFactory(new PropertyValueFactory<>("nombre_productos"));
		this.productoPrecio.setCellValueFactory(new PropertyValueFactory<>("precio_productos"));

		Callback<TableColumn<Productos, String>, TableCell<Productos, String>> celda = (
				TableColumn<Productos, String> parametros) -> {
			final TableCell<Productos, String> cel = new TableCell<Productos, String>() {

				@Override
				protected void updateItem(String arg0, boolean arg1) {
					// TODO Auto-generated method stub
					super.updateItem(arg0, arg1);
					if (arg1) {
						setGraphic(null);
						setText(null);
					} else {
						FontAwesomeIconView borrarIcono = new FontAwesomeIconView(FontAwesomeIcon.TRASH);
						FontAwesomeIconView modificarIcono = new FontAwesomeIconView(FontAwesomeIcon.PENCIL);
						borrarIcono.setGlyphStyle("-fx-fill:RED;-glyph-size: 18px;-fx-cursor:hand;");
						modificarIcono.setGlyphStyle("-fx-cursor:hand;" + "-glyph-size:18px;" + "-fx-fill:#0a1ce8;");
						borrarIcono.setOnMouseClicked((MouseEvent evento) -> {
							
							Alert msg = new Alert(Alert.AlertType.CONFIRMATION);
							msg.setHeaderText("Eliminar Productos");
				    		msg.setContentText("Se eliminara cualquier Registro del Producto registrado en las otras Tablas");
				    		
				    		Optional<ButtonType> resultado = msg.showAndWait();
				    		if(resultado.get() == ButtonType.OK) {
				    			Productos producto= tblTablaProducto.getSelectionModel().getSelectedItem();
								borrarProducto(producto.getId_productos());
				    		}
							
						});
						modificarIcono.setOnMouseClicked((MouseEvent evento) -> {
							Productos producto = tblTablaProducto.getSelectionModel().getSelectedItem();
							modificarProducto(producto);
						});
						HBox hbox= new HBox(borrarIcono,modificarIcono);
						hbox.setStyle("-fx-alignment:center");
						HBox.setMargin(borrarIcono, new Insets(2,2,0,3));
						HBox.setMargin(modificarIcono, new Insets(2,3,0,2));
						setGraphic(hbox);
						setText(null);
						
					}

				}

			};
			return cel;
		};
		this.comandoProducto.setCellFactory(celda);
		this.traerDatosProd();

	}
	
	private void traerDatosProd() {
		try {
			Conexion cnn =new Conexion();
			this.tblTablaProducto.getItems().clear();
			this.tblTablaProducto.setItems(cnn.todosProductos());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			Alert alerta = new Alert(Alert.AlertType.WARNING);
			alerta.setTitle("Problema en B.D");
			alerta.setHeaderText("Error en la app");
			alerta.setContentText("Consulta al fabricante please");
			alerta.showAndWait();
			e.printStackTrace();
		}
	}
	
	private void borrarProducto(int id) {
		try {
			Conexion cnn = new Conexion();
			cnn.eliminarProductos(id);
			this.traerDatosProd();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			Alert alerta = new Alert(Alert.AlertType.WARNING);
			alerta.setTitle("Problema en B.D");
			alerta.setHeaderText("Error en la app");
			alerta.setContentText("Consulta al fabricante please");
			e.printStackTrace();
		} 
		
	}
	private void modificarProducto(Productos producto) {
		try {
			FXMLLoader alta = new FXMLLoader(getClass().getResource("/fes/aragon/vista/NuevoProducto.fxml"));
			Parent parent=(Parent)alta.load();
			((NuevoProductoController)alta.getController()).modificarProductos(producto);
			Scene escena= new Scene(parent);
			Stage escenario=new Stage();
			escenario.setScene(escena);
			escenario.initStyle(StageStyle.UTILITY);
			escenario.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
