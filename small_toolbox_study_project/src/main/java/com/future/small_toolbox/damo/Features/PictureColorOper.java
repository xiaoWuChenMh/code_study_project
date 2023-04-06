package com.future.small_toolbox.damo.Features;

import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.Dispatch;
import com.jacob.com.Variant;

/**
 * @Description 图色
 * @Author xiaowuchen
 * @Date 2020/9/23 10:44
 */
public class PictureColorOper {

    private ActiveXComponent dm;

    public PictureColorOper(ActiveXComponent dm){
        this.dm=dm;
    }

    /**
     * 对指定的数据地址和长度，组合成新的参数. FindPicMem FindPicMemE 以及FindPicMemEx专用
     * @param pic_info 老的地址描述串
     * @param addr 数据地址
     * @param size 数据长度
     * @return 新的地址描述串
     */
    public String AppendPicAddr(String pic_info,int addr,int size){
        return Dispatch.call(dm,"AppendPicAddr",pic_info,addr,size).getString();
    }

    /**
     * 把BGR(按键格式)的颜色格式转换为RGB
     * @param bgr_color bgr格式的颜色字符串
     * @return RGB格式的字符串
     */
    public String BGR2RGB(String bgr_color){
        return Dispatch.call(dm,"BGR2RGB",bgr_color).getString();
    }

    /**
     * 抓取指定区域(x1, y1, x2, y2)的图像,保存为file(24位位图)
     * @param x1 区域的左上X坐标
     * @param y1 区域的左上Y坐标
     * @param x2 区域的右下X坐标
     * @param y2 区域的右下Y坐标
     * @param file 保存的文件名,保存的地方一般为SetPath中设置的目录<br/>
     * 当然这里也可以指定全路径名.
     * @return 0: 失败 1:成功
     */
    public int Capture(int x1, int y1,int x2,int y2,String file){
        return Dispatch.call(dm,"Capture",x1, y1, x2, y2, file).getInt();
    }

    /**
     * 抓取指定区域(x1, y1, x2, y2)的动画，保存为gif格式
     * @param x1 区域的左上X坐标
     * @param y1 区域的左上Y坐标
     * @param x2 区域的右下X坐标
     * @param y2 区域的右下Y坐标
     * @param file 保存的文件名,保存的地方一般为SetPath中设置的目录<br/>
     * 当然这里也可以指定全路径名.
     * @param delay 动画间隔，单位毫秒。如果为0，表示只截取静态图片
     * @param time 总共截取多久的动画，单位毫秒。
     * @return 0: 失败 1:成功
     */
    public int CaptureGif(int x1,int y1,int x2,int y2,String file,int delay,int time){
        return Dispatch.call(dm,"CaptureGif",x1, y1, x2, y2, file,delay,time).getInt();
    }

    /**
     * 抓取指定区域(x1, y1, x2, y2)的图像,保存为file(JPG压缩格式)
     * @param x1 区域的左上X坐标
     * @param y1 区域的左上Y坐标
     * @param x2 区域的右下X坐标
     * @param y2 区域的右下Y坐标
     * @param file 保存的文件名,保存的地方一般为SetPath中设置的目录<br/>
     * 当然这里也可以指定全路径名.
     * @param quality jpg压缩比率(1-100) 越大图片质量越好
     * @return 0: 失败 1:成功
     */
    public int CaptureJpg(int x1,int y1,int x2,int y2,String file, int quality){
        return Dispatch.call(dm,"CaptureJpg",x1, y1, x2, y2, file, quality).getInt();
    }

    /**
     * 同Capture函数，只是保存的格式为PNG.
     * @param x1 区域的左上X坐标
     * @param y1 区域的左上Y坐标
     * @param x2 区域的右下X坐标
     * @param y2 区域的右下Y坐标
     * @param file 保存的文件名,保存的地方一般为SetPath中设置的目录<br/>
     * 当然这里也可以指定全路径名.
     * @return 0: 失败 1:成功
     */
    public int CapturePng(int x1,int y1,int x2,int y2,String file){
        return Dispatch.call(dm,"CapturePng",x1,y1,x2,y2,file).getInt();
    }

