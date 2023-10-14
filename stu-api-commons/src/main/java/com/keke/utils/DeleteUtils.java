package com.keke.utils;

import java.io.File;

public class DeleteUtils {

    public static boolean deleteDir(File dir) {
        if (dir.isDirectory()) {
            System.out.println("存在该目录！");
            String[] children = dir.list();
            //递归删除目录中的子目录下
            for (int i=0; i<children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    System.out.println("删除失败！");
                    return false;
                }
            }
        }
        // 目录此时为空，可以删除
        System.out.println("目录为空！");
        return dir.delete();
    }
}
