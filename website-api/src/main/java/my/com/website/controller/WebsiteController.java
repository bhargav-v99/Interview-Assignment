package my.com.website.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import my.com.website.dto.CreateWebsiteDTO;
import my.com.website.dto.WebsiteDTO;
import my.com.website.dto.WebsitePageDTO;
import my.com.website.service.IWebsiteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;

@RestController
@RequestMapping("/api/websites")
@AllArgsConstructor
public class WebsiteController {

    private static final Logger logger = LoggerFactory.getLogger(WebsiteController.class);

    private IWebsiteService service;

    @GetMapping()
    public ResponseEntity<WebsitePageDTO> getBookmarks(@RequestParam(name = "page", defaultValue = "1") Integer page,
                                       @RequestParam(name = "pageSize", defaultValue = "5") Integer pageSize,
                                       @RequestParam(name = "name", required = false) String name){

        logger.info("Request : page: {}, pageSize: {}, query: {}", page, pageSize, name);

        // Check if the search name is null or empty
        WebsitePageDTO websitePageDTO;
        if (name == null || name.trim().isEmpty()) {
            // If name is empty, retrieve all websites
            websitePageDTO = service.getWebsites(page, pageSize);
        } else {
            // If name is not empty, search for websites matching the name
            websitePageDTO = service.searchWebsites(name, page, pageSize);
        }

        logger.info(websitePageDTO.toString());
        return ResponseEntity.status(HttpStatus.OK).body(websitePageDTO);
    }

    @PostMapping
    public ResponseEntity<WebsiteDTO> createWebsite(@RequestBody @Valid CreateWebsiteDTO request){
        logger.info("Request : {}", request);

        // Delegate the creation of the website to the service layer
        WebsiteDTO websiteDTO = service.createWebsite(request);
        logger.info("Response : {}", websiteDTO);

        // Return the created website as a response entity with a 201 Created status code
        return ResponseEntity.status(HttpStatus.CREATED).body(websiteDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<WebsiteDTO> updateWebsite(@PathVariable Long id, @RequestBody @Valid CreateWebsiteDTO request) {
        logger.info("Request : id: {}, request: {}", id, request);

        // Delegate the update of the website to the service layer
        WebsiteDTO websiteDTO = service.updateWebsite(id, request);
        logger.info("Response : {}", websiteDTO);

        // Return the updated website as a response entity with a 200 OK status code
        return ResponseEntity.status(HttpStatus.OK).body(websiteDTO);
    }

}
