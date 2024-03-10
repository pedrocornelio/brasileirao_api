package br.com.phcc.brasileirao_api.util;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.phcc.brasileirao_api.dto.PartidaGoogleDTO;

public class ScrapingUtil {

//	private static final Logger LOGGER = LoggerFactory.getLogger(ScrapingUtil.class);
	private static final String URL_PATTERN = "https://www.google.com/search?q=";
	private static final String URL_LOCALE = "&hl=";
	private static final String URL_PTBR = "pt-BR";
	
	public static void main(String[] args) {
		String urlGoogle = "palmeiras+x+corinthians+08/08/2020";
		String url = URL_PATTERN.concat(urlGoogle).concat(URL_LOCALE).concat(URL_PTBR);

		ScrapingUtil scraping = new ScrapingUtil();
		scraping.obtemInformaçoesPartida(url);
			
	}
	
	public PartidaGoogleDTO obtemInformaçoesPartida (String url) {
		PartidaGoogleDTO partida = new PartidaGoogleDTO();
		
		Document document = null;
		
		try {
			document = Jsoup.connect(url).get();
			String title = document.title();
//			LOGGER.info(title);
			System.out.println(title);
			
			otemStatusPartida(document);
			
		} catch (IOException e) {
//			LOGGER.error("Erro ao conectar no Google com JSoup -> {}", e.getMessage());
			System.out.println(e.getMessage());
		}
		
		return partida;
	}
	
	public StatusPartida otemStatusPartida (Document document) {
		StatusPartida statusPartida = StatusPartida.PARTIDA_NAO_INICIADA;
		
		boolean isTemporPartida = document.select("div[class=imso_mh__pst-m-stts-l]").isEmpty();
		
		if (!isTemporPartida) {
			String tempoPartida = document.select("div[class=imso_mh__pst-m-stts-l]").first().text();
			statusPartida = StatusPartida.PARTIDA_EM_ANDAMENTO;
			System.out.println(tempoPartida);
		}
		
		return statusPartida;
	}

}
