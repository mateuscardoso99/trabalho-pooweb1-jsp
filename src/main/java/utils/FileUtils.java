package utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

public class FileUtils {
    public static void retornarArquivoResponse(String path, String mimeType, HttpServletResponse resp, boolean baixar) throws IOException{
        File file = new File(path);

        //FileInputStream destina-se à leitura de fluxos de bytes brutos, como dados de imagem
        FileInputStream fileInputStream = new FileInputStream(file);
        
        // se Content-Disposition tiver attachment faz o navegador baixar o arquivo e não só mostrar

        if(baixar){
            String headerKey = "Content-Disposition";
            String headerValue = String.format("attachment; filename=\"%s\"", file.getName());
            resp.setHeader(headerKey, headerValue);
        }

        resp.setContentType(mimeType);
        resp.setContentLength((int)file.length());

        OutputStream outputStream = resp.getOutputStream();

        byte[] buffer = new byte[4096];
        int bytesRead = -1;

        //método read() da classe InputStream lê um byte de dados do fluxo de entrada e coloca dentro do buffer. O próximo byte de dados é retornado ou -1 se o final do arquivo for atingido
        while((bytesRead = fileInputStream.read(buffer)) != -1){
            outputStream.write(buffer,0,bytesRead);
        }

        fileInputStream.close();
        outputStream.close();
    }

    public static String getSubmittedFileName(Part part) {
        for (String cd : part.getHeader("content-disposition").split(";")) {
            if (cd.trim().startsWith("filename")) {
                String fileName = cd.substring(cd.indexOf('=') + 1).trim().replace("\"", "");
                return fileName.substring(fileName.lastIndexOf('/') + 1).substring(fileName.lastIndexOf('\\') + 1); // MSIE fix.
            }
        }
        return null;
    }

    public static String generateHashFilename(){
        String customTag = String.valueOf(new Random().nextInt(15));
        try{
            String chrs = "0123456789abcdefghijklmnopqrstuvwxyz-_ABCDEFGHIJKLMNOPQRSTUVWXYZ";
            SecureRandom secureRandom = SecureRandom.getInstanceStrong();
            customTag = secureRandom
                .ints(20, 0, chrs.length()) // 20 is the length of the string you want
                .mapToObj(i -> chrs.charAt(i))
                .collect(StringBuilder::new, StringBuilder::append, StringBuilder::append)
                .toString();

        }catch(NoSuchAlgorithmException ex){
            ex.printStackTrace();
        }
        return customTag;
    }
}
