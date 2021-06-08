import java.io.Serializable;

public class PlayerData implements Serializable {
    private int p1Wickets;
    private int p2Wickets;
    private int p1Score;
    private int p2Score;
    private boolean have2players;
    private int Winner;
    private String p1Batting;
    private String p1Bowling;
    private String p2Batting;
    private String p2Bowling;
    private boolean Bowling;
    private boolean Batting;

    PlayerData(){
        p1Wickets = 10;
        p2Wickets = 10;
        p1Score = 0;
        p2Score = 0;
        have2players = false;
        Winner = 0;
        p1Batting = " ";
        p2Batting = " ";
        p1Bowling = " ";
        p2Bowling = " ";
        Bowling = false;
        Batting = false;
    }


}