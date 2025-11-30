package obuchenie;

import java.io.*;
import java.util.*;
import java.util.jar.Attributes;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.Manifest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * User: tor
 * Date: 19.11.2025
 * Time: 18:15
 * <p>
 * Подготовьте файл с логом Maven, например `maven.log`.
 * <p>
 * Запустите:
 * ```bash
 * java MavenSystemPathFixer < maven.log > install-libs.bat
 * ```
 * <p>
 * Получите `install-libs.bat` — запустите его в той же директории, где находится ваш проект (чтобы относительные пути работали).
 * <p>
 * - Скрипт предполагает, что вы запускаете `.bat` из корня проекта (`${project.basedir}`).
 * - Версия артефакта извлекается из имени JAR-файла (например, `hornetq-core-client-2.4.7.Final.jar` → версия `2.4.7.Final`). Если версия не распознана — ставится `1.0`.
 */
public class MavenBatGenerator {


    // Регулярное выражение для извлечения groupId, artifactId, version и systemPath
    private static final String WARNING_PATTERN =
            "\\[WARNING\\] 'dependencies\\.dependency\\.systemPath' for "
                    + "([^:]+):"               // groupId
                    + "([^:]+):"               // artifactId
                    + "([^\\s]+)"              // packaging (jar)
                    + " should not point at files within the project directory, "
                    + "\\$\\{(?:project\\.)?basedir\\}(/[^@]+?\\.jar)"  // path
                    + " will be unresolvable by dependent projects @ line (\\d+), column (\\d+)";

    private static final String BASE_DIR = "E:\\project\\e2013";

