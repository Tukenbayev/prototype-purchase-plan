package kz.sapasoft.prototype.repository;

import kz.sapasoft.prototype.domain.ApprovementFile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ApprovementFileRepository extends JpaRepository<ApprovementFile, Long> {

    List<ApprovementFile> findAllByPurchasePlanId(Long planId);
}
