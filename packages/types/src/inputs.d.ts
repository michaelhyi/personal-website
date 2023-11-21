import { Experience, Post, Project } from ".";

export type ExperienceInputs = Omit<Experience, "id">;
export type ProjectInputs = Omit<Project, "id">;
export type PostInputs = Omit<Post, "id" | "date">;
