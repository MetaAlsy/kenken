package Ui;

import Generator.Board;
import Generator.KenKenBuilder;
import Generator.KenKenDirector;
import Operations.Divisione;
import Operations.Multiplicazione;
import Operations.Somma;
import Operations.Sottrazione;
import Solver.Cage;
import Solver.Point;
import Solver.Solver;
import repository.BoardConnection;

import javax.swing.*;
import java.awt.*;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class KenKenController {
     private MainPanel mainPanel;
     private Board board;
     private RepoPanel repoPanel;
     private ConstructPanel constructPanel;
     private BoardPanel boardPanel;
     private StartPanel startPanel;
     private BoardConnection boardConnection;
     private List<int[][]> soluzioni;
     private int n;

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
         n=0;
         mainPanel.showPanel("StartScrean");
     }


    public void showCreatScrean(int size) {
         constructPanel.reset();
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
         KenKenBuilder build = new KenKenBuilder(size);
         KenKenDirector director = new KenKenDirector(build);
         board=director.createKenken();
         boardPanel.initBoard(board);
         SwingUtilities.invokeLater(()->boardPanel.updateBoard(board.getBoard(),false));
         mainPanel.showPanel("BoardScrean");
    }

    public void createCage(Cage c) {
         board.getCages().add(c);
    }

    public void inizia() {
         showBoardScrean();
    }

    private void showBoardScrean() {
         boardPanel.reset();
         boardPanel.initBoard(board);
         SwingUtilities.invokeLater(()->boardPanel.updateBoard(board.getBoard(),false));
         mainPanel.showPanel("BoardScrean");
    }

    public void checkSoluzione() {
        if( board.esisteSoluzione()){
            JOptionPane.showMessageDialog(boardPanel,"Hai trovato la soluzione");
        }else
            JOptionPane.showMessageDialog(boardPanel,"Soluzione fornita non è corretta ripriva");
    }


    public void returnToMenu() {
         boardPanel.reset();
         constructPanel.reset();
         mainPanel.showPanel("StartScrean");
    }

    public void solvePuzzle() {
         Solver s = new Solver(board.getN(),board.getCages());
         soluzioni=s.getSoluzione();
         if(!soluzioni.isEmpty()){
             JOptionPane.showMessageDialog(boardPanel,"Puzzle ha soluzioni: "+soluzioni.size());

             boardPanel.updateCount(n + 1);
             boardPanel.updateBoard(soluzioni.get(n),true);
         }else {
             JOptionPane.showMessageDialog(boardPanel, "Puzzle non ha soluzioni");
             System.out.println("Nessunasoluzione");
         }
    }

    public void elimina(int id) {
         boardConnection.deletePuzzle( id);
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
    public void showNextSol() {
        if(n<soluzioni.size()-1){
            n++;
            boardPanel.updateCount(n+1);
            boardPanel.updateBoard(soluzioni.get(n),true);
        }
    }

    public void showPriviusSol() {
        if(n>0){
            n--;
            boardPanel.updateCount(n+1);
            boardPanel.updateBoard(soluzioni.get(n),true);
        }
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

    public void carica(byte[] buf) {
         try{
            ByteArrayInputStream bais = new ByteArrayInputStream(buf);
            ObjectInputStream ois = new ObjectInputStream(bais);

            this.board =(Board)ois.readObject();
            showBoardScrean();
         } catch (IOException | ClassNotFoundException e) {
             throw new RuntimeException(e);
         }

    }

    public void save(String name) {
         boardConnection.saveBoard(board,name);
    }
}
