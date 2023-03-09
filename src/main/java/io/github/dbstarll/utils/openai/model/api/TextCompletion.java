package io.github.dbstarll.utils.openai.model.api;

import io.github.dbstarll.utils.openai.model.fragment.Choice;
import io.github.dbstarll.utils.openai.model.fragment.Usage;

import java.util.List;
import java.util.StringJoiner;

public final class TextCompletion extends Base {
    private String model;
    private Usage usage;
    private List<Choice> choices;

    /**
     * get model.
     *
     * @return model
     */
    public String getModel() {
        return model;
    }

    /**
     * set model.
     *
     * @param model model
     */
    public void setModel(final String model) {
        this.model = model;
    }

    /**
     * get usage.
     *
     * @return usage
     */
    public Usage getUsage() {
        return usage;
    }

    /**
     * set usage.
     *
     * @param usage usage
     */
    public void setUsage(final Usage usage) {
        this.usage = usage;
    }

    /**
     * get choices.
     *
     * @return choices
     */
    public List<Choice> getChoices() {
        return choices;
    }

    /**
     * set choices.
     *
     * @param choices choices
     */
    public void setChoices(final List<Choice> choices) {
        this.choices = choices;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", super.toString() + "[", "]")
                .add("model='" + getModel() + "'")
                .add("usage=" + getUsage())
                .add("choices=" + getChoices())
                .toString();
    }
}
