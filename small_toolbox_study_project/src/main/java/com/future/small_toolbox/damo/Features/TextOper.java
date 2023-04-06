package com.future.small_toolbox.damo.Features;

import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.Dispatch;
import com.jacob.com.Variant;

/**
 * @Description
 * @Author xiaowuchen
 * @Date 2020/9/23 10:46
 */
public class TextOper {

    private ActiveXComponent dm;

    public TextOper(ActiveXComponent dm){
        this.dm=dm;
    }

    /**
     * 给指定的字库中添加一条字库信息.
     * @param index 字库的序号,取值为0-9,目前最多支持10个字库
     * @param dict_info 字库描述串，具体参考大漠综合工具中的字符定义
     * @return 0:失败 1:成功
     */
    public int AddDict(int index,String dict_info){
        return Dispatch.call(dm,"AddDict",index,dict_info).getInt();
    }

    /**
     * 给指定的字库中添加一条字库信息.
     * @param index 字库的序号,取值为0-9,目前最多支持10个字库
     * @return 0:失败 1:成功
     */
    public int ClearDict(int index){
        return Dispatch.call(dm,"ClearDict",index).getInt();
    }

    /**
     * 根据指定的范围,以及指定的颜色描述，提取点阵信息，类似于大漠工具里的单独提取.
     * @param x1 左上角X坐标
     * @param y1 左上角Y坐标
     * @param x2 右下角X坐标
     * @param y2 右下角Y坐标
     * @param color 颜色格式串.注意，RGB和HSV格式都支持.
     * @param word 待定义的文字,不能为空，且不能为关键符号"$"
     * @return 识别到的点阵信息，可用于AddDict,如果失败，返回空
     */
    public String FetchWord(int x1,int y1,int x2,int y2,String color,String word){
        return Dispatch.call(dm,"FetchWord",x1, y1, x2, y2, color, word).getString();
    }

    /**
     * 在屏幕范围(x1,y1,x2,y2)内,查找string(可以是任意个字符串的组合),并返回符合color_format的坐标位置,相似度sim同Ocr接口描述.
     * @param x1 区域的左上X坐标
     * @param y1 区域的左上Y坐标
     * @param x2 区域的右下X坐标
     * @param y2 区域的右下Y坐标
     * @param string 待查找的字符串,可以是字符串组合，比如"长安|洛阳|大雁塔",中间用"|"来分割字符串
     * @param color_format 颜色格式串, 可以包含换行分隔符,语法是","后加分割字符串. 具体可以查看下面的示例 .注意，RGB和HSV格式都支持.
     * @param sim 相似度,取值范围0.1-1.0
     * @param intX 变参指针:返回X坐标没找到返回-1
     * @param intY 变参指针:返回Y坐标没找到返回-1
     * @return 返回字符串的索引 没找到返回-1, 比如"长安|洛阳",若找到长安，则返回0
     */
    public int FindStr(int x1, int y1, int x2, int y2, String string, String color_format, double sim, Variant intX, Variant intY){
        return Dispatch.callN(dm,"FindStr",new Object[]{x1,y1,x2,y2,string,color_format,sim,intX,intY}).getInt();
    }

    /**
     * 在屏幕范围(x1,y1,x2,y2)内,查找string(可以是任意个字符串的组合),并返回符合color_format的坐标位置,相似度sim同Ocr接口描述.
     * @param x1 区域的左上X坐标
     * @param y1 区域的左上Y坐标
     * @param x2 区域的右下X坐标
     * @param y2 区域的右下Y坐标
     * @param string 待查找的字符串,可以是字符串组合，比如"长安|洛阳|大雁塔",中间用"|"来分割字符串
     * @param color_format 颜色格式串, 可以包含换行分隔符,语法是","后加分割字符串. 具体可以查看下面的示例 .注意，RGB和HSV格式都支持.
     * @param sim 相似度,取值范围0.1-1.0
     * @return 返回字符串序号以及X和Y坐标,形式如"id|x|y", 比如"0|100|200",没找到时，id和X以及Y均为-1，"-1|-1|-1"
     */
    public String FindStrE(int x1,int y1,int x2,int y2,String string,String color_format,double sim){
        return Dispatch.call(dm,"FindStrE",x1,y1,x2,y2,string,color_format,sim).getString();
    }

