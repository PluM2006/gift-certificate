package ru.clevertec.ecl.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.clevertec.ecl.entity.Certificate;

import java.util.List;
import java.util.Optional;

public interface CertificateRepository extends JpaRepository<Certificate, Long> {

    Optional<Certificate> findByName(String name);

    @Query("SELECT c, t FROM Certificate c " +
            "LEFT JOIN c.tags t " +
            "WHERE (LOWER(c.description)  LIKE CONCAT('%',LOWER(:description),'%') or :description IS null )" +
            "AND (LOWER(t.name) = LOWER(:tagName) OR :tagName IS null)")
    List<Certificate> findByTagNameDescription(String tagName, String description, Pageable pageable);

}
