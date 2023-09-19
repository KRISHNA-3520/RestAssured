package stepDefinations;

import java.io.IOException;

import io.cucumber.java.Before;

public class Hooks {

	@Before("@DeletePlace")
	public void BeforeHook() throws IOException {

		StepDefination d = new StepDefination();
		if (StepDefination.place_id == null) {
			d.add_place_payload_with("Deepak", "Romanian", "Russia");
			d.user_calls_with_http_request("AddPlaceAPI", "POST");
			d.verify_place_id_created_maps_to_using("Deepak", "GetPlaceAPI");
		}
	}
}
