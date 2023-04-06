package com.future.small_toolbox.damo.Features;

import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.Dispatch;

/**
 * @Description 文件
 * @Author xiaowuchen
 * @Date 2020/9/23 10:46
 */
public class FileOper {

    private ActiveXComponent dm;

    public FileOper(ActiveXComponent dm){
        this.dm=dm;
    }

    /**
     * 拷贝文件.
     * @param src_file 原始文件名
     * @param dst_file 目标文件名
     * @param over <br/>
     * 	0 : 如果dst_file文件存在则不覆盖返回.<br/>
     *  1 : 如果dst_file文件存在则覆盖.
     * @return 0:失败 1:成功
     */
    public int CopyFile(String src_file,String dst_file,int over){
        return Dispatch.call(dm,"CopyFile",src_file,dst_file,over).getInt();
    }

    /**
     * 创建指定目录.
     * @param folder 目录名
     * @return 0:失败 1:成功
     */
    public int CreateFolder(String folder){
        return Dispatch.call(dm,"CreateFolder",folder).getInt();
    }

    /**
     * 删除文件.
     * @param file 文件名
     * @return 0:失败 1:成功
     */
    public int DeleteFile(String file){
        return Dispatch.call(dm,"DeleteFile",file).getInt();
    }

    /**
     * 删除指定目录.
     * @param folder 目录名
     * @return 0:失败 1:成功
     */
    public int DeleteFolder(String folder){
        return Dispatch.call(dm,"DeleteFolder",folder).getInt();
    }

    /**
     * 删除指定的ini小节.
     * @param section 小节名
     * @param key 变量名. 如果这个变量为空串，则删除整个section小节.
     * @param file ini文件名.
     * @return ini文件名.
     */
    public int DeleteIni(String section,String key,String file){
        return Dispatch.call(dm,"DeleteIni",section,key,file).getInt();
    }

    /**
     * 从internet上下载一个文件.
     * @param url 下载的url地址.
     * @param save_file 要保存的文件名.
     * @param timeout 连接超时时间，单位是毫秒.
     * @return 1 : 成功<br/>
     * -1 : 网络连接失败<br/>
     * -2 : 写入文件失败<br/>
     */
    public int DownloadFile(String url,String save_file,int timeout){
        return Dispatch.call(dm,"DownloadFile",url,save_file,timeout).getInt();
    }

    /**
     * 获取指定的文件长度.
     * @param file 文件名
     * @return 文件长度(字节数)
     */
    public int GetFileLength(String file){
        return Dispatch.call(dm,"GetFileLength",file).getInt();
    }

    /**
     * 判断指定文件是否存在.
     * @param file 文件名
     * @return 0 : 不存在 1 : 存在
     */
    public int IsFileExist(String file){
        return Dispatch.call(dm,"IsFileExist",file).getInt();
    }

    /**
     * 移动文件
     * @param src_file 原始文件名
     * @param dst_file 目标文件名
     * @return 0 : 失败 1 : 成功
     */
    public int MoveFile(String src_file,String dst_file){
        return Dispatch.call(dm,"MoveFile",src_file,dst_file).getInt();
    }

    /**
     * 从指定的文件读取内容.
     * @return 读入的文件内容
     */
    public String ReadFile(String file){
        return Dispatch.call(dm,"ReadFile",file).getString();
    }

    /**
     * 从Ini中读取指定信息.
     * @param section 小节名
     * @param key 变量名.
     * @param file ini文件名.
     * @return 字符串形式表达的读取到的内容
     */
    public String ReadIni(String section,String key,String file){
        return Dispatch.call(dm,"ReadIni",section,key,file).getString();
    }

    /**
     * 弹出选择文件夹对话框，并返回选择的文件夹.
     * @return 选择的文件夹全路径
     */
    public String SelectDirectory(){
        return Dispatch.call(dm,"SelectDirectory").getString();
    }

    /**
     * 弹出选择文件对话框，并返回选择的文件
     * @return 选择的文件全路径
     */
    public String SelectFile(){
        return Dispatch.call(dm,"SelectFile").getString();
    }

    /**
     * 向指定文件追加字符串.
     * @param file 文件
     * @param content 写入的字符串.
     * @return 0 : 失败 1 : 成功
     */
    public int WriteFile(String file,String content){
        return Dispatch.call(dm,"WriteFile",file,content).getInt();
    }

    /**
     * 向指定的Ini写入信息.
     * @param section 小节名
     * @param key 变量名
     * @param value 变量内容
     * @param file ini文件名
     * @return 0 : 失败 1 : 成功
     */
    public int WriteIni(String section,String key,String value,String file){
        return Dispatch.call(dm,"WriteIni",section,key,value,file).getInt();
    }
}
