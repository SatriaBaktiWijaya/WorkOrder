/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package repository;

import database.IDatabase;
import model.WorkOrder;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author acer
 */
public class WorkOrderRepository implements IWorkOrderRepository {

    private final IDatabase database;

    public WorkOrderRepository(IDatabase database) {
        this.database = database;
    }

    @Override
    public boolean insert(WorkOrder wo) {
        String sql = "INSERT INTO work_orders (id, pelanggan, barang, status, biaya) VALUES (?, ?, ?, ?, ?)";
        try {
            Connection conn = database.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, wo.getId());
            ps.setString(2, wo.getPelanggan());
            ps.setString(3, wo.getBarang());
            ps.setString(4, wo.getStatus());
            ps.setDouble(5, wo.getBiaya());
            int result = ps.executeUpdate();
            ps.close();
            return result > 0;
        } catch (SQLException e) {
            System.err.println("Gagal insert data: " + e.getMessage());
            return false;
        }
    }

    @Override
    public List<WorkOrder> getAll() {
        List<WorkOrder> list = new ArrayList<>();
        String sql = "SELECT * FROM work_orders ORDER BY id ASC";
        try {
            Connection conn = database.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                list.add(new WorkOrder(
                        rs.getString("id"),
                        rs.getString("pelanggan"),
                        rs.getString("barang"),
                        rs.getString("status"),
                        rs.getDouble("biaya")
                ));
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            System.err.println("Gagal membaca data: " + e.getMessage());
        }
        return list;
    }

    @Override
    public WorkOrder getById(String id) {
        String sql = "SELECT * FROM work_orders WHERE id = ?";
        try {
            Connection conn = database.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            WorkOrder wo = null;
            if (rs.next()) {
                wo = new WorkOrder(
                        rs.getString("id"),
                        rs.getString("pelanggan"),
                        rs.getString("barang"),
                        rs.getString("status"),
                        rs.getDouble("biaya")
                );
            }
            rs.close();
            ps.close();
            return wo;
        } catch (SQLException e) {
            System.err.println("Gagal getById: " + e.getMessage());
            return null;
        }
    }

    @Override
    public boolean update(WorkOrder wo) {
        String sql = "UPDATE work_orders SET pelanggan = ?, barang = ?, status = ?, biaya = ? WHERE id = ?";
        try {
            Connection conn = database.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, wo.getPelanggan());
            ps.setString(2, wo.getBarang());
            ps.setString(3, wo.getStatus());
            ps.setDouble(4, wo.getBiaya());
            ps.setString(5, wo.getId());
            int result = ps.executeUpdate();
            ps.close();
            return result > 0;
        } catch (SQLException e) {
            System.err.println("Gagal update data: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean delete(String id) {
        String sql = "DELETE FROM work_orders WHERE id = ?";
        try {
            Connection conn = database.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, id);
            int result = ps.executeUpdate();
            ps.close();
            return result > 0;
        } catch (SQLException e) {
            System.err.println("Gagal delete data: " + e.getMessage());
            return false;
        }
    }

    @Override
    public List<WorkOrder> search(String keyword) {
        List<WorkOrder> list = new ArrayList<>();
        String sql = "SELECT * FROM work_orders WHERE id LIKE ? OR pelanggan LIKE ? OR barang LIKE ? ORDER BY id ASC";
        try {
            Connection conn = database.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            String p = "%" + keyword + "%";
            ps.setString(1, p);
            ps.setString(2, p);
            ps.setString(3, p);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new WorkOrder(
                        rs.getString("id"),
                        rs.getString("pelanggan"),
                        rs.getString("barang"),
                        rs.getString("status"),
                        rs.getDouble("biaya")
                ));
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            System.err.println("Gagal search: " + e.getMessage());
        }
        return list;
    }

    @Override
    public String generateId() {
        String sql = "SELECT id FROM work_orders ORDER BY id DESC LIMIT 1";
        try {
            Connection conn = database.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) {
                String lastId = rs.getString("id");
                int num = Integer.parseInt(lastId.substring(3)) + 1;
                rs.close();
                stmt.close();
                return String.format("WO-%03d", num);
            }
            rs.close();
            stmt.close();
        } catch (Exception ignored) {
        }
        return "WO-001";
    }
}
