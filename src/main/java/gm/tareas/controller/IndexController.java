package gm.tareas.controller;

import gm.tareas.model.Tarea;
import gm.tareas.service.TareaService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import java.net.URL;
import java.util.ResourceBundle;

@Component
public class IndexController implements Initializable {

    private static final Logger logger = LoggerFactory.getLogger("IndexController.class");

    @Autowired
    private TareaService tareaService;

    @FXML
    private TableView<Tarea> tareaTabla;

    @FXML
    private TableColumn<Tarea, Integer> idTareaColumna;

    @FXML
    private TableColumn<Tarea, String> nombreTareaColumna;

    @FXML
    private TableColumn<Tarea, String> responsableTareaColumna;

    @FXML
    private TableColumn<Tarea, String> estatusTareaColumna;

    private final ObservableList<Tarea> tareaList = FXCollections.observableArrayList();

    private Integer idTareaInterno;

    // Creo instacia lo que son las cajas de texto, '@FXML', hago saber que este pertenece a la scene y que el controlador lo puede manejar

    @FXML
    private TextField nombreTareaText;

    @FXML
    private TextField responsableText;

    @FXML
    private TextField estatusText;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tareaTabla.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        configurarColumnas();
        listarTareas();
    }

    private void configurarColumnas(){
        idTareaColumna.setCellValueFactory(new PropertyValueFactory<>("idTarea"));
        nombreTareaColumna.setCellValueFactory(new PropertyValueFactory<>("nombreTarea"));
        responsableTareaColumna.setCellValueFactory(new PropertyValueFactory<>("responsableTarea"));
        estatusTareaColumna.setCellValueFactory(new PropertyValueFactory<>("estatusTarea"));
    }

    private void listarTareas(){
        logger.info("Ejecutando listado de tareas");
        tareaList.clear();
        tareaList.addAll(tareaService.listarTareas());
        tareaTabla.setItems(tareaList);
    }

    public void agregarTarea(){
        if(nombreTareaText.getText().isEmpty()){
            mostrarMensaje("Error de validacion", "Debe proporcionar una tarea");
            nombreTareaText.requestFocus();
        }else{
            Tarea tarea = new Tarea();
            recolectarDatosFormulario(tarea);
            tarea.setIdTarea(null);
            tareaService.guardarTarea(tarea);
            mostrarMensaje("Información", "Tarea agregada");
            limpiarFormulario();
            listarTareas();
        }
    }

    public void cargarTareaFormulario(){
        var tarea = tareaTabla.getSelectionModel().getSelectedItem();
        if(tarea != null) {
            idTareaInterno = tarea.getIdTarea();
            nombreTareaText.setText(tarea.getNombreTarea());
            responsableText.setText(tarea.getResponsableTarea());
            estatusText.setText(tarea.getEstatusTarea());
        }
    }

    public void limpiarFormulario(){
        idTareaInterno = null;
        nombreTareaText.clear();
        responsableText.clear();
        estatusText.clear();
    }

    private void recolectarDatosFormulario(Tarea tarea){
        if(idTareaInterno != null){
            tarea.setIdTarea(idTareaInterno);
            tarea.setNombreTarea(nombreTareaText.getText());

        }
        tarea.setNombreTarea(nombreTareaText.getText());
        tarea.setResponsableTarea(responsableText.getText());
        tarea.setEstatusTarea(estatusText.getText());
    }

    public void modificarTarea(){
        if(idTareaInterno == null){
            mostrarMensaje("Información", "Debes seleccionar una tarea");
        }
        if(nombreTareaText.getText().isEmpty()){
            mostrarMensaje("Error validación", "Debe proporcionar una tarea");
            nombreTareaText.requestFocus();
        }
        var tarea = new Tarea();
        recolectarDatosFormulario(tarea);
        tareaService.guardarTarea(tarea);
        mostrarMensaje("Información", "Tarea modificada");
        limpiarFormulario();
        listarTareas();
    }

    public void eliminarTarea(){
        Tarea tarea = tareaTabla.getSelectionModel().getSelectedItem();
        if(tarea != null){
            logger.info("Registro a eliminar: " + tarea);
            tareaService.eliminarTareaPorId(tarea);
            mostrarMensaje("Información", "Tarea eliminada: " + tarea.getIdTarea());
            limpiarFormulario();
            listarTareas();
        }else{
            mostrarMensaje("Error", "No se ha mostrado ninguna tarea");
        }
    }

    public void mostrarMensaje(String titulo, String mensaje){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        // Se informa al usuario y se espera que este la cierre
        alert.showAndWait();
    }

}