    /**
     * 抓取上次操作的图色区域，保存为file(24位位图)
     *
     * 注意，要开启此函数，必须先调用EnableDisplayDebug<br/>
     * 任何图色或者文字识别函数，都可以通过这个来截取. 具体可以查看常见问题中"本机文字识别正常,别的机器为何不正常"这一节.
     * @param file 保存的文件名,保存的地方一般为SetPath中设置的目录<br/>
     * 当然这里也可以指定全路径名.
     * @return 0: 失败 1:成功
     */
    public int CapturePre(String file){
        return Dispatch.call(dm,"CapturePre",file).getInt();
    }

    /**
     * 比较指定坐标点(x,y)的颜色
     * @param x X坐标
     * @param y Y坐标
     * @param color 颜色字符串,可以支持偏色,多色,例如 "ffffff-202020|000000-000000" 这个表示白色偏色为202020,和黑色偏色为000000.颜色最多支持10种颜色组合. 注意，这里只支持RGB颜色.
     * @param sim 相似度(0.1-1.0)
     * @return 0: 颜色匹配 1: 颜色不匹配
     */
    public int CmpColor(int x,int y,String color,double sim){
        return Dispatch.call(dm,"CmpColor",x,y,color,sim).getInt();
    }

    /**
     * 开启图色调试模式，此模式会稍许降低图色和文字识别的速度.默认不开启.
     * @param enable_debug 0 为关闭 1 为开启
     * @return 0: 失败 1:成功
     */
    public int EnableDisplayDebug(int enable_debug){
        return Dispatch.call(dm,"EnableDisplayDebug",enable_debug).getInt();
    }

    /**
     * 允许调用GetColor GetColorBGR GetColorHSV 以及 CmpColor时，以截图的方式来获取颜色。
     * @param enable 0 为关闭 1 为开启
     * @return 0: 失败 1:成功
     */
    public int EnableGetColorByCapture(int enable){
        return Dispatch.call(dm,"EnableGetColorByCapture",enable).getInt();
    }

    /**
     * 查找指定区域内的颜色,颜色格式"RRGGBB-DRDGDB",注意,和按键的颜色格式相反
     * @param x1 区域的左上X坐标
     * @param y1 区域的左上Y坐标
     * @param x2 区域的右下X坐标
     * @param y2 区域的右下Y坐标
     * @param color 颜色 格式为"RRGGBB-DRDGDB",比如"123456-000000|aabbcc-202020".注意，这里只支持RGB颜色.
     * @param sim 相似度,取值范围0.1-1.0
     * @param dir 查找方向 0: 从左到右,从上到下 <br/>
     *   1: 从左到右,从下到上 <br/>
     *   2: 从右到左,从上到下 <br/>
     *   3: 从右到左,从下到上 <br/>
     *   4：从中心往外查找<br/>
     *   5: 从上到下,从左到右 <br/>
     *   6: 从上到下,从右到左<br/>
     *   7: 从下到上,从左到右<br/>
     *   8: 从下到上,从右到左<br/>
     * @param intX 变参指针:返回X坐标
     * @param intY 变参指针:返回Y坐标
     * @return 0:没找到 1:找到
     */
    public int FindColor(int x1, int y1, int x2, int y2, String color, double sim, int dir, Variant intX, Variant intY){
        return Dispatch.callN(dm, "FindColor", new Object[]{x1,y1,x2,y2,color,sim,dir,intX,intY}).getInt();
    }

