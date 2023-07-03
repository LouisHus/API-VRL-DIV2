package fr.lightnew.APIVRL.lol;

import fr.lightnew.MatchsEntity;
import fr.lightnew.RoosterEntity;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Div2 {

    public static List<ClassementEntity> classement = new ArrayList<>();
    public static List<MatchsEntity> matchs = new ArrayList<>();

    public static List<RoosterEntity> getRooster(String idTeam) {
        List<RoosterEntity> rooster = new ArrayList<>();
        try {
            Document doc = Jsoup.connect("https://division2lol.fr/tournois/6730754298049781760/equipes/" + idTeam).get();
            Elements players = doc.select(".player");

            for (Element element : players) {
                String logo = "";
                for (Element e2 : element.getElementsByClass("photo"))
                    logo = e2.select("img").attr("src");
                String name = element.getElementsByClass("name").text();
                String pseudo = element.getElementsByClass("pseudo").text();
                rooster.add(new RoosterEntity(logo, name, pseudo));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return rooster;
    }

    public static List<MatchsEntity> matchsTeam(String idTeam) {
        List<MatchsEntity> matchs = new ArrayList<>();
        try {
            Document doc = Jsoup.connect("https://division2lol.fr/tournois/6730754298049781760/equipes/" + idTeam).get();
            Elements element_matches = doc.select(".match-row ");
            Elements titles = doc.select(".title");

            for (Element elem : titles) {
                if (elem.text().equalsIgnoreCase("Prochains matchs")) {
                    for (Element element : element_matches) {
                        // A REVOIR
                        String date = element.getElementsByClass("date").text();
                        String time = element.getElementsByClass("time").text();

                        String logo_1 = "";
                        String logo_2 = "";
                        String name1 = "";
                        String name2 = "";
                        for (Element e : element.getElementsByClass("match")) {
                            //loss
                            for (Element e1 : e.getElementsByClass("team-row neutral")) {
                                for (Element e2 : e1.getElementsByClass("logo"))
                                    logo_1 = e2.select("img").attr("src");
                                for (Element e3 : e1.getElementsByClass("none name neutral"))
                                    name1 = e3.text();
                            }

                            for (Element e1 : e.getElementsByClass("team-row")) {
                                for (Element e2 : e1.getElementsByClass("logo"))
                                    logo_2 = e2.select("img").attr("src");

                                for (Element e3 : e1.getElementsByClass("none name neutral"))
                                    name2 = e3.text();
                            }
                        }
                        if (name1 == "" || name1 == null)
                            continue;
                        else
                            matchs.add(new MatchsEntity(false, date, time, name1, name2, logo_1, logo_2, "", ""));

                    }
                }

                //System.out.println("\n");

                if (elem.text().equalsIgnoreCase("Derniers matchs")) {
                    for (Element element : element_matches) {
                        String date = element.getElementsByClass("date").text();
                        String time = element.getElementsByClass("time").text();
                        String loss_name = element.getElementsByClass("loss name neutral").text();
                        String win_name = element.getElementsByClass("win name neutral").text();
                        String loss_score = element.getElementsByClass("loss score").text();
                        String win_score = element.getElementsByClass("win score").text();
                        String logo_win = "";
                        String logo_loss = "";
                        for (Element e : element.getElementsByClass("match")) {
                            //loss
                            for (Element e1 : e.getElementsByClass("team-row neutral"))
                                for (Element e2 : e1.getElementsByClass("logo"))
                                    logo_loss = e2.select("img").attr("src");

                            for (Element e1 : e.getElementsByClass("team-row"))
                                for (Element e2 : e1.getElementsByClass("logo"))
                                    logo_win = e2.select("img").attr("src");
                        }
                        if (loss_name.length() == 0)
                            continue;
                        else
                            matchs.add(new MatchsEntity(false, date, time, loss_name, win_name, logo_loss, logo_win, loss_score, win_score));

                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return matchs;
    }

    public static void send() {
        /*TAKE MATCHES*/
        try {
            matchs.addAll(matchsTeam("6730961969577861120"));

            /*TAKE RESULTS*/
            Document document = Jsoup.connect("https://division2lol.fr/resultats").get();
            Elements element_results = document.select(".ranking-row");
            for (Element elements : element_results) {
                String position = elements.getElementsByClass("position").text();
                String name = String.format("%-4s", elements.getElementsByClass("name-responsive").text()).replace(' ', ' ');
                String played = elements.getElementsByClass("played").text();
                String winned = elements.getElementsByClass("winned").text();
                String lossed = elements.getElementsByClass("lossed").text();
                String points = elements.getElementsByClass("points").text();
                if (!position.equalsIgnoreCase("pos."))
                    classement.add(new ClassementEntity(position, name, played, winned, lossed, points));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
