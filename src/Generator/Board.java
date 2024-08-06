package Generator;

import Solver.Cage;

import java.util.List;

public class Board {
    private final int n;
    private int[][] board;
    private List<Cage> cages;

    public Board(int[][]b,List<Cage>c) {
        this.n = b.length;
        this.board=b;
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
