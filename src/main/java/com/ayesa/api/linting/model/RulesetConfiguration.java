package com.ayesa.api.linting.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

@Entity
@Table(name = "ruleset_configurations")
public class RulesetConfiguration {

    @Id
    @Column(nullable = false, updatable = false)
    private String rulesetId;

    @Column(nullable = false)
    private boolean enabled = true;

    @Column(nullable = false)
    private LocalDateTime lastModified;

    private String modifiedBy;

    public RulesetConfiguration() {
    }

    public RulesetConfiguration(String rulesetId) {
        this.rulesetId = rulesetId;
    }

    public RulesetConfiguration(String rulesetId, boolean enabled, LocalDateTime lastModified, String modifiedBy) {
        this.rulesetId = rulesetId;
        this.enabled = enabled;
        this.lastModified = lastModified;
        this.modifiedBy = modifiedBy;
    }

    @PrePersist
    @PreUpdate
    void updateTimestamp() {
        if (lastModified == null) {
            lastModified = LocalDateTime.now();
        }
    }

    public String getRulesetId() {
        return rulesetId;
    }

    public void setRulesetId(String rulesetId) {
        this.rulesetId = rulesetId;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public LocalDateTime getLastModified() {
        return lastModified;
    }

    public void setLastModified(LocalDateTime lastModified) {
        this.lastModified = lastModified;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }
}
