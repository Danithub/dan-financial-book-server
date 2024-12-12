package dan.example.dan_financial_book.file.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
@ToString
@Builder
public class UploadFileDto {
    private List<MultipartFile> files;
    private List<String> fileList;
}
