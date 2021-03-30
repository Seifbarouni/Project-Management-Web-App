package app.data_microservice.Projects.Data;

import org.springframework.data.jpa.repository.JpaRepository;

import app.data_microservice.Projects.Models.Project;

public interface ProjectsRepository extends JpaRepository<Project, Long> {

}
