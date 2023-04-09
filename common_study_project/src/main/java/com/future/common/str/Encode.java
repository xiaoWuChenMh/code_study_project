package com.future.common.str;


import java.io.*;


/**
 * 拉丁 转为 utf-8
 * 背景：myslq的表级编码是 latin1,表级编码是uft-8,因此拉取到hive会造成中文乱码
 *
 */
public class Encode {

    private static String coding = "UTF-8";

    private static String sPath = "C:/Users/hhui/Downloads/xxxx.txt";

    private static String mPath = "C:/Users/hhui/Downloads/xxxdddd.txt";

    public static void main(String[] args) {
        try {
            String valaue = "è\u0090¨å†…ï¼šæ˜Žå¤©æˆ‘ï¼Œå°±è¦\u0081ç¦»å¼€[å¾®ç¬‘]æ‹œä»";
            System.out.printf(convertCharset(valaue));
//            readFile();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 一行一行读取文件：
     * 流的关闭顺序：先打开的后关，后打开的先关，否则有可能出现java.io.IOException: Stream closed异常
     * @throws IOException
     */
    public static void readFile() throws IOException {
        //解决读取中文字符时出现乱码——指定编码
        FileInputStream fis = new FileInputStream(sPath);
        InputStreamReader isr = new InputStreamReader(fis, coding);
        BufferedReader br = new BufferedReader(isr);
        String line = "";
        String[] arrs = null;
        int count = 0;
        while ((line=br.readLine())!=null) {
            count+=1;
            arrs=line.split("\t");

            arrs[2] = convertCharset(arrs[2]);

            writeFile(arrs);
            System.out.println("当前读取的是第"+count+"条数据："+line);
        }
        br.close();
        isr.close();
        fis.close();
    }


    /**
     * 一行一行写入文件，解决写入中文字符时出现乱码
     *
     * 流的关闭顺序：先打开的后关，后打开的先关，
     *       否则有可能出现java.io.IOException: Stream closed异常
     *
     * @throws IOException
     */
    public static void writeFile(String[] arrs) throws IOException {
        //写入中文字符时解决中文乱码问题——指定编码\
        FileOutputStream fos = new FileOutputStream(new File(mPath),true);
        OutputStreamWriter osw = new OutputStreamWriter(fos,coding);
        BufferedWriter bw = new BufferedWriter(osw);
        for(String arr:arrs){
            bw.write(arr+"\t");
        }
        bw.write("\n");
        //注意关闭的先后顺序，先打开的后关闭，后打开的先关闭
        bw.close();
        osw.close();
        fos.close();
    }


    /**|
     *  拉丁转为utf-8
     * @param s
     * @return
     */
    public static String convertCharset(String s) {
        if (s != null) {
            try {
                int length = s.length();
                byte[] buffer = new byte[length];
                // 0x81 to Unicode 0x0081, 0x8d to 0x008d, 0x8f to 0x008f, 0x90
                // to 0x0090, and 0x9d to 0x009d.
                for (int i = 0; i < length; ++i) {
                    char c = s.charAt(i);
                    if (c == 0x0081) {
                        buffer[i] = (byte) 0x81;
                    } else if (c == 0x008d) {
                        buffer[i] = (byte) 0x8d;
                    } else if (c == 0x008f) {
                        buffer[i] = (byte) 0x8f;
                    } else if (c == 0x0090) {
                        buffer[i] = (byte) 0x90;
                    } else if (c == 0x009d) {
                        buffer[i] = (byte) 0x9d;
                    } else {
                        buffer[i] = Character.toString(c).getBytes("CP1252")[0];
                    }
                }
                String result = new String(buffer, "UTF-8");
                return result;
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

}
