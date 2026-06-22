package com.ayesa.api.linting.repository;

import com.ayesa.api.linting.model.RulesetConfiguration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RulesetConfigRepository extends JpaRepository<RulesetConfiguration, String> {

    Optional<RulesetConfiguration> findByRulesetId(String rulesetId);
}
