import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.util.ArrayList;


public class FileEx extends File {

    private static final char END_LINE = '\r';

    public ArrayList<String> readLines(int num) throws Exception {
        ArrayList<String> stringList = new ArrayList<>();
        if (num <= 0) num = Integer.MAX_VALUE; // read whole file
        FileInputStream fis = new FileInputStream(this);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            int data;
            data = fis.read();
            while (stringList.size() < num) {
                if (data == END_LINE || data == -1) {
                    stringList.add(bos.toString());
                    bos.reset();
                } else bos.write(data);
                data = fis.read();
            }
        } finally {
            bos.close();
            fis.close();
        }
        return stringList;
    }

    public ArrayList<String> readLinesReverse(int num) throws Exception {
        ArrayList<String> stringList = new ArrayList<>();
        if (num <= 0) return readLines(0); // read whole file
        StringBuilder sb = new StringBuilder();
        READ_FILE:
        try (RandomAccessFile file = new RandomAccessFile(this, "r")) {
            long fileLen = file.length() - 1;
            READ_LINES:
            for (long pointer = fileLen; pointer >= 0; pointer--) {
                file.seek(pointer);
                int data = file.read();
                char chr = (char) data;
                if (data == END_LINE && sb.length() > 0) {
                    stringList.add(sb.reverse().toString());
                    if (stringList.size() == num) break READ_FILE;
                    sb.setLength(0);
                } else
                    sb.append(chr);
                if (data == -1) break READ_LINES;
            }
            stringList.add(sb.reverse().toString());
        }
        return stringList;
    }

    public void writeLine(String text) throws Exception {
        try (BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(this, true))) {
            bos.write(text.getBytes());
            bos.write(END_LINE);
        }
    }

    public FileEx(@NotNull String pathname) {
        super(pathname);
    }

}
