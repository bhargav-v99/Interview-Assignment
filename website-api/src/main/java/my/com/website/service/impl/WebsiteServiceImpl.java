package my.com.website.service.impl;

import lombok.AllArgsConstructor;
import my.com.website.dto.CreateWebsiteDTO;
import my.com.website.dto.WebsiteDTO;
import my.com.website.dto.WebsitePageDTO;
import my.com.website.entity.Website;
import my.com.website.exception.RecordNotFoundException;
import my.com.website.exception.WebsiteAlreadyExistsException;
import my.com.website.mapper.WebsiteMapper;
import my.com.website.repo.WebsiteRepository;
import my.com.website.service.IWebsiteService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Optional;

/**
 * Implementation of the WebsiteService interface.
 * Provides methods for retrieving and creating websites.
 */
@Service
@Transactional
@AllArgsConstructor
public class WebsiteServiceImpl implements IWebsiteService {

    private WebsiteRepository repository;
    private WebsiteMapper mapper;

    /**
     * Retrieves a page of websites
     */
    @Override
    @Transactional(readOnly = true)
    public WebsitePageDTO getWebsites(Integer page, Integer pageSize) {
        // Normalize pagination parameters to 0-indexed and default values
        page = page < 1 ? 0 : page-1;
        pageSize = pageSize < 1 ? 5 : pageSize;

        // Create a Pageable object to specify pagination and sorting
        Pageable pageable = PageRequest.of(page, pageSize, Sort.Direction.DESC, "createdAt");

        // Retrieve a page of websites from the repository
        Page<WebsiteDTO> websiteDTOPage = repository.findWebsites(pageable);

        // Wrap the retrieved page in a WebsitePageDTO and return it
        return new WebsitePageDTO(websiteDTOPage);
    }

    /**
     * Searches for websites by name
     */
    @Override
    @Transactional(readOnly = true)
    public WebsitePageDTO searchWebsites(String name, Integer pageNo, Integer pageSize) {
        // Normalize pagination parameters to 0-indexed and default values
        pageNo = pageNo < 1 ? 0 : pageNo-1;
        pageSize = pageSize < 1 ? 5 : pageSize;

        // Create a Pageable object to specify pagination and sorting
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.Direction.DESC, "createdAt");

        // Delegate the search to the repository
        Page<WebsiteDTO> websiteDTOPage = repository.searchWebsites(name, pageable);

        // Wrap the retrieved page in a WebsitePageDTO and return it
        return new WebsitePageDTO(websiteDTOPage);
    }

    /**
     * Creates a new website.
     */
    @Override
    public WebsiteDTO createWebsite(CreateWebsiteDTO request) {
        // Create a new Website object with the provided name, URL, and current timestamp
        Website website = new Website(null, request.getName(), request.getUrl(), Instant.now());

        // Check if a website with the same name already exists in the repository
        Optional<Website> optionalCustomer = repository.findByName(website.getName());
        // If a website with the same name exists, throw an exception
        if(optionalCustomer.isPresent()) {
            throw new WebsiteAlreadyExistsException("Website already exists for name : " +website.getName());
        }
        // Save the new website to the repository
        Website savedWebsite = repository.save(website);

        return mapper.toDto(website);
    }

    /**
     * Updates an existing website.
     */
    @Override
    public WebsiteDTO updateWebsite(Long id, CreateWebsiteDTO request) {
        // Retrieve the website with the given ID & throw exception if not found
        Website website = repository.findById(id).orElseThrow(
                () -> new RecordNotFoundException("Website not found with ID: " + id)
        );

        // Update the website's name and URL
        website.setName(request.getName());
        website.setUrl(request.getUrl());

        // Save the changes
        Website updatedWebsite = repository.save(website);

        // Return the updated website as a WebsiteDTO
        return mapper.toDto(updatedWebsite);
    }
}
