
package fes.aragon.controlador;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import fes.aragon.modelo.Facturas;
import fes.aragon.modelo.FacturasProductos;
import fes.aragon.modelo.Productos;
import fes.aragon.mysql.Conexion;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
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

public class FactProdController implements Initializable{

    @FXML // fx:id="tblTablaFactProd"
    private TableView<FacturasProductos> tblTablaFactProd; 

    @FXML // fx:id="facturasID_factprod"
    private TableColumn<Facturas, Integer> facturasID_factprod;

    @FXML // fx:id="cantidad_factprod"
    private TableColumn<FacturasProductos, Double> cantidad_factprod;

    @FXML // fx:id="referencia_FactProc"
    private TableColumn<Facturas, String> referencia_FactProc; 

    @FXML // fx:id="fecha_FactProc"
    private TableColumn<Facturas, LocalDate> fecha_FactProc; 

    @FXML // fx:id="Nombre_FactProct"
    private TableColumn<Productos, String> Nombre_FactProct; 

    @FXML // fx:id="Precio_Producto"
    private TableColumn<Productos, Double> Precio_Producto; 

    @FXML // fx:id="comandoEditar_factprod"
    private TableColumn<FacturasProductos, String> comandoEditar_factprod;

    @FXML
    void nuevaFactProd(MouseEvent event) {
    	try {
			Parent parent = FXMLLoader.load(getClass().getResource("/fes/aragon/vista/NuevaFacturaProducto.fxml"));
			Scene escena= new Scene(parent);
			Stage escenario=new Stage();
			escenario.initModality(Modality.APPLICATION_MODAL);
			escenario.initOwner(tblTablaFactProd.getScene().getWindow());
			escenario.setScene(escena);
			escenario.initStyle(StageStyle.UTILITY);
			escenario.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

    }

    @FXML
    void refrescarFactProd(MouseEvent event) {
    	this.traerDatos();
    }
    
    @Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		this.facturasID_factprod.setCellValueFactory(new PropertyValueFactory<>("id"));
		this.cantidad_factprod.setCellValueFactory(new PropertyValueFactory<>("cantidad_facturas_productos"));
		this.referencia_FactProc.setCellValueFactory(new PropertyValueFactory<>("referencia"));
		this.fecha_FactProc.setCellValueFactory(new PropertyValueFactory<>("fecha"));
		this.Nombre_FactProct.setCellValueFactory(new PropertyValueFactory<>("nombre_productos"));
		this.Precio_Producto.setCellValueFactory(new PropertyValueFactory<>("precio_productos"));
		
		
		Callback<TableColumn<FacturasProductos, String>, TableCell<FacturasProductos, String>> celda = (
				TableColumn<FacturasProductos, String> parametros) -> {
			final TableCell<FacturasProductos, String>cel = new TableCell<FacturasProductos, String>() {

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
							FacturasProductos facturap= tblTablaFactProd.getSelectionModel().getSelectedItem();
							borrarFacturas(facturap.getFactura().getId(),facturap.getProducto().getId_productos());
						});
						modificarIcono.setOnMouseClicked((MouseEvent evento) -> {
							FacturasProductos facturap= tblTablaFactProd.getSelectionModel().getSelectedItem();
							modificarFacturasProductos(facturap);
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
		this.comandoEditar_factprod.setCellFactory(celda);
		this.traerDatos();
	}
    private void traerDatos() {
		try {
			Conexion cnn =new Conexion();
			this.tblTablaFactProd.getItems().clear();
			this.tblTablaFactProd.setItems(cnn.todasFacturasProductos());
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
    
    private void borrarFacturas(int id_facturas , int id_productos) {
		try {
			Conexion cnn = new Conexion();
			cnn.eliminarFacturasProductos(id_facturas, id_productos);
			this.traerDatos();
			
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
    
    private void modificarFacturasProductos(FacturasProductos factprod) {
		try {
			FXMLLoader alta = new FXMLLoader(getClass().getResource("/fes/aragon/vista/ModificarFacturaProducto.fxml"));
			Parent parent=(Parent)alta.load();
			((ModificarFacturaProductoController)alta.getController()).modificarFacturasProductos(factprod);
			Scene escena= new Scene(parent);
			Stage escenario=new Stage();
			escenario.initModality(Modality.APPLICATION_MODAL);
			escenario.initOwner(tblTablaFactProd.getScene().getWindow());
			escenario.setScene(escena);
			escenario.initStyle(StageStyle.UTILITY);
			escenario.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
