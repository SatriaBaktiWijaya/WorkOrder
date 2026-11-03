/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package repository;

import java.util.List;
import model.WorkOrder;

/**
 *
 * @author acer
 */
public interface IWorkOrderRepository {

    boolean insert(WorkOrder wo);

    List<WorkOrder> getAll();

    WorkOrder getById(String id);

    boolean update(WorkOrder wo);

    boolean delete(String id);

    List<WorkOrder> search(String keyword);

    String generateId();
}
