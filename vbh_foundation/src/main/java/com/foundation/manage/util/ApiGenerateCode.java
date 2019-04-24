package com.foundation.manage.util;

import java.io.*;
import java.net.URLDecoder;
import java.util.regex.Matcher;

public class ApiGenerateCode {


    public static void main(String[] args) {

        String tempPath = "D:\\ApiGenerateCode\\code_template\\";
        String newFilePath = "D:\\ApiGenerateCode\\code_generate\\";

        try {
            tempPath = URLDecoder.decode(ApiGenerateCode.class.getClassLoader().getResource("code_template").toString().replaceAll("file:/","")+"\\","utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        System.out.println("tempPath:" + tempPath);


        String tableName = "vbc_website_news_category";  //表名称
        String manageName = "NewsCategory";              //模块名称 (controller前面一部分 NewsCategory--->Controller)
        String requestPath = "newsCategory";
        String columns[] = {"NEWS_CATEGORY_ID", "NEWS_CATEGORY_NAME", "SEQ", "STATUS", "CREATE_BY", "CREATE_TIME", "LAST_UPDATE_BY", "LAST_UPDATE_TIME", "LANGUAGE"};
        String filedNames[] = {"newsCategoryId", "newsCategoryName", "seq", "status", "createBy", "createTime", "lastUpdateBy", "lastUpdateTime", "language"};
        String fieldDescs[] = {"主键", "新闻分类名称", "序号", "状态", "创建人", "创建时间", "最后修改人", "最后修改时间", "语言"};
        String columnTypes[] = {"Long", "String", "Long", "Long", "String", "Date", "String", "Date", "String"};

         generateCode( tempPath, newFilePath, tableName, manageName, requestPath, columns, filedNames, fieldDescs, columnTypes);

    }


    public static void generateCode(String tempPath, String newFilePath, String tableName, String manageName, String requestPath, String[] columns, String filedNames[], String fieldDescs[], String columnTypes[]) {
        //  String newFilePath = "D:\\work\\code_generate\\";
        // String tempPath = "D:\\work\\code_template\\";
//
//
//        String tableName = "vbc_website_news_category";
//        String manageName = "NewsCategory";
//        String requestPath = "newsCategory";
//        String columns[] = {"NEWS_CATEGORY_ID", "NEWS_CATEGORY_NAME", "SEQ", "STATUS", "CREATE_BY", "CREATE_TIME", "LAST_UPDATE_BY", "LAST_UPDATE_TIME", "LANGUAGE"};
//        String filedNames[] = {"newsCategoryId", "newsCategoryName", "seq", "status", "createBy", "createTime", "lastUpdateBy", "lastUpdateTime", "language"};
//        String fieldDescs[] = {"主键", "新闻分类名称", "序号", "状态", "创建人", "创建时间", "最后修改人", "最后修改时间", "语言"};
//        String columnTypes[] = {"Long", "String", "Long", "Long", "String", "Date", "String", "Date", "String"};

        String primaryField = filedNames[0];

        /**
         * 1. controller
         *
         *  1.1  com.foundation.manage.controller   替换为   com.foundation.manage.controller.ms
         *  1.2  Notice         News
         *  1.2  notice 替换为  news
         *  1.3  Notice 替换为  News
         *  1.4  文件名替换 NewsController
         */

        System.out.println("1. 开始生成controller.");

        String fileName = tempPath + "controller\\NoticeController.java1";
        String outFileName = newFilePath + manageName + "\\controller\\" + manageName + "Controller.java";

        try {
            StringBuffer newSb = new StringBuffer();

            BufferedReader br = new BufferedReader(new FileReader(fileName));


            createNewFile(outFileName);

            BufferedWriter bw = new BufferedWriter(new FileWriter(new File(outFileName)));

            String line = null;
            while ((line = br.readLine()) != null) {
                line = line.replaceAll("/system/", "/vbc/");
                line = line.replaceAll("com.foundation.manage.controller", "com.foundation.manage.controller.ms");
                line = line.replaceAll("com.foundation.manage.service.NoticeService", "com.foundation.manage.service.vbc." + manageName + "Service");
                line = line.replaceAll("com.foundation.manage.entity.Notice", "com.foundation.manage.entity.vbc." + manageName);
                line = line.replaceAll("com.foundation.manage.warpper.NoticeWrapper", "com.foundation.manage.warpper.vbc." + manageName + "Wrapper");
                line = line.replaceAll("notice", requestPath);
                line = line.replaceAll("Notice", manageName);
                newSb.append(line).append("\r\n");
            }

            bw.write(newSb.toString());
            bw.flush();
            bw.close();
            System.out.println("1. controller 生成完成.");

            /**
             * 2. service
             * 2.1 Notice   com.foundation.manage.entity.vbc.News
             * 2.2   Notice                            News
             *
             */

            System.out.println("2. 开始生成service.");

            newSb = new StringBuffer();
            fileName = tempPath + "\\service\\NoticeService.java1";
            outFileName = newFilePath + manageName + "\\service\\" + manageName + "Service.java";
            createNewFile(outFileName);
            br = new BufferedReader(new FileReader(fileName));
            bw = new BufferedWriter(new FileWriter(new File(outFileName)));

            line = null;
            while ((line = br.readLine()) != null) {
                line = line.replaceAll("com.foundation.manage.service;", "com.foundation.manage.service.vbc;");
                line = line.replaceAll("com.foundation.manage.entity.Notice", "com.foundation.manage.entity.vbc." + manageName);
                line = line.replaceAll("com.foundation.manage.mapper.NoticeMapper", "com.foundation.manage.mapper." + manageName + "Mapper");

                line = line.replaceAll("Notice", manageName);
                newSb.append(line).append("\r\n");
            }

            bw.write(newSb.toString());
            bw.flush();
            bw.close();

            /**
             * 3. warpper
             * 2.1 Notice                             .News
             *
             */
            System.out.println("3. 开始生成warpper.");

            newSb = new StringBuffer();
            fileName = tempPath + "\\warpper\\NoticeWrapper.java1";
            outFileName = newFilePath + manageName + "\\warpper\\" + manageName + "Wrapper.java";
            createNewFile(outFileName);
            br = new BufferedReader(new FileReader(fileName));
            bw = new BufferedWriter(new FileWriter(new File(outFileName)));

            line = null;
            while ((line = br.readLine()) != null) {
                line = line.replaceAll("com.foundation.manage.warpper;", "com.foundation.manage.warpper.vbc;");
                line = line.replaceAll("Notice", manageName);
                newSb.append(line).append("\r\n");
            }

            bw.write(newSb.toString());
            bw.flush();
            bw.close();


            /**
             * 4. entiry
             *
             */

            System.out.println("4. 开始生成entity.");

            newSb = new StringBuffer();
            fileName = tempPath + "\\entity\\Notice.java1";
            outFileName = newFilePath + manageName + "\\entity\\" + manageName + ".java";
            createNewFile(outFileName);
            br = new BufferedReader(new FileReader(fileName));
            bw = new BufferedWriter(new FileWriter(new File(outFileName)));

            line = null;
            while ((line = br.readLine()) != null) {
                line = line.replaceAll("com.foundation.manage.entity;", "com.foundation.manage.entity.vbc;");
                line = line.replaceAll("Notice", manageName);
                line = line.replaceAll("sys_notice", tableName);
                newSb.append(line).append("\r\n");
            }

            StringBuffer columnSb = new StringBuffer();
            StringBuffer getsetSb = new StringBuffer();

            for (int i = 0; i < columns.length; i++) {
                String columnName = columns[i];
                String fieldName = filedNames[i];
                String columnType = columnTypes[i];
                String desc = fieldDescs[i];


                if (i == 0) {
                    columnSb.append("//" + desc).append("\r\n");
                    columnSb.append("@TableId(value = \"" + columnName + "\", type = IdType.AUTO)").append("\r\n");
                    columnSb.append("private " + columnType + " " + fieldName + ";").append("\r\n");
                } else {
                    columnSb.append("//" + desc).append("\r\n");
                    if (columnName.equalsIgnoreCase("CREATE_TIME") || columnName.equalsIgnoreCase("CREATE_BY")) {
                        columnSb.append("@TableField(value = \"" + columnName + "\", fill = FieldFill.INSERT)").append("\r\n");
                    } else if (columnName.equalsIgnoreCase("LAST_UPDATE_BY") || columnName.equalsIgnoreCase("LAST_UPDATE_TIME")) {
                        columnSb.append("@TableField(value = \"" + columnName + "\", fill = FieldFill.INSERT_UPDATE)").append("\r\n");
                    } else {
                        columnSb.append("@TableField(\"" + columnName + "\")").append("\r\n");
                    }
                    columnSb.append("private " + columnType + " " + fieldName + ";").append("\r\n");
                }

                String fileNameFistToUpper = fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
                getsetSb.append(" public void set" + fileNameFistToUpper + "(" + columnType + " " + fieldName + ") { this." + fieldName + " = " + fieldName + "; }").append("\r\n");
                getsetSb.append(" public " + columnType + " get" + fileNameFistToUpper + "() {  return " + fieldName + ";}").append("\r\n");

            }

            String result = newSb.toString();
            result = result.replaceAll("autoField", columnSb.toString()) + "\r\n";
            result = result.replaceAll("autoGetSet", getsetSb.toString()) + "\r\n";
            bw.write(result);
            bw.flush();
            bw.close();


            /**
             * 5. mapper
             */
            System.out.println("5. 开始生成mapper.");

            newSb = new StringBuffer();
            fileName = tempPath + "\\\\mapper\\NoticeMapper.java1";
            outFileName = newFilePath + manageName + "\\mapper\\" + manageName + "Mapper.java";
            createNewFile(outFileName);
            br = new BufferedReader(new FileReader(fileName));
            bw = new BufferedWriter(new FileWriter(new File(outFileName)));

            line = null;
            while ((line = br.readLine()) != null) {
                line = line.replaceAll("com.foundation.manage.mapper;", "com.foundation.manage.mapper;");
                line = line.replaceAll("com.foundation.manage.entity.Notice", "com.foundation.manage.entity.vbc." + manageName);
                line = line.replaceAll("Notice", manageName);
                newSb.append(line).append("\r\n");
            }

            bw.write(newSb.toString());
            bw.flush();
            bw.close();


            /**
             * 6. mapper/mapping
             */
            System.out.println("6. 开始生成mapping.");

            newSb = new StringBuffer();
            fileName = tempPath + "\\mapper\\mapping\\NoticeMapper.xml1";
            outFileName = newFilePath + manageName + "\\mapper\\mapping\\" + manageName + "Mapper.xml";
            createNewFile(outFileName);
            br = new BufferedReader(new FileReader(fileName));
            bw = new BufferedWriter(new FileWriter(new File(outFileName)));

            line = null;
            while ((line = br.readLine()) != null) {
                line = line.replaceAll("com.foundation.manage.mapper.NoticeMapper", "com.foundation.manage.mapper." + manageName + "Mapper");
                line = line.replaceAll("com.foundation.manage.entity.Notice", "com.foundation.manage.entity.vbc." + manageName);
                line = line.replaceAll("sys_notice", tableName);
                newSb.append(line).append("\r\n");
            }


            StringBuffer mapSb = new StringBuffer();
            StringBuffer columnListSb = new StringBuffer();

            for (int i = 0; i < columns.length; i++) {
                String columnName = columns[i];
                String fieldName = filedNames[i];


                if (i == 0) {
                    mapSb.append("<id column=\"" + columnName + "\" property=\"" + fieldName + "\" />").append("\r\n");
                } else {
                    mapSb.append("<result column=\"" + columnName + "\" property=\"" + fieldName + "\" />").append("\r\n");
                }
                columnListSb.append(columnName).append(" AS ").append(fieldName).append(",");
            }
            columnListSb = new StringBuffer(columnListSb.substring(0, columnListSb.length() - 1));

            String resultMapping = newSb.toString().replaceAll("mapMappingContent", mapSb.toString());
            resultMapping = resultMapping.toString().replaceAll("columnMappingContent", columnListSb.toString());


            bw.write(resultMapping);
            bw.flush();
            bw.close();

            /**
             * 7. html
             */

            /**
             * notice.html
             */

            newSb = new StringBuffer();
            fileName = tempPath + "\\html\\notice.html";
            outFileName = newFilePath + manageName + "\\html\\" + requestPath + ".html";
            createNewFile(outFileName);
            br = new BufferedReader(new FileReader(fileName));
            bw = new BufferedWriter(new FileWriter(new File(outFileName)));

            line = null;
            while ((line = br.readLine()) != null) {
                line = line.replaceAll("/system/notice/notice.js", "/vbc/" + requestPath + "/" + requestPath + ".js");
                line = line.replaceAll("Notice", manageName);
                line = line.replaceAll("/notice", "/" + requestPath);
                newSb.append(line).append("\r\n");
            }

            bw.write(newSb.toString());
            bw.flush();
            bw.close();

            /**
             * notice_add.html
             */

            newSb = new StringBuffer();
            fileName = tempPath + "\\html\\notice_add.html";
            outFileName = newFilePath + manageName + "\\html\\" + requestPath + "_add.html";
            createNewFile(outFileName);
            br = new BufferedReader(new FileReader(fileName));
            bw = new BufferedWriter(new FileWriter(new File(outFileName)));

            line = null;
            while ((line = br.readLine()) != null) {
                line = line.replaceAll("/system/notice/notice_add.js", "/vbc/" + requestPath + "/" + requestPath + "_add.js");
                line = line.replaceAll("Notice", manageName);
                line = line.replaceAll("notice", requestPath);
                newSb.append(line).append("\r\n");
            }

            StringBuffer fieldHtmlSb = new StringBuffer();

            for (int i = 1; i < filedNames.length; i++) {
                String fieldName = filedNames[i];
                String title = fieldDescs[i];
                String columnName = columns[i];

                if (!columnName.equalsIgnoreCase("CREATE_TIME") && !columnName.equalsIgnoreCase("CREATE_BY") && !columnName.equalsIgnoreCase("LAST_UPDATE_BY") && !columnName.equalsIgnoreCase("LAST_UPDATE_TIME")) {
                    fieldHtmlSb.append(" <div class=\"form-group\">").append("\r\n");
                    fieldHtmlSb.append("<h5>" + title + " <span class=\"text-danger\">*</span></h5>").append("\r\n");
                    fieldHtmlSb.append("  <div class=\"controls\">").append("\r\n");
                    fieldHtmlSb.append("   <input id=\"" + fieldName + "\" type=\"text\" class=\"form-control\">").append("\r\n");
                    fieldHtmlSb.append("  </div>").append("\r\n");
                    fieldHtmlSb.append("   </div>").append("\r\n");
                }


            }

            String htmlFieldResult = newSb.toString().replaceAll("fieldHtmlContent", fieldHtmlSb.toString());


            bw.write(htmlFieldResult);
            bw.flush();
            bw.close();

            /**
             * notice_edit.html
             */

            newSb = new StringBuffer();
            fileName = tempPath + "\\html\\notice_edit.html";
            outFileName = newFilePath + manageName + "\\html\\" + requestPath + "_edit.html";
            createNewFile(outFileName);
            br = new BufferedReader(new FileReader(fileName));
            bw = new BufferedWriter(new FileWriter(new File(outFileName)));

            line = null;
            while ((line = br.readLine()) != null) {
                line = line.replaceAll("/system/notice/notice_edit.js", "/vbc/" + requestPath + "/" + requestPath + "_edit.js");
                line = line.replaceAll("Notice", manageName);
                line = line.replaceAll("notice", requestPath);
                newSb.append(line).append("\r\n");
            }

            fieldHtmlSb = new StringBuffer();
            StringBuffer fieldHiddenHtmlSb = new StringBuffer();

            for (int i = 0; i < filedNames.length; i++) {
                String fieldName = filedNames[i];
                String title = fieldDescs[i];
                String columnName = columns[i];

                if (i == 0) {
                    fieldHiddenHtmlSb.append("<input id=\"" + fieldName + "\" value=\"${" + fieldName + "}\" type=\"hidden\" class=\"form-control\">").append("\r\n");
                } else {
                    if (!columnName.equalsIgnoreCase("CREATE_TIME") && !columnName.equalsIgnoreCase("CREATE_BY") && !columnName.equalsIgnoreCase("LAST_UPDATE_BY") && !columnName.equalsIgnoreCase("LAST_UPDATE_TIME")) {
                        fieldHtmlSb.append(" <div class=\"form-group\">").append("\r\n");
                        fieldHtmlSb.append("<h5>" + title + " <span class=\"text-danger\">*</span></h5>").append("\r\n");
                        fieldHtmlSb.append("  <div class=\"controls\">").append("\r\n");
                        fieldHtmlSb.append("   <input id=\"" + fieldName + "\" value=\"${" + fieldName + "}\"  type=\"text\" class=\"form-control\">").append("\r\n");
                        fieldHtmlSb.append("  </div>").append("\r\n");
                        fieldHtmlSb.append("   </div>").append("\r\n");
                    }


                }


            }

            htmlFieldResult = newSb.toString().replaceAll("fieldHtmlContent", Matcher.quoteReplacement(fieldHtmlSb.toString()));
            htmlFieldResult = htmlFieldResult.toString().replaceAll("fieldHiddenHtmlContent", Matcher.quoteReplacement(fieldHiddenHtmlSb.toString()));


            bw.write(htmlFieldResult);
            bw.flush();
            bw.close();


            /**
             * 8. js
             */


            /**
             * notice.js
             */

            newSb = new StringBuffer();
            fileName = tempPath + "\\js\\notice.js";
            outFileName = newFilePath + manageName + "\\js\\" + requestPath + ".js";
            createNewFile(outFileName);
            br = new BufferedReader(new FileReader(fileName));
            bw = new BufferedWriter(new FileWriter(new File(outFileName)));

            line = null;
            while ((line = br.readLine()) != null) {
                line = line.replaceAll("primaryField", primaryField);
                line = line.replaceAll("Notice", manageName);
                line = line.replaceAll("notice", requestPath);
                newSb.append(line).append("\r\n");
            }

            fieldHtmlSb = new StringBuffer();

            for (int i = 0; i < filedNames.length; i++) {
                String fieldName = filedNames[i];
                String title = fieldDescs[i];

                if (i == 0) {
                    fieldHtmlSb.append("{title: 'id', field: '" + primaryField + "', visible: false, align: 'center', valign: 'middle'},").append("\r\n");
                } else {
                    if (i == filedNames.length - 1) {
                        fieldHtmlSb.append("{title: '" + title + "', field: '" + fieldName + "', align: 'center', valign: 'middle', sortable: true}").append("\r\n");
                    } else {
                        fieldHtmlSb.append("{title: '" + title + "', field: '" + fieldName + "', align: 'center', valign: 'middle', sortable: true},").append("\r\n");
                    }
                }
            }

            htmlFieldResult = newSb.toString().replaceAll("fieldHtmlContent", fieldHtmlSb.toString());


            bw.write(htmlFieldResult);
            bw.flush();
            bw.close();


            /**
             * notice_add.js
             */

            newSb = new StringBuffer();
            fileName = tempPath + "\\js\\notice_add.js";
            outFileName = newFilePath + manageName + "\\js\\" + requestPath + "_add.js";
            createNewFile(outFileName);
            br = new BufferedReader(new FileReader(fileName));
            bw = new BufferedWriter(new FileWriter(new File(outFileName)));

            line = null;
            while ((line = br.readLine()) != null) {
                line = line.replaceAll("Notice", manageName);
                line = line.replaceAll("notice", requestPath);
                newSb.append(line).append("\r\n");
            }

            fieldHtmlSb = new StringBuffer();
            StringBuffer setDataHtmlSb = new StringBuffer();

            for (int i = 1; i < filedNames.length; i++) {
                String fieldName = filedNames[i];
                String columnName = columns[i];
                if (i == filedNames.length - 1) {
                    fieldHtmlSb.append("" + fieldName + ": \"\"").append("\r\n");
                } else {
                    fieldHtmlSb.append(" " + fieldName + ": \"\",").append("\r\n");
                }

                if (!columnName.equalsIgnoreCase("CREATE_TIME") && !columnName.equalsIgnoreCase("CREATE_BY") && !columnName.equalsIgnoreCase("LAST_UPDATE_BY") && !columnName.equalsIgnoreCase("LAST_UPDATE_TIME")) {
                    setDataHtmlSb.append(manageName).append("AddDlg.data." + fieldName + " = $(\"#" + fieldName + "\").val();").append("\r\n");
                }

            }

            htmlFieldResult = newSb.toString().replaceAll("fieldHtmlContent", fieldHtmlSb.toString());
            htmlFieldResult = htmlFieldResult.replaceAll("setDataJSContent", Matcher.quoteReplacement(setDataHtmlSb.toString()));

            bw.write(htmlFieldResult);
            bw.flush();
            bw.close();


            /**
             * notice_edit.js
             */

            newSb = new StringBuffer();
            fileName = tempPath + "\\js\\notice_edit.js";
            outFileName = newFilePath + manageName + "\\js\\" + requestPath + "_edit.js";
            createNewFile(outFileName);
            br = new BufferedReader(new FileReader(fileName));
            bw = new BufferedWriter(new FileWriter(new File(outFileName)));

            line = null;
            while ((line = br.readLine()) != null) {
                line = line.replaceAll("Notice", manageName);
                line = line.replaceAll("notice", requestPath);
                newSb.append(line).append("\r\n");
            }

            fieldHtmlSb = new StringBuffer();
            setDataHtmlSb = new StringBuffer();

            for (int i = 0; i < filedNames.length; i++) {
                String fieldName = filedNames[i];
                String columnName = columns[i];
                if (!columnName.equalsIgnoreCase("CREATE_TIME") && !columnName.equalsIgnoreCase("CREATE_BY") && !columnName.equalsIgnoreCase("LAST_UPDATE_BY") && !columnName.equalsIgnoreCase("LAST_UPDATE_TIME")) {
                    if (i == filedNames.length - 1) {
                        fieldHtmlSb.append("" + fieldName + ": $(\"#" + fieldName + "\").val()").append("\r\n");
                    } else {
                        fieldHtmlSb.append("" + fieldName + ": $(\"#" + fieldName + "\").val(),").append("\r\n");
                    }

                    setDataHtmlSb.append(manageName).append("EditDlg.data." + fieldName + " = $(\"#" + fieldName + "\").val();").append("\r\n");
                }
            }

            htmlFieldResult = newSb.toString().replaceAll("fieldHtmlContent", Matcher.quoteReplacement(fieldHtmlSb.toString()));
            htmlFieldResult = htmlFieldResult.replaceAll("setDataJSContent", Matcher.quoteReplacement(setDataHtmlSb.toString()));


            bw.write(htmlFieldResult);
            bw.flush();
            bw.close();


        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void createNewFile(String fileName) throws Exception {
        File file = new File(fileName);

        if (!file.exists() && !file.isDirectory()) {
            if (file.isDirectory()) {
                file.mkdirs();
            } else {
                if (!file.getParentFile().exists()) {
                    file.getParentFile().mkdirs();
                }
                if (!file.getParentFile().getParentFile().exists()) {
                    file.getParentFile().getParentFile().mkdirs();
                }
                file.createNewFile();
            }
        }
    }
}
