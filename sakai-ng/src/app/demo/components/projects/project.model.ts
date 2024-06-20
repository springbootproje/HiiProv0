import { UserDto } from 'src/app/interfaces/user-dto';

export interface ProjectSummaryDto {
    id: number;
    title: string;
    description: string;
    createDate: string; // Ensure this field is included
    members: UserDto[];
    showMembers?: boolean;
    tasks: TaskDto[];
}

export interface ProjectEntity {
    id: number;
    title: string;
    description: string;
    createDate: string;
    creatorUserId: number;
    members: UserDto[];
    tasks: TaskDto[];
}

export interface ApiResponse {
    message: string;
}

export interface ProjectCreationDto {
    title: string;
    description: string;
    memberIds: number[];
}

export interface TaskDto {

    id: number;
    title: string;
    description: string;
    status: string;
    userId: number;
    projactId: number;
    // Other task properties as needed
}

export interface TaskCreationDto {
    title: string;
    description: string;
    status: string; // Adjust the type if necessary
    userId?: number;
    projectId: number; // Include the projectId here
}
