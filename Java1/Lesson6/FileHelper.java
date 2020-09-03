import java.io.*;

public class FileHelper {

    // проверяет существование файла
    public static boolean fileExists(String src) {
        File srcFile = new File(src);
        return (srcFile.exists() && srcFile.isFile());
    }

    // проверяет существование директории
    public static boolean dirExists(String src) {
        File srcFile = new File(src);
        return (srcFile.exists() && srcFile.isDirectory());
    }

    // возвращает содержимое директории
    public static File[] dirList(String dir) {
        if (dirExists(dir)) {
            File fDir = new File(dir);
            return fDir.listFiles();
        } else
            return null;
    }

    // возвращает содержимое файла
    public static StringBuilder getFileContent(String src) throws IOException {
        if (fileExists(src)) {
            FileInputStream fileSrc = new FileInputStream(src);
            StringBuilder str = new StringBuilder();
            int c;
            do {
                c = fileSrc.read();
                if (c >= 0) str.append((char) c);
            } while (c >= 0);
            fileSrc.close();
            return str;
        }
        return null;
    }

    // файл содержит строку
    public static boolean containsString(String src, String str) throws IOException {
        StringBuilder contentSrc = getFileContent(src);
        if (contentSrc != null) {
            return contentSrc.toString().contains(str);
        }
        return false;
    }

    // возвращает путь к файлу, который содержит строку
    public static String dirContainsString(String dir, String str) throws IOException {
        String fileContains = null;
        File[] dirList = dirList(dir);
        if (dirList != null) {
            for (int i = 0; i < dirList.length; i++) {
                System.out.println(dirList[i].getAbsolutePath());
                if (dirList[i].isDirectory()) fileContains = dirContainsString(dirList[i].getAbsolutePath(), str);
                else if (dirList[i].isFile())
                    if (containsString(dirList[i].getAbsolutePath(), str))
                        fileContains = dirList[i].getAbsolutePath();
                if (fileContains != null) return fileContains;
            }
        }
        return null;
    }

    // склеивает файлы
    public static boolean appendFile(String dst, String src) throws IOException {
        StringBuilder contentSrc = getFileContent(src);
        if (contentSrc != null) {
            FileOutputStream fileDst = new FileOutputStream(dst, true);
            fileDst.write(contentSrc.toString().getBytes());
            fileDst.flush();
            fileDst.close();
            return true;
        }
        return false;
    }
}