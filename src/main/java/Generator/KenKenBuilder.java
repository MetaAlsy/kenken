package Generator;

import Operations.*;

import java.util.*;

public class KenKenBuilder extends Subject implements Builder {

    private List<Cage> cages = new ArrayList<>();
    private int n;
    private int point;

    public KenKenBuilder(int n){
        this.n=n;
    }

    public void addCage(Cage c){
        cages.add(c);
        point+=c.getPoints().size();
        notifyObservers();
    }

    public Board build(){
        if(n*n == point)
            return new Board(new int[n][n],cages);
        else
            throw new IllegalArgumentException("Non tutti i punti appartengono alle reggioni");
    }
    public int getN(){
        return this.n;
    }

    public void removeCage(Cage c) {
        cages.remove(c);
        point-=c.getPoints().size();
        notifyObservers();
    }
    public List<Cage> getCages(){
        return cages;
    }

}
