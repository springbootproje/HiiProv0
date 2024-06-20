package com.java.app.ws.service;

import com.java.app.ws.dto.*;
import com.java.app.ws.repository.ParticipantRepo;
import com.java.app.ws.repository.ProjectRepository;
import com.java.app.ws.repository.UserRepository;
import com.java.app.ws.entity.ParticipantEntity;
import com.java.app.ws.entity.ParticipantId;
import com.java.app.ws.entity.ProjectEntity;
import com.java.app.ws.entity.UserEntity;
import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.autoconfigure.metrics.MetricsProperties;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ProjectServiceImpl implements ProjectService {
    @Autowired
    private final ProjectRepository projectRepository;
    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private ParticipantRepo participantRepository;

    @Autowired
    public ProjectServiceImpl(ProjectRepository projectRepository, UserRepository userRepository, ParticipantRepo participantRepository) {
        this.projectRepository = projectRepository;
        this.userRepository =userRepository;
        this.participantRepository = participantRepository;
    }





    // @Transactional
    // @Override
    // public ProjectEntity createProject(String title, String description, Long creatorUserId, List<Long> memberIds) {
    //     // Find the creator user
    //     UserEntity creator = userRepository.findById(creatorUserId)
    //             .orElseThrow(() -> new NoSuchElementException("User not found with ID: " + creatorUserId));

    //     // Create and save the project
    //     ProjectEntity project = new ProjectEntity();
    //     project.setTitle(title);
    //     project.setDescription(description);
    //     project.setCreateDate(LocalDate.now());
    //     project.setCreator(creator);
    //     project = projectRepository.save(project);

    //     // Add members to the project
    //     for (Long memberId : memberIds) {
    //         UserEntity member = userRepository.findById(memberId)
    //                 .orElseThrow(() -> new NoSuchElementException("User not found with ID: " + memberId));
    //         addMemberToProject(member, project);
    //     }

    //     return project;
    // }


    // private void addMemberToProject(UserEntity user, ProjectEntity project) {
    //     // Check if the user is already a member of the project
    //     boolean exists = participantRepository.existsByUserAndProject(user, project);
    //     if (!exists) {
    //         // Add user to the project
    //         ParticipantEntity participant = new ParticipantEntity();
    //         ParticipantId participantId = new ParticipantId();
    //         participantId.setUserId(user.getId());
    //         participantId.setProjectId(project.getId());
    //         participant.setId(participantId);
    //         participant.setUser(user);
    //         participant.setProject(project);
    //         participantRepository.save(participant);
    //     }
    // }

    @Transactional
    @Override
    public ProjectEntity createProject(String title, String description, Long creatorUserId, List<Long> memberIds) {
        // Find the creator user
        UserEntity creator = userRepository.findById(creatorUserId)
                .orElseThrow(() -> new NoSuchElementException("User not found with ID: " + creatorUserId));

        // Create and save the project
        ProjectEntity project = new ProjectEntity();
        project.setTitle(title);
        project.setDescription(description);
        project.setCreateDate(LocalDate.now());
        project.setCreator(creator);
        project = projectRepository.save(project);

        // Add creator as a member
        addMemberToProject(creator, project);

        // Add additional members to the project
        for (Long memberId : memberIds) {
            UserEntity member = userRepository.findById(memberId)
                    .orElseThrow(() -> new NoSuchElementException("User not found with ID: " + memberId));
            addMemberToProject(member, project);
        }

        return project;
    }

    private void addMemberToProject(UserEntity user, ProjectEntity project) {
        // Check if the user is already a member of the project
        boolean exists = participantRepository.existsByUserAndProject(user, project);
        if (!exists) {
            // Add user to the project via the Participant entity
            ParticipantEntity participant = new ParticipantEntity();
            ParticipantId participantId = new ParticipantId();
            participantId.setUserId(user.getId());
            participantId.setProjectId(project.getId());
            participant.setId(participantId);
            participant.setUser(user);
            participant.setProject(project);
            participantRepository.save(participant);
        }
    }

    @Override
    public List<ProjectSummaryDto> getAllProjectSummaries() {
        List<ProjectEntity> projectEntities = projectRepository.findAll();

        return projectEntities.stream().map(entity -> {
            ProjectSummaryDto dto = new ProjectSummaryDto();
            dto.setId(entity.getId()); // Set the project ID
            dto.setTitle(entity.getTitle());
            dto.setDescription(entity.getDescription());
            dto.setCreateDate(entity.getCreateDate());

            // Fetch members from the Participant table
            List<UserDto> members = participantRepository.findByProject(entity).stream()
                    .map(ParticipantEntity::getUser)
                    .map(user -> {
                        UserDto userDto = new UserDto();
                        userDto.setId(user.getId());
                        userDto.setFirstName(user.getFirstName());
                        userDto.setLastName(user.getLastName());
                        userDto.setEmail(user.getEmail());
                        return userDto;
                    }).collect(Collectors.toList());

            dto.setMembers(members);

            // Fetch tasks related to the project
            List<TacheDto> tasks = entity.getTasks().stream()
                    .map(task -> {
                        TacheDto taskDto = new TacheDto();
                        taskDto.setId(task.getId());
                        taskDto.setTitle(task.getTitle());
                        taskDto.setDescription(task.getDescription());
                        taskDto.setStatus(task.getStatut());
                        taskDto.setDateCreation(task.getDateCreation());
                        taskDto.setUserId(task.getUser().getId());
                        return taskDto;
                    }).collect(Collectors.toList());

            dto.setTasks(tasks);

            return dto;
        }).collect(Collectors.toList());
    }
    @Override
    public List<ProjectSummaryDto> getProjectSummariesByUser(Long userId) {
        // Fetch all projects from the repository
        List<ProjectEntity> projectEntities = projectRepository.findAll();

        // Map each project to ProjectSummaryDto
        List<ProjectSummaryDto> projectSummaryDtos = projectEntities.stream().map(entity -> {
            ProjectSummaryDto dto = new ProjectSummaryDto();
            dto.setId(entity.getId()); // Set the project ID
            dto.setTitle(entity.getTitle());
            dto.setDescription(entity.getDescription());
            dto.setCreateDate(entity.getCreateDate());

            // Fetch members from the Participant table
            List<UserDto> members = participantRepository.findByProject(entity).stream()
                    .map(ParticipantEntity::getUser)
                    .map(user -> {
                        UserDto userDto = new UserDto();
                        userDto.setId(user.getId());
                        userDto.setFirstName(user.getFirstName());
                        userDto.setLastName(user.getLastName());
                        userDto.setEmail(user.getEmail());
                        return userDto;
                    }).collect(Collectors.toList());

            dto.setMembers(members);

            // Fetch tasks related to the project
            List<TacheDto> tasks = entity.getTasks().stream()
                    .map(task -> {
                        TacheDto taskDto = new TacheDto();
                        taskDto.setId(task.getId());
                        taskDto.setTitle(task.getTitle());
                        taskDto.setDescription(task.getDescription());
                        taskDto.setDateCreation(task.getDateCreation());
                        taskDto.setStatus(task.getStatut());

                        taskDto.setUserId(task.getUser().getId());
                   System.out.println(task.getDateCreation());
                        return taskDto;
                    }).collect(Collectors.toList());

            dto.setTasks(tasks);

            return dto;
        }).collect(Collectors.toList());

        // Filter the project summary list where the user is a member
        List<ProjectSummaryDto> filteredProjectSummaries = projectSummaryDtos.stream()
                .filter(projectSummary ->
                        projectSummary.getMembers().stream()
                                .anyMatch(member -> member.getId() == userId) // Use == for direct comparison
                )
                .collect(Collectors.toList());

        // Return the filtered list
        return filteredProjectSummaries;
    }






    public ProjectSummaryDto getProjectById(Long projectId) {
        ProjectEntity projectEntity = projectRepository.findById(projectId)
                .orElseThrow(() -> new NoSuchElementException("Project not found with ID: " + projectId));

        ProjectSummaryDto dto = new ProjectSummaryDto();
        dto.setId(projectEntity.getId());
        dto.setTitle(projectEntity.getTitle());
        dto.setDescription(projectEntity.getDescription());
        dto.setCreateDate(projectEntity.getCreateDate());

        // Fetch members from the Participant table
        List<UserDto> members = participantRepository.findByProject(projectEntity).stream()
                .map(ParticipantEntity::getUser)
                .map(user -> {
                    UserDto userDto = new UserDto();
                    userDto.setId(user.getId());
                    userDto.setFirstName(user.getFirstName());
                    userDto.setLastName(user.getLastName());
                    userDto.setEmail(user.getEmail());
                    return userDto;
                }).collect(Collectors.toList());

        dto.setMembers(members);

        // Fetch tasks related to the project
        List<TacheDto> tasks = projectEntity.getTasks().stream()
                .map(task -> {
                    TacheDto taskDto = new TacheDto();
                    taskDto.setId(task.getId());
                    taskDto.setTitle(task.getTitle());
                    taskDto.setDescription(task.getDescription());
                    taskDto.setStatus(task.getStatut());
                    taskDto.setUserId(task.getUser().getId());
                    taskDto.setDateCreation(task.getDateCreation());
                    return taskDto;
                }).collect(Collectors.toList());

        dto.setTasks(tasks);

        return dto;
    }

    // @Override
    // public List<ProjectSummaryDto> getAllProjectSummaries() {
    //     List<ProjectEntity> projectEntities = projectRepository.findAll();
    //     return projectEntities.stream().map(entity -> {
    //         ProjectSummaryDto dto = new ProjectSummaryDto();
    //         dto.setTitle(entity.getTitle());
    //         dto.setDescription(entity.getDescription());
    //         return dto;
    //     }).collect(Collectors.toList());
    // }

    // @Override
    // public List<ProjectSummaryDto> getAllProjectSummaries() {
    //     List<ProjectEntity> projectEntities = projectRepository.findAll();

    //     return projectEntities.stream().map(entity -> {
    //         ProjectSummaryDto dto = new ProjectSummaryDto();
    //         dto.setTitle(entity.getTitle());
    //         dto.setDescription(entity.getDescription());
    //         dto.setCreateDate(entity.getCreateDate()); // Set the create date

    //         // Convert members to UserDto and set members
    //         List<UserDto> members = entity.getMembers().stream()
    //             .map(user -> {
    //                 UserDto userDto = new UserDto();
    //                 userDto.setId(user.getId());
    //                 userDto.setFirstName(user.getFirstName());
    //                 userDto.setLastName(user.getLastName());
    //                 userDto.setEmail(user.getEmail());
    //                 // Assuming UserDto does not have avatarUrl; otherwise, set it here
    //                 return userDto;
    //             }).collect(Collectors.toList());

    //         dto.setMembers(members);

    //         return dto;
    //     }).collect(Collectors.toList());
    // }

    @Transactional
    @Override
    public List<ProjectSummaryDto> searchByTitle(String title) {
        List<ProjectEntity> projectEntities = projectRepository.findByTitleContaining(title);
        return projectEntities.stream().map(entity -> {
            ProjectSummaryDto dto = new ProjectSummaryDto();
            dto.setTitle(entity.getTitle());
            dto.setDescription(entity.getDescription());
            return dto;
        }).collect(Collectors.toList());
    }

    @Override
    public void deleteProject(Long projectId) {
        // Find the project
        ProjectEntity project = projectRepository.findById(projectId)
                .orElseThrow(() -> new NoSuchElementException("Project not found with ID: " + projectId));

        // Delete participants associated with the project
        List<ParticipantEntity> participants = participantRepository.findByProject(project);
        participantRepository.deleteAll(participants);

        // Delete the project
        projectRepository.delete(project);
    }


    @Override
    public List<ProjectDto> getProjectsFromStartDate(LocalDate startDate) {
        return projectRepository.findByCreateDateGreaterThanEqual(startDate);
    }






    //hadou li lteht mazal mandirhoum




    public ProjectEntity updateProject(Long id, ProjectEntity projectDetails) {
        //  existing project and update its details
        ProjectEntity projectEntity = projectRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Project not found with id: " + id));

        projectEntity.setTitle(projectDetails.getTitle());
        projectEntity.setDescription(projectDetails.getDescription());
        // Optionally allow updating the creation date or other fields
        // Do not update create datebut you can add a field called update_date that you automatically update at this moment


        return projectRepository.save(projectEntity);
    }

}