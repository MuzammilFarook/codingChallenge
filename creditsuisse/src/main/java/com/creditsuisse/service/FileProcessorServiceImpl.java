package com.creditsuisse.service;

import java.io.File;

import javax.annotation.PostConstruct;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class FileProcessorServiceImpl {

    @Value("${inputFilePath}")
    private String path;

    @Autowired
    private EventProcessorServiceImpl eventHandlerService;

    @PostConstruct
    private void init() {
        try {
            readFile(path);
        } catch (Exception e) {
            log.error("ERROR PROCESSING FILE {}", path, e);
        }
    }

    public void readFile(final String path) throws Exception {
        File logFile = new File(path);

        if (!logFile.canRead()) {
            log.error("CANNOT READ FILE {}.", path);
            return;
        }
        LineIterator lineIterator = FileUtils.lineIterator(logFile);
        try {
            while (lineIterator.hasNext()) {
                eventHandlerService.processEvents(lineIterator.nextLine());
            }
        } finally {
            lineIterator.close();
        }

    }
}
