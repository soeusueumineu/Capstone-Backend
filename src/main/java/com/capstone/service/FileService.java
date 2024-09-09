package com.capstone.service;

import com.capstone.domain.Member;
import com.capstone.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FileService {

    private final MemberRepository memberRepository;

    @Value("${upload-path}")
    private String uploadPath;

    public void saveFile(MultipartFile file, Long memberId) throws IOException {
        if (!file.isEmpty()) {
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdirs();
            }

            String originalFilename = file.getOriginalFilename();
            String fileExtension = "";

            if (originalFilename != null && originalFilename.contains(".")) {
                fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
            }

            String uuidFilename = UUID.randomUUID() + fileExtension;
            Path filePath = Paths.get(uploadPath, uuidFilename);
            Files.copy(file.getInputStream(), filePath);

            Optional<Member> memberOptional = memberRepository.findById(memberId);
            if (memberOptional.isPresent()) {
                Member member = memberOptional.get();
                member.setMyImage(uuidFilename);
                memberRepository.save(member);
            }
        }
    }


    public Resource loadFileAsResource(String filename) throws MalformedURLException {
        Path filePath = Paths.get(uploadPath).resolve(filename).normalize();
        Resource resource = new UrlResource(filePath.toUri());
        if(resource.exists() || resource.isReadable()) {
            return resource;
        } else {
            throw new RuntimeException("Could not read the file!");
        }
    }

}
