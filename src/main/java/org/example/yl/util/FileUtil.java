package org.example.yl.util;

import org.example.yl.model.FileDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class FileUtil {
    public static List<FileDto> uploadFile(MultipartFile[] imageFiles){
        List<FileDto> list = new ArrayList<>();

        String basePath = "D:\\image";

        for (MultipartFile imageFile : imageFiles) {
            if (!imageFile.isEmpty()) {
                // UUID를 이용해 unique한 파일 이름을 만들어준다.
                FileDto dto = new FileDto();
                dto.setUuid(UUID.randomUUID().toString());
                dto.setFileName(imageFile.getOriginalFilename());
                dto.setContentType(imageFile.getContentType());

                list.add(dto);

                File newFileName = new File(basePath,dto.getUuid() + "_" + dto.getFileName());
                try {
                    imageFile.transferTo(newFileName);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return list;
    }
}