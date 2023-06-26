package Web.App.Upload;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class FileUpload {
public static void salvarFicheiro(String diretorio, String nomeFicheiro, MultipartFile multipartFile) throws IOException {
    Path caminho = Paths.get(diretorio);

    if(!Files.exists(caminho)){
        Files.createDirectories(caminho);
    }
    try(InputStream inputStream = multipartFile.getInputStream()){
        Path caminhoFicheiro = caminho.resolve(nomeFicheiro);
        Files.copy(inputStream, caminhoFicheiro, StandardCopyOption.REPLACE_EXISTING);
    }catch (IOException e){
         throw new IOException("could not save file "+ nomeFicheiro, e);
    }
}
}
