package code.climb.cccars.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileService {
    private static final Path PICTURES_STORE = Paths.get("files/pictures");

    static {
        // create the folder for the first time
        try {
            Files.createDirectories(PICTURES_STORE.toAbsolutePath());
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    public String storePicture(MultipartFile file) throws IOException {
        String uniqueFileName = UUID.randomUUID().toString();

        // get extension
        String originalFileName = file.getOriginalFilename();
        if (originalFileName != null) {
            int index = originalFileName.lastIndexOf('.');
            if (index > 0) {
                uniqueFileName = uniqueFileName + '.' + originalFileName.substring(index + 1);
            }
        }

        Path uniqueFilePath = PICTURES_STORE.resolve(uniqueFileName);
        Files.copy(file.getInputStream(), uniqueFilePath);
        return PICTURES_STORE.resolve(uniqueFileName).toString().replace("\\", "/");
    }
}
