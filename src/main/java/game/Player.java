package game;

public class Player {
    private Duel duel;

    public void makeGesture(Gesture gesture){
        if(duel!=null){
            duel.handleGesture(this,gesture);
        }
    }
    public void enterDuel(Duel duel){
        this.duel=duel;
    }
    public void leaveDuel(){
        this.duel=null;
    }
    public boolean isDueling(){return duel !=null;}
    public Duel getDuel(){return duel;}

}
