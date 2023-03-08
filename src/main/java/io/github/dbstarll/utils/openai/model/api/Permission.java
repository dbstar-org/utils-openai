package io.github.dbstarll.utils.openai.model.api;

import java.util.StringJoiner;

public final class Permission extends Base {
    private boolean allowCreateEngine;
    private boolean allowSampling;
    private boolean allowLogprobs;
    private boolean allowSearchIndices;
    private boolean allowView;
    private boolean allowFineTuning;
    private String organization;
    private String group;
    private boolean isBlocking;

    public boolean isAllowCreateEngine() {
        return allowCreateEngine;
    }

    public void setAllowCreateEngine(final boolean allowCreateEngine) {
        this.allowCreateEngine = allowCreateEngine;
    }

    public boolean isAllowSampling() {
        return allowSampling;
    }

    public void setAllowSampling(final boolean allowSampling) {
        this.allowSampling = allowSampling;
    }

    public boolean isAllowLogprobs() {
        return allowLogprobs;
    }

    public void setAllowLogprobs(final boolean allowLogprobs) {
        this.allowLogprobs = allowLogprobs;
    }

    public boolean isAllowSearchIndices() {
        return allowSearchIndices;
    }

    public void setAllowSearchIndices(final boolean allowSearchIndices) {
        this.allowSearchIndices = allowSearchIndices;
    }

    public boolean isAllowView() {
        return allowView;
    }

    public void setAllowView(final boolean allowView) {
        this.allowView = allowView;
    }

    public boolean isAllowFineTuning() {
        return allowFineTuning;
    }

    public void setAllowFineTuning(final boolean allowFineTuning) {
        this.allowFineTuning = allowFineTuning;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(final String organization) {
        this.organization = organization;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(final String group) {
        this.group = group;
    }

    public boolean isIsBlocking() {
        return isBlocking;
    }

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
