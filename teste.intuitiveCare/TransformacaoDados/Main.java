import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class TransformacaoDados {

    public static void main(String[] args) {
        String pdfPath = "Anexo_I_Rol_2021RN_465.2021_RN473_RN478_RN480_RN513_RN536.pdf";
        String seuNome = "LuisEduardo";
        String csvPath = "Teste_" + seuNome + ".csv";
        String zipPath = "Teste_" + seuNome + ".zip";

        try {
            List<String[]> dados = extrairDadosPDF(pdfPath);

            salvarCSV(dados, csvPath);

            compactarArquivo(csvPath, zipPath);

            new File(csvPath).delete();

            System.out.println("Processo concluído com sucesso! Arquivo ZIP criado: " + zipPath);

        } catch (Exception e) {
            System.err.println("Erro durante o processamento: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static List<String[]> extrairDadosPDF(String pdfPath) throws IOException {
        List<String[]> dados = new ArrayList<>();

        try (PDDocument document = PDDocument.load(new File(pdfPath))) {
            PDFTextStripper stripper = new PDFTextStripper();
            String texto = stripper.getText(document);

            int inicio = texto.indexOf("Rol de Procedimentos e Eventos em Saúde");
            int fim = texto.indexOf("Legenda:");
            String tabelaTexto = texto.substring(inicio, fim);

            String[] linhas = tabelaTexto.split("\\r?\\n");
            Pattern padraoLinha = Pattern.compile("^\\d{4}\\s+.*");

            for (String linha : linhas) {
                linha = linha.trim();
                Matcher matcher = padraoLinha.matcher(linha);

                if (matcher.matches()) {
                    String codigo = linha.substring(0, 4).trim();

                    String odAmb = linha.substring(Math.max(0, linha.length() - 3)).trim();

                    String descricao = linha.substring(4, Math.max(4, linha.length() - 3)).trim();

                    dados.add(new String[]{codigo, descricao, odAmb});
                }
            }
        }

        System.out.println(dados.size() + " registros extraídos do PDF.");
        return dados;
    }

    private static void salvarCSV(List<String[]> dados, String csvPath) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(csvPath))) {
            writer.write("Código,Descrição,OD_AMB");
            writer.newLine();

            for (String[] linha : dados) {
                String odAmb = linha[2];
                if ("OD".equals(odAmb)) {
                    odAmb = "Odontológico";
                } else if ("AMB".equals(odAmb)) {
                    odAmb = "Ambulatorial";
                }

                writer.write(String.format("\"%s\",\"%s\",\"%s\"",
                        linha[0], linha[1], odAmb));
                writer.newLine();
            }
        }

        System.out.println("Arquivo CSV criado: " + csvPath);
    }

    private static void compactarArquivo(String arquivoOrigem, String arquivoDestino) throws IOException {
        try (FileOutputStream fos = new FileOutputStream(arquivoDestino);
             ZipOutputStream zipOut = new ZipOutputStream(fos);
             FileInputStream fis = new FileInputStream(arquivoOrigem)) {

            ZipEntry zipEntry = new ZipEntry(new File(arquivoOrigem).getName());
            zipOut.putNextEntry(zipEntry);

            byte[] bytes = new byte[1024];
            int length;
            while ((length = fis.read(bytes)) >= 0) {
                zipOut.write(bytes, 0, length);
            }
        }

        System.out.println("Arquivo ZIP criado: " + arquivoDestino);
    }
}