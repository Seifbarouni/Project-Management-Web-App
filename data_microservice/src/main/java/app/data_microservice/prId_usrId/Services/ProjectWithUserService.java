package app.data_microservice.prId_usrId.Services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import app.data_microservice.prId_usrId.Data.ProjectWithUserRepository;
import app.data_microservice.prId_usrId.Models.ProjectWithUser;

@Service
public class ProjectWithUserService {
    @Autowired
    private ProjectWithUserRepository projectWithUserRepository;

    public List<ProjectWithUser> getProjectById(Long id) {
        Optional<List<ProjectWithUser>> res = projectWithUserRepository.findByUserId(id);
        if (res.isEmpty()) {
            return new ArrayList<ProjectWithUser>();
        }
        return res.get();
    }

    public void addProjectWithUser(ProjectWithUser pr) {
        if (pr != null) {
            projectWithUserRepository.save(pr);
        }
    }

    public void deleteProjectWithUserByProjectId(Long id) {
        if (id != 0) {
            projectWithUserRepository.deleteByProjectId(id);
        }
    }

    public Boolean isProjectWithUserExistant(ProjectWithUser pr) {
        return projectWithUserRepository.exists(Example.of(pr));
    }

    public Long getNumberOfColl(Long id) {
        if (id != 0 && projectWithUserRepository.existsById(id)) {
            return projectWithUserRepository.countByProjectId(id);
        }
        return 0L;
    }

    public Long getNumberOfProjectsByUserId(Long userId) {
        return projectWithUserRepository.countByUserId(userId);
    }

    public List<Long> getCollaboratorsByprojectId(Long projectId, Long userId) {

        Optional<List<ProjectWithUser>> prs = projectWithUserRepository.findByProjectId(projectId);
        if (!prs.isEmpty()) {
            List<Long> res = new ArrayList<Long>();
            for (ProjectWithUser pr : prs.get()) {
                if (!pr.getUserId().equals(userId)) {
                    res.add(pr.getUserId());
                }
            }
            return res;
        }
        return null;
    }

}
