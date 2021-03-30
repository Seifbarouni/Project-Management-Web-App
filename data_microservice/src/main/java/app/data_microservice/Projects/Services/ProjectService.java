package app.data_microservice.Projects.Services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.data_microservice.Projects.Data.ProjectsRepository;
import app.data_microservice.Projects.Models.Project;

@Service
public class ProjectService {
    @Autowired
    private ProjectsRepository projectsRepository;

    public Project getProjectById(Long id) {
        Optional<Project> res = projectsRepository.findById(id);
        if (res.isEmpty()) {
            return null;
        }
        return res.get();
    }

    public Project addProject(Project pr) {
        if (pr != null) {
            projectsRepository.saveAndFlush(pr);
            return pr;
        }
        return null;
    }

    public void deleteProjectById(Long id) {
        if (projectsRepository.existsById(id)) {
            projectsRepository.deleteById(id);
        }
    }

    public Boolean isProjectExistant(Long id) {
        return projectsRepository.existsById(id);
    }

    public String updateProject(Project pr) {
        if (projectsRepository.existsById(pr.getId())) {
            projectsRepository.saveAndFlush(pr);
            return "Success";
        }
        return "Cannot find project";
    }

}
