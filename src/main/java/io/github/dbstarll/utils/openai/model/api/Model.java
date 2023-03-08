package io.github.dbstarll.utils.openai.model.api;

import java.util.List;
import java.util.StringJoiner;

public final class Model extends Base {
    private String ownedBy;
    private String root;
    private Object parent;
    private List<Permission> permission;

    public String getOwnedBy() {
        return ownedBy;
    }

    public void setOwnedBy(final String ownedBy) {
        this.ownedBy = ownedBy;
    }

    public String getRoot() {
        return root;
    }

    public void setRoot(final String root) {
        this.root = root;
    }

    public Object getParent() {
        return parent;
    }

    public void setParent(final Object parent) {
        this.parent = parent;
    }

    public List<Permission> getPermission() {
        return permission;
    }

    public void setPermission(final List<Permission> permission) {
        this.permission = permission;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", super.toString() + "[", "]")
                .add("ownedBy='" + getOwnedBy() + "'")
                .add("root='" + getRoot() + "'")
                .add("parent=" + getParent())
                .add("permission=" + getPermission())
                .toString();
    }
}
