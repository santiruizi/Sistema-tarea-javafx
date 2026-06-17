package gm.tareas.service;

import gm.tareas.model.Tarea;
import java.util.List;

public interface ITareaService {

    List<Tarea> listarTareas();

    Tarea buscarPorTareaPorI(Integer idTarea);

    void guardarTarea(Tarea tarea);

    void eliminarTareaPorId(Tarea tarea);
}
