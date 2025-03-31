import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Main {

    private static final String TARGET_URL = "https://www.gov.br/ans/pt-br/acesso-a-informacao/participacao-da-sociedade/atualizacao-do-rol-de-procedimentos";
    private static final String TEMP_DIR = "anexos_ans";
    private static final String ZIP_FILE = "anexos_ans.zip";

    public static void main(String[] args) {
        try {
            Files.createDirectories(Paths.get(TEMP_DIR));

            Document doc = Jsoup.connect(TARGET_URL).get();
            Elements links = doc.select("a[href$=.pdf]");

            List<String> downloadedFiles = new ArrayList<>();

            for (Element link : links) {
                String href = link.attr("href");
                String text = link.text();

                if (text.contains("Anexo I") || text.contains("Anexo II") ||
                        href.contains("Anexo+I") || href.contains("Anexo+II") ||
                        href.contains("Anexo%20I") || href.contains("Anexo%20II")) {

                    String fileName = href.substring(href.lastIndexOf('/') + 1);
                    String filePath = TEMP_DIR + File.separator + fileName;

                    System.out.println("Baixando: " + fileName);
                    downloadFile(href, filePath);
                    downloadedFiles.add(filePath);
                }
            }

            if (!downloadedFiles.isEmpty()) {
                System.out.println("Compactando arquivos...");
                createZipFile(downloadedFiles, ZIP_FILE);
                System.out.println("Arquivo ZIP criado com sucesso: " + ZIP_FILE);
            } else {
                System.out.println("Nenhum arquivo dos Anexos I ou II foi encontrado.");
            }

            for (String filePath : downloadedFiles) {
                Files.deleteIfExists(Paths.get(filePath));
            }
            Files.deleteIfExists(Paths.get(TEMP_DIR));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void downloadFile(String fileUrl, String savePath) throws IOException {
        try (InputStream in = new URL(fileUrl).openStream();
             FileOutputStream out = new FileOutputStream(savePath)) {

            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = in.read(buffer)) != -1) {
                out.write(buffer, 0, bytesRead);
            }
        }
    }

    private static void createZipFile(List<String> filesToZip, String zipFileName) throws IOException {
        try (FileOutputStream fos = new FileOutputStream(zipFileName);
             ZipOutputStream zos = new ZipOutputStream(fos)) {

            for (String filePath : filesToZip) {
                File file = new File(filePath);
                ZipEntry zipEntry = new ZipEntry(file.getName());
                zos.putNextEntry(zipEntry);

                byte[] bytes = Files.readAllBytes(Paths.get(filePath));
                zos.write(bytes, 0, bytes.length);
                zos.closeEntry();
            }
        }
    }
}