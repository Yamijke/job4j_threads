package thread;

import java.io.*;
import java.util.function.Predicate;

public final class ParseFile {
    private final File file;

    public ParseFile(File file) {
        this.file = file;
    }

    public String content(Predicate<Character> filter) throws IOException {
        try (InputStream input = new BufferedInputStream(new FileInputStream(file))) {
            StringBuilder str = new StringBuilder();
            int data;
            while ((data = input.read()) != -1) {
                if (filter.test((char) data)) {
                    str.append((char) data);
                }
            }
            return str.toString();
        }
    }

    public String getContent() throws IOException {
        return content(ch -> true);
    }

    public String getContentWithoutUnicode() throws IOException {
        return content(ch -> ch < 0x80);
    }
}
