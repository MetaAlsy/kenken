package repository;

import Generator.Board;

import java.io.*;
import java.sql.*;

public class BoardService {
    private  Connection connection;
    private  String url;

    public BoardService(String url){
        this.url = url;
        try{
            connection = DriverManager.getConnection(url);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void saveBoard(Board b, String name){
        String sql = "INSERT INTO Board(name, board_data) values (?,?)";
        try (/*Connection connection = DriverManager.getConnection(url);*/
             PreparedStatement ps = connection.prepareStatement(sql);
             ByteArrayOutputStream bos = new ByteArrayOutputStream();
             ObjectOutputStream oos = new ObjectOutputStream(bos)) {
                oos.writeObject(b);
                oos.flush();
                byte[] bytes = bos.toByteArray();
                ps.setString(1,name);
                ps.setBytes(2,bytes);
                ps.executeUpdate();
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
    }
    public Board getBoard(int id){
        Board ret = null;
        String sql = "select board_data from Board where id = ? ";
        try(/*Connection connection = DriverManager.getConnection(url);*/
            PreparedStatement ps = connection.prepareStatement(sql)){
            ps.setInt(1,id);
            ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    byte[] bytes = rs.getBytes("board_data");
                    try(ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
                    ObjectInputStream ois = new ObjectInputStream(bis)){
                        ret = (Board) ois.readObject();
                }
            }
        } catch (SQLException | IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return ret;
    }
    public Connection getConnection(){
//        try{
//            connection = DriverManager.getConnection(url);
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
        return connection;
    }

    public void deletePuzzle(int id) {
        String sql = "delete from Board where id=? ";
        try(/*Connection connection = DriverManager.getConnection(url);*/
            PreparedStatement ps = connection.prepareStatement(sql)){
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

    public void close() {
        try {
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
