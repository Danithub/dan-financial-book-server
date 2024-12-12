package dan.example.dan_financial_book.file.controller;

import dan.example.dan_financial_book.common.ResponseEntity;
import dan.example.dan_financial_book.common.service.ResponseService;
import dan.example.dan_financial_book.file.dto.UploadFileDto;
import dan.example.dan_financial_book.file.service.FileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class FileController {
    private final ResponseService responseService;
    private final FileService fileService;

    @PostMapping("download")
    @ResponseBody
    public ResponseEntity<Object> download(@RequestParam String fileKey)  {
        return responseService.toResponseEntity("200", "OK");
    }

    @PostMapping("upload")
    @ResponseBody
    public ResponseEntity<Object> upload(@RequestPart List<MultipartFile> files, @RequestPart List<String> fileList) throws Exception {
        fileService.fileUpload(UploadFileDto.builder().files(files).fileList(fileList).build());
        return responseService.toResponseEntity("200", "OK");
    }

    @DeleteMapping("delete")
    @ResponseBody
    public ResponseEntity<Object> delete(@RequestParam String fileKey)  {
        fileService.fileDelete(fileKey);
        return responseService.toResponseEntity("200", "OK");
    }
}
