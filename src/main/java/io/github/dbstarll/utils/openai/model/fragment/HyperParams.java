package io.github.dbstarll.utils.openai.model.fragment;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.StringJoiner;

public class HyperParams implements Serializable {
    @JsonProperty("n_epochs")
    private Integer numEpochs;
    private Integer batchSize;
    private Float learningRateMultiplier;
    private Float promptLossWeight;

    /**
     * The number of epochs to train the model for. An epoch refers to one full cycle through the training dataset.
     *
     * <p>
     * integer
     * Optional
     * Defaults to 4
     * </p>
     *
     * @return numEpochs
     */
    public final Integer getNumEpochs() {
        return numEpochs;
    }

    /**
     * set numEpochs.
     *
     * @param numEpochs numEpochs
     */
    public final void setNumEpochs(final Integer numEpochs) {
        this.numEpochs = numEpochs;
    }

    /**
     * The batch size to use for training. The batch size is the number of training examples used to train
     * a single forward and backward pass.
     *
     * <p>
     * By default, the batch size will be dynamically configured to be ~0.2% of the number of examples in the training
     * set, capped at 256 - in general, we've found that larger batch sizes tend to work better for larger datasets.
     * </p>
     *
     * <p>
     * integer
     * Optional
     * Defaults to null
     * </p>
     *
     * @return batchSize
     */
    public final Integer getBatchSize() {
        return batchSize;
    }

    /**
     * set batchSize.
     *
     * @param batchSize batchSize
     */
    public final void setBatchSize(final Integer batchSize) {
        this.batchSize = batchSize;
    }

    /**
     * The learning rate multiplier to use for training. The fine-tuning learning rate is the original
     * learning rate used for pretraining multiplied by this value.
     *
     * <p>
     * By default, the learning rate multiplier is the 0.05, 0.1, or 0.2 depending on final batch_size
     * (larger learning rates tend to perform better with larger batch sizes). We recommend experimenting
     * with values in the range 0.02 to 0.2 to see what produces the best results.
     * </p>
     *
     * <p>
     * number
     * Optional
     * Defaults to null
     * </p>
     *
     * @return learningRateMultiplier
     */
    public final Float getLearningRateMultiplier() {
        return learningRateMultiplier;
    }

    /**
     * set learningRateMultiplier.
     *
     * @param learningRateMultiplier learningRateMultiplier
     */
    public final void setLearningRateMultiplier(final Float learningRateMultiplier) {
        this.learningRateMultiplier = learningRateMultiplier;
    }

    /**
     * The weight to use for loss on the prompt tokens. This controls how much the model tries to
     * learn to generate the prompt (as compared to the completion which always has a weight of 1.0),
     * and can add a stabilizing effect to training when completions are short.
     *
     * <p>
     * If prompts are extremely long (relative to completions), it may make sense to reduce this weight
     * so as to avoid over-prioritizing learning the prompt.
     * </p>
     *
     * <p>
     * number
     * Optional
     * Defaults to 0.01
     * </p>
     *
     * @return promptLossWeight
     */
    public final Float getPromptLossWeight() {
        return promptLossWeight;
    }

    /**
     * set promptLossWeight.
     *
     * @param promptLossWeight promptLossWeight
     */
    public final void setPromptLossWeight(final Float promptLossWeight) {
        this.promptLossWeight = promptLossWeight;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", getClass().getSimpleName() + "[", "]")
                .add("numEpochs=" + getNumEpochs())
                .add("batchSize=" + getBatchSize())
                .add("learningRateMultiplier=" + getLearningRateMultiplier())
                .add("promptLossWeight=" + getPromptLossWeight())
                .toString();
    }
}
