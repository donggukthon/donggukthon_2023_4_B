package donggukthon.volunmate.config;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
public class S3Config {
    @Value("${cloud.aws.access_key}")
    private String accessKey;

    @Value("${cloud.aws.secret_key}")
    private String secretKey;

    @Value("${cloud.aws.region}")
    private String region;

    @Value("${cloud.aws.s3_bucket}")
    private String bucket;

    @Bean
    public AmazonS3Client amazonS3Client() {
        return (AmazonS3Client) AmazonS3ClientBuilder.standard()
                .withRegion(region).enablePathStyleAccess()
                .build();
    }
}
