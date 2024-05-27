package tech.nocountry.c1832ftjavaangular.web.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import tech.nocountry.c1832ftjavaangular.entity.GroupPlanEntity;
import tech.nocountry.c1832ftjavaangular.entity.GroupPlanMemberEntity;
import tech.nocountry.c1832ftjavaangular.model.plan.GroupPlanMemberCreateRequest;
import tech.nocountry.c1832ftjavaangular.model.plan.GroupPlanMemberUpdateRequest;
import tech.nocountry.c1832ftjavaangular.utils.StringResponse;

import java.util.List;

@SecurityRequirement(name = "Authorization")
@RestController
@RequestMapping("/groups")
@RequiredArgsConstructor
public class GroupPlanController {

    @GetMapping
    public ResponseEntity<List<GroupPlanEntity>> getUserGroups() {
        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED, "Not implemented");
    }

    @GetMapping("/{id}")
    public ResponseEntity<GroupPlanEntity> getUserGroup(@PathVariable long id) {
        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED, "Not implemented");
    }

    @GetMapping("/members")
    public ResponseEntity<List<GroupPlanMemberEntity>> getGroupMembers(@RequestParam long groupId) {
        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED, "Not implemented");
    }

    @PostMapping("/members")
    public ResponseEntity<GroupPlanMemberEntity> addGroupMember(@RequestBody GroupPlanMemberCreateRequest data) {
        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED, "Not implemented");
    }

    @PutMapping("/members")
    public ResponseEntity<GroupPlanMemberEntity> updateGroupMember(@RequestBody GroupPlanMemberUpdateRequest data) {
        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED, "Not implemented");
    }

    @DeleteMapping("/members/{id}")
    public ResponseEntity<StringResponse> removeGroupMember(@PathVariable long id) {
        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED, "Not implemented");
    }

    @GetMapping("/members/{id}")
    public ResponseEntity<GroupPlanMemberEntity> getGroupMember(@PathVariable long id) {
        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED, "Not implemented");
    }

}