    /**
     * 在屏幕范围(x1,y1,x2,y2)内,查找string(可以是任意字符串的组合),并返回符合color_format的所有坐标位置,相似度sim同Ocr接口描述.
     * @param x1 区域的左上X坐标
     * @param y1 区域的左上Y坐标
     * @param x2 区域的右下X坐标
     * @param y2 区域的右下Y坐标
     * @param string 待查找的字符串,可以是字符串组合，比如"长安|洛阳|大雁塔",中间用"|"来分割字符串
     * @param color_format 颜色格式串, 可以包含换行分隔符,语法是","后加分割字符串. 具体可以查看下面的示例 .注意，RGB和HSV格式都支持.
     * @param sim 相似度,取值范围0.1-1.0
     * @return 返回所有找到的坐标集合,格式如下:<br/>
     * "id,x0,y0|id,x1,y1|......|id,xn,yn"<br/>
     * 比如"0,100,20|2,30,40" 表示找到了两个,第一个,对应的是序号为0的字符串,坐标是(100,20),第二个是序号为2的字符串,坐标(30,40)
     */
    public String FindStrEx(int x1,int y1,int x2,int y2,String string,String color_format,double sim){
        return Dispatch.call(dm,"FindStrEx",x1,y1,x2,y2,string,color_format,sim).getString();
    }

    /**
     * 在屏幕范围(x1,y1,x2,y2)内,查找string(可以是任意个字符串的组合),并返回符合color_format的坐标位置,相似度sim同Ocr接口描述.
     * @param x1 区域的左上X坐标
     * @param y1 区域的左上Y坐标
     * @param x2 区域的右下X坐标
     * @param y2 区域的右下Y坐标
     * @param string 待查找的字符串,可以是字符串组合，比如"长安|洛阳|大雁塔",中间用"|"来分割字符串
     * @param color_format 颜色格式串, 可以包含换行分隔符,语法是","后加分割字符串. 具体可以查看下面的示例 .注意，RGB和HSV格式都支持.
     * @param sim 相似度,取值范围0.1-1.0
     * @param intX 变参指针:返回X坐标没找到返回-1
     * @param intY 变参指针:返回Y坐标没找到返回-1
     * @return 返回字符串的索引 没找到返回-1, 比如"长安|洛阳",若找到长安，则返回0
     */
    public int FindStrFast(int x1,int y1,int x2,int y2,String string,String color_format,double sim,Variant intX,Variant intY){
        return Dispatch.callN(dm,"FindStrFast",new Object[]{x1,y1,x2,y2,string,color_format,sim,intX,intY}).getInt();
    }

    /**
     * 在屏幕范围(x1,y1,x2,y2)内,查找string(可以是任意个字符串的组合),并返回符合color_format的坐标位置,相似度sim同Ocr接口描述.
     * @param x1 区域的左上X坐标
     * @param y1 区域的左上Y坐标
     * @param x2 区域的右下X坐标
     * @param y2 区域的右下Y坐标
     * @param string 待查找的字符串,可以是字符串组合，比如"长安|洛阳|大雁塔",中间用"|"来分割字符串
     * @param color_format 颜色格式串, 可以包含换行分隔符,语法是","后加分割字符串. 具体可以查看下面的示例 .注意，RGB和HSV格式都支持.
     * @param sim 相似度,取值范围0.1-1.0
     * @return 返回字符串序号以及X和Y坐标,形式如"id|x|y", 比如"0|100|200",没找到时，id和X以及Y均为-1，"-1|-1|-1"
     */
    public String FindStrFastE(int x1,int y1,int x2,int y2,String string,String color_format,double sim){
        return Dispatch.call(dm,"FindStrFastE",x1,y1,x2,y2,string,color_format,sim).getString();
    }

    /**
     * 在屏幕范围(x1,y1,x2,y2)内,查找string(可以是任意字符串的组合),并返回符合color_format的所有坐标位置,相似度sim同Ocr接口描述.
     * @param x1 区域的左上X坐标
     * @param y1 区域的左上Y坐标
     * @param x2 区域的右下X坐标
     * @param y2 区域的右下Y坐标
     * @param string 待查找的字符串,可以是字符串组合，比如"长安|洛阳|大雁塔",中间用"|"来分割字符串
     * @param color_format 颜色格式串, 可以包含换行分隔符,语法是","后加分割字符串. 具体可以查看下面的示例 .注意，RGB和HSV格式都支持.
     * @param sim 相似度,取值范围0.1-1.0
     * @return 返回所有找到的坐标集合,格式如下:<br/>
     * "id,x0,y0|id,x1,y1|......|id,xn,yn"<br/>
     * 比如"0,100,20|2,30,40" 表示找到了两个,第一个,对应的是序号为0的字符串,坐标是(100,20),第二个是序号为2的字符串,坐标(30,40)
     */
    public String FindStrFastEx(int x1,int y1,int x2,int y2,String string,String color_format,double sim){
        return Dispatch.call(dm,"FindStrFastEx",x1,y1,x2,y2,string,color_format,sim).getString();
    }

