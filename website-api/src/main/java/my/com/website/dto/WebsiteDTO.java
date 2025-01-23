package my.com.website.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.time.Instant;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Data
public class WebsiteDTO {

    private Long id;
    private String name;
    private String url;
    private String createdBy;
    private Instant createdAt;

}