    /**
     * 查找指定区域内的颜色,颜色格式"RRGGBB-DRDGDB",注意,和按键的颜色格式相反
     * @param x1 区域的左上X坐标
     * @param y1 区域的左上Y坐标
     * @param x2 区域的右下X坐标
     * @param y2 区域的右下Y坐标
     * @param color 颜色 格式为"RRGGBB-DRDGDB",比如"123456-000000|aabbcc-202020".注意，这里只支持RGB颜色.
     * @param sim 相似度,取值范围0.1-1.0
     * @param dir 查找方向 0: 从左到右,从上到下 <br/>
     *   1: 从左到右,从下到上 <br/>
     *   2: 从右到左,从上到下 <br/>
     *   3: 从右到左,从下到上 <br/>
     *   4：从中心往外查找<br/>
     *   5: 从上到下,从左到右 <br/>
     *   6: 从上到下,从右到左<br/>
     *   7: 从下到上,从左到右<br/>
     *   8: 从下到上,从右到左<br/>
     * @return 返回X和Y坐标 形式如"x|y", 比如"100|200"
     */
    public String FindColorE(int x1,int y1,int x2,int y2,String color,double sim,int dir){
        return Dispatch.call(dm,"FindColorE",x1, y1, x2, y2, color, sim, dir).getString();
    }

    /**
     * 查找指定区域内的所有颜色,颜色格式"RRGGBB-DRDGDB",注意,和按键的颜色格式相反
     * @param x1 区域的左上X坐标
     * @param y1 区域的左上Y坐标
     * @param x2 区域的右下X坐标
     * @param y2 区域的右下Y坐标
     * @param color 颜色 格式为"RRGGBB-DRDGDB",比如"123456-000000|aabbcc-202020".注意，这里只支持RGB颜色.
     * @param sim 相似度,取值范围0.1-1.0
     * @param dir 查找方向 0: 从左到右,从上到下 <br/>
     *   1: 从左到右,从下到上 <br/>
     *   2: 从右到左,从上到下 <br/>
     *   3: 从右到左,从下到上 <br/>
     *   4：从中心往外查找<br/>
     *   5: 从上到下,从左到右 <br/>
     *   6: 从上到下,从右到左<br/>
     *   7: 从下到上,从左到右<br/>
     *   8: 从下到上,从右到左<br/>
     * @return 返回所有颜色信息的坐标值,然后通过GetResultCount等接口来解析 (由于内存限制,返回的颜色数量最多为1800个左右)
     */
    public String FindColorEx(int x1,int y1,int x2,int y2,String color,double sim,int dir){
        return Dispatch.call(dm,"FindColorEx",x1, y1, x2, y2, color, sim, dir).getString();
    }

    /**
     * 根据指定的多点查找颜色坐标
     * @param x1 区域的左上X坐标
     * @param y1 区域的左上Y坐标
     * @param x2 区域的右下X坐标
     * @param y2 区域的右下Y坐标
     * @param first_color <br/>
     * 颜色 格式为"RRGGBB-DRDGDB",比如"123456-000000" <br/>
     * 这里的含义和按键自带Color插件的意义相同，只不过我的可以支持偏色 <br/>
     * 所有的偏移色坐标都相对于此颜色.注意，这里只支持RGB颜色. <br/>
     * @param offset_color <br/>
     * 偏移颜色 可以支持任意多个点 格式和按键自带的Color插件意义相同<br/>
     *  格式为"x1|y1|RRGGBB-DRDGDB,……xn|yn|RRGGBB-DRDGDB"<br/>
     * 比如"1|3|aabbcc,-5|-3|123456-000000"等任意组合都可以，支持偏色<br/>
     * 还可以支持反色模式，比如"1|3|-aabbcc,-5|-3|-123456-000000","-"表示除了指定颜色之外的颜色.<br/>
     * @param sim 相似度,取值范围0.1-1.0
     * @param dir 查找方向 0: 从左到右,从上到下 1: 从左到右,从下到上 2: 从右到左,从上到下 3: 从右到左, 从下到上
     * @param intX 变参指针:返回X坐标(坐标为first_color所在坐标)
     * @param intY 变参指针:返回Y坐标(坐标为first_color所在坐标)
     * @return 0:没找到 1:找到
     */
    public int FindMultiColor(int x1,int y1,int x2,int y2,String first_color,String offset_color,double sim,int dir,Variant intX,Variant intY){
        return Dispatch.callN(dm,"FindMultiColor",new Object[]{x1, y1, x2, y2,first_color,offset_color,sim, dir,intX,intY}).getInt();
    }

