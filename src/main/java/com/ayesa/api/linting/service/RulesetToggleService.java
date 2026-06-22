package com.ayesa.api.linting.service;

import com.ayesa.api.linting.model.RulesetConfiguration;
import com.ayesa.api.linting.repository.RulesetConfigRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class RulesetToggleService {

    private final RulesetConfigRepository repo;
    private final Map<String, String> defaultRulesetStatus;
    private final Map<String, Boolean> cache = new ConcurrentHashMap<>();

    public RulesetToggleService(RulesetConfigRepository repo,
                                @Qualifier("rulesetStatus") Map<String, String> defaultRulesetStatus) {
        this.repo = repo;
        this.defaultRulesetStatus = defaultRulesetStatus;
    }

    public boolean isRulesetEnabled(String rulesetId) {
        Boolean cachedValue = cache.get(rulesetId);
        if (cachedValue != null) {
            return cachedValue;
        }

        Optional<RulesetConfiguration> config = repo.findByRulesetId(rulesetId);
        if (config.isPresent()) {
            boolean enabled = config.get().isEnabled();
            cache.put(rulesetId, enabled);
            return enabled;
        }

        boolean enabledByDefault = !"disabled".equalsIgnoreCase(defaultRulesetStatus.get(rulesetId));
        cache.put(rulesetId, enabledByDefault);
        return enabledByDefault;
    }

    public void toggleRuleset(String rulesetId, boolean enabled, String modifiedBy) {
        RulesetConfiguration config = repo.findByRulesetId(rulesetId)
                .orElseGet(() -> new RulesetConfiguration(rulesetId));
        config.setEnabled(enabled);
        config.setModifiedBy(modifiedBy);
        config.setLastModified(LocalDateTime.now());
        repo.save(config);
        cache.put(rulesetId, enabled);
    }

    public RulesetConfiguration getStatus(String rulesetId) {
        return repo.findByRulesetId(rulesetId).orElse(null);
    }

    public void clearCache() {
        cache.clear();
    }
}
