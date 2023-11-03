package com.galaxy.novelit.workspace.domain;

import com.galaxy.novelit.author.domain.User;
import jakarta.persistence.Column;
import jakarta.persistence.ConstraintMode;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "workspace")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class Workspace {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "workspace_id", nullable = false)
    private Long id;

    @Column(name = "workspace_uuid", length = 36, nullable = false)
    private String workspaceUUID;

    @Column(name = "title", length = 60,nullable = false)
    private String title;


//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "user_uuid", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
//    private User user;

    @Column(name = "user_uuid", length = 36, nullable = false)
    private String userUUID;
}