    /**
     * 根据指定的多点查找颜色坐标
     * @param x1 区域的左上X坐标
     * @param y1 区域的左上Y坐标
     * @param x2 区域的右下X坐标
     * @param y2 区域的右下Y坐标
     * @param first_color <br/>
     * 颜色 格式为"RRGGBB-DRDGDB",比如"123456-000000" <br/>
     * 这里的含义和按键自带Color插件的意义相同，只不过我的可以支持偏色 <br/>
     * 所有的偏移色坐标都相对于此颜色.注意，这里只支持RGB颜色. <br/>
     * @param offset_color <br/>
     * 偏移颜色 可以支持任意多个点 格式和按键自带的Color插件意义相同<br/>
     *  格式为"x1|y1|RRGGBB-DRDGDB,……xn|yn|RRGGBB-DRDGDB"<br/>
     * 比如"1|3|aabbcc,-5|-3|123456-000000"等任意组合都可以，支持偏色<br/>
     * 还可以支持反色模式，比如"1|3|-aabbcc,-5|-3|-123456-000000","-"表示除了指定颜色之外的颜色.<br/>
     * @param sim 相似度,取值范围0.1-1.0
     * @param dir 查找方向 0: 从左到右,从上到下 1: 从左到右,从下到上 2: 从右到左,从上到下 3: 从右到左, 从下到上
     * @return 返回X和Y坐标 形式如"x|y", 比如"100|200"
     */
    public String FindMultiColorE(int x1,int y1,int x2,int y2,String first_color,String offset_color,double sim,int dir){
        return Dispatch.call(dm,"FindMultiColorE",x1, y1, x2, y2,first_color,offset_color,sim, dir).getString();
    }

    /**
     * 根据指定的多点查找所有颜色坐标
     * @param x1 区域的左上X坐标
     * @param y1 区域的左上Y坐标
     * @param x2 区域的右下X坐标
     * @param y2 区域的右下Y坐标
     * @param first_color <br/>
     * 颜色 格式为"RRGGBB-DRDGDB",比如"123456-000000" <br/>
     * 这里的含义和按键自带Color插件的意义相同，只不过我的可以支持偏色 <br/>
     * 所有的偏移色坐标都相对于此颜色.注意，这里只支持RGB颜色. <br/>
     * @param offset_color <br/>
     * 偏移颜色 可以支持任意多个点 格式和按键自带的Color插件意义相同<br/>
     *  格式为"x1|y1|RRGGBB-DRDGDB,……xn|yn|RRGGBB-DRDGDB"<br/>
     * 比如"1|3|aabbcc,-5|-3|123456-000000"等任意组合都可以，支持偏色<br/>
     * 还可以支持反色模式，比如"1|3|-aabbcc,-5|-3|-123456-000000","-"表示除了指定颜色之外的颜色.<br/>
     * @param sim 相似度,取值范围0.1-1.0
     * @param dir 查找方向 0: 从左到右,从上到下 1: 从左到右,从下到上 2: 从右到左,从上到下 3: 从右到左, 从下到上
     * @return 返回所有颜色信息的坐标值,然后通过GetResultCount等接口来解析(由于内存限制,返回的坐标数量最多为1800个左右)
    坐标是first_color所在的坐标
     */
    public String FindMultiColorEx(int x1,int y1,int x2,int y2,String first_color,String offset_color,double sim,int dir){
        return Dispatch.call(dm,"FindMultiColorEx",x1, y1, x2, y2,first_color,offset_color,sim, dir).getString();
    }

