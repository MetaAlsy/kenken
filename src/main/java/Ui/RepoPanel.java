package Ui;

import repository.BoardConnection;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class RepoPanel extends JPanel {
    private KenKenController controller;
    private DefaultTableModel tableModel;

    public RepoPanel(KenKenController controller){
        this.controller=controller;
        setLayout(new BorderLayout());
        tableModel = new DefaultTableModel(new Object[]{"ID","Nome","Board"},0);
        JTable table = new JTable(tableModel);
        JScrollPane scrol = new JScrollPane(table);
        add(scrol,BorderLayout.CENTER);
        JButton eliminaButtun = new JButton("Elimina");
        JButton tornaButton = new JButton("Torna nel menu");
        eliminaButtun.addActionListener(e->controller.elimina(table));
        tornaButton.addActionListener(e->controller.returnToMenu());
        JPanel buttunPannel = new JPanel();
        buttunPannel.add(eliminaButtun);
        buttunPannel.add(tornaButton);
        add(buttunPannel,BorderLayout.SOUTH);
    }
    public void loadData(){
        tableModel.setRowCount(0);
        try {
            Connection c = controller.getboardConnection().getConnection();
            Statement st = c.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM Board");
            while (rs.next()){
                int id = rs.getInt("id");
                String name = rs.getString("name");
                byte[] board = rs.getBytes("Board_data");
                tableModel.addRow(new Object[]{id,name,board});
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
