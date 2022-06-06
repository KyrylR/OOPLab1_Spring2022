package ua.univ.events;

import org.keycloak.models.ClientModel;
import org.keycloak.models.GroupModel;
import org.keycloak.models.RoleModel;
import org.keycloak.models.UserModel;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class AdminUser implements UserModel {

    @Override
    public String getId() {
        return null;
    }

    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public void setUsername(String s) {
        // Do nothing
    }

    @Override
    public Long getCreatedTimestamp() {
        return null;
    }

    @Override
    public void setCreatedTimestamp(Long aLong) {
        // Do nothing
    }

    @Override
    public boolean isEnabled() {
        return false;
    }

    @Override
    public void setEnabled(boolean b) {
        // Do nothing
    }

    @Override
    public void setSingleAttribute(String s, String s1) {
        // Do nothing
    }

    @Override
    public void setAttribute(String s, List<String> list) {
        // Do nothing
    }

    @Override
    public void removeAttribute(String s) {
        // Do nothing
    }

    @Override
    public String getFirstAttribute(String s) {
        return null;
    }

    @Override
    public List<String> getAttribute(String s) {
        return Collections.emptyList();
    }

    @Override
    public Map<String, List<String>> getAttributes() {
        return Collections.emptyMap();
    }

    @Override
    public Set<String> getRequiredActions() {
        return Collections.emptySet();
    }

    @Override
    public void addRequiredAction(String s) {
        // Do nothing
    }

    @Override
    public void removeRequiredAction(String s) {
        // Do nothing
    }

    @Override
    public void addRequiredAction(RequiredAction requiredAction) {
        // Do nothing
    }

    @Override
    public void removeRequiredAction(RequiredAction requiredAction) {
        // Do nothing
    }

    @Override
    public String getFirstName() {
        return null;
    }

    @Override
    public void setFirstName(String s) {
        // Do nothing
    }

    @Override
    public String getLastName() {
        return null;
    }

    @Override
    public void setLastName(String s) {
        // Do nothing
    }

    @Override
    public String getEmail() {
        return "admin@example.com";
    }

    @Override
    public void setEmail(String s) {
        // Do nothing
    }

    @Override
    public boolean isEmailVerified() {
        return false;
    }

    @Override
    public void setEmailVerified(boolean b) {
        // Do nothing
    }

    @Override
    public Set<GroupModel> getGroups() {
        return Collections.emptySet();
    }

    @Override
    public void joinGroup(GroupModel groupModel) {
        // Do nothing
    }

    @Override
    public void leaveGroup(GroupModel groupModel) {
        // Do nothing
    }

    @Override
    public boolean isMemberOf(GroupModel groupModel) {
        return false;
    }

    @Override
    public String getFederationLink() {
        return null;
    }

    @Override
    public void setFederationLink(String s) {
        // Do nothing
    }

    @Override
    public String getServiceAccountClientLink() {
        return null;
    }

    @Override
    public void setServiceAccountClientLink(String s) {
        // Do nothing
    }

    @Override
    public Set<RoleModel> getRealmRoleMappings() {
        return Collections.emptySet();
    }

    @Override
    public Set<RoleModel> getClientRoleMappings(ClientModel clientModel) {
        return Collections.emptySet();
    }

    @Override
    public boolean hasRole(RoleModel roleModel) {
        return false;
    }

    @Override
    public void grantRole(RoleModel roleModel) {
        // Do nothing
    }

    @Override
    public Set<RoleModel> getRoleMappings() {
        return Collections.emptySet();
    }

    @Override
    public void deleteRoleMapping(RoleModel roleModel) {
        // Do nothing
    }
}
