package com.capstone;

import com.capstone.dto.MemberSizeDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

public class PythonTest {

//    @Test
//    public void python() throws IOException, InterruptedException {
//
//        String height = "170";
//
//        ProcessBuilder processBuilder = new ProcessBuilder(
//                "C:\\Users\\PC\\AppData\\Local\\Programs\\Python\\Python312\\python.exe",
//                "src/main/resources/static/python/capstone-opencv/BodyMeasurement.py",
//                height);
//
//        // 프로세스 시작
//        Process process = processBuilder.start();
//        process.waitFor();
//
//        // 표준 출력 스트림 읽기
//        InputStream inputStream = process.getInputStream();
//        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
//
//        String line;
//        while ((line = reader.readLine()) != null) {
//            // 실행 결과 처리
//            System.out.println(line);
//        }
//
//        // 오류 스트림 읽기
//        InputStream errorStream = process.getErrorStream();
//        BufferedReader errorReader = new BufferedReader(new InputStreamReader(errorStream));
//        while ((line = errorReader.readLine()) != null) {
//            // 오류 출력 처리
//            System.err.println(line);
//        }
//    }

//    @Test
//    public void python() throws IOException, InterruptedException {
//
//        String height = "170";
//
//        ProcessBuilder processBuilder = new ProcessBuilder(
//                "C:\\Users\\PC\\AppData\\Local\\Programs\\Python\\Python312\\python.exe",
//                "src/main/resources/static/python/BodyMeasurement.py",
//                height
//        );
//
//        // 환경 변수 설정
//        Map<String, String> env = processBuilder.environment();
//        env.put("PYTHONIOENCODING", "utf-8");
//
//        // 표준 출력과 오류를 별도의 스레드로 읽기
//        Process process = processBuilder.start();
//
//        // 표준 출력 스트림 읽기
//        Thread outputThread = new Thread(() -> {
//            try (InputStream inputStream = process.getInputStream();
//                 BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
//
//                String line;
//                while ((line = reader.readLine()) != null) {
//                    // 실행 결과 처리
//                    System.out.println(line);
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        });
//
//        // 오류 스트림 읽기
//        Thread errorThread = new Thread(() -> {
//            try (InputStream errorStream = process.getErrorStream();
//                 BufferedReader errorReader = new BufferedReader(new InputStreamReader(errorStream))) {
//
//                String line;
//                while ((line = errorReader.readLine()) != null) {
//                    // 오류 출력 처리
//                    System.err.println(line);
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        });
//
//        outputThread.start();
//        errorThread.start();
//
//        // 프로세스가 종료될 때까지 대기
//        process.waitFor();
//
//        // 출력 및 오류 스레드가 종료될 때까지 대기
//        outputThread.join();
//        errorThread.join();
//    }

    @Test
    public void python() throws IOException, InterruptedException {
        String height = "170";
        ProcessBuilder processBuilder = new ProcessBuilder(
                "C:\\Users\\PC\\AppData\\Local\\Programs\\Python\\Python312\\python.exe",
                "src/main/resources/static/python/BodyMeasurement.py",
                height
        );

        // 환경 변수 설정
        Map<String, String> env = processBuilder.environment();
        env.put("PYTHONIOENCODING", "utf-8");

        // 표준 출력과 오류를 별도의 스레드로 읽기
        Process process = processBuilder.start();
        StringBuilder resultBuilder = new StringBuilder();
        AtomicReference<MemberSizeDto> memberSizeDtoAtomicReference = new AtomicReference<>(new MemberSizeDto());
        // 표준 출력 스트림 읽기
        Thread outputThread = new Thread(() -> {
            try (InputStream inputStream = process.getInputStream();
                 BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {

                String line;
                while ((line = reader.readLine()) != null) {
                    // 실행 결과 처리
                    resultBuilder.append(line);
                    String jsonString = resultBuilder.toString();
                    ObjectMapper objectMapper = new ObjectMapper();
                    memberSizeDtoAtomicReference.set(objectMapper.readValue(jsonString, MemberSizeDto.class));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
                // 오류 스트림 읽기
        Thread errorThread = new Thread(() -> {
            try (InputStream errorStream = process.getErrorStream();
                 BufferedReader errorReader = new BufferedReader(new InputStreamReader(errorStream))) {

                String line;
                while ((line = errorReader.readLine()) != null) {
                    // 오류 출력 처리
                    System.err.println(line);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        outputThread.start();
        errorThread.start();

        // 프로세스가 종료될 때까지 대기
        process.waitFor();
        // JSON 문자열을 MemberSizeDto 객체로 변환

        // 출력 및 오류 스레드가 종료될 때까지 대기
        outputThread.join();
        errorThread.join();

    }
}
