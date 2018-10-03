package io.github.actar676309180.iconfont2flutter;

import java.io.File;
import java.io.IOException;
import java.text.MessageFormat;

public class Main {

    public static void main(String[] args) {
        if (args.length >= 2 && args.length <= 3) {
            process(args);
        } else {
            help();
        }
    }

    private static void process(String[] args) {
        String input = args[0];
        String output = args[1];

        File file = new File(input);

        if (file.isDirectory()) {
            file = new File(file, "demo_unicode.html");
            input = file.getAbsolutePath();
        }

        if (!file.isFile()) {
            System.out.println(MessageFormat.format("{0} 不存在", input));
            return;
        }


        file = new File(output);
        if (file.isDirectory()) {
            file = new File(file, "icon_font.dart");
            output = file.getAbsolutePath();
        }

        if (file.isDirectory()) {
            System.out.println(MessageFormat.format("{0} 是一个目录", output));
            return;
        }

        IconFont2FlutterIconData i2f = new IconFont2FlutterIconData();
        try {
            i2f.parse(input);

            if (args.length == 3) {
                String className = args[2];
                i2f.outputFile(output, className);
            } else {
                i2f.outputFile(output);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void help() {
        System.out.println("本工具是将iconfont下载的文件生成Flutter中可用的代码");
        System.out.println("用法 iconfont2flutter [输入目录或文件] [输出目录或文件] [可选-生成代码的类名]");
        System.out.println("例子: iconfont2flutter '/home/download/iconfont' '/home/dart_demo/lib/font/icon_font.dart'");
        System.out.println("原理: 通过解析 demo_unicode.html 来生成dart代码");
    }
}
