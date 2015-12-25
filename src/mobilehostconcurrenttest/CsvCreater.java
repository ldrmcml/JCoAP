package mobilehostconcurrenttest;

import java.io.FileWriter;
import java.io.IOException;

/**
 * 写csv文件
 * @title: CsvCreater  
 * @description:   
 * @version 1.0
 */
public class CsvCreater {
 public static void main(String[] args) {
  try {
   FileWriter fw = new FileWriter("D:\\CsvCreater.csv");
   fw.write("aaa,bbb,ccc,ddd,eee,fff,ggg,hhh\n");
   fw.write("aa1,bb1,cc1,dd1,ee1,ff1,gg1,hh1\n");
   fw.write("aaa\n");
   fw.write("aa2,bb2,cc2,dd2,ee2,ff2,gg2,hh2\n");
   fw.close();
  } catch (IOException e) {
   e.printStackTrace();
  }
 }
}

