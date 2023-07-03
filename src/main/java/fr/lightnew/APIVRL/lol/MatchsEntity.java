package fr.lightnew;

public class MatchsEntity {

    private boolean finish;
    private final String date;
    private final String hour;
    private final String name_team_1;
    private final String name_team_2;
    private final String logo_team_1;
    private final String logo_team_2;
    private final String score_team_1;
    private final String score_team_2;


    public MatchsEntity (boolean finish, String date, String hour, String nameTeam1, String nameTeam2, String logoTeam1, String logoTeam2, String score_team_1, String score_team_2) {
        this.finish = finish;
        this.date = date;
        this.hour = hour;
        name_team_1 = nameTeam1;
        name_team_2 = nameTeam2;
        logo_team_1 = logoTeam1;
        logo_team_2 = logoTeam2;
        this.score_team_1 = score_team_1;
        this.score_team_2 = score_team_2;
    }

    public boolean isFinish(){
        return finish;
    }

    public String getDate() {
        return date;
    }

    public String getHour() {
        return hour;
    }

    public String getLogo_team_1() {
        return logo_team_1;
    }

    public String getLogo_team_2() {
        return logo_team_2;
    }

    public String getName_team_1() {
        return name_team_1;
    }

    public String getName_team_2() {
        return name_team_2;
    }

    public String getScore_team_1() {
        return score_team_1;
    }

    public String getScore_team_2() {
        return score_team_2;
    }

    public void setFinish(boolean finish) {
        this.finish = finish;
    }

    @Override
    public String toString() {
        return "MatchsEntity{" +
                "finish=" + finish +
                ", date='" + date + '\'' +
                ", hour='" + hour + '\'' +
                ", name_team_1='" + name_team_1 + '\'' +
                ", name_team_2='" + name_team_2 + '\'' +
                ", logo_team_1='" + logo_team_1 + '\'' +
                ", logo_team_2='" + logo_team_2 + '\'' +
                ", score_team_1='" + score_team_1 + '\'' +
                ", score_team_2='" + score_team_2 + '\'' +
                '}';
    }
}
