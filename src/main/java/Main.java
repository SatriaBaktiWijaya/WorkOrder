/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author acer
 */
import controller.WorkOrderController;
import database.Database;
import database.IDatabase;
import repository.IWorkOrderRepository;
import repository.WorkOrderRepository;
import view.WorkOrderView;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Main {

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {
        }

        SwingUtilities.invokeLater(() -> {
            IDatabase database = new Database();
            IWorkOrderRepository repository = new WorkOrderRepository(database);
            WorkOrderView view = new WorkOrderView();
            new WorkOrderController(view, repository);

            view.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    database.closeConnection();
                }
            });

            view.setVisible(true);
        });
    }
}
