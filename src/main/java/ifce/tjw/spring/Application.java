package ifce.tjw.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	public static Map<String,String> getToken(String token) {
		Base64.Decoder decoder = Base64.getUrlDecoder();
		String[] chunks = token.split("\\.");
		String payload = new String(decoder.decode(chunks[1]));

		String[] listPay = payload.split(",");
		Map<String,String> mapPayload = new HashMap<>();

		for(String pair : listPay){
			pair = pair.replace("{", "");
			pair = pair.replace("}", "");
			String[] entry = pair.split(":");
			entry[0] = entry[0].replaceAll("\"", "");
			entry[1] = entry[1].replaceAll("\"", "");
			mapPayload.put(entry[0].trim(), entry[1].trim());
		}

		return mapPayload;
	}
}