    public static void main(String[] args) {
        try {

            List<String> warnings = (args != null) ? readInput() : readInput(new File("d:\\log_file.log"));
            List<DependencyInfo> deps = parseWarnings(warnings);
            generateBatScript(deps, System.out);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static List<String> readInput() throws IOException {
        List<String> lines = new ArrayList<String>();
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String line;
        while ((line = reader.readLine()) != null) {
            lines.add(line);
        }
        return lines;
    }

    private static List<String> readInput(File file) throws IOException {
        List<String> lines = new ArrayList<String>();
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
        String line;
        while ((line = reader.readLine()) != null) {
            lines.add(line);
        }
        System.out.println("lines.size() = " + lines.size());
        return lines;
    }

    private static List<DependencyInfo> parseWarnings(List<String> lines) {
        List<DependencyInfo> deps = new ArrayList<DependencyInfo>();
        Pattern pattern = Pattern.compile(WARNING_PATTERN);

        for (String line : lines) {
            Matcher matcher = pattern.matcher(line);
            if (!matcher.find()) {
//                System.err.println("NO MATCH: " + line);
                continue;
            }

            String groupId = matcher.group(1); // local-lib
            String artifactId = matcher.group(2); // classes12.jar
            String packaging = matcher.group(3); // jar
            String relativePath = matcher.group(4); // точный путь типа /../big-project/.../file.jar
            System.out.println("G1=" + groupId + "; G2=" + artifactId + "; G3=" + packaging + "; PATH_RAW=" + relativePath);
            // Определяем версию по имени файла
//            String fileName = new File(relativePath).getName(); // classes12.jar
//            String version = extractVersionFromFileName(fileName);
            String version = extractVersion(relativePath);
            String cleaned = sanitizeRelativePath(relativePath);
            File abs = new File(BASE_DIR, cleaned).getAbsoluteFile();
            deps.add(new DependencyInfo(groupId, artifactId, version, packaging, abs.getAbsolutePath()));
        }
        deps = dedupeByFile(deps);
        Collections.sort(deps, new Comparator<DependencyInfo>() {
            public int compare(DependencyInfo a, DependencyInfo b) {
                return a.getArtifactId().compareToIgnoreCase(b.getArtifactId());
            }
        });

        return deps;
    }

    // Простая эвристика: если имя файла содержит цифры и точки — считаем это версией
    private static String extractVersionFromFileName(String fileName) {
        // Убираем расширение .jar
        if (fileName.endsWith(".jar")) {
            fileName = fileName.substring(0, fileName.length() - 4);
        }

        // Ищем подстроку, похожую на версию (цифры и точки)
        Pattern versionPattern = Pattern.compile(".*?([0-9]+\\.[0-9]+(?:\\.[0-9]+)*(?:-[a-zA-Z0-9._-]*)?)$");
        Matcher m = versionPattern.matcher(fileName);
        if (m.matches()) {
            return m.group(1);
        }

        // Если не нашли — возвращаем null
        return null;
    }

    private static String sanitizeRelativePath(String rawPath) {
        if (rawPath == null) return null;
        String s = rawPath.trim();

        // Уберём ведущие пробелы и кавычки
        if (s.startsWith("\"") && s.endsWith("\"")) {
            s = s.substring(1, s.length() - 1).trim();
        }

        // Если путь начинается с ${basedir} или ${project.basedir}, удалим это (на всякий)
        s = s.replace("${basedir}", "").replace("${project.basedir}", "");

        // Убрать ведущие разделители, чтобы File(parent, child) использовал parent
        while (s.length() > 0 && (s.charAt(0) == '/' || s.charAt(0) == '\\')) {
            s = s.substring(1);
        }

        // Привести разделители к платформенным (Windows ожидает '\')
        s = s.replace('/', File.separatorChar).replace('\\', File.separatorChar);
        if (s.startsWith("..\\big-project")) {
            s = s.replace("..\\big-project", "core\\big-project");
        } else if (s.startsWith("..\\core\\big-project")) {
            s = s.replace("..\\core\\big-project", "core\\big-project");
        } else if (s.startsWith("lib"))
            s = s.replace("lib", "core\\big-project\\lib");
        return s;
    }

    public static String extractVersion(String relativePath) {

        // BASE_DIR — должна быть java.io.File
        String cleaned = sanitizeRelativePath(relativePath);
        File abs = new File(BASE_DIR, cleaned).getAbsoluteFile();

        if (!abs.exists()) {
            System.err.println("abs.getAbsolutePath() = " + abs.getAbsolutePath());
            return "1.0";
        }

        JarFile jar = null;
        try {
            jar = new JarFile(abs);

            // 1. META-INF/maven/**/**/pom.properties
            String version = getVersionFromPomProperties(jar);
            if (version != null) return version;

            // 2. MANIFEST.MF
            version = getVersionFromManifest(jar);
            if (version != null) return version;

            // 3. Embedded pom.xml
            version = getVersionFromPomXml(jar);
            if (version != null) return version;

        } catch (Exception ignored) {
            // можно логировать
        } finally {
            if (jar != null) {
                try {
                    jar.close();
                } catch (Exception ignored) {
                }
            }
        }

        // 4. fallback: версия по имени файла
        String fallback = extractVersionFromFileName(abs.getName());
        return fallback != null ? fallback : "1.0";
    }

    private static String getVersionFromPomProperties(JarFile jar) {
        try {
            Enumeration<JarEntry> entries = jar.entries();
            while (entries.hasMoreElements()) {
                JarEntry e = entries.nextElement();
                if (e.getName().endsWith("pom.properties")
                        && e.getName().startsWith("META-INF/maven/")) {

                    Properties p = new Properties();
                    p.load(jar.getInputStream(e));
                    return p.getProperty("version");
                }
            }
        } catch (Exception ignored) {
        }
        return null;
    }

    private static String extractVersionFromJar(File jarFile) {
        JarFile jar = null;
        try {
            jar = new JarFile(jarFile);
            Manifest manifest = jar.getManifest();
            if (manifest == null) return null;

            Attributes attrs = manifest.getMainAttributes();

            String version;

            // 1) Maven/Gradle-style
            version = attrs.getValue("Implementation-Version");
            if (version != null) return version.trim();

            // 2) Java standard
            version = attrs.getValue("Specification-Version");
            if (version != null) return version.trim();

            // 3) OSGi bundles
            version = attrs.getValue("Bundle-Version");
            if (version != null) return version.trim();

            // 4) last chance
            version = attrs.getValue("Implementation-Title");
            if (version != null && version.matches(".*\\d+.*"))
                return version.trim();

        } catch (IOException e) {
            return null;
        } finally {
            try {
                if (jar != null) jar.close();
            } catch (IOException ignored) {
            }
        }
        return null;
    }

    private static String getVersionFromManifest(JarFile jar) {
        try {
            Manifest mf = jar.getManifest();
            if (mf == null) return null;

            Attributes attrs = mf.getMainAttributes();
            for (String key : Arrays.asList("Implementation-Version", "Bundle-Version", "Specification-Version")) {
                String v = attrs.getValue(key);
                if (v != null) return v;
            }
        } catch (Exception ignored) {
        }
        return null;
    }

    private static String getVersionFromPomXml(JarFile jar) {
        try {
            Enumeration<JarEntry> entries = jar.entries();
            while (entries.hasMoreElements()) {
                JarEntry e = entries.nextElement();
                if (e.getName().endsWith("pom.xml")) {

                    InputStream in = null;
                    try {
                        in = jar.getInputStream(e);

                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        byte[] buffer = new byte[4096];
                        int n;

                        while ((n = in.read(buffer)) != -1) {
                            baos.write(buffer, 0, n);
                        }

                        String xml = new String(baos.toByteArray(), "UTF-8");

                        Matcher m = Pattern.compile("<version>([^<]+)</version>").matcher(xml);
                        if (m.find()) {
                            return m.group(1);
                        }

                    } finally {
                        if (in != null) {
                            try {
                                in.close();
                            } catch (Exception ignored) {
                            }
                        }
                    }
                }
            }
        } catch (Exception ignored) {
            // можно логировать, если надо
        }

        return null;
    }

    private static List<DependencyInfo> dedupeByFile(List<DependencyInfo> input) {
        LinkedHashMap<String, DependencyInfo> map = new LinkedHashMap<String, DependencyInfo>();

        for (DependencyInfo d : input) {
            // абсолютный путь (как String)
            String absPath = d.getRelativePath();
            /*String absPath = new File(BASE_DIR, sanitizeRelativePath(d.getRelativePath()))
                    .getAbsoluteFile()
                    .getPath();*/

            // вставляем только если отсутствует
            if (!map.containsKey(absPath)) {
                map.put(absPath, d);
            }
            // если хочешь, можешь здесь логировать дубликаты
        }

        return new ArrayList<DependencyInfo>(map.values());
    }

    private static void generateBatScript(List<DependencyInfo> deps, PrintStream out) {
        out.println("@echo off");
        out.println("set BASE=E:\\project\\e2013\n");
        out.println("echo Using BASE=%BASE%");
        out.println("REM Auto-generated script to install systemPath dependencies into local Maven repo");
        out.println();

        for (DependencyInfo dep : deps) {
            // Заменяем / на \ для Windows путей
            String windowsPath = dep.relativePath.replace('/', '\\');
            // Убираем начальный \, если есть
            if (windowsPath.startsWith("\\")) {
                windowsPath = windowsPath.substring(1);
            }
            windowsPath = windowsPath.replaceAll("E:\\\\project\\\\e2013", "%BASE%");
            // Формируем команду
            String cmd = String.format(
                    "call mvn install:install-file -DgroupId=%s -DartifactId=%s -Dversion=%s -Dpackaging=%s -Dfile=\"%s\"",
                    "ecwo6"/* dep.groupId*/, dep.artifactId.replaceAll(".jar", ""), dep.version, dep.packaging, windowsPath
            );
            out.println(cmd);
        }

        out.println();
        out.println("echo All dependencies installed.");
        out.println("pause");
    }

    static class DependencyInfo {
        final String groupId;
        final String artifactId;
        final String version;
        final String packaging;
        final String relativePath;

        DependencyInfo(String groupId, String artifactId, String version, String packaging, String relativePath) {
            this.groupId = groupId;
            this.artifactId = artifactId;
            this.version = version;
            this.packaging = packaging;
            this.relativePath = relativePath;
        }

        public String getGroupId() {
            return groupId;
        }

        public String getArtifactId() {
            return artifactId;
        }

        public String getVersion() {
            return version;
        }

        public String getRelativePath() {
            return relativePath;
        }

        @Override
        public String toString() {
            final StringBuffer sb = new StringBuffer("DependencyInfo{");
            sb.append("groupId='").append(groupId).append('\'');
            sb.append(", artifactId='").append(artifactId).append('\'');
            sb.append(", version='").append(version).append('\'');
            sb.append(", packaging='").append(packaging).append('\'');
            sb.append(", relativePath='").append(relativePath).append('\'');
            sb.append('}');
            return sb.toString();
        }
    }

}