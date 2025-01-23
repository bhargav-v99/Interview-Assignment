package my.com.website.repo;

import my.com.website.dto.WebsiteDTO;
import my.com.website.entity.Website;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface WebsiteRepository extends JpaRepository<Website, Long> {

    @Query("select new my.com.website.dto.WebsiteDTO(b.id, b.name, b.url, b.createdBy, b.createdAt) from Website b")
    Page<WebsiteDTO> findWebsites(Pageable pageable);

    @Query("""
            select new my.com.website.dto.WebsiteDTO(b.id, b.name, b.url, b.createdBy, b.createdAt) from Website b 
            where lower(b.name) like lower(concat('%',:name,'%'))
            """)
    Page<WebsiteDTO> searchWebsites(String name, Pageable pageable);

    Optional<Website> findByName(String name);
}