    /**
     * 同FindStr，但是不使用SetDict设置的字库，而利用系统自带的字库，速度比FindStr稍慢.
     * @param x1 区域的左上X坐标
     * @param y1 区域的左上Y坐标
     * @param x2 区域的右下X坐标
     * @param y2 区域的右下Y坐标
     * @param string 待查找的字符串,可以是字符串组合，比如"长安|洛阳|大雁塔",中间用"|"来分割字符串
     * @param color_format 颜色格式串, 可以包含换行分隔符,语法是","后加分割字符串. 具体可以查看下面的示例 .注意，RGB和HSV格式都支持.
     * @param sim 相似度,取值范围0.1-1.0
     * @param font_name 系统字体名,比如"宋体"
     * @param font_size 系统字体尺寸，这个尺寸一定要以大漠综合工具获取的为准.如果获取尺寸看视频教程.
     * @param flag 字体类别 取值可以是以下值的组合,比如1+2+4+8,2+4. 0表示正常字体.<br/>
     * 1 : 粗体 2 : 斜体 4 : 下划线 8 : 删除线
     * @param intX 变参指针:返回X坐标没找到返回-1
     * @param intY 变参指针:返回Y坐标没找到返回-1
     * @return 返回字符串的索引 没找到返回-1, 比如"长安|洛阳",若找到长安，则返回0
     */
    public int FindStrWithFont(int x1,int y1,int x2,int y2,String string,String color_format,double sim,String font_name,int font_size,int flag,Variant intX,Variant intY){
        return Dispatch.callN(dm,"FindStrWithFont",new Object[]{x1,y1,x2,y2,string,color_format,sim,font_name,font_size,flag,intX,intY}).getInt();
    }

    /**
     * 同FindStrE，但是不使用SetDict设置的字库，而利用系统自带的字库，速度比FindStrE稍慢
     * @param x1 区域的左上X坐标
     * @param y1 区域的左上Y坐标
     * @param x2 区域的右下X坐标
     * @param y2 区域的右下Y坐标
     * @param string 待查找的字符串,可以是字符串组合，比如"长安|洛阳|大雁塔",中间用"|"来分割字符串
     * @param color_format 颜色格式串, 可以包含换行分隔符,语法是","后加分割字符串. 具体可以查看下面的示例 .注意，RGB和HSV格式都支持.
     * @param sim 相似度,取值范围0.1-1.0
     * @param font_name 系统字体名,比如"宋体"
     * @param font_size 系统字体尺寸，这个尺寸一定要以大漠综合工具获取的为准.如果获取尺寸看视频教程.
     * @param flag 字体类别 取值可以是以下值的组合,比如1+2+4+8,2+4. 0表示正常字体.<br/>
     * 1 : 粗体 2 : 斜体 4 : 下划线 8 : 删除线
     * @return 返回字符串序号以及X和Y坐标,形式如"id|x|y", 比如"0|100|200",没找到时，id和X以及Y均为-1，"-1|-1|-1"
     */
    public String FindStrWithFontE(int x1,int y1,int x2,int y2,String string,String color_format,double sim,String font_name,int font_size,int flag){
        return Dispatch.callN(dm,"FindStrWithFontE",new Object[]{x1,y1,x2,y2,string,color_format,sim,font_name,font_size,flag}).getString();
    }
    /**
     * 同FindStrEx，但是不使用SetDict设置的字库，而利用系统自带的字库，速度比FindStrEx稍慢
     * @param x1 区域的左上X坐标
     * @param y1 区域的左上Y坐标
     * @param x2 区域的右下X坐标
     * @param y2 区域的右下Y坐标
     * @param string 待查找的字符串,可以是字符串组合，比如"长安|洛阳|大雁塔",中间用"|"来分割字符串
     * @param color_format 颜色格式串, 可以包含换行分隔符,语法是","后加分割字符串. 具体可以查看下面的示例 .注意，RGB和HSV格式都支持.
     * @param sim 相似度,取值范围0.1-1.0
     * @param font_name 系统字体名,比如"宋体"
     * @param font_size 系统字体尺寸，这个尺寸一定要以大漠综合工具获取的为准.如果获取尺寸看视频教程.
     * @param flag 字体类别 取值可以是以下值的组合,比如1+2+4+8,2+4. 0表示正常字体.<br/>
     * 1 : 粗体 2 : 斜体 4 : 下划线 8 : 删除线
     * @return 返回所有找到的坐标集合,格式如下:<br/>
     * "id,x0,y0|id,x1,y1|......|id,xn,yn"<br/>
     * 比如"0,100,20|2,30,40" 表示找到了两个,第一个,对应的是序号为0的字符串,坐标是(100,20),第二个是序号为2的字符串,坐标(30,40)<br/>
     */
    public String FindStrWithFontEx(int x1,int y1,int x2,int y2,String string,String color_format,double sim,String font_name,int font_size,int flag){
        return Dispatch.callN(dm,"FindStrWithFontEx",new Object[]{x1,y1,x2,y2,string,color_format,sim,font_name,font_size,flag}).getString();
    }

