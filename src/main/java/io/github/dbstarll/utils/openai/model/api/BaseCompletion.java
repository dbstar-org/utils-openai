package io.github.dbstarll.utils.openai.model.api;

import io.github.dbstarll.utils.openai.model.fragment.Choice;
import io.github.dbstarll.utils.openai.model.fragment.Usage;

import java.util.List;
import java.util.StringJoiner;

public abstract class BaseCompletion<C extends Choice> extends Base {
    private String model;
    private Usage usage;
    private List<C> choices;

    /**
     * get model.
     *
     * @return model
     */
    public final String getModel() {
        return model;
    }

    /**
     * set model.
     *
     * @param model model
     */
    public final void setModel(final String model) {
        this.model = model;
    }

    /**
     * get usage.
     *
     * @return usage
     */
    public final Usage getUsage() {
        return usage;
    }

    /**
     * set usage.
     *
     * @param usage usage
     */
    public final void setUsage(final Usage usage) {
        this.usage = usage;
    }

    /**
     * get choices.
     *
     * @return choices
     */
    public final List<C> getChoices() {
        return choices;
    }

    /**
     * set choices.
     *
     * @param choices choices
     */
    public final void setChoices(final List<C> choices) {
        this.choices = choices;
    }

    @Override
    public final String toString() {
        return new StringJoiner(", ", getClass().getSimpleName() + "[", "]")
                .add("model='" + getModel() + "'")
                .add("usage=" + getUsage())
                .add("choices=" + getChoices())
                .toString();
    }
}
