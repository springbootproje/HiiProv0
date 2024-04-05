package com.java.app.ws.Repository;

import com.java.app.ws.entity.ProjectEntity;
import com.java.app.ws.request.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProjectRepository extends CrudRepository<ProjectEntity, Long> {

    //test
    public List<Project> findById(int organizerId) {
        return events.stream().filter(event -> event.organizer().id() == organizerId).toList();
    }

    public Optional<Event> findById(int id) {
        return events.stream().filter(event -> event.id() == id).findAny();
    }





    Project findByEmail(String email);
    List<Project> findAll();


}
