package fr.lightnew.APIVRL;

import fr.lightnew.APIVRL.lol.ClassementEntity;
import fr.lightnew.APIVRL.lol.Div2;
import fr.lightnew.APIVRL.valorant.VRL;
import fr.lightnew.MatchsEntity;
import fr.lightnew.RoosterEntity;
import org.json.JSONObject;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

@SpringBootApplication
@RestController
public class ApivrlApplication implements ErrorController {

	private static final String PATH = "/error";

	public static void main(String[] args) {
		SpringApplication.run(ApivrlApplication.class, args);
	}

	/*
	* MAPPING
	*/

	private ResponseEntity<String> sendPage(String indexPage) {
		try {
			Resource resource = new ClassPathResource(indexPage);
			String htmlContent = new String(resource.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
			return ResponseEntity.ok().contentType(MediaType.TEXT_HTML).body(htmlContent);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	@RequestMapping(value = PATH)// if bad url -> error.html
	public ResponseEntity<String> handleError() {
		return sendPage("./templates/error.html");
	}

	/*
	* JobLife
	*/

	@GetMapping("/matchs/joblife")
	public ModelAndView matchsJoblifeRequest() {
		ModelAndView modelAndView = new ModelAndView();
		JSONObject jo = new JSONObject();
		Div2.send();
		for (int i = 0; i < Div2.matchs.size(); i++) {
			MatchsEntity classement = Div2.matchs.get(i);
			jo.put(classement.getDate(), classement);
		}

		modelAndView.setViewName("matchs");
		modelAndView.addObject("json", jo.toString());

		return modelAndView;
	}

	@GetMapping("/api/div2/classement")
	public ModelAndView div2ClassementRequest() {
		ModelAndView modelAndView = new ModelAndView();
		JSONObject jo = new JSONObject();
		Div2.send();
		for (int i = 0; i < Div2.classement.size(); i++) {
			ClassementEntity classement = Div2.classement.get(i);
			jo.put(classement.getName(), classement);
		}

		modelAndView.setViewName("classement");
		modelAndView.addObject("json", jo.toString());

		return modelAndView;
	}

	/*
	* DIV2
	*/

	@GetMapping("/api/div2/matchs/{id}")
	public ModelAndView div2MatchsRequest(@PathVariable("id") String id) {
		ModelAndView modelAndView = new ModelAndView();
		JSONObject jo = new JSONObject();
		List<MatchsEntity> matchs = Div2.matchsTeam(String.valueOf(id));
		for (int i = 0; i < matchs.size(); i++) {
			MatchsEntity match = matchs.get(i);
			jo.put(match.getDate(), match);
		}

		modelAndView.setViewName("matchs");
		modelAndView.addObject("json", jo.toString());

		return modelAndView;
	}

	@GetMapping("/api/div2/rooster/{id}")
	public ModelAndView div2RoosterRequest(@PathVariable("id") String id) {
		ModelAndView modelAndView = new ModelAndView();
		JSONObject jo = new JSONObject();
		List<RoosterEntity> matchs = Div2.getRooster(String.valueOf(id));
		for (int i = 0; i < matchs.size(); i++) {
			RoosterEntity rooster = matchs.get(i);
			jo.put(rooster.getPseudo(), rooster);
		}

		modelAndView.setViewName("rooster");
		modelAndView.addObject("json", jo.toString());

		return modelAndView;
	}

	/*
	* VRL
	*/

	@GetMapping("/api/vrl/team/{id}")
	public ModelAndView vrlRequest(@PathVariable("id") Long id) throws IOException {
		ModelAndView modelAndView = new ModelAndView();
		Connection.Response document = Jsoup.connect("https://www.vlr.gg/team/" + id).execute();
		int statutsCode = document.statusCode();
		if (statutsCode == 404) {
			modelAndView.setViewName("error");
			return modelAndView;
		}
		JSONObject jo = new JSONObject();
		jo.put("", "");

		modelAndView.setViewName("team");
		modelAndView.addObject("json", "{}");

		return modelAndView;
	}

	@GetMapping("/api/vrl/rooster/{id}")
	public ModelAndView vrlRoosterRequest(@PathVariable("id") Long id) throws IOException {
		ModelAndView modelAndView = new ModelAndView();
		Connection.Response document = Jsoup.connect("https://www.vlr.gg/team/" + id).execute();
		int statutsCode = document.statusCode();
		if (statutsCode == 404) {
			modelAndView.setViewName("error");
			return modelAndView;
		}
		JSONObject jo = new JSONObject();
		List<RoosterEntity> roosters = VRL.getRooster(String.valueOf(id));
		for (int i = 0; i < roosters.size(); i++) {
			RoosterEntity rooster = roosters.get(i);
			jo.put(rooster.getPseudo(), rooster);
		}

		modelAndView.setViewName("rooster");
		modelAndView.addObject("json", jo.toString());

		return modelAndView;
	}
}
