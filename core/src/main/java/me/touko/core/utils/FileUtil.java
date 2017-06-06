/*
 *  Copyright (C) 2017 seiginonakama (https://github.com/seiginonakama).
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package me.touko.core.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileUtil {

  public static boolean copyFile(File srcFile, File dstFile) {
    if (srcFile.exists() && srcFile.isFile()) {
      if (dstFile.isDirectory()) {
        return false;
      }
      if (dstFile.exists()) {
        dstFile.delete();
      }
      try {
        byte[] buffer = new byte[2048];
        BufferedInputStream input = new BufferedInputStream(
            new FileInputStream(srcFile));
        BufferedOutputStream output = new BufferedOutputStream(
            new FileOutputStream(dstFile));
        while (true) {
          int count = input.read(buffer);
          if (count == -1) {
            break;
          }
          output.write(buffer, 0, count);
        }
        input.close();
        output.flush();
        output.close();
        return true;
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    return false;
  }


  public static void deletePath(String path) {
    File file = new File(path);
    if (!file.exists()) {
      return;
    }
    if (file.isFile()) {
      file.delete();
      return;
    }
    String[] tmpList = file.list();
    if (tmpList == null) {
      return;
    }
    for (String fileName : tmpList) {
      if (fileName == null) {
        continue;
      }
      String tmpPath = null;
      if (path.endsWith(File.separator)) {
        tmpPath = path + fileName;
      } else {
        tmpPath = path + File.separator + fileName;
      }
      File tmpFile = new File(tmpPath);
      if (tmpFile.isFile()) {
        tmpFile.delete();
      }
      if (tmpFile.isDirectory()) {
        deletePath(tmpPath);
      }
    }
    file.delete();
  }

  public static void clearPath(String path) {
    File file = new File(path);
    if (!file.exists() || !file.isDirectory()) {
      return;
    }
    String[] tmpList = file.list();
    for (String fileName : tmpList) {
      String tmpPath = null;
      if (path.endsWith(File.separator)) {
        tmpPath = path + fileName;
      } else {
        tmpPath = path + File.separator + fileName;
      }
      File tmpFile = new File(tmpPath);
      if (tmpFile.isFile()) {
        tmpFile.delete();
      }
      if (tmpFile.isDirectory()) {
        deletePath(tmpPath);
      }
    }
  }

}
