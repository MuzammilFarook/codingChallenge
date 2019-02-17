package com.creditsuisse;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.util.Arrays;
import java.util.Optional;


@SpringBootApplication
@EnableTransactionManagement
@EnableAsync
@Slf4j
public class CSCodingChallengeApplication {

    public static void main(String[] args) {
        Optional<String> pathParam = Arrays.stream(args).filter(a -> a.startsWith("--inputFilePath=")).findAny();

        if (!pathParam.isPresent()) {
        	log.error("**********ERROR : INPUT PATH MISSING***************");
        	log.error("YOU SHOULD PROVIDE A VALID INPUT FILE PATH IN PROGRAM ARGS OR IN COMMAND LINE. EXAMPLE : --inputFilePath=\"D:\\file.txt\"");
            return;
        }

        String filePath = pathParam.get().split("=")[1];

        File logFile = new File(filePath);

        if (!logFile.canRead()) {
            log.error("ERROR: COULD NOT READ FILE "+filePath);
            return;
        }

        SpringApplication.run(CSCodingChallengeApplication.class, args);
    }
}

