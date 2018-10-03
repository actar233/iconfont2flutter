package io.github.actar676309180.iconfont2flutter;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.*;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class IconFont2FlutterIconData {
    private Map<String, String> map = new HashMap<String, String>();

    public void reset() {
        map.clear();
    }

    public void parse(String path) throws IOException {
        this.parse(new File(path));
    }

    public void parse(File file) throws IOException {
        Document document = Jsoup.parse(file, "utf-8");
        Elements names = document.getElementsByClass("name");
        Elements codes = document.getElementsByClass("code");
        int size = names.size();
        for (int i = 0; i < size; i++) {
            String name = names.get(i).text();
            String code = codes.get(i).text();
            put(name, code);
        }
    }

    private void put(String key, String value) {
        map.put(key, value.replace("&#", "0").replace(";", ""));
    }

    public void outputFile(String path,String className) throws IOException {
        this.outputFile(new File(path),className);
    }

    public void outputFile(String path) throws IOException {
        this.outputFile(new File(path));
    }

    public void outputFile(File file) throws IOException {
        outputFile(file,"IconFont");
    }

    public void outputFile(File file, String className) throws IOException {
        StringBuilder builder = new StringBuilder();
        builder.append("import 'package:flutter/widgets.dart';\r\n");
        builder.append(MessageFormat.format("// 这是由 iconfont2flutter 自动生成的 {0} \r\n", className));
        builder.append(MessageFormat.format("class {0} '{'\r\n", className));
        builder.append(MessageFormat.format("  {0}._();\r\n",className));
        Set<String> keySet = map.keySet();
        for (String key : keySet) {
            builder.append(MessageFormat.format("  // 这是由 iconfont2flutter 自动生成的 {0} 图标的IconData\r\n", key));
            builder.append(MessageFormat.format("  static const IconData {0} = IconData({1}, fontFamily: ''{2}'');\r\n", key, map.get(key), className));
        }
        builder.append("}\r\n");
        OutputStreamWriter writer = new OutputStreamWriter(new BufferedOutputStream(new FileOutputStream(file)));
        writer.write(builder.toString());
        writer.flush();
        writer.close();
    }
}
