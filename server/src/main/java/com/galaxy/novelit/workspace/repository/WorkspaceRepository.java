package com.galaxy.novelit.workspace.repository;

import com.galaxy.novelit.workspace.domain.Workspace;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkspaceRepository extends JpaRepository<Workspace, Long> {

    Optional<Workspace> findByWorkspaceUUID(String workSpaceUUID);

    List<Workspace> findAllByUserUUID(String userUUID);

}
