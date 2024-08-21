package Ui;

import Command.*;

import Command.HistoryCommandHandler;
import Generator.Board;
import Generator.KenKenBuilder;
import Generator.KenKenDirector;
import Generator.Cage;
import Solver.Solver;
import repository.BoardService;

import javax.swing.*;
import java.awt.event.WindowEvent;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.List;

public class KenKenController {
     private MainPanel mainPanel;
     private Board board;
     private RepoPanel repoPanel;
     private ConstructPanel constructPanel;
     private BoardPanel boardPanel;
     private StartPanel startPanel;
     private BoardService boardConnection;
     private KenKenBuilder builder;
     private HistoryCommandHandler command;

     public KenKenController(MainPanel mp){
         this.mainPanel=mp;
         this.boardConnection=new BoardService("jdbc:sqlite:kenken.db");
         this.command = new HistoryCommandHandler();
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
         constructPanel.reset();
         board = new Board(size);//non serve
         this.builder = new KenKenBuilder(size);
         constructPanel.initBoard(size);
         builder.attach(constructPanel);
         mainPanel.showPanel("ConstructScrean");
    }

    public void showRepoScrean() {
         repoPanel.loadData();
         mainPanel.showPanel("RepoScrean");
    }

    public BoardService getboardConnection() {
         return boardConnection;
    }

    public void start(int size) {
         KenKenBuilder build = new KenKenBuilder(size);
         KenKenDirector director = new KenKenDirector(build);
         board=director.createKenken();
         board.attach(boardPanel);
         boardPanel.initBoard(board);
         SwingUtilities.invokeLater(()->boardPanel.updateBoard(board,false));
         mainPanel.showPanel("BoardScrean");
    }

    public void createCage(Cage c ) {
         Command cageCommand = new CageCommand(builder,c);
         command.handle(cageCommand);
    }

    public void inizia() {
         board=builder.build();
         builder.detach(constructPanel);
         showBoardScrean();
    }

    private void showBoardScrean() {
         board.attach(boardPanel);
         boardPanel.reset();
         boardPanel.initBoard(board);
         SwingUtilities.invokeLater(()->board.notifica());
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

    public void solvePuzzle(int max) {
         Solver s = new Solver(board,max);
         s.risolviKenken();
         //soluzioni=s.getSoluzione();
         if(board.getNumSoluzioni()>0){
             JOptionPane.showMessageDialog(boardPanel,"Puzzle ha soluzioni: "+board.getNumSoluzioni());

             //boardPanel.updateCount(n + 1);
             //boardPanel.updateBoard(soluzioni.get(n),true);//updateBoard
             //board.setBoard(soluzioni.get(n));
             board.primaSol();
         }else {
             JOptionPane.showMessageDialog(boardPanel, "Puzzle non ha soluzioni");
             System.out.println("Nessunasoluzione");
         }
    }

    public void elimina(int id) {
         boardConnection.deletePuzzle( id);
    }
    public void inserisciValore(int m,int n, String v){
        board.inserisciValore(m,n,v);
    }

    public Board getBoard() {
         return board;
    }
    public void showNextSol() {
        board.prossimaSol();
    }

    public void showPriviusSol() {
       board.precedenteSol();
    }
    public void undo(){
        command.undo();
    }
    public void redo(){
        command.redo();
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
         boardConnection.saveBoard(this.board,name);
    }
    public static void main(String... args){
         SwingUtilities.invokeLater(()->{
             JFrame frame = new JFrame("KENKEN");
             MainPanel mainPanel = new MainPanel();
             KenKenController controller= new KenKenController(mainPanel);

             frame.setContentPane(mainPanel);
             frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
             frame.addWindowListener(new java.awt.event.WindowAdapter(){
                 @Override
                 public void windowClosing(WindowEvent e) {
                     controller.getboardConnection().close();
                 }
             });
             frame.setSize(500,500);
             frame.setLocationRelativeTo(null);
             frame.setVisible(true);
         });
    }
}


