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
        JButton caricaButton = new JButton("Carica");
        caricaButton.addActionListener(e->{
            int row = table.getSelectedRow();
            if(row!=-1){
                byte[] buf = (byte[]) tableModel.getValueAt(row,2);
                controller.carica(buf);
            }else{
                JOptionPane.showMessageDialog(this,"Seleziona una riga prima di caricare");
            }
        });
        eliminaButtun.addActionListener(e->{
            int row = table.getSelectedRow();
            if(row!=-1){
                int id = (int)tableModel.getValueAt(row,0);
                int resp = JOptionPane.showConfirmDialog(null,"Sei sicuro di voler eliminare puzle: "+id,"Conferma",JOptionPane.YES_NO_OPTION,JOptionPane.WARNING_MESSAGE);
                if(resp==JOptionPane.YES_OPTION){
                    controller.elimina(id);
                    tableModel.removeRow(row);
                    JOptionPane.showMessageDialog(null,"Puzzle eliminato");
                }
            }
        });
        tornaButton.addActionListener(e->controller.returnToMenu());
        JPanel buttunPannel = new JPanel();
        buttunPannel.add(caricaButton);
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