    /**
     * 查找指定区域内的图片,位图必须是24位色格式,支持透明色,当图像上下左右4个顶点的颜色一样时,则这个颜色将作为透明色处理.
     * 这个函数可以查找多个图片,只返回第一个找到的X Y坐标
     * @param x1 区域的左上X坐标
     * @param y1 区域的左上Y坐标
     * @param x2 区域的右下X坐标
     * @param y2 区域的右下Y坐标
     * @param pic_name 图片名,可以是多个图片,比如"test.bmp|test2.bmp|test3.bmp"
     * @param delta_color 颜色色偏比如"203040" 表示RGB的色偏分别是20 30 40 (这里是16进制表示)
     * @param sim 相似度,取值范围0.1-1.0
     * @param dir 查找方向 0: 从左到右,从上到下 1: 从左到右,从下到上 2: 从右到左,从上到下 3: 从右到左, 从下到上
     * @param intX 变参指针:返回图片左上角的X坐标
     * @param intY 变参指针:返回图片左上角的Y坐标
     * @return 返回找到的图片的序号,从0开始索引.如果没找到返回-1
     */
    public int FindPic(int x1,int y1,int x2,int y2,String pic_name,String delta_color,double sim,int dir,Variant intX, Variant intY){
        return Dispatch.callN(dm,"FindPic",new Object[]{x1, y1, x2, y2, pic_name, delta_color,sim, dir,intX, intY}).getInt();
    }

    /**
     * 查找指定区域内的图片,位图必须是24位色格式,支持透明色,当图像上下左右4个顶点的颜色一样时,则这个颜色将作为透明色处理.
     * 这个函数可以查找多个图片,只返回第一个找到的X Y坐标.
     * @param x1 区域的左上X坐标
     * @param y1 区域的左上Y坐标
     * @param x2 区域的右下X坐标
     * @param y2 区域的右下Y坐标
     * @param pic_name 图片名,可以是多个图片,比如"test.bmp|test2.bmp|test3.bmp"
     * @param delta_color 颜色色偏比如"203040" 表示RGB的色偏分别是20 30 40 (这里是16进制表示)
     * @param sim 相似度,取值范围0.1-1.0
     * @param dir 查找方向 0: 从左到右,从上到下 1: 从左到右,从下到上 2: 从右到左,从上到下 3: 从右到左, 从下到上
     * @return 返回找到的图片序号(从0开始索引)以及X和Y坐标 形式如"index|x|y", 比如"3|100|200"
     */
    public String FindPicE(int x1,int y1,int x2,int y2,String pic_name,String delta_color,double sim,int dir){
        return Dispatch.call(dm,"FindPicE",x1, y1, x2, y2, pic_name, delta_color,sim, dir).getString();
    }

    /**
     * 查找指定区域内的图片,位图必须是24位色格式,支持透明色,当图像上下左右4个顶点的颜色一样时,则这个颜色将作为透明色处理.
     * 这个函数可以查找多个图片,并且返回所有找到的图像的坐标.
     * @param x1 区域的左上X坐标
     * @param y1 区域的左上Y坐标
     * @param x2 区域的右下X坐标
     * @param y2 区域的右下Y坐标
     * @param pic_name 图片名,可以是多个图片,比如"test.bmp|test2.bmp|test3.bmp"
     * @param delta_color 颜色色偏比如"203040" 表示RGB的色偏分别是20 30 40 (这里是16进制表示)
     * @param sim 相似度,取值范围0.1-1.0
     * @param dir 查找方向 0: 从左到右,从上到下 1: 从左到右,从下到上 2: 从右到左,从上到下 3: 从右到左, 从下到上
     * @return 返回的是所有找到的坐标格式如下:"id,x,y|id,x,y..|id,x,y" (图片左上角的坐标)<br/>
     * 比如"0,100,20|2,30,40" 表示找到了两个,第一个,对应的图片是图像序号为0的图片,坐标是(100,20),第二个是序号为2的图片,坐标(30,40)(由于内存限制,返回的图片数量最多为1500个左右)
     */
    public String FindPicEx(int x1,int y1,int x2,int y2,String pic_name,String delta_color,double sim,int dir){
        return Dispatch.call(dm,"FindPicEx",x1, y1, x2, y2, pic_name, delta_color,sim, dir).getString();
    }