    /**
     * 获取指定的字库中的字符数量
     * @param index 字库序号(0-9)
     * @return 字库数量
     */
    public int GetDictCount(int index){
        return Dispatch.call(dm,"GetDictCount",index).getInt();
    }

    /**
     * 根据指定的文字，以及指定的系统字库信息，获取字库描述信息.
     * @param str 需要获取的字符串
     * @param font_name 系统字体名,比如"宋体"
     * @param font_size 系统字体尺寸，这个尺寸一定要以大漠综合工具获取的为准.如何获取尺寸看视频教程.
     * @param flag 字体类别 取值可以是以下值的组合,比如1+2+4+8,2+4. 0表示正常字体<br/>
     * 1 : 粗体  2 : 斜体 4 : 下划线 8 : 删除线
     * @return 返回字库信息,每个字符的字库信息用"|"来分割
     */
    public String GetDictInfo(String str,String font_name,int font_size,int flag){
        return Dispatch.call(dm,"GetDictInfo",str,font_name,font_size,flag).getString();
    }

    /**
     * 获取当前使用的字库序号(0-9)
     * @return 字库序号(0-9)
     */
    public int GetNowDict(){
        return Dispatch.call(dm,"GetNowDict").getInt();
    }

    /**
     * 对插件部分接口的返回值进行解析,并返回ret中的坐标个数
     * @param ret 部分接口的返回串
     * @return 返回ret中的坐标个数
     */
    public int GetResultCount(String ret){
        return Dispatch.call(dm,"GetResultCount",ret).getInt();
    }

    /**
     * 对插件部分接口的返回值进行解析,并根据指定的第index个坐标,返回具体的值
     * @param ret 部分接口的返回串
     * @param index 第几个坐标
     * @param intX 变参指针: 返回X坐标
     * @param intY 变参指针: 返回Y坐标
     * @return 0:失败 1:成功
     */
    public int GetResultPos(String ret,int index,Variant intX,Variant intY){
        return Dispatch.call(dm,"GetResultPos",ret,index,intX,intY).getInt();
    }

    /**
     * 在使用GetWords进行词组识别以后,可以用此接口进行识别词组数量的计算.
     * @param str GetWords接口调用以后的返回值
     * @return 返回词组数量
     */
    public int GetWordResultCount(String str){
        return Dispatch.call(dm,"GetWordResultCount",str).getInt();
    }

    /**
     * 在使用GetWords进行词组识别以后,可以用此接口进行识别各个词组的坐标
     * @param str GetWords的返回值
     * @param index 表示第几个词组
     * @param intX 返回的X坐标
     * @param intY 返回的X坐标
     * @return 0:失败 1:成功
     */
    public int GetWordResultPos(String str,int index,Variant intX,Variant intY){
        return Dispatch.call(dm,"GetWordResultPos",str,index,intX,intY).getInt();
    }

    /**
     * 在使用GetWords进行词组识别以后,可以用此接口进行识别各个词组的内容
     * @param str GetWords的返回值
     * @param index 表示第几个词组
     * @return 返回的第index个词组内容
     */
    public String GetWordResultStr(String str,int index){
        return Dispatch.call(dm,"GetWordResultPos",str,index).getString();
    }

