package fr.lightnew.APIVRL.lol;

public class ClassementEntity {

    private final String position;
    private final String name;
    private final String matchsPlayed;
    private final String matchsWinned;
    private final String matchsLossed;
    private final String points;

    public ClassementEntity(String position, String name, String played, String winned, String lossed, String points) {
        this.position = position;
        this.name = name;
        this.matchsPlayed = played;
        this.matchsWinned = winned;
        this.matchsLossed = lossed;
        this.points = points;
    }

    public String getMatchsLossed() {
        return matchsLossed;
    }

    public String getMatchsPlayed() {
        return matchsPlayed;
    }

    public String getMatchsWinned() {
        return matchsWinned;
    }

    public String getPoints() {
        return points;
    }

    public String getPosition() {
        return position;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "ClassementEntity{" +
                "position=" + position +
                ", name='" + name + '\'' +
                ", matchsPlayed=" + matchsPlayed +
                ", matchsWinned=" + matchsWinned +
                ", matchsLossed=" + matchsLossed +
                ", points=" + points +
                '}';
    }
}
