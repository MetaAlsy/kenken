package Ui;

import Generator.Board;
import Generator.KenKenBuilder;
import Generator.KenKenDirector;
import Operations.*;
import Solver.Cage;
import Solver.Point;
import Solver.Solver;
import repository.BoardConnection;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class KenKenView extends JFrame {
    private int size;
    private CardLayout cardLayout;
    private DefaultTableModel tableModel;
    private JPanel mainPanel,boardPanel,constructPanel,repoPanel;
    private KenKenDirector director;
    private JPanel[][] boardGUI;
    private Board b;
    private static final int C_SIZE=50;
    private StandardOperationFactory standardOperationFactory;

    private List<Point> punti = new ArrayList<>();
    private List<Point> puntiVisitati = new ArrayList<>();
    public KenKenView(int size) {
        this.size = size;
        this.boardGUI = new JPanel[size][size];
        setTitle("KenKen");
        setSize(500, 500);
        setLocation(250, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        createStart();
        createBoard();
        createConstruct();
        createRepo();
        add(mainPanel);

    }

    private void createRepo() {
        JPanel panel = new JPanel(new BorderLayout());
        repoPanel = new JPanel();
        panel.add(repoPanel,BorderLayout.CENTER);
        tableModel = new DefaultTableModel(new Object[]{"ID","Nome","Board"},0);
        JTable table = new JTable(tableModel);
        JScrollPane scroll = new JScrollPane(table);
        panel.add(scroll,BorderLayout.CENTER);
        JButton eliminaButtun = new JButton("Elimina");
        JButton tornaButton = new JButton("Torna nel menu");
        JPanel buttunPannel = new JPanel();
        buttunPannel.add(eliminaButtun);
        buttunPannel.add(tornaButton);
        panel.add(buttunPannel,BorderLayout.SOUTH);
        loadData();

        mainPanel.add(panel,"RepoScrean");
    }

    private void loadData() {
        BoardConnection boardConnection = new BoardConnection();
        Connection c = boardConnection.getConnection();
        try {
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

    private void createConstruct() {
        b = new Board(size);
        JPanel panel = new JPanel(new BorderLayout());
        constructPanel = new JPanel(new GridLayout(size,size));
        panel.add(constructPanel,BorderLayout.CENTER);

        JPanel validPanel = new JPanel(new GridLayout(5,1,0,10));
        //validPanel.setLayout(new GridBagLayout());
        JButton createButton = new JButton("Crea reggione");
        createButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                String opration = JOptionPane.showInputDialog("Entra operazione");
//                String target = JOptionPane.showInputDialog("Entra target:");
//                if(opration!=null && target!=null){
//                    List<Point> punt = new ArrayList<>(punti);
//                    puntiVisitati.addAll(punti);
//                    punti.clear();
                    popUp();
                }
            //}
        });
        JButton createPuzzle = new JButton("Crea puzzle");
        createPuzzle.addActionListener(e -> {
            cardLayout.show(mainPanel,"BoardScrean");
            inizializza();
        });
        validPanel.add(createPuzzle);
        validPanel.add(createButton);
        panel.add(validPanel,BorderLayout.EAST);
        mainPanel.add(panel,"ConstructScrean");
    }

    private void popUp() {
        JDialog finestra = new JDialog();
        finestra.setTitle("Scegli parametri");
        finestra.setLocation(250,250);
        finestra.setSize(270,270);
        finestra.setLayout(new FlowLayout());

        JRadioButton b1 = new JRadioButton("Somma",true);
        JRadioButton b2 = new JRadioButton("Sottrazione");
        JRadioButton b3 = new JRadioButton("Moltiplicazione");
        JRadioButton b4 = new JRadioButton("Divisione");

        ButtonGroup group = new ButtonGroup();
        group.add(b1);
        group.add(b2);
        group.add(b3);
        group.add(b4);

        finestra.add(b1);
        finestra.add(b2);
        finestra.add(b3);
        finestra.add(b4);

        finestra.add(new JLabel("Inserisci il target:"));
        JTextField numero = new JTextField(10);
        finestra.add(numero);

        JButton okButtun = new JButton("Ok");
        finestra.add(okButtun);
        okButtun.addActionListener(e->{
            if(!numero.getText().isEmpty()){
            List<Point> punt = new ArrayList<>(punti);
            puntiVisitati.addAll(punti);
            punti.clear();
                if(b1.isSelected())
                    b.getCages().add(new Cage(punt,Integer.valueOf(numero.getText()), standardOperationFactory.createSum()));
                if(b2.isSelected())
                    b.getCages().add(new Cage(punt,Integer.valueOf(numero.getText()), standardOperationFactory.createSott()));
                if(b3.isSelected())
                    b.getCages().add(new Cage(punt,Integer.valueOf(numero.getText()), standardOperationFactory.createMul()));
                if(b4.isSelected())
                    b.getCages().add(new Cage(punt,Integer.valueOf(numero.getText()), standardOperationFactory.createDiv()));
                paintCage();
                finestra.dispose();
            }
        });
        finestra.setModal(true);
        finestra.setVisible(true);

    }

    private void createBoard() {
        JPanel panel = new JPanel(new BorderLayout());
        boardPanel = new JPanel(new GridLayout(size,size));
        panel.add(boardPanel, BorderLayout.CENTER);

        JPanel controllPannel = new JPanel();
        JButton checkButtun = new JButton("Check");
        checkButtun.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if( b.esisteSoluzione()){
                    JOptionPane.showMessageDialog(boardPanel,"Hai trovato la soluzione");
                }else
                    JOptionPane.showMessageDialog(boardPanel,"Soluzione fornita non è corretta ripriva");
            }
        });

        JButton returnButtun = new JButton("Torna al menu");
        returnButtun.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(mainPanel,"StartScrean");
            }
        });

        JButton risolviButtun = new JButton("Risolvi");
        risolviButtun.addActionListener(a->{
//            Solver solver = new Solver(size,b.getCages());
//            int[][] soluzione = solver.getSoluzione().get(0);
//            for(int i=0;i<size;i++){
//                for(int j=0;j<size;j++){
//                    JTextField f = (JTextField) boardGUI[i][j].getComponent(0);
//                    f.setText(String.valueOf(soluzione[i][j]));
//                }
//            }
       });

        controllPannel.add(checkButtun);
        controllPannel.add(returnButtun);
        controllPannel.add(risolviButtun);

        panel.add(controllPannel,BorderLayout.SOUTH);
        mainPanel.add(panel,"BoardScrean");
    }

    private void createStart() {
        JPanel startPanel = new JPanel(new GridLayout(5,1));


        JLabel initialLable = new JLabel("KenKen Puzzle",JLabel.CENTER);

        ButtonGroup sizeGroup = new ButtonGroup();

        JRadioButton small = new JRadioButton("3x3");
        JRadioButton medium = new JRadioButton("6x6",true);
        JRadioButton big = new JRadioButton("9x9");
        sizeGroup.add(small);
        sizeGroup.add(medium);
        sizeGroup.add(big);
        JPanel sizePanel = new JPanel(new GridLayout(2,1));
        sizePanel.add(new JLabel("Seleziona la dimensione",JLabel.CENTER));
        JPanel sizeButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        sizeButtonPanel.add(small);
        sizeButtonPanel.add(medium);
        sizeButtonPanel.add(big);
        sizePanel.add(sizeButtonPanel);



        JButton startButtun = new JButton("Start");
        startButtun.setFont(new Font("Times New Roman",Font.BOLD,15));
        JPanel starPanel = new JPanel();
        starPanel.add(startButtun);
        startButtun.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                KenKenBuilder build = new KenKenBuilder(size);
                director = new KenKenDirector(build);
                b=director.createKenken();
                cardLayout.show(mainPanel,"BoardScrean");
                inizializza();
            }
        });
        JButton creatButtun = new JButton("Crea personaliizato");
        creatButtun.setFont(new Font("Times New Roman",Font.BOLD,15));
        starPanel.add(creatButtun);
        creatButtun.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boardGUI = new JPanel[size][size];
                for(int i=0;i<size;i++){
                    for(int j=0;j<size;j++) {
                        boardGUI[i][j] = new JPanel(null);
                        boardGUI[i][j].setPreferredSize(new Dimension(C_SIZE, C_SIZE));
                        boardGUI[i][j].setBorder(new MatteBorder(1, 1, 1, 1, Color.DARK_GRAY));
                        int m = i;
                        int n = j;
                        boardGUI[i][j].addMouseListener(new MouseAdapter() {
                            @Override
                            public void mousePressed(MouseEvent e) {
                                super.mousePressed(e);
                                if(punti.contains(new Point(m,n))){
                                    punti.remove(new Point(m,n));
                                    boardGUI[m][n].setBackground(null);
                                } else if (!puntiVisitati.contains(new Point(m,n))) {
                                    punti.add(new Point(m,n));
                                    boardGUI[m][n].setBackground(Color.CYAN);

                                }
                            }
                        });
                        constructPanel.add(boardGUI[i][j]);
                    }
                }
                cardLayout.show(mainPanel,"ConstructScrean");
            }
        });
        JButton repoButton = new JButton("Salvati");
        repoButton.addActionListener(a->{
            cardLayout.show(mainPanel,"RepoScrean");
        });


        startPanel.add(initialLable);
        starPanel.add(repoButton);
        startPanel.add(sizePanel);


        startPanel.add(starPanel);
        mainPanel.add(startPanel,"StartScrean");
    }

    private void addTarget(JPanel jPanel, String inp) {
        JLabel l = new JLabel(inp);
        l.setBounds(5,0,30,18);
        l.setFont(new Font("Arial",Font.BOLD,18));

        jPanel.add(l);
        jPanel.revalidate();
        jPanel.repaint();
    }
    private void inserisciValore(int m,int n, String v){
        if(v!=null && !v.isEmpty()){
            int val=Integer.parseInt(v);
            if(val<=size && val>0)
                b.getBoard()[m][n]=val;
        }
    }
    private void inizializza(){
        boardGUI = new JPanel[size][size];
        for(int i=0;i<size;i++){
            for(int j=0;j<size;j++){
                boardGUI[i][j]=new JPanel(null);
                boardGUI[i][j].setPreferredSize(new Dimension(C_SIZE,C_SIZE));
                boardGUI[i][j].setBorder(new MatteBorder(1,1,1,1, Color.DARK_GRAY));
                int m=i;int n=j;
                JTextField textField = new JTextField();
                textField.setHorizontalAlignment(SwingConstants.CENTER);
                textField.setBounds(0,0,C_SIZE+30,C_SIZE+20);
                textField.setOpaque(false);
                textField.setBorder(null);
                textField.getDocument().addDocumentListener(new DocumentListener() {
                    @Override
                    public void insertUpdate(DocumentEvent e) {
                        inserisciValore(m,n,textField.getText());
                    }

                    @Override
                    public void removeUpdate(DocumentEvent e) {
                        inserisciValore(m,n,textField.getText());
                    }

                    @Override
                    public void changedUpdate(DocumentEvent e) {
                        inserisciValore(m,n,textField.getText());
                    }
                });
                boardGUI[i][j].add(textField);
                boardPanel.add(boardGUI[i][j]);
//                boardGUI[i][j].addMouseListener(new MouseListener() {
//                    @Override
//                    public void mouseClicked(MouseEvent e) {
//
//                    }
//
//                    @Override
//                    public void mousePressed(MouseEvent e) {
//                        if(SwingUtilities.isRightMouseButton(e)){
//                            String inp = JOptionPane.showInputDialog("Inserisci target: ");
//                            if(inp!=null && !inp.isEmpty())
//                                addTarget(boardGUI[m][n],inp);
//                        }else{
//
//                        }
//                    }
//
//                    @Override
//                    public void mouseReleased(MouseEvent e) {
//
//                    }
//
//                    @Override
//                    public void mouseEntered(MouseEvent e) {
//
//                    }
//
//                    @Override
//                    public void mouseExited(MouseEvent e) {
//
//                    }
//                });
            }
        }
        paintCage();
        boardPanel.revalidate();
        boardPanel.repaint();
    }

    private void paintCage() {
        for(Cage c:b.getCages()){
            Random random = new Random();
            Color color = Color.getHSBColor(random.nextFloat(),0.6f,0.8f);
            addTarget(boardGUI[c.getPoints().get(0).getM()][c.getPoints().get(0).getN()],String.valueOf(c.getTarget())+c.getOperation().toString());
            for(Point p:c.getPoints()){
                boardGUI[p.getM()][p.getN()].setBackground(color);
                //boardGUI[p.getM()][p.getN()].setBorder(new MatteBorder(1,1,1,1, Color.DARK_GRAY));

                int top = 3,left=3,bottom=3,right=3;
                if(c.getPoints().contains(new Point(p.getM()-1,p.getN())))
                    top=1;
                if(c.getPoints().contains(new Point(p.getM()+1,p.getN())))
                    bottom=1;
                if(c.getPoints().contains(new Point(p.getM(),p.getN()-1)))
                    left=1;
                if(c.getPoints().contains(new Point(p.getM(),p.getN()+1)))
                    right=1;
                boardGUI[p.getM()][p.getN()].setBorder(BorderFactory.createMatteBorder(top,left,bottom,right,Color.BLACK));
            }
        }
    }


    public static void main(String... args){
        KenKenView k = new KenKenView(4);
        k.setVisible(true);
    }
}
