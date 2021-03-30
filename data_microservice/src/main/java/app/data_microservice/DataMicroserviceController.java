package app.data_microservice;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import app.data_microservice.Projects.Models.Project;
import app.data_microservice.Projects.Services.ProjectService;
import app.data_microservice.prId_usrId.Models.ProjectWithUser;
import app.data_microservice.prId_usrId.Services.ProjectWithUserService;

@RestController()
@RequestMapping("/data")
public class DataMicroserviceController {

    @Autowired
    private ProjectService projectService;
    @Autowired
    private ProjectWithUserService projectWithUserService;

    @GetMapping("/{id}")
    @Cacheable(value = "pr_with_usr", key = "#root.args[0]")
    public List<Project> getData(@PathVariable(name = "id") Long id) {
        List<ProjectWithUser> projectsByUser = projectWithUserService.getProjectById(id);
        List<Project> res = new ArrayList<Project>();

        if (!projectsByUser.isEmpty()) {

            for (ProjectWithUser project : projectsByUser) {
                res.add(projectService.getProjectById(project.getProjectId()));
            }
        }
        return res;
    }

    @GetMapping("/project/{id}")
    @Cacheable(value = "pr")
    public Project getProject(@PathVariable(name = "id") Long id) {
        return projectService.getProjectById(id);
    }

    @PostMapping("/{id}")
    @Caching(evict = { @CacheEvict(value = "pr_with_usr", key = "#root.args[0]"),
            @CacheEvict(value = "nb", allEntries = true), @CacheEvict(value = "nb_pr", allEntries = true),
            @CacheEvict(value = "nb_clb", allEntries = true) })
    public String addProject(@PathVariable(name = "id") Long id, @RequestBody Project pr) {
        if (id != 0 && pr != null) {
            pr = projectService.addProject(pr);
            if (pr != null) {
                ProjectWithUser prw = new ProjectWithUser(pr.getId(), id);
                projectWithUserService.addProjectWithUser(prw);
                return "Success";
            }
        }
        return "Problem";
    }

    @PostMapping("/addUserToProject/{userId}/{projectId}")
    @Caching(evict = { @CacheEvict(value = "pr_with_usr", key = "#root.args[0]"),
            @CacheEvict(value = "nb", allEntries = true), @CacheEvict(value = "nb_pr", allEntries = true),
            @CacheEvict(value = "nb_clb", allEntries = true) })
    public void addUserToProject(@PathVariable(name = "userId") Long userId,
            @PathVariable(name = "projectId") Long projectId) {
        if (projectId != 0) {
            if (projectService.isProjectExistant(projectId)) {
                ProjectWithUser prw = new ProjectWithUser(projectId, userId);
                if (!projectWithUserService.isProjectWithUserExistant(prw)) {
                    projectWithUserService.addProjectWithUser(prw);
                }
            }
        }
    }

    @GetMapping("/getNumber/{projectId}")
    @Cacheable("nb")
    public Long getNumberOfCollaboratorsByProjectId(@PathVariable(name = "projectId") Long projectId) {
        return projectWithUserService.getNumberOfColl(projectId);
    }

    @GetMapping("/getprojectsnum/{userId}")
    @Cacheable("nb_pr")
    public Long getNumberOfProjectsByUserId(@PathVariable(name = "userId") Long userId) {
        return projectWithUserService.getNumberOfProjectsByUserId(userId);
    }

    @GetMapping("/getcollaborators/{projectId}/{userId}")
    @Cacheable("nb_clb")
    public List<Long> getCollaboratorsByProjectId(@PathVariable(name = "userId") Long userId,
            @PathVariable(name = "projectId") Long projectId) {
        return projectWithUserService.getCollaboratorsByprojectId(projectId, userId);
    }

    @GetMapping("/prwithusr/{projectId}/{userId}")
    public Boolean isProjectWithUserExistant(@PathVariable(name = "userId") Long userId,
            @PathVariable(name = "projectId") Long projectId) {
        ProjectWithUser prw = new ProjectWithUser(projectId, userId);
        return projectWithUserService.isProjectWithUserExistant(prw);
    }

    @Transactional
    @DeleteMapping("/{id}")
    @Caching(evict = { @CacheEvict(value = "pr_with_usr", allEntries = true),
            @CacheEvict(value = "pr", allEntries = true), @CacheEvict(value = "nb_pr", allEntries = true),
            @CacheEvict(value = "nb_clb", allEntries = true) })
    public void deleteProject(@PathVariable(name = "id") Long id) {
        if (id != 0) {
            projectService.deleteProjectById(id);
            projectWithUserService.deleteProjectWithUserByProjectId(id);
        }
    }

    @PutMapping("/updateTitle")
    @CacheEvict(value = "pr_with_usr")
    @Caching(evict = { @CacheEvict(value = "pr_with_usr", allEntries = true),
            @CacheEvict(value = "pr", allEntries = true) })
    @CrossOrigin(origins = "http://localhost:8080")
    public String updateTitle(@RequestBody Project pr) {
        Project res = projectService.getProjectById(pr.getId());
        res.setTitle(pr.getTitle());
        return projectService.updateProject(res);
    }
}
