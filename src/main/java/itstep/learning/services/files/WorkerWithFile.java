package itstep.learning.services.file;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.sql.DriverManager;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class WorkerWithFile implements ReadingFile{

    @Override
    public Map<String,String> readConnectionDataFromFile() {

        Map<String,String> data = new HashMap<>();

        try {
            ClassLoader classLoader = WorkerWithFile.class.getClassLoader();


                try (InputStream rs = classLoader.getResourceAsStream("db.ini")) {
                    String content = readStream(rs);
                    String[] lines = content.split("\n");
                    Map<String, String> ini = new HashMap<>();

                    for (String line : lines) {
                        String[] parts = line.split("=");
                        ini.put(parts[0].trim(), parts[1].trim());
                    }
                    data.put("url", String.format("jdbc:%s://%s:%s/%s?useUnicode=%s&characterEncoding=%s",
                            ini.get("dbms"),
                            ini.get("host"),
                            ini.get("port"),
                            ini.get("schema"),
                            ini.get("useUnicode"),
                            ini.get("characterEncoding")));

                    data.put("user", ini.get("user"));
                    data.put("password", ini.get("password"));
                }
        } catch (Exception ex) {
            System.err.println("Ошибка при чтении файла: " + ex.getMessage());
        }

        return data;
    }

    private String readStream(InputStream in, Charset charset) throws IOException{

        byte[] buffer = new byte[4096];

        try(ByteArrayOutputStream byteBuilder = new ByteArrayOutputStream();
            BufferedInputStream bis = new BufferedInputStream(in) )
        {
            int len;
            while((len = bis.read(buffer)) != -1)
            {
                byteBuilder.write(buffer, 0, len);
            }
            return byteBuilder.toString(charset.name());
        }
    }

    private String readStream(InputStream in) throws IOException{
        return readStream(in, StandardCharsets.UTF_8);
    }
}