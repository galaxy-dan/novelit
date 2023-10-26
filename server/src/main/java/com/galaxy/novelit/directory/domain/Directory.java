package com.galaxy.novelit.directory.domain;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Document(collection = "directory")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Directory {
	@Id
	private String _id;
	private String uuid;
	private String name;
	@Field(name = "is_directory")
	private boolean directory;
	@Field(name = "parent_uuid")
	private String parentUUID;
	@Field(name = "prev_uuid")
	private String prevUUID;
	@Field(name = "next_uuid")
	private String nextUUID;
	@DBRef
	private List<Directory> children;
	@Field(name = "workspace_uuid")
	private String workspaceUUID;
	@Field(name = "file_uuid")
	private String fileUUID;

	public void editName(String name){
		this.name = name;
	}
}