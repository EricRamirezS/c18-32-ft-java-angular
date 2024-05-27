package tech.nocountry.c1832ftjavaangular.model.plan;

import jakarta.validation.constraints.NotNull;

public class GroupPlanMemberUpdateRequest {
    @NotNull
    private long id;
    @NotNull
    private Boolean isProposerMember;
}
