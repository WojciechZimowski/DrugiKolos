package game;

public class Duel {
    private Player player1;
    private Player player2;
    private Gesture gesture1;
    private Gesture gesture2;
    private Runnable onEnd;

    public Duel(Player player1,Player player2){
        this.player1 = player1;
        this.player2 = player2;

        player1.enterDuel(this);
        player2.enterDuel(this);
    }
    public void setGesture(Player player,Gesture gesture){
        if(player==player1){
            this.gesture1 = gesture;
        }else if(player==player2){
            this.gesture2 = gesture;
        }
    }
    public Gesture getGesture(Player player){
        if(player==player1){
            return gesture1;
        }else if(player==player2){
            return gesture2;
        }
        return null;

    }
    public void handleGesture(Player player,Gesture gesture){
        setGesture(player,gesture);
        System.out.println(player+"gestured"+gesture);
    }
    public record Result(Player winner,Player looser){}
    public Result evaluate(){
        if(gesture1 ==null || gesture2 ==null){
            return null;
        }
        int compare = gesture1.compareWith(gesture2);
        Result result = null;
        if(compare>0){
            result= new Result(player1,player2);
        }else if(compare<0){
            result= new Result(player2,player1);
        }
        player1.leaveDuel();
        player2.leaveDuel();
        return result;
    }
    public void setOnEnd(Runnable onEnd){
        this.onEnd= onEnd;
    }

}
