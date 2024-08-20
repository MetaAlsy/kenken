package repository;

import Generator.Board;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class BoardConnectionTest {
    private BoardService boardConnection;
    @BeforeEach
    void setUp() throws SQLException {
        boardConnection = new BoardService("jdbc:sqlite::memory:");
        try{
            Statement st = boardConnection.getConnection().createStatement();
            st.execute("CREATE TABLE IF NOT EXISTS Board (id INTEGER PRIMARY KEY, name TEXT, board_data BLOB)");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @AfterEach
    void close() throws SQLException {
        if(boardConnection.getConnection()!=null)
            boardConnection.getConnection().close();
    }
    @Test
    void testDatabaseConnection() throws SQLException {
        assertNotNull(boardConnection.getConnection());
        assertFalse(boardConnection.getConnection().isClosed());

    }
    @Test
    void saveBoard(){
        Board b = new Board(new int[3][3],new ArrayList<>());
        boardConnection.saveBoard(b,"Prova 1");
        Board bs = boardConnection.getBoard(1);
        assertNotNull(bs);
    }
    @Test
    void deleteBoard(){
        Board b = new Board(new int[3][3],new ArrayList<>());
        boardConnection.saveBoard(b,"Prova 1");
        Board bs = boardConnection.getBoard(1);
        assertNotNull(bs);
        boardConnection.deletePuzzle(1);
        bs = boardConnection.getBoard(1);
        assertNull(bs);
    }

}