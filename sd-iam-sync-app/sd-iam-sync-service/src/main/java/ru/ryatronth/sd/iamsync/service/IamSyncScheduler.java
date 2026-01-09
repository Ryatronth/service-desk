package ru.ryatronth.sd.iamsync.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class IamSyncScheduler {

    private final IamSyncService syncService;

    @Scheduled(
        initialDelayString = "${sd.iam-sync.scheduler.initial-delay-ms}",
        fixedDelayString = "${sd.iam-sync.scheduler.fixed-delay-ms}"
    )
    public void runSync() {
        var result = syncService.syncAllUsers();
        log.info("IAM sync completed: processedUsers={}", result.processedUsers());
    }

}