    /**
     * 根据指定的范围,以及设定好的词组识别参数(一般不用更改,除非你真的理解了)<br/>
     * 识别这个范围内所有满足条件的词组. 比较适合用在未知文字的情况下,进行不定识别.
     * @param x1 左上角X坐标
     * @param y1 左上角Y坐标
     * @param x2 右下角X坐标
     * @param y2 右下角Y坐标
     * @param color 颜色格式串.注意，RGB和HSV格式都支持.
     * @param sim 相似度 0.1-1.0
     * @return 识别到的格式串,要用到专用函数来解析
     */
    public String GetWords(int x1,int y1,int x2,int y2,String color,double sim){
        return Dispatch.call(dm,"GetWords",x1, y1, x2, y2, color, sim).getString();
    }

    /**
     * 根据指定的范围,以及设定好的词组识别参数(一般不用更改,除非你真的理解了)<br/>
     * 识别这个范围内所有满足条件的词组. 这个识别函数不会用到字库。只是识别大概形状的位置
     * @param x1 左上角X坐标
     * @param y1 左上角Y坐标
     * @param x2 右下角X坐标
     * @param y2 右下角Y坐标
     * @param color 颜色格式串.注意，RGB和HSV格式都支持.
     * @return 识别到的格式串,要用到专用函数来解析
     */
    public String GetWordsNoDict(int x1,int y1,int x2,int y2,String color){
        return Dispatch.call(dm,"GetWordsNoDict",x1, y1, x2, y2, color).getString();
    }

    /**
     * 识别屏幕范围(x1,y1,x2,y2)内符合color_format的字符串,并且相似度为sim,sim取值范围(0.1-1.0),<br/>
     * 这个值越大越精确,越大速度越快,越小速度越慢,请斟酌使用!
     * @param x1 左上角X坐标
     * @param y1 左上角Y坐标
     * @param x2 右下角X坐标
     * @param y2 右下角Y坐标
     * @param color_format 颜色格式串. 可以包含换行分隔符,语法是","后加分割字符串. 具体可以查看下面的示例.注意，RGB和HSV格式都支持.
     * @param sim 相似度,取值范围0.1-1.0
     * @return 返回识别到的字符串
     */
    public String Ocr(int x1,int y1,int x2,int y2,String color_format,double sim){
        return Dispatch.call(dm,"Ocr",x1,y1,x2,y2,color_format,sim).getString();
    }

    /**
     * 识别屏幕范围(x1,y1,x2,y2)内符合color_format的字符串,并且相似度为sim,sim取值范围(0.1-1.0),<br/>
     * 这个值越大越精确,越大速度越快,越小速度越慢,请斟酌使用!<br/>
     * 这个函数可以返回识别到的字符串，以及每个字符的坐标.
     * @param x1 左上角X坐标
     * @param y1 左上角Y坐标
     * @param x2 右下角X坐标
     * @param y2 右下角Y坐标
     * @param color_format 颜色格式串. 可以包含换行分隔符,语法是","后加分割字符串. 具体可以查看下面的示例.注意，RGB和HSV格式都支持.
     * @param sim 相似度,取值范围0.1-1.0
     * @return 返回识别到的字符串 格式如  "识别到的信息|x0,y0|…|xn,yn"
     */
    public String OcrEx(int x1,int y1,int x2,int y2,String color_format,double sim){
        return Dispatch.call(dm,"OcrEx",x1,y1,x2,y2,color_format,sim).getString();
    }

    /**
     * 识别位图中区域(x1,y1,x2,y2)的文字
     * @param x1 左上角X坐标
     * @param y1 左上角Y坐标
     * @param x2 右下角X坐标
     * @param y2 右下角Y坐标
     * @param pic_name 图片文件名
     * @param color_format 颜色格式串. 可以包含换行分隔符,语法是","后加分割字符串. 具体可以查看下面的示例.注意，RGB和HSV格式都支持.
     * @param sim 相似度,取值范围0.1-1.0
     * @return 返回识别到的字符串
     */
    public String OcrInFile(int x1,int y1,int x2,int y2,String pic_name,String color_format,double sim){
        return Dispatch.call(dm,"OcrInFile",x1, y1, x2, y2, pic_name, color_format, sim).getString();
    }

    /**
     * 保存指定的字库到指定的文件中.
     * @param index 字库索引序号 取值为0-9对应10个字库
     * @param file 文件名
     * @return 0:失败 1:成功
     */
    public int SaveDict(int index,String file){
        return Dispatch.call(dm,"SaveDict",index,file).getInt();
    }

