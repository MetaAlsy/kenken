package Ui;

import Generator.Board;
import Operations.Divisione;
import Operations.Multiplicazione;
import Operations.Somma;
import Operations.Sottrazione;
import Solver.Cage;
import Solver.Point;
import repository.BoardConnection;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class KenKenController {
     private MainPanel mainPanel;
     private Board board;
     private RepoPanel repoPanel;
     private ConstructPanel constructPanel;
     private BoardPanel boardPanel;
     private StartPanel startPanel;
     private BoardConnection boardConnection;

     public KenKenController(MainPanel mp){
         this.mainPanel=mp;
         this.boardConnection=new BoardConnection();
         init();
     }
     private void init(){
         boardPanel= new BoardPanel(this);
         constructPanel= new ConstructPanel(this);
         repoPanel= new RepoPanel(this);
         startPanel = new StartPanel(this);
         mainPanel.add("BoardScrean",boardPanel);
         mainPanel.add("RepoScrean",repoPanel);
         mainPanel.add("ConstructScrean",constructPanel);
         mainPanel.add("StartScrean",startPanel);

         mainPanel.showPanel("StartScrean");
     }


    public void showCreatScrean(int size) {
         board = new Board(size);
         constructPanel.initBoard(size);
         mainPanel.showPanel("ConstructScrean");
    }

    public void showRepoScrean() {
         repoPanel.loadData();
         mainPanel.showPanel("RepoScrean");
    }

    public BoardConnection getboardConnection() {
         return boardConnection;
    }

    public void start(int size) {
         board = new Board(size);
         boardPanel.initBoard(board);
         boardPanel.updateBoard(board);
         mainPanel.showPanel("BoardScrean");
    }

    public void createCage(Cage c) {
         board.getCages().add(c);
    }

    public void inizia() {
         showBoardScrean();
    }

    private void showBoardScrean() {

         boardPanel.initBoard(board);
         boardPanel.updateBoard(board);
         mainPanel.showPanel("BoardScrean");
    }

    public void checkSoluzione() {
    }

    public void returnToMenu() {
         boardPanel.reset();
         mainPanel.showPanel("StartScrean");
    }

    public void solvePuzzle() {

    }

    public void elimina(JTable table) {
    }
    public void inserisciValore(int m,int n, String v){
        if(v!=null && !v.isEmpty()){
            int val=Integer.parseInt(v);
            if(val<=board.getN() && val>0)
                board.getBoard()[m][n]=val;
        }
    }
    public List<Cage> getCages(){
         return board.getCages();
    }
    public Board getBoard() {
         return board;
    }
    public static void main(String... args){
         SwingUtilities.invokeLater(()->{
             JFrame frame = new JFrame("KENKEN");
             MainPanel mainPanel = new MainPanel();
             KenKenController controller= new KenKenController(mainPanel);

             frame.setContentPane(mainPanel);
             frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
             frame.setSize(500,500);
             frame.setLocationRelativeTo(null);
             frame.setVisible(true);
         });
    }

}
