package my.com.website.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.data.domain.Page;

import java.time.Instant;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Data
public class WebsitePageDTO {

    private List<WebsiteDTO> websiteDTOList;
    private long totalElements;
    private int totalPages;
    private int currentPage;
    @JsonProperty("isFirst")
    private boolean isFirst;
    @JsonProperty("isLast")
    private boolean isLast;
    private boolean hasNext;
    private boolean hasPrevious;

    public WebsitePageDTO(Page<WebsiteDTO> websitePage){
        this.setWebsiteDTOList(websitePage.getContent());
        this.setTotalElements(websitePage.getTotalElements());
        this.setTotalPages(websitePage.getTotalPages());
        this.setCurrentPage(websitePage.getNumber()+1);
        this.setFirst(websitePage.isFirst());
        this.setLast(websitePage.isLast());
        this.setHasNext(websitePage.hasNext());
        this.setHasPrevious(websitePage.hasPrevious());
    }
}
