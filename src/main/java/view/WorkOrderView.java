/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

/**
 *
 * @author acer
 */
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import javax.swing.event.ListSelectionListener;

public class WorkOrderView extends JFrame {

    private final JTextField textId;
    private final JTextField textPelanggan;
    private final JTextField textBarang;
    private final JComboBox<String> cmbStatus;
    private final JTextField textBiaya;

    private final JButton btnSimpan;
    private final JButton btnUbah;
    private final JButton btnHapus;
    private final JButton btnReset;

    private final JTextField textCari;
    private final JButton btnCari;
    private final JButton btnRefresh;

    private final JTable table;
    private final DefaultTableModel tableModel;

    public WorkOrderView() {
        setTitle("PT Alexander Bubut - Pengelolaan Work Order (MVP)");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(750, 520);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(8, 8));

        // Panel Atas: Judul, Formulir Input, dan Tombol
        JPanel panelNorth = new JPanel(new BorderLayout(5, 5));
        panelNorth.setBorder(BorderFactory.createEmptyBorder(10, 15, 5, 15));

        JLabel lblHeader = new JLabel("Sistem Pengelolaan Work Order - PT Alexander Bubut", JLabel.CENTER);
        lblHeader.setFont(new Font("Segoe UI", Font.BOLD, 15));
        panelNorth.add(lblHeader, BorderLayout.NORTH);

        JPanel panelForm = new JPanel(new GridLayout(5, 2, 6, 6));
        panelForm.setBorder(BorderFactory.createTitledBorder("Formulir Work Order"));

        panelForm.add(new JLabel("ID Work Order:"));
        textId = new JTextField();
        textId.setEditable(false);
        textId.setBackground(new Color(240, 240, 240));
        panelForm.add(textId);

        panelForm.add(new JLabel("Nama Pelanggan:"));
        textPelanggan = new JTextField();
        panelForm.add(textPelanggan);

        panelForm.add(new JLabel("Nama Barang:"));
        textBarang = new JTextField();
        panelForm.add(textBarang);

        panelForm.add(new JLabel("Status Pengerjaan:"));
        cmbStatus = new JComboBox<>(new String[]{"Received", "Process", "Finish"});
        panelForm.add(cmbStatus);

        panelForm.add(new JLabel("Estimasi Biaya (Rp):"));
        textBiaya = new JTextField();
        panelForm.add(textBiaya);

        panelNorth.add(panelForm, BorderLayout.CENTER);

        JPanel panelTombol = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 5));
        btnSimpan = new JButton("Simpan");
        btnUbah = new JButton("Ubah");
        btnHapus = new JButton("Hapus");
        btnReset = new JButton("Reset");

        panelTombol.add(btnSimpan);
        panelTombol.add(btnUbah);
        panelTombol.add(btnHapus);
        panelTombol.add(btnReset);

        panelNorth.add(panelTombol, BorderLayout.SOUTH);
        add(panelNorth, BorderLayout.NORTH);

        // Panel Tengah: JTable
        tableModel = new DefaultTableModel(new String[]{"ID", "Pelanggan", "Barang", "Status", "Biaya (Rp)"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table = new JTable(tableModel);
        table.setRowHeight(22);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(0, 15, 0, 15),
                BorderFactory.createTitledBorder("Daftar Work Order")
        ));
        add(scrollPane, BorderLayout.CENTER);

        // Panel Bawah: Pencarian
        JPanel panelSouth = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 10));
        panelSouth.add(new JLabel("Pencarian:"));
        textCari = new JTextField(18);
        panelSouth.add(textCari);

        btnCari = new JButton("Cari");
        btnRefresh = new JButton("Tampilkan Semua");
        panelSouth.add(btnCari);
        panelSouth.add(btnRefresh);

        add(panelSouth, BorderLayout.SOUTH);
    }

    public String getId() {
        return textId.getText().trim();
    }

    public void setId(String id) {
        textId.setText(id);
    }

    public String getPelanggan() {
        return textPelanggan.getText().trim();
    }

    public void setPelanggan(String pelanggan) {
        textPelanggan.setText(pelanggan);
    }

    public String getBarang() {
        return textBarang.getText().trim();
    }

    public void setBarang(String barang) {
        textBarang.setText(barang);
    }

    public String getStatus() {
        return (String) cmbStatus.getSelectedItem();
    }

    public void setStatus(String status) {
        cmbStatus.setSelectedItem(status);
    }

    public String getBiaya() {
        return textBiaya.getText().trim();
    }

    public void setBiaya(String biaya) {
        textBiaya.setText(biaya);
    }

    public String getKeyword() {
        return textCari.getText().trim();
    }

    public void setKeyword(String keyword) {
        textCari.setText(keyword);
    }

    public JTable getTable() {
        return table;
    }

    public DefaultTableModel getTableModel() {
        return tableModel;
    }

    public void addSimpanListener(ActionListener listener) {
        btnSimpan.addActionListener(listener);
    }

    public void addUbahListener(ActionListener listener) {
        btnUbah.addActionListener(listener);
    }

    public void addHapusListener(ActionListener listener) {
        btnHapus.addActionListener(listener);
    }

    public void addResetListener(ActionListener listener) {
        btnReset.addActionListener(listener);
    }

    public void addCariListener(ActionListener listener) {
        btnCari.addActionListener(listener);
    }

    public void addRefreshListener(ActionListener listener) {
        btnRefresh.addActionListener(listener);
    }

    public void addTableSelectionListener(ListSelectionListener listener) {
        table.getSelectionModel().addListSelectionListener(listener);
    }

    public void showMessage(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Informasi", JOptionPane.INFORMATION_MESSAGE);
    }

    public void showError(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Kesalahan", JOptionPane.ERROR_MESSAGE);
    }

    public boolean showConfirm(String msg) {
        int opt = JOptionPane.showConfirmDialog(this, msg, "Konfirmasi", JOptionPane.YES_NO_OPTION);
        return opt == JOptionPane.YES_OPTION;
    }
}
