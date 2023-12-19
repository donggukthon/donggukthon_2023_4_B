package donggukthon.volunmate.utils;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.PutObjectRequest;
import donggukthon.volunmate.config.S3Config;
import donggukthon.volunmate.exception.CustomException;
import donggukthon.volunmate.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class S3Util {
    private final AmazonS3Client amazonS3Client;
    private final S3Config s3Config;

    public String upload(File file, String fileName, String dirName) {
        try {
            String fullPath = dirName + fileName;
            amazonS3Client.putObject(new PutObjectRequest(s3Config.getBucket(), fullPath, file));

            return amazonS3Client.getUrl(s3Config.getBucket(), fullPath).toString();
        } catch (AmazonS3Exception e) {
            throw new CustomException(ErrorCode.FILE_UPLOAD_ERROR);
        }
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

    private String makeFileName(String originFileName) {
        return originFileName + "~" + UUID.randomUUID() + ".jpg";
    }
}
