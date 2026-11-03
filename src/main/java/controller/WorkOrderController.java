/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

/**
 *
 * @author acer
 */
import model.WorkOrder;
import repository.IWorkOrderRepository;
import view.WorkOrderView;

import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class WorkOrderController {

    private final WorkOrderView view;
    private final IWorkOrderRepository repository;

    public WorkOrderController(WorkOrderView view, IWorkOrderRepository repository) {
        this.view = view;
        this.repository = repository;

        initListeners();
        loadTable(repository.getAll());
        resetForm();
    }

    private void initListeners() {
        view.addSimpanListener(new SimpanAction());
        view.addUbahListener(new UbahAction());
        view.addHapusListener(new HapusAction());
        view.addResetListener(e -> resetForm());
        view.addCariListener(e -> searchAction());
        view.addRefreshListener(e -> {
            view.setKeyword("");
            loadTable(repository.getAll());
            resetForm();
        });
        view.addTableSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                handleTableClick();
            }
        });
    }

    private void loadTable(List<WorkOrder> list) {
        DefaultTableModel model = view.getTableModel();
        model.setRowCount(0);
        for (WorkOrder wo : list) {
            model.addRow(new Object[]{
                wo.getId(),
                wo.getPelanggan(),
                wo.getBarang(),
                wo.getStatus(),
                String.format("%.0f", wo.getBiaya())
            });
        }
    }

    private void resetForm() {
        view.setId(repository.generateId());
        view.setPelanggan("");
        view.setBarang("");
        view.setStatus("Received");
        view.setBiaya("");
        view.getTable().clearSelection();
    }

    private void handleTableClick() {
        int row = view.getTable().getSelectedRow();
        if (row >= 0) {
            String id = (String) view.getTableModel().getValueAt(row, 0);
            WorkOrder wo = repository.getById(id);
            if (wo != null) {
                view.setId(wo.getId());
                view.setPelanggan(wo.getPelanggan());
                view.setBarang(wo.getBarang());
                view.setStatus(wo.getStatus());
                view.setBiaya(String.valueOf((long) wo.getBiaya()));
            }
        }
    }

    private WorkOrder validateForm() {
        String id = view.getId();
        String pelanggan = view.getPelanggan();
        String barang = view.getBarang();
        String status = view.getStatus();
        String biayaStr = view.getBiaya();

        if (id.isEmpty() || pelanggan.isEmpty() || barang.isEmpty() || biayaStr.isEmpty()) {
            view.showError("Semua data harus diisi!");
            return null;
        }

        double biaya;
        try {
            biaya = Double.parseDouble(biayaStr.replace(",", ""));
            if (biaya < 0) {
                view.showError("Biaya tidak boleh bernilai negatif!");
                return null;
            }
        } catch (NumberFormatException ex) {
            view.showError("Biaya harus berupa angka!");
            return null;
        }

        return new WorkOrder(id, pelanggan, barang, status, biaya);
    }

    private class SimpanAction implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            WorkOrder wo = validateForm();
            if (wo == null) {
                return;
            }

            if (repository.getById(wo.getId()) != null) {
                wo.setId(repository.generateId());
            }

            if (repository.insert(wo)) {
                view.showMessage("Data Work Order berhasil disimpan!");
                loadTable(repository.getAll());
                resetForm();
            } else {
                view.showError("Gagal menyimpan data ke database.");
            }
        }
    }

    private class UbahAction implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            int row = view.getTable().getSelectedRow();
            if (row < 0) {
                view.showError("Pilih baris pada tabel terlebih dahulu yang ingin diubah!");
                return;
            }

            WorkOrder wo = validateForm();
            if (wo == null) {
                return;
            }

            if (repository.update(wo)) {
                view.showMessage("Data Work Order berhasil diperbarui!");
                loadTable(repository.getAll());
                resetForm();
            } else {
                view.showError("Gagal memperbarui data.");
            }
        }
    }

    private class HapusAction implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            int row = view.getTable().getSelectedRow();
            if (row < 0) {
                view.showError("Pilih baris data pada tabel yang ingin dihapus!");
                return;
            }

            String id = view.getId();
            if (view.showConfirm("Yakin ingin menghapus Work Order " + id + "?")) {
                if (repository.delete(id)) {
                    view.showMessage("Data berhasil dihapus!");
                    loadTable(repository.getAll());
                    resetForm();
                } else {
                    view.showError("Gagal menghapus data.");
                }
            }
        }
    }

    private void searchAction() {
        String keyword = view.getKeyword();
        if (keyword.isEmpty()) {
            loadTable(repository.getAll());
        } else {
            loadTable(repository.search(keyword));
        }
    }
}
