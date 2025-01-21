package my.com.website.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CreateWebsiteDTO {

    @NotEmpty(message = "Please enter Name")
    private String name;

    @NotEmpty(message = "Please enter URL")
    private String url;
}
