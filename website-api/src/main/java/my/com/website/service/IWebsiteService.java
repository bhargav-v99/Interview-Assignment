package my.com.website.service;

import my.com.website.dto.CreateWebsiteDTO;
import my.com.website.dto.WebsiteDTO;
import my.com.website.dto.WebsitePageDTO;

public interface IWebsiteService {

    WebsitePageDTO getWebsites(Integer page, Integer pageSize);

    WebsitePageDTO searchWebsites(String name, Integer page, Integer pageSize);

    WebsiteDTO createWebsite(CreateWebsiteDTO request);

    WebsiteDTO updateWebsite(Long id, CreateWebsiteDTO request);

}
