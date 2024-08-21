package Command;

import Generator.Cage;
import Generator.KenKenBuilder;

import javax.swing.*;

public class CageCommand implements Command{
    private KenKenBuilder builder;
    private Cage cage;
    private JPanel[][] board;

    public CageCommand(KenKenBuilder b, Cage c){
        this.builder=b;
        this.cage=c;
    }



    @Override
    public boolean doIt() {
        builder.addCage(cage);
        return true;
    }

    @Override
    public boolean undoIt() {
        builder.removeCage(cage);
        return true;
    }
}
