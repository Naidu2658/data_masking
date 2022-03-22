package com.datamasking.helperClasses;

import org.springframework.web.multipart.MultipartFile;

public class FileUploadRequestBody {
    MultipartFile file;
    String filename;

    public FileUploadRequestBody(MultipartFile file, String filename) {
        this.file = file;
        this.filename = filename;
    }

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }
}
