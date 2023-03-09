package io.github.dbstarll.utils.openai.model.api;

import java.util.StringJoiner;

public final class ModelPermission extends Base {
    private boolean allowCreateEngine;
    private boolean allowSampling;
    private boolean allowLogprobs;
    private boolean allowSearchIndices;
    private boolean allowView;
    private boolean allowFineTuning;
    private String organization;
    private String group;
    private boolean isBlocking;

    /**
     * 获得allowCreateEngine.
     *
     * @return allowCreateEngine
     */
    public boolean isAllowCreateEngine() {
        return allowCreateEngine;
    }

    /**
     * 设置allowCreateEngine.
     *
     * @param allowCreateEngine allowCreateEngine
     */
    public void setAllowCreateEngine(final boolean allowCreateEngine) {
        this.allowCreateEngine = allowCreateEngine;
    }

    /**
     * 获得allowSampling.
     *
     * @return allowSampling
     */
    public boolean isAllowSampling() {
        return allowSampling;
    }

    /**
     * 设置allowSampling.
     *
     * @param allowSampling allowSampling
     */
    public void setAllowSampling(final boolean allowSampling) {
        this.allowSampling = allowSampling;
    }

    /**
     * 获得allowLogprobs.
     *
     * @return allowLogprobs
     */
    public boolean isAllowLogprobs() {
        return allowLogprobs;
    }

    /**
     * 设置allowLogprobs.
     *
     * @param allowLogprobs allowLogprobs
     */
    public void setAllowLogprobs(final boolean allowLogprobs) {
        this.allowLogprobs = allowLogprobs;
    }

    /**
     * 获得allowSearchIndices.
     *
     * @return allowSearchIndices
     */
    public boolean isAllowSearchIndices() {
        return allowSearchIndices;
    }

    /**
     * 设置allowSearchIndices.
     *
     * @param allowSearchIndices allowSearchIndices
     */
    public void setAllowSearchIndices(final boolean allowSearchIndices) {
        this.allowSearchIndices = allowSearchIndices;
    }

    /**
     * 获得allowView.
     *
     * @return allowView
     */
    public boolean isAllowView() {
        return allowView;
    }

    /**
     * 设置allowView.
     *
     * @param allowView allowView
     */
    public void setAllowView(final boolean allowView) {
        this.allowView = allowView;
    }

    /**
     * 获得allowFineTuning.
     *
     * @return allowFineTuning
     */
    public boolean isAllowFineTuning() {
        return allowFineTuning;
    }

    /**
     * 设置allowFineTuning.
     *
     * @param allowFineTuning allowFineTuning
     */
    public void setAllowFineTuning(final boolean allowFineTuning) {
        this.allowFineTuning = allowFineTuning;
    }

    /**
     * 获得organization.
     *
     * @return organization
     */
    public String getOrganization() {
        return organization;
    }

    /**
     * 设置organization.
     *
     * @param organization organization
     */
    public void setOrganization(final String organization) {
        this.organization = organization;
    }

    /**
     * 获得group.
     *
     * @return group
     */
    public String getGroup() {
        return group;
    }

    /**
     * 设置group.
     *
     * @param group group
     */
    public void setGroup(final String group) {
        this.group = group;
    }

    /**
     * 获得isBlocking.
     *
     * @return isBlocking
     */
    public boolean isIsBlocking() {
        return isBlocking;
    }

    /**
     * 设置isBlocking.
     *
     * @param isBlocking isBlocking
     */
    public void setIsBlocking(final boolean isBlocking) {
        this.isBlocking = isBlocking;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", super.toString() + "[", "]")
                .add("allowCreateEngine=" + isAllowCreateEngine())
                .add("allowSampling=" + isAllowSampling())
                .add("allowLogprobs=" + isAllowLogprobs())
                .add("allowSearchIndices=" + isAllowSearchIndices())
                .add("allowView=" + isAllowView())
                .add("allowFineTuning=" + isAllowFineTuning())
                .add("organization='" + getOrganization() + "'")
                .add("group=" + getGroup())
                .add("isBlocking=" + isIsBlocking())
                .toString();
    }
}
