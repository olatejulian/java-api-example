package dev.olatejulian.javaapiexample.account.infra.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountSpringDataJpaRepository extends JpaRepository<AccountSpringDataJpaModel, UUID> {
}
