package repository;

import Generator.Board;

import java.io.*;
import java.sql.*;

public class BoardConnection {
    private  Connection connection;
    public BoardConnection(){
        String url = "jdbc:sqlite:kenken.db";
        try{
            connection = DriverManager.getConnection(url);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void saveBoard(Board b, String name){
        try{
            PreparedStatement ps = connection.prepareStatement("INSERT INTO Board(name, board_data) values (?,?)");
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(b);
            oos.flush();
            byte[] bytes = bos.toByteArray();
            ps.setString(1,name);
            ps.setBytes(2,bytes);
            ps.execute();

        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
    }
    public Board getBoard(int id){
        Board ret = null;
        try{
            PreparedStatement ps = connection.prepareStatement("select board_data from Board where id = ? ");
            ps.setInt(1,id);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                //Blob blob = rs.getBlob("board_data");
                byte [] bytes = rs.getBytes("board_data");
                ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
                ObjectInputStream ois = new ObjectInputStream(bis);
                ret = (Board) ois.readObject();
            }
        } catch (SQLException | IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return ret;
    }
    public Connection getConnection(){
        return connection;
    }

    public void deletePuzzle(int id) {
        try{
            PreparedStatement ps = connection.prepareStatement("delete from Board where id=? ");
            ps.setInt(1,id);
            ps.execute();
            if(ps.executeUpdate()>0){
                System.out.println("Eliminato");
            }else
                System.out.println("Qualcosa e andato storto");


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