    /**
     * 释放指定的图片,此函数不必要调用,除非你想节省内存
     * @param pic_name
     * 文件名比如"1.bmp|2.bmp|3.bmp" 等,可以使用通配符,比如<br/>
    "*.bmp" 这个对应了所有的bmp文件<br/>
    "a?c*.bmp" 这个代表了所有第一个字母是a 第三个字母是c 第二个字母任意的所有bmp文件<br/>
    "abc???.bmp|1.bmp|aa??.bmp" 可以这样任意组合.<br/>
     * @return 0:失败 1:成功
     */
    public int FreePic(String pic_name){
        return Dispatch.call(dm,"FreePic",pic_name).getInt();
    }

    /**
     * 获取范围(x1,y1,x2,y2)颜色的均值,返回格式"H.S.V"
     * @param x1 左上角X
     * @param y1 左上角Y
     * @param x2 右下角X
     * @param y2 右下角Y
     * @return 颜色字符串
     */
    public String GetAveHSV(int x1,int y1,int x2,int y2){
        return Dispatch.call(dm,"GetAveHSV",x1,y1,x2,y2).getString();
    }

    /**
     * 获取范围(x1,y1,x2,y2)颜色的均值,返回格式"RRGGBB"
     * @param x1 左上角X
     * @param y1 左上角Y
     * @param x2 右下角X
     * @param y2 右下角Y
     * @return 颜色字符串
     */
    public String GetAveRGB(int x1,int y1,int x2,int y2){
        return Dispatch.call(dm,"GetAveRGB",x1,y1,x2,y2).getString();
    }

    /**
     * 获取(x,y)的颜色,颜色返回格式"RRGGBB",注意,和按键的颜色格式相反
     * @param x X坐标
     * @param y Y坐标
     * @return 颜色字符串(注意这里都是小写字符，和工具相匹配)
     */
    public String GetColor(int x,int y){
        return Dispatch.call(dm,"GetColor",x,y).getString();
    }

    /**
     * 获取(x,y)的颜色,颜色返回格式"BBGGRR"
     * @param x X坐标
     * @param y Y坐标
     * @return 颜色字符串(注意这里都是小写字符，和工具相匹配)
     */
    public String GetColorBGR(int x,int y){
        return Dispatch.call(dm,"GetColorBGR",x,y).getString();
    }

    /**
     * 获取(x,y)的颜色,颜色返回格式"H.S.V"
     * @param x X坐标
     * @param y Y坐标
     * @return 颜色字符串
     */
    public String GetColorHSV(int x,int y){
        return Dispatch.call(dm,"GetColorHSV",x,y).getString();
    }

    /**
     * 获取指定区域的颜色数量,颜色格式"RRGGBB-DRDGDB",注意,和按键的颜色格式相反
     * @param x1 区域的左上X坐标
     * @param y1 区域的左上Y坐标
     * @param x2 区域的右下X坐标
     * @param y2 区域的右下Y坐标
     * @param color 颜色 格式为"RRGGBB-DRDGDB",比如"123456-000000|aabbcc-202020".注意，这里只支持RGB颜色.
     * @param sim 相似度,取值范围0.1-1.0
     * @return 颜色数量
     */
    public int GetColorNum(int x1,int y1,int x2,int y2,String color,double sim){
        return Dispatch.call(dm,"GetColorNum",x1, y1, x2, y2, color, sim).getInt();
    }

    /**
     * 获取指定图片的尺寸，如果指定的图片已经被加入缓存，则从缓存中获取信息.
     *此接口也会把此图片加入缓存.
     * @param pic_name 文件名比如"1.bmp"
     * @return 形式如 "w,h" 比如"30,20"
     */
    public String GetPicSize(String pic_name){
        return Dispatch.call(dm,"GetPicSize",pic_name).getString();
    }

