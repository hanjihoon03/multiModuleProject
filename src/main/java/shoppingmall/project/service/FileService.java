package shoppingmall.project.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;
import shoppingmall.project.domain.UploadFile;
import shoppingmall.project.exception.FileStorageException;
import shoppingmall.project.repository.FileRepository;

import java.io.File;
import java.io.IOException;
import java.nio.channels.Pipe;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class FileService {

    private final FileRepository fileRepository;

    @Value("${file.dir}")
    private String fileDir;


    /**
     * 아이템 아이디에 해당하는 아이템의 이미지 파일을 찾기 위한 로직
     * @param id 찾을 아이템의 id
     * @return 아이템의 이미지를 담은 객체
     */
    @Transactional(readOnly = true)
    public UploadFile findUploadFileItemId(Long id) {
        return fileRepository.findByItemId(id);
    }

    /**
     * 설정된 파일 저장 디렉토리와 파일이름을 결합해 전체 저장 경로 반환
     * @param filename 파일 이름
     * @return 전체 저장 경로 반환
     */
    public String getFullPath(String filename) {
        return fileDir + filename;
    }

    /**
     * 사용자로부터 받은 MultipartFile을 서버에 저장하고, 저장된 파일에 대한 정보를 담은 UploadFile 객체를 반환
     *
     * @param multipartFile 사용자로부터 받은 파일
     * @return 저장된 파일 정보를 담은 UploadFile 객체. 파일이 null이거나 비어 있을 경우 null을 반환
     * @throws FileStorageException 파일 저장 중 발생하는 예외를 처리하기 위한 사용자 정의 예외
     */
    public UploadFile storeFile(MultipartFile multipartFile) throws FileStorageException {
        try {

            if (multipartFile == null || multipartFile.isEmpty()) {
                return null;
            }

            String originalFilename = multipartFile.getOriginalFilename();


            if (originalFilename == null) {
                throw new IllegalArgumentException("Original filename is null.");
            }

            String storeFileName = createStoreFileName(originalFilename);
            String fullPath = fileDir + storeFileName;

            Path path = Paths.get(fullPath);


            byte[] fileBytes = multipartFile.getBytes();
            if (fileBytes == null) {
                throw new IllegalStateException("Byte array is null.");
            }

            Files.write(path, fileBytes);
            multipartFile.transferTo(new File(getFullPath(storeFileName)));

            return new UploadFile(originalFilename, storeFileName);
        } catch (IOException e) {
            log.error("Error occurred while storing file: {}", e.getMessage());
            throw new FileStorageException("Failed to store file.", e);
        } catch (IllegalArgumentException | IllegalStateException e) {
            // 명시적인 null 체크 예외를 잡아서 로그에 기록
            log.error("Error occurred: {}", e.getMessage());
            return null;
        }

    }

    /**
     * 서버 내부에서 관리하는 파일명은 유일한 이름을 생성
     */
    private String createStoreFileName(String originalFilename) {
        String uuid = UUID.randomUUID().toString();
        String ext = extractExt(originalFilename);
        return uuid + "." + ext;
    }

    /**
     *확장자를 별도로 추출해서 서버 내부에서 관리하는 파일명에도 붙여준다.
     */
    private String extractExt(String originalFilename) {
        int pos = originalFilename.lastIndexOf(".");
        return originalFilename.substring(pos + 1);
    }
}
