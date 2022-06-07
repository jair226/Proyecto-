package fes.aragon.controlador;


import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ResourceBundle;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import fes.aragon.modelo.Clientes;
import fes.aragon.modelo.Facturas;
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


public class FacturasController implements Initializable {
	

    @FXML // fx:id="tblTablaFacturas"
    private TableView<Facturas> tblTablaFacturas; // Value injected by FXMLLoader
    

    @FXML // fx:id="facturasID"
    private TableColumn<Facturas, Integer> facturasID; // Value injected by FXMLLoader

    @FXML // fx:id="facturasReferencia"
    private TableColumn<Facturas, String> facturasReferencia; // Value injected by FXMLLoader

    @FXML // fx:id="facturasFechas"
    private TableColumn<Facturas, LocalDate>facturasFechas; // Value injected by FXMLLoader
    
    @FXML // fx:id="nombreCFact"
    private TableColumn<Clientes, String> nombreCFact; // Value injected by FXMLLoader

    @FXML // fx:id="apellidoCFact"
    private TableColumn<Clientes, String> apellidoCFact; // Value injected by FXMLLoader

    @FXML // fx:id="comandoEditFact"
    private TableColumn<Facturas, String> comandoEditFact; // Value injected by FXMLLoader

    @FXML
    void nuevaFactura(MouseEvent event) {
    	try {
			Parent parent = FXMLLoader.load(getClass().getResource("/fes/aragon/vista/NuevaFacturaVD.fxml"));
			Scene escena= new Scene(parent);
			Stage escenario=new Stage();
			escenario.initModality(Modality.APPLICATION_MODAL);
			escenario.initOwner(tblTablaFacturas.getScene().getWindow());
			escenario.setScene(escena);
			escenario.initStyle(StageStyle.UTILITY);
			escenario.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
    
    

    @FXML
    void refrescarFactura(MouseEvent event) {
    	this.traerDatos();
    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		this.facturasID.setCellValueFactory(new PropertyValueFactory<>("id"));
		this.facturasReferencia.setCellValueFactory(new PropertyValueFactory<>("referencia"));
		this.facturasFechas.setCellValueFactory(new PropertyValueFactory<>("fecha"));
		this.nombreCFact.setCellValueFactory(new PropertyValueFactory<>("nombre"));
		this.apellidoCFact.setCellValueFactory(new PropertyValueFactory<>("apellido"));
		
		
		
		Callback<TableColumn<Facturas, String>, TableCell<Facturas, String>> celda = (
				TableColumn<Facturas, String> parametros) -> {
			final TableCell<Facturas, String>cel = new TableCell<Facturas, String>() {

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
							Facturas factura= tblTablaFacturas.getSelectionModel().getSelectedItem();
							borrarFacturas(factura.getId());
						});
						modificarIcono.setOnMouseClicked((MouseEvent evento) -> {
							Facturas factura= tblTablaFacturas.getSelectionModel().getSelectedItem();
							modificarFacturas(factura);
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
		this.comandoEditFact.setCellFactory(celda);
		this.traerDatos();
	}
	
	private void traerDatos() {
		try {
			Conexion cnn =new Conexion();
			this.tblTablaFacturas.getItems().clear();
			this.tblTablaFacturas.setItems(cnn.todasFacturas());
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
	
	private void borrarFacturas(int id_facturas) {
		try {
			Conexion cnn = new Conexion();
			cnn.eliminarFacturas(id_facturas);
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
	
	private void modificarFacturas(Facturas factura) {
		try {
			FXMLLoader alta = new FXMLLoader(getClass().getResource("/fes/aragon/vista/ModificarFacturaVD.fxml"));
			Parent parent=(Parent)alta.load();
			((ModificarFacturaVDController)alta.getController()).modificarFacturas(factura);
			Scene escena= new Scene(parent);
			Stage escenario=new Stage();
			escenario.initModality(Modality.APPLICATION_MODAL);
			escenario.initOwner(tblTablaFacturas.getScene().getWindow());
			escenario.setScene(escena);
			escenario.initStyle(StageStyle.UTILITY);
			escenario.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

}