package dan.example.dan_financial_book.file.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import dan.example.dan_financial_book.file.dto.UploadFileDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
@RequiredArgsConstructor
@Slf4j
public class FileService {
    private final AmazonS3Client amazonS3;
    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    // 폴더 생성
    public void createFolder(String bucketName, String folderName) {
        amazonS3.putObject(bucketName, folderName + "/", new ByteArrayInputStream(new byte[0]), new ObjectMetadata());
    }

    // 다중 파일 업로드
    public void fileUpload(UploadFileDto dto) throws Exception {
        if (amazonS3 != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            Date date = new Date();
            String today = sdf.format(date);

            if (!dto.getFiles().isEmpty()) {
                createFolder(bucket, "files/" + today);
            }

            ObjectMetadata objectMetadata = new ObjectMetadata();
            for (int i = 0; i < dto.getFiles().size(); i++) {
                objectMetadata.setContentType(dto.getFiles().get(i).getContentType());
                objectMetadata.setContentLength(dto.getFiles().get(i).getSize());
                objectMetadata.setHeader("filename", dto.getFiles().get(i).getOriginalFilename());
                amazonS3.putObject(new PutObjectRequest(bucket, "files/" + today + "/" + dto.getFileList().get(i), dto.getFiles().get(i).getInputStream(), objectMetadata));
            }
        } else {
            // throw new Exception(ErrorType.aws_credentials_fail, null);
        }
    }

    // 파일 삭제
    public void fileDelete(String fileKey) {
        if (amazonS3 != null) {
            amazonS3.deleteObject(new DeleteObjectRequest(bucket, fileKey));
        } else {
            // throw new Exception(ErrorType.aws_credentials_fail, null);
        }
    }
}
