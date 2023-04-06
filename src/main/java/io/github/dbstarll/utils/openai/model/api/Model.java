package io.github.dbstarll.utils.openai.model.api;

import java.util.List;
import java.util.StringJoiner;

public final class Model extends Base {
    private String ownedBy;
    private String root;
    private transient Object parent;
    private List<ModelPermission> permission;

    /**
     * 获得ownedBy.
     *
     * @return ownedBy
     */
    public String getOwnedBy() {
        return ownedBy;
    }

    /**
     * 设置ownedBy.
     *
     * @param ownedBy ownedBy
     */
    public void setOwnedBy(final String ownedBy) {
        this.ownedBy = ownedBy;
    }

    /**
     * 获得root.
     *
     * @return root
     */
    public String getRoot() {
        return root;
    }

    /**
     * 设置root.
     *
     * @param root root
     */
    public void setRoot(final String root) {
        this.root = root;
    }

    /**
     * 获得parent.
     *
     * @return parent
     */
    public Object getParent() {
        return parent;
    }

    /**
     * 设置parent.
     *
     * @param parent parent
     */
    public void setParent(final Object parent) {
        this.parent = parent;
    }

    /**
     * 获得Permission列表.
     *
     * @return Permission列表
     */
    public List<ModelPermission> getPermission() {
        return permission;
    }

    /**
     * 设置Permission列表.
     *
     * @param permission Permission列表
     */
    public void setPermission(final List<ModelPermission> permission) {
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
