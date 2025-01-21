package my.com.website;

import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class WebsiteApiApplicationTests {

	@Autowired
	private MockMvc mvc;

	@ParameterizedTest
	@CsvSource({"1,15,3,1", "2,15,3,2", "3,15,3,3"})
	void shouldGetWebsites(int pageNo, int totalElements, int totalPges, int currentPage) throws Exception {
		this.mvc.perform(MockMvcRequestBuilders.get("/api/websites?page=" + pageNo)).
				andExpect(status().isOk()).
				andExpect(jsonPath("$.totalElements", CoreMatchers.equalTo(totalElements))).
				andExpect(jsonPath("$.totalPages", CoreMatchers.equalTo(totalPges))).
				andExpect(jsonPath("$.currentPage", CoreMatchers.equalTo(currentPage)));

	}

	@ParameterizedTest
	@CsvSource({"'GitHub','https://github.com/'"})
	void createWebsiteSuccessfully(String title, String url) throws Exception {
		this.mvc.perform(MockMvcRequestBuilders.post("/api/websites").
						contentType(MediaType.APPLICATION_JSON).content("""
                                 {
                                 "name": "GitHub",
                                 "url": "https://github.com"
                                }
                                """)).
				andExpect(status().isCreated()).
				andExpect(jsonPath("$.id", notNullValue())).
				andExpect(jsonPath("$.name", is(title))).
				andExpect(jsonPath("$.url", is(url)));
	}

	@Test
	void failToCreateIfWebsiteAlreadyExists() throws Exception {
		this.mvc.perform(MockMvcRequestBuilders.post("/api/websites").
						contentType(MediaType.APPLICATION_JSON).content("""
                                 {
                                 "name": "YouTube",
                                 "url": "https://www.youtube.com"
                                }
                                """)).
				andExpect(status().isConflict());
	}

	@Test
	void failToUpdateIfWebsiteNotExists() throws Exception {
		this.mvc.perform(MockMvcRequestBuilders.put("/api/websites/20").
						contentType(MediaType.APPLICATION_JSON).content("""
								 {
								 "name": "YouTube",
								 "url": "https://www.youtube.com"
								}
								""")).
				andExpect(status().isNotFound());
	}

}
