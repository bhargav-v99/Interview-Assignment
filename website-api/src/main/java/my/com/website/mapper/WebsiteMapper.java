package my.com.website.mapper;

import my.com.website.dto.WebsiteDTO;
import my.com.website.entity.Website;
import org.springframework.stereotype.Component;

@Component
public class WebsiteMapper {

    public WebsiteDTO toDto(Website website){
        return new WebsiteDTO(website.getId(), website.getName(), website.getUrl(), website.getCreatedAt());
    }
}
