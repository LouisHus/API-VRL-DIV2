package fr.lightnew.APIVRL.valorant;

import fr.lightnew.RoosterEntity;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class VRL {

    //11232
    public static List<RoosterEntity> getRooster(String idTeam) {
        List<RoosterEntity> rooster = new ArrayList<>();
        try {
            Document doc = Jsoup.connect("https://www.vlr.gg/team/" + idTeam).get();
            Elements players = doc.select(".team-roster-item");

            for (Element element : players) {
                String photo = "";
                for (Element e2 : element.getElementsByClass("team-roster-item-img"))
                    photo = "https:" + e2.select("img").attr("src");
                String name = element.getElementsByClass("team-roster-item-name-real").text();
                String pseudo = element.getElementsByClass("team-roster-item-name-alias").text();
                rooster.add(new RoosterEntity(photo, name, pseudo));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return rooster;
    }
}
