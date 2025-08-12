
import com.todoapp.model.ToDoModel;
import com.todoapp.ui.ToDoView;
import com.todoapp.controller.ToDoController;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {
            ToDoModel model = new ToDoModel();
            ToDoView view = new ToDoView();
            new ToDoController(model, view);
            view.show();
        });
    }
}
