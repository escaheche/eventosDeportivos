package cl.eventos.deportivos.servicios;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class GeocodingService {

	public Map<String, Double> getLatLongFromAddress(String address) {
		RestTemplate restTemplate = new RestTemplate();
		String url = "https://nominatim.openstreetmap.org/search";

		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url).queryParam("q", address)
				.queryParam("format", "json").queryParam("addressdetails", 1).queryParam("limit", 1);

		Map<String, Double> coordinates = new HashMap<>();
		try {
			Map[] response = restTemplate.getForObject(builder.toUriString(), Map[].class);
			if (response != null && response.length > 0) {
				Map<String, Object> location = response[0];
				coordinates.put("lat", Double.parseDouble((String) location.get("lat")));
				coordinates.put("lng", Double.parseDouble((String) location.get("lon")));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return coordinates;
	}

}
