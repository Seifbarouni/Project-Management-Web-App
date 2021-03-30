package app.data_microservice.prId_usrId.Data;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import app.data_microservice.prId_usrId.Models.ProjectWithUser;

public interface ProjectWithUserRepository extends JpaRepository<ProjectWithUser, Long> {
    Optional<List<ProjectWithUser>> findByUserId(Long id);

    Optional<List<ProjectWithUser>> findByProjectId(Long id);

    void deleteByProjectId(Long id);

    Long countByProjectId(Long id);

    Long countByUserId(Long id);

}
