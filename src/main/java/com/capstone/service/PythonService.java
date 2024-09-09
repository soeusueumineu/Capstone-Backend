package com.capstone.service;

import com.capstone.domain.Member;
import com.capstone.domain.MemberSize;
import com.capstone.dto.MemberSizeDto;
import com.capstone.repository.MemberRepository;
import com.capstone.repository.MemberSizeRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;


@Service
@RequiredArgsConstructor
public class PythonService {

    private final MemberRepository memberRepository;
    private final MemberSizeRepository memberSizeRepository;

    public MemberSize setMemberSize(Long memberId, String filePath) throws IOException, InterruptedException {
        Member member = memberRepository.findById(memberId).get();
        String height = String.valueOf(member.getHeight());

        ProcessBuilder processBuilder = new ProcessBuilder(
                "C:\\Users\\PC\\AppData\\Local\\Programs\\Python\\Python312\\python.exe",
                "src/main/resources/static/python/BodyMeasurement.py",
                filePath,
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

        MemberSize memberSize;
        MemberSizeDto memberSizeDto = memberSizeDtoAtomicReference.get();
        Optional<MemberSize> byMember = memberSizeRepository.findByMember(member);
        if(byMember.isEmpty()){
            memberSize = new MemberSize();
            memberSize.setMember(member);
            memberSize.setBottomLength(memberSizeDto.getBottomLength());
            memberSize.setSleeveLength(memberSizeDto.getSleeveLength());
            memberSize.setShoulderWidth(memberSizeDto.getShoulderWidth());
            memberSize.setTopLength(memberSizeDto.getTopLength());
            memberSize.setWaistWidth(memberSizeDto.getWaistWidth());
            memberSize.setChestCrossSection(memberSizeDto.getChestCrossSection());
        }else{
            memberSize = byMember.get();
            memberSize.setMember(member);
            memberSize.setBottomLength(memberSizeDto.getBottomLength());
            memberSize.setSleeveLength(memberSizeDto.getSleeveLength());
            memberSize.setShoulderWidth(memberSizeDto.getShoulderWidth());
            memberSize.setTopLength(memberSizeDto.getTopLength());
            memberSize.setWaistWidth(memberSizeDto.getWaistWidth());
            memberSize.setChestCrossSection(memberSizeDto.getChestCrossSection());
        }

        memberSizeRepository.save(memberSize);
        return memberSize;
    }
}