    /**
     * 获取指定区域的图像,用二进制数据的方式返回,（不适合按键使用）方便二次开发
     * @param x1 区域的左上X坐标
     * @param y1 区域的左上Y坐标
     * @param x2 区域的右下X坐标
     * @param y2 区域的右下Y坐标
     * @return 返回的是指定区域的二进制颜色数据地址,每个颜色是4个字节,表示方式为(00RRGGBB)
     */
    public int GetScreenData(int x1,int y1,int x2,int y2){
        return Dispatch.call(dm,"GetScreenData",x1,y1,x2,y2).getInt();
    }

    /**
     * 转换图片格式为24位BMP格式.
     * @param pic_name 要转换的图片名
     * @param bmp_name 要保存的BMP图片名
     * @return 0 : 失败 1 : 成功
     */
    public int ImageToBmp(String pic_name,String bmp_name){
        return Dispatch.call(dm,"ImageToBmp",pic_name,bmp_name).getInt();
    }

    /**
     * 判断指定的区域，在指定的时间内(秒),图像数据是否一直不变.(卡屏).
     * @param x1 区域的左上X坐标
     * @param y1 区域的左上Y坐标
     * @param x2 区域的右下X坐标
     * @param y2 区域的右下Y坐标
     * @param t 需要等待的时间,单位是秒
     * @return 0 : 没有卡屏，图像数据在变化.<br/>
    1 : 卡屏. 图像数据在指定的时间内一直没有变化.
     */
    public int IsDisplayDead(int x1,int y1,int x2,int y2,int t){
        return Dispatch.call(dm,"IsDisplayDead",x1,y1,x2,y2,t).getInt();
    }

    /**
     * 预先加载指定的图片,这样在操作任何和图片相关的函数时,将省去了加载图片的时间。调用此函数后,没必要一定要调用FreePic,插件自己会自动释放.
     * @param pic_name <br/>
     * 文件名比如"1.bmp|2.bmp|3.bmp" 等,可以使用通配符,比如<br/>
     *    "*.bmp" 这个对应了所有的bmp文件<br/>
     *    "a?c*.bmp" 这个代表了所有第一个字母是a 第三个字母是c 第二个字母任意的所有bmp文件<br/>
     *    "abc???.bmp|1.bmp|aa??.bmp" 可以这样任意组合.<br/>
     * @return 0:失败 1:成功
     */
    public int LoadPic(String pic_name){
        return Dispatch.call(dm,"LoadPic",pic_name).getInt();
    }

    /**
     * 根据通配符获取文件集合. 方便用于FindPic和FindPicEx
     * @param pic_name <br/>
     * 文件名比如"1.bmp|2.bmp|3.bmp" 等,可以使用通配符,比如<br/>
     *    "*.bmp" 这个对应了所有的bmp文件<br/>
     *    "a?c*.bmp" 这个代表了所有第一个字母是a 第三个字母是c 第二个字母任意的所有bmp文件<br/>
     *    "abc???.bmp|1.bmp|aa??.bmp" 可以这样任意组合.<br/>
     * @return 返回的是通配符对应的文件集合，每个图片以|分割
     */
    public String MatchPicName(String pic_name){
        return Dispatch.call(dm,"MatchPicName",pic_name).getString();
    }

    /**
     * 把RGB的颜色格式转换为BGR(按键格式)
     * @param rgb_color rgb格式的颜色字符串
     * @return BGR格式的字符串
     */
    public String RGB2BGR(String rgb_color){
        return Dispatch.call(dm,"RGB2BGR",rgb_color).getString();
    }

    /**
     * 设置图片密码，如果图片本身没有加密，那么此设置不影响不加密的图片，一样正常使用<br/>
     * 注意,此函数必须在使用图片之前调用.
     * @param pwd 图片密码
     * @return 0:失败 1:成功
     */
    public int SetPicPwd(String pwd){
        return Dispatch.call(dm,"SetPicPwd",pwd).getInt();
    }
}
