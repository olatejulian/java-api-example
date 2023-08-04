package dev.olatejulian.javaapiexample;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import dev.olatejulian.javaapiexample.account.application.AccountService;
import dev.olatejulian.javaapiexample.account.infra.repository.AccountSpringDataJpaRepository;
import dev.olatejulian.javaapiexample.account.infra.repository.AccountSpringDataJpaRepositoryFacade;

@Configuration
@EnableJpaRepositories(basePackageClasses = { AccountSpringDataJpaRepository.class })
public class ApplicationConfiguration {
    @Bean
    AccountSpringDataJpaRepositoryFacade accountSpringDataJpaRepositoryFacade(
            AccountSpringDataJpaRepository accountSpringDataJpaRepository) {
        return new AccountSpringDataJpaRepositoryFacade(accountSpringDataJpaRepository);
    }

    @Bean
    AccountService accountService(AccountSpringDataJpaRepositoryFacade accountSpringDataJpaRepositoryFacade) {
        return new AccountService(accountSpringDataJpaRepositoryFacade);
    }
}
