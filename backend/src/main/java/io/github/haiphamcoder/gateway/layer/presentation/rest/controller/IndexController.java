package io.github.haiphamcoder.gateway.layer.presentation.rest.controller;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.time.Duration;
import java.time.Instant;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.haiphamcoder.gateway.layer.presentation.rest.dto.response.ServiceInfoResponse;
import io.github.haiphamcoder.gateway.layer.presentation.rest.dto.response.ServiceInfoResponse.ServiceDetails;
import io.github.haiphamcoder.gateway.layer.presentation.rest.dto.response.ServiceInfoResponse.SystemInfo;
import io.github.haiphamcoder.gateway.utility.common.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/")
@CrossOrigin(origins = "*")
@Slf4j
@RequiredArgsConstructor
public class IndexController {

    private final Environment environment;

    @Value("${spring.application.name}")
    private String applicationName;

    @Value("${project.version}")
    private String projectVersion;

    @Value("${project.description}")
    private String projectDescription;

    @Value("${server.port}")
    private String serverPort;

    /**
     * Get the service info
     * 
     * @return The service info
     */
    @GetMapping
    public ResponseEntity<ApiResponse<ServiceInfoResponse>> index() {
        try {
            ServiceInfoResponse serviceInfo = buildServiceInfo();
            return ResponseEntity.ok(ApiResponse.success(serviceInfo));
        } catch (Exception e) {
            log.error("Error building service info", e);
            // Return a minimal but informative response
            ServiceInfoResponse fallbackInfo = ServiceInfoResponse.builder()
                    .service(ServiceInfoResponse.ServiceDetails.builder()
                            .name(applicationName)
                            .description(projectDescription)
                            .version(projectVersion)
                            .status("RUNNING")
                            .uptime("N/A")
                            .environment("N/A")
                            .build())
                    .timestamp(Instant.now())
                    .build();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ApiResponse.success(fallbackInfo));
        }
    }

    /**
     * Build the service info
     * @return
     */
    private ServiceInfoResponse buildServiceInfo() {
        RuntimeMXBean runtimeBean = ManagementFactory.getRuntimeMXBean();
        Runtime runtime = Runtime.getRuntime();

        // Calculate uptime with better formatting
        String uptimeFormatted = formatUptime(runtimeBean.getUptime());

        // Build service details
        ServiceDetails serviceDetails = ServiceDetails.builder()
                .name(applicationName)
                .description(projectDescription)
                .version(projectVersion)
                .status("RUNNING")
                .uptime(uptimeFormatted)
                .environment(getActiveProfiles())
                .build();

        // Build system info
        SystemInfo systemInfo = SystemInfo.builder()
                .javaVersion(System.getProperty("java.version"))
                .javaVendor(System.getProperty("java.vendor"))
                .osName(System.getProperty("os.name"))
                .osVersion(System.getProperty("os.version"))
                .osArch(System.getProperty("os.arch"))
                .availableProcessors(runtime.availableProcessors())
                .totalMemory(formatMemorySize(runtime.totalMemory()))
                .freeMemory(formatMemorySize(runtime.freeMemory()))
                .maxMemory(formatMemorySize(runtime.maxMemory()))
                .usedMemory(formatMemorySize(runtime.totalMemory() - runtime.freeMemory()))
                .memoryUsagePercent(calculateMemoryUsagePercent(runtime.totalMemory(), runtime.freeMemory()))
                .build();

        return ServiceInfoResponse.builder()
                .service(serviceDetails)
                .system(systemInfo)
                .timestamp(Instant.now())
                .build();
    }

    /**
     * Format the uptime
     * 
     * @param uptimeMillis The uptime in milliseconds
     * @return The formatted uptime
     */
    private String formatUptime(long uptimeMillis) {
        if (uptimeMillis <= 0) {
            return "N/A";
        }

        Duration uptime = Duration.ofMillis(uptimeMillis);
        long days = uptime.toDays();
        long hours = uptime.toHoursPart();
        long minutes = uptime.toMinutesPart();
        long seconds = uptime.toSecondsPart();

        if (days > 0) {
            return String.format("%dd %dh %dm %ds", days, hours, minutes, seconds);
        } else if (hours > 0) {
            return String.format("%dh %dm %ds", hours, minutes, seconds);
        } else if (minutes > 0) {
            return String.format("%dm %ds", minutes, seconds);
        } else {
            return String.format("%ds", seconds);
        }
    }

    /**
     * Get the active profiles
     * 
     * @return The active profiles
     */
    private String getActiveProfiles() {
        String[] profiles = environment.getActiveProfiles();
        return profiles.length == 0 ? "default" : String.join(",", profiles);
    }

    /**
     * Format the memory size
     * 
     * @param bytes The memory size in bytes
     * @return The formatted memory size
     */
    private String formatMemorySize(long bytes) {
        if (bytes < 0)
            return "N/A";

        final String[] units = { "B", "KB", "MB", "GB", "TB" };
        int unitIndex = 0;
        double size = bytes;

        while (size >= 1024.0 && unitIndex < units.length - 1) {
            size /= 1024.0;
            unitIndex++;
        }

        if (unitIndex == 0) {
            return String.format("%d %s", (long) size, units[unitIndex]);
        } else {
            return String.format("%.2f %s", size, units[unitIndex]);
        }
    }

    /**
     * Calculate the memory usage percent
     * 
     * @param totalMemory The total memory in bytes
     * @param freeMemory  The free memory in bytes
     * @return The memory usage percent
     */
    private double calculateMemoryUsagePercent(long totalMemory, long freeMemory) {
        if (totalMemory <= 0)
            return 0.0;
        long usedMemory = totalMemory - freeMemory;
        return Math.round((double) usedMemory / totalMemory * 100.0 * 100.0) / 100.0;
    }

}
