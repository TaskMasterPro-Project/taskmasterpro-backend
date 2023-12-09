package com.taskmaster.server.domain;

import com.taskmaster.server.auth.UserRepository;
import com.taskmaster.server.auth.model.UserModel;
import com.taskmaster.server.auth.security.UserPrincipal;
import com.taskmaster.server.domain.membership.ProjectMembershipRepository;
import com.taskmaster.server.domain.membership.model.ProjectUserRole;
import com.taskmaster.server.exception.NotAuthorizedException;
import org.springframework.stereotype.Service;

@Service
public class SecurityUtility
{
    private final ProjectMembershipRepository projectMembershipRepository;
    private final UserRepository userRepository;

    public SecurityUtility(ProjectMembershipRepository projectMembershipRepository, UserRepository userRepository)
    {
        this.projectMembershipRepository = projectMembershipRepository;
        this.userRepository = userRepository;
    }

    public boolean isProjectOwner(final Long projectId, UserPrincipal userPrincipal)
    {
        UserModel loggedUser = userRepository
                .findByUsernameOrEmail(userPrincipal.getUsername(), userPrincipal.getUsername())
                .orElseThrow(NotAuthorizedException::new);
        return projectMembershipRepository.existsByProjectIdAndUserIdAndIdRole(projectId, loggedUser.getId(),
                                                                               ProjectUserRole.OWNER);
    }

    public boolean isCommentOwner(final Long projectId, final Long userId)
    {
        return false;
    }

    public boolean isTaskOwner(final Long projectId, final Long userId)
    {
        return false;
    }
}
