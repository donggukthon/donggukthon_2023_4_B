package donggukthon.volunmate.utils;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.PutObjectRequest;
import donggukthon.volunmate.config.S3Config;
import donggukthon.volunmate.exception.CustomException;
import donggukthon.volunmate.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class S3Util {
    private final AmazonS3Client amazonS3Client;
    private final S3Config s3Config;

    public String upload(MultipartFile file) {
        if (file == null) {
            return null;
        }
        String fileName = makeFileName();
        try {
            amazonS3Client.putObject(new PutObjectRequest(s3Config.getBucket(), fileName, file.getInputStream(), null));
            return amazonS3Client.getUrl(s3Config.getBucket(), fileName).toString();
        } catch (IOException e) {
            throw new CustomException(ErrorCode.FILE_UPLOAD_ERROR);
        }

    }

    public String updateImage(MultipartFile file, String path) {
        deleteS3File(path);
        return upload(file);
    }

    /**
     * 절대 경로를 상대 경로로 변환
     * @param path
     * @return
     * @throws URISyntaxException
     */
    public String convertPath(String path) throws URISyntaxException {
        URI uri = new URI(path);
        String res = uri.getPath();
        return res.substring(s3Config.getBucket().length() + 2);
    }

    public void deleteS3File(String path) {
        try {
            String relativePath = convertPath(path);
            amazonS3Client.deleteObject(s3Config.getBucket(), relativePath);
        } catch (AmazonS3Exception | URISyntaxException e) {
            throw new CustomException(ErrorCode.FILE_NOT_FOUND);
        }
    }

    private String makeFileName() {
        return LocalDateTime.now().toString() + UUID.randomUUID() + ".jpg";
    }
}
