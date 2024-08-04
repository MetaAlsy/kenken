package Generator;

import Solver.Cage;

import java.util.List;

public class Board {
    private final int n;
    private int[][] board;
    private List<Cage> cages;

    public Board(int n) {
        this.n = n;
        this.board=new int[n][n];
    }

    public int[][] getBoard() {
        return board;
    }

    public List<Cage> getCages() {
        return cages;
    }

    public void setCages(List<Cage> cages) {
        this.cages = cages;
    }
}
