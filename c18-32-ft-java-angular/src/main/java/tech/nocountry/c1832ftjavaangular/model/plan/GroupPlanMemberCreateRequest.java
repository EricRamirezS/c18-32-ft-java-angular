package tech.nocountry.c1832ftjavaangular.model.plan;

import jakarta.validation.constraints.NotNull;

public class GroupPlanMemberCreateRequest {
    @NotNull
    private Boolean isProposerMember;

    private String email;
    private String username;
    @NotNull
    private long groupId;
}
