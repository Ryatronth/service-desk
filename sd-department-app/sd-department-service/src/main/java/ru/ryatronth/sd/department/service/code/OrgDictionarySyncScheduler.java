package ru.ryatronth.sd.department.service.code;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrgDictionarySyncScheduler {

    private final OrgDictionarySyncService syncService;

    @Scheduled(
        initialDelayString = "${sd.departments-sync.scheduler.initial-delay-ms}",
        fixedDelayString = "${sd.departments-sync.scheduler.fixed-delay-ms}"
    )
    public void runSync() {
        var result = syncService.sync();
        log.info("IAM sync completed: processedUsers={}", result.branchesUpserted());
    }

}