    /**
     * 高级用户使用,在不使用字库进行词组识别前,可设定文字的列距,默认列距是1
     * @param col_gap 文字列距
     * @return 0:失败 1:成功
     */
    public int SetColGapNoDict(int col_gap){
        return Dispatch.call(dm,"SetColGapNoDict",col_gap).getInt();
    }

    /**
     * 设置字库文件<br/>
     * 注: 此函数速度很慢，全局初始化时调用一次即可，切换字库用UseDict
     * @param index 整形数:字库的序号,取值为0-9,目前最多支持10个字库
     * @param file 字符串:字库文件名
     * @return 0: 失败 1: 成功
     */
    public int SetDict(int index,String file){
        return Dispatch.call(dm, "SetDict", index,file).getInt();
    }

    /**
     * 设置字库的密码,在SetDict前调用,目前的设计是,所有字库通用一个密码.
     * @param pwd 字库密码
     * @return 0: 失败 1: 成功
     */
    public int SetDictPwd(String pwd){
        return Dispatch.call(dm, "SetDictPwd", pwd).getInt();
    }

    /**
     * 高级用户使用,在使用文字识别功能前，设定是否开启精准识别.
     * @param exact_ocr 0 表示关闭精准识别  1 开启精准识别
     * @return 0: 失败 1: 成功
     */
    public int SetExactOcr(int exact_ocr){
        return Dispatch.call(dm, "SetExactOcr", exact_ocr).getInt();
    }

    /**
     * 高级用户使用,在识别前,如果待识别区域有多行文字,可以设定列间距,默认的列间距是0,
     * 如果根据情况设定,可以提高识别精度。一般不用设定。<br/>
     * 注意：此设置如果不为0,那么将不能识别连体字 慎用.
     * @param min_col_gap 最小列间距
     * @return 0: 失败 1: 成功
     */
    public int SetMinColGap(int min_col_gap){
        return Dispatch.call(dm, "SetMinColGap", min_col_gap).getInt();
    }

    /**
     * 高级用户使用,在识别前,如果待识别区域有多行文字,可以设定行间距,默认的行间距是1,
     * 如果根据情况设定,可以提高识别精度。一般不用设定。
     * @param min_row_gap 最小行间距
     * @return 0: 失败 1: 成功
     */
    public int SetMinRowGap(int min_row_gap){
        return Dispatch.call(dm, "SetMinRowGap", min_row_gap).getInt();
    }

    /**
     * 高级用户使用,在不使用字库进行词组识别前,可设定文字的行距,默认行距是1
     * @param row_gap 文字行距
     * @return 0: 失败 1: 成功
     */
    public int SetRowGapNoDict(int row_gap){
        return Dispatch.call(dm, "SetRowGapNoDict", row_gap).getInt();
    }

    /**
     * 高级用户使用,在识别词组前,可设定词组间的间隔,默认的词组间隔是5
     * @param word_gap 单词间距
     * @return 0: 失败 1: 成功
     */
    public int SetWordGap(int word_gap){
        return Dispatch.call(dm, "SetWordGap", word_gap).getInt();
    }

    /**
     * 高级用户使用,在不使用字库进行词组识别前,可设定词组间的间隔,默认的词组间隔是5
     * @param word_gap 单词间距
     * @return 0: 失败 1: 成功
     */
    public int SetWordGapNoDict(int word_gap){
        return Dispatch.call(dm, "SetWordGapNoDict", word_gap).getInt();
    }

    /**
     * 高级用户使用,在识别词组前,可设定文字的平均行高,默认的词组行高是10
     * @param line_height 行高
     * @return 0: 失败 1: 成功
     */
    public int SetWordLineHeight(int line_height){
        return Dispatch.call(dm, "SetWordLineHeight", line_height).getInt();
    }

    /**
     * 高级用户使用,在不使用字库进行词组识别前,可设定文字的平均行高,默认的词组行高是10
     * @param line_height 行高
     * @return 0: 失败 1: 成功
     */
    public int SetWordLineHeightNoDict(int line_height){
        return Dispatch.call(dm, "SetWordLineHeightNoDict", line_height).getInt();
    }

    /**
     * 表示使用哪个字库文件进行识别(index范围:0-9)
     * 设置之后，永久生效，除非再次设定
     * @param index 字库编号(0-9)
     * @return 0: 失败 1: 成功
     */
    public int UseDict(int index){
        return Dispatch.call(dm, "UseDict", index).getInt();
    }
}
