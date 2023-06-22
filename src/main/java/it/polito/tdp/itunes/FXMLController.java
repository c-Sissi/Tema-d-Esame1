/**
 * Sample Skeleton for 'Scene.fxml' Controller Class
 */

package it.polito.tdp.itunes;


import java.net.URL;
import java.util.ResourceBundle;
import it.polito.tdp.itunes.model.Album;
import it.polito.tdp.itunes.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {
	
	private Model model;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="btnAdiacenze"
    private Button btnAdiacenze; // Value injected by FXMLLoader

    @FXML // fx:id="btnCreaGrafo"
    private Button btnCreaGrafo; // Value injected by FXMLLoader

    @FXML // fx:id="btnPercorso"
    private Button btnPercorso; // Value injected by FXMLLoader

    @FXML // fx:id="cmbA1"
    private ComboBox<Album> cmbA1; // Value injected by FXMLLoader

    @FXML // fx:id="cmbA2"
    private ComboBox<Album> cmbA2; // Value injected by FXMLLoader

    @FXML // fx:id="txtN"
    private TextField txtN; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML // fx:id="txtX"
    private TextField txtX; // Value injected by FXMLLoader

    @FXML
    void doCalcolaAdiacenze(ActionEvent event) throws IllegalArgumentException{
    	
    	Album a = this.cmbA1.getValue() ;
    	
    	if (this.model.getVertex().contains(a)) {
    		txtResult.setText("ELENCO DEI NODI SUCCESSORI: \n");
    		txtResult.appendText(this.model.elencoSuccessori(a));
		}
    	else {
    		txtResult.setText("VERTICE NON PRESENTE NEL GRAFO");
    		throw new IllegalArgumentException () ;
    	}
    }
    	

    @FXML
    void doCalcolaPercorso(ActionEvent event) {
    	int bilancio ;
    	Album source = this.cmbA1.getValue() ;
    	Album target = this.cmbA2.getValue() ;
    	try {
    		bilancio = Integer.parseInt(txtX.getText()) ;
    	}
    	catch (NumberFormatException e){
    		txtResult.setText("INSERIRE UN NUMERO" ) ;
    		return ;
    	}
    	
    	this.model.simplePath(bilancio, source , target) ;
    	if(this.model.equals(null)) {
    		txtResult.setText("NESSUN CAMMINO TROVATO");
    	}
    	else {
	    	txtResult.setText("IL CAMMINO TROVATO E':\n") ;
	    	txtResult.appendText(this.model.toStringSimplePath());
    	}
    }

    @FXML
    void doCreaGrafo(ActionEvent event) {
    	int n ;
    	
    	try {
    		n = Integer.parseInt(txtN.getText()) ;
    		
    	}
    	catch(NumberFormatException e) {
    		txtResult.setText("INSERIRE UN VALORE NUMERICO");
    		return;
    	}
    	this.model.creaGrafo(n);
    	this.model.addArco();
    	txtResult.setText("GRAFO CREATO CORRETAMENTE\n");
    	txtResult.appendText("# VERTICI: " + this.model.getVertexSet()+"\n") ;
    	txtResult.appendText("# ARCHI: " + this.model.getEdgeSet());
    }

    
    public void setModel(Model model) {
    	this.model = model;
    	this.cmbA1.getItems().addAll(this.model.listaAlbum());
    	this.cmbA2.getItems().addAll(this.model.listaAlbum());

    }
    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert btnAdiacenze != null : "fx:id=\"btnAdiacenze\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnCreaGrafo != null : "fx:id=\"btnCreaGrafo\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnPercorso != null : "fx:id=\"btnPercorso\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbA1 != null : "fx:id=\"cmbA1\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbA2 != null : "fx:id=\"cmbA2\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtN != null : "fx:id=\"txtN\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtX != null : "fx:id=\"txtX\" was not injected: check your FXML file 'Scene.fxml'.";

    }

    
   
}
