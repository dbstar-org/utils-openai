package io.github.dbstarll.utils.openai.model.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.github.dbstarll.utils.openai.model.DateConverter;
import io.github.dbstarll.utils.openai.model.fragment.HyperParams;

import java.util.Date;
import java.util.List;
import java.util.StringJoiner;

public final class FineTune extends Base {
    private String organizationId;
    private String model;
    @JsonProperty("hyperparams")
    private HyperParams hyperParams;
    private List<File> trainingFiles;
    private List<File> validationFiles;
    private List<File> resultFiles;
    private String status; // pending | running | succeeded | cancelled
    private String fineTunedModel;
    private List<FineTuneEvent> events;

    @JsonDeserialize(converter = DateConverter.class)
    private Date updatedAt;

    /**
     * 设置创建时间.
     *
     * @param createdAt 创建时间
     */
    @JsonDeserialize(converter = DateConverter.class)
    public void setCreatedAt(final Date createdAt) {
        setCreated(createdAt);
    }

    /**
     * get organizationId.
     *
     * @return organizationId
     */
    public String getOrganizationId() {
        return organizationId;
    }

    /**
     * set organizationId.
     *
     * @param organizationId organizationId
     */
    public void setOrganizationId(final String organizationId) {
        this.organizationId = organizationId;
    }

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
     * get hyperParams.
     *
     * @return hyperParams
     */
    public HyperParams getHyperParams() {
        return hyperParams;
    }

    /**
     * set hyperParams.
     *
     * @param hyperParams hyperParams
     */
    public void setHyperParams(final HyperParams hyperParams) {
        this.hyperParams = hyperParams;
    }

    /**
     * get trainingFiles.
     *
     * @return trainingFiles
     */
    public List<File> getTrainingFiles() {
        return trainingFiles;
    }

    /**
     * set trainingFiles.
     *
     * @param trainingFiles trainingFiles
     */
    public void setTrainingFiles(final List<File> trainingFiles) {
        this.trainingFiles = trainingFiles;
    }

    /**
     * get validationFiles.
     *
     * @return validationFiles
     */
    public List<File> getValidationFiles() {
        return validationFiles;
    }

    /**
     * set validationFiles.
     *
     * @param validationFiles validationFiles
     */
    public void setValidationFiles(final List<File> validationFiles) {
        this.validationFiles = validationFiles;
    }

    /**
     * get resultFiles.
     *
     * @return resultFiles
     */
    public List<File> getResultFiles() {
        return resultFiles;
    }

    /**
     * set resultFiles.
     *
     * @param resultFiles resultFiles
     */
    public void setResultFiles(final List<File> resultFiles) {
        this.resultFiles = resultFiles;
    }

    /**
     * get status.
     *
     * @return status
     */
    public String getStatus() {
        return status;
    }

    /**
     * set status.
     *
     * @param status status
     */
    public void setStatus(final String status) {
        this.status = status;
    }

    /**
     * get fineTunedModel.
     *
     * @return fineTunedModel
     */
    public String getFineTunedModel() {
        return fineTunedModel;
    }

    /**
     * set fineTunedModel.
     *
     * @param fineTunedModel fineTunedModel
     */
    public void setFineTunedModel(final String fineTunedModel) {
        this.fineTunedModel = fineTunedModel;
    }

    /**
     * get events.
     *
     * @return events
     */
    public List<FineTuneEvent> getEvents() {
        return events;
    }

    /**
     * set events.
     *
     * @param events events
     */
    public void setEvents(final List<FineTuneEvent> events) {
        this.events = events;
    }

    /**
     * get updatedAt.
     *
     * @return updatedAt
     */
    public Date getUpdatedAt() {
        return updatedAt;
    }

    /**
     * set updatedAt.
     *
     * @param updatedAt updatedAt
     */
    public void setUpdatedAt(final Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", super.toString() + "[", "]")
                .add("organizationId='" + getOrganizationId() + "'")
                .add("model='" + getModel() + "'")
                .add("hyperParams=" + getHyperParams())
                .add("trainingFiles=" + getTrainingFiles())
                .add("validationFiles=" + getValidationFiles())
                .add("resultFiles=" + getResultFiles())
                .add("status='" + getStatus() + "'")
                .add("fineTunedModel='" + getFineTunedModel() + "'")
                .add("events=" + getEvents())
                .add("updatedAt=" + getUpdatedAt())
                .toString();
    }
}
