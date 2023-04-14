package io.github.dbstarll.utils.openai.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public final class CreateFineTuneRequest {
    private String trainingFile;
    private String validationFile;
    private String model;
    @JsonProperty("n_epochs")
    private Integer numEpochs;
    private Integer batchSize;
    private Float learningRateMultiplier;
    private Float promptLossWeight;
    private Boolean computeClassificationMetrics;
    @JsonProperty("classification_n_classes")
    private Integer classificationNumClasses;
    private String classificationPositiveClass;
    private String suffix;

    /**
     * The ID of an uploaded file that contains training data.
     *
     * <p>
     * string
     * Required
     * </p>
     *
     * @return trainingFile
     */
    public String getTrainingFile() {
        return trainingFile;
    }

    /**
     * set trainingFile.
     *
     * @param trainingFile trainingFile
     */
    public void setTrainingFile(final String trainingFile) {
        this.trainingFile = trainingFile;
    }

    /**
     * The ID of an uploaded file that contains validation data.
     *
     * <p>
     * string
     * Optional
     * </p>
     *
     * @return validationFile
     */
    public String getValidationFile() {
        return validationFile;
    }

    /**
     * set validationFile.
     *
     * @param validationFile validationFile
     */
    public void setValidationFile(final String validationFile) {
        this.validationFile = validationFile;
    }

    /**
     * The name of the base model to fine-tune. You can select one of "ada", "babbage", "curie", "davinci",
     * or a fine-tuned model created after 2022-04-21.
     *
     * <p>
     * string
     * Optional
     * Defaults to curie
     * </p>
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

    /**
     * If set, we calculate classification-specific metrics such as accuracy and F-1 score using the
     * validation set at the end of every epoch. These metrics can be viewed in the results file.
     *
     * <p>
     * In order to compute classification metrics, you must provide a validation_file.
     * Additionally, you must specify classification_n_classes for multiclass classification
     * or classification_positive_class for binary classification.
     * </p>
     *
     * <p>
     * boolean
     * Optional
     * Defaults to false
     * </p>
     *
     * @return computeClassificationMetrics
     */
    public Boolean getComputeClassificationMetrics() {
        return computeClassificationMetrics;
    }

    /**
     * set computeClassificationMetrics.
     *
     * @param computeClassificationMetrics computeClassificationMetrics
     */
    public void setComputeClassificationMetrics(final Boolean computeClassificationMetrics) {
        this.computeClassificationMetrics = computeClassificationMetrics;
    }

    /**
     * The number of classes in a classification task.
     *
     * <p>
     * This parameter is required for multiclass classification.
     * </p>
     *
     * <p>
     * integer
     * Optional
     * Defaults to null
     * </p>
     *
     * @return classificationNumClasses
     */
    public Integer getClassificationNumClasses() {
        return classificationNumClasses;
    }

    /**
     * set classificationNumClasses.
     *
     * @param classificationNumClasses classificationNumClasses
     */
    public void setClassificationNumClasses(final Integer classificationNumClasses) {
        this.classificationNumClasses = classificationNumClasses;
    }

    /**
     * The positive class in binary classification.
     *
     * <p>
     * This parameter is needed to generate precision, recall, and F1 metrics when doing binary classification.
     * </p>
     *
     * <p>
     * string
     * Optional
     * Defaults to null
     * </p>
     *
     * @return classificationPositiveClass
     */
    public String getClassificationPositiveClass() {
        return classificationPositiveClass;
    }

    /**
     * set classificationPositiveClass.
     *
     * @param classificationPositiveClass classificationPositiveClass
     */
    public void setClassificationPositiveClass(final String classificationPositiveClass) {
        this.classificationPositiveClass = classificationPositiveClass;
    }

    /**
     * A string of up to 40 characters that will be added to your fine-tuned model name.
     *
     * <p>
     * For example, a suffix of "custom-model-name" would produce a model name like
     * ada:ft-your-org:custom-model-name-2022-02-15-04-21-04.
     * </p>
     * <p>
     * string
     * Optional
     * Defaults to null
     * </p>
     *
     * @return suffix
     */
    public String getSuffix() {
        return suffix;
    }

    /**
     * set suffix.
     *
     * @param suffix suffix
     */
    public void setSuffix(final String suffix) {
        this.suffix = suffix;
    }
}
