package com.sososhopping;

import com.sososhopping.entity.member.AccountStatus;
import com.sososhopping.auth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;

@Configuration
@RequiredArgsConstructor
@EnableScheduling
public class CronJob {

    private final UserRepository userRepository;

    @Transactional
    @Scheduled(cron = "0 0 5 * * *")
    public void deleteUser() {
        userRepository.findByActive(AccountStatus.WITHDRAW)
                .forEach(user -> {
                    user.getOrders().forEach(order -> order.nullifyUser());
                    userRepository.delete(user);
                });
    }
}
