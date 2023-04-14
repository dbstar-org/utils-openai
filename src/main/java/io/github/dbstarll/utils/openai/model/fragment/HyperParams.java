package io.github.dbstarll.utils.openai.model.fragment;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.StringJoiner;

public final class HyperParams implements Serializable {
    @JsonProperty("n_epochs")
    private Integer numEpochs;
    private Integer batchSize;
    private Float learningRateMultiplier;
    private Float promptLossWeight;

    /**
     * get numEpochs.
     *
     * @return numEpochs
     */
    public Integer getNumEpochs() {
        return numEpochs;
    }

    /**
     * set numEpochs.
     *
     * @param numEpochs numEpochs
     */
    public void setNumEpochs(final Integer numEpochs) {
        this.numEpochs = numEpochs;
    }

    /**
     * get batchSize.
     *
     * @return batchSize
     */
    public Integer getBatchSize() {
        return batchSize;
    }

    /**
     * set batchSize.
     *
     * @param batchSize batchSize
     */
    public void setBatchSize(final Integer batchSize) {
        this.batchSize = batchSize;
    }

    /**
     * get learningRateMultiplier.
     *
     * @return learningRateMultiplier
     */
    public Float getLearningRateMultiplier() {
        return learningRateMultiplier;
    }

    /**
     * set learningRateMultiplier.
     *
     * @param learningRateMultiplier learningRateMultiplier
     */
    public void setLearningRateMultiplier(final Float learningRateMultiplier) {
        this.learningRateMultiplier = learningRateMultiplier;
    }

    /**
     * get promptLossWeight.
     *
     * @return promptLossWeight
     */
    public Float getPromptLossWeight() {
        return promptLossWeight;
    }

    /**
     * set promptLossWeight.
     *
     * @param promptLossWeight promptLossWeight
     */
    public void setPromptLossWeight(final Float promptLossWeight) {
        this.promptLossWeight = promptLossWeight;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", HyperParams.class.getSimpleName() + "[", "]")
                .add("numEpochs=" + getNumEpochs())
                .add("batchSize=" + getBatchSize())
                .add("learningRateMultiplier=" + getLearningRateMultiplier())
                .add("promptLossWeight=" + getPromptLossWeight())
                .toString();
    }
}
