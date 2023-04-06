package com.future.small_toolbox.damo.Features;

import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.Dispatch;

/**
 * @Description 内存
 * @Author xiaowuchen
 * @Date 2020/9/23 10:42
 */
public class MemoryOPer {

    private ActiveXComponent dm;

    public MemoryOPer(ActiveXComponent dm){
        this.dm=dm;
    }

    /**
     * 把双精度浮点数转换成二进制形式.
     * @param value 需要转化的双精度浮点数
     * @return 字符串形式表达的二进制数据. 可以用于WriteData FindData FindDataEx等接口
     */
    public String DoubleToData(double value){
        return Dispatch.call(dm,"DoubleToData",value).getString();
    }

    /**
     * 搜索指定的二进制数据,默认步长是1.如果要定制步长，请用FindDataEx
     * @param hwnd 指定搜索的窗口句柄或者进程ID.  默认是窗口句柄. 如果要指定为进程ID,需要调用
     * @param addr_range 指定搜索的地址集合，字符串类型，这个地方可以是上次FindXXX的返回地址集合,可以进行二次搜索.(类似CE的再次扫描),如果要进行地址范围搜索，那么这个值为的形如如下(类似于CE的新搜索);"00400000-7FFFFFFF" "80000000-BFFFFFFF" "00000000-FFFFFFFF" 等.
     * @param data 要搜索的二进制数据 以字符串的形式描述比如"00 01 23 45 67 86 ab ce f1"等.
     * @return 返回搜索到的地址集合，地址格式如下:"addr1|addr2|addr3…|addrn" 比如"400050|423435|453430"
     */
    public String FindData(int hwnd, String addr_range, String data){
        return Dispatch.call(dm,"FindData",hwnd,addr_range,data).getString();
    }

    /**
     * 搜索指定的双精度浮点数,默认步长是1.如果要定制步长，请用FindDoubleEx
     * @param hwnd 指定搜索的窗口句柄或者进程ID.  默认是窗口句柄. 如果要指定为进程ID,需要调用SetMemoryHwndAsProcessId
     * @param addr_range 指定搜索的地址集合，字符串类型，这个地方可以是上次FindXXX的返回地址集合,可以进行二次搜索.(类似CE的再次扫描);如果要进行地址范围搜索，那么这个值为的形如如下(类似于CE的新搜索);"00400000-7FFFFFFF" "80000000-BFFFFFFF" "00000000-FFFFFFFF" 等.
     * @param double_value_min 搜索的双精度数值最小值
     * @param double_value_max 搜索的双精度数值最大值
     * @return 返回搜索到的地址集合,比如"400050|423435|453430";最终搜索的数值大与等于double_value_min,并且小于等于double_value_max
     */
    public String FindDouble(int hwnd, String addr_range, double double_value_min,double double_value_max){
        return Dispatch.call(dm,"FindDouble",hwnd,addr_range,double_value_min,double_value_max).getString();
    }

    /**
     * 搜索指定的单精度浮点数,默认步长是1.如果要定制步长，请用FindFloatEx
     * @param hwnd 指定搜索的窗口句柄或者进程ID.  默认是窗口句柄. 如果要指定为进程ID,需要调用SetMemoryHwndAsProcessId
     * @param addr_range 指定搜索的地址集合，字符串类型，这个地方可以是上次FindXXX的返回地址集合,可以进行二次搜索.(类似CE的再次扫描);如果要进行地址范围搜索，那么这个值为的形如如下(类似于CE的新搜索); "00400000-7FFFFFFF" "80000000-BFFFFFFF" "00000000-FFFFFFFF" 等.
     * @param float_value_min 搜索的单精度数值最小值
     * @param float_value_max 搜索的单精度数值最大值
     * @return 返回搜索到的地址集合，比如"400050|423435|453430" 最终搜索的数值大与等于float_value_min,并且小于等于float_value_max
     */
    public String FindFloat(int hwnd, String addr_range, float float_value_min,float float_value_max){
        return Dispatch.call(dm,"FindFloat",hwnd, addr_range, float_value_min, float_value_max).getString();
    }

    /**
     * 搜索指定的整数,默认步长是1.如果要定制步长，请用FindIntEx
     * @param hwnd 指定搜索的窗口句柄或者进程ID.  默认是窗口句柄. 如果要指定为进程ID,需要调用SetMemoryHwndAsProcessId
     * @param addr_range 指定搜索的地址集合，字符串类型，这个地方可以是上次FindXXX的返回地址集合,可以进行二次搜索.(类似CE的再次扫描);如果要进行地址范围搜索，那么这个值为的形如如下(类似于CE的新搜索):"00400000-7FFFFFFF" "80000000-BFFFFFFF" "00000000-FFFFFFFF" 等.
     * @param int_value_min 搜索的整数数值最小值
     * @param int_value_max 搜索的整数数值最大值
     * @param type 搜索的整数类型,取值如下  0:32位; 1:16位; 2:8位
     * @return 返回搜索到的地址集合,比如"400050|423435|453430"
     */
    public String FindInt(int hwnd, String addr_range,int int_value_min,int int_value_max,int type){
        return Dispatch.call(dm,"FindInt",hwnd, addr_range, int_value_min, int_value_max,type).getString();
    }

    /**
     * 搜索指定的字符串,默认步长是1.如果要定制步长，请用FindStringEx
     * @param hwnd 指定搜索的窗口句柄或者进程ID.  默认是窗口句柄. 如果要指定为进程ID,需要调用SetMemoryHwndAsProcessId
     * @param addr_range 指定搜索的地址集合，字符串类型，这个地方可以是上次FindXXX的返回地址集合,可以进行二次搜索.(类似CE的再次扫描);如果要进行地址范围搜索，那么这个值为的形如如下(类似于CE的新搜索):"00400000-7FFFFFFF" "80000000-BFFFFFFF" "00000000-FFFFFFFF" 等.
     * @param string_value 搜索的字符串
     * @param type 搜索的字符串类型,取值如下 0 : Ascii字符串;1 : Unicode字符串
     * @return 返回搜索到的地址集合，地址格式如下: "addr1|addr2|addr3…|addrn" 比如"400050|423435|453430"
     */
    public String FindString(int hwnd, String addr_range, String string_value,int type){
        return Dispatch.call(dm,"FindString",hwnd, addr_range, string_value,type).getString();
    }

    /**
     * 把单精度浮点数转换成二进制形式
     * @param value 需要转化的单精度浮点数
     * @return 字符串形式表达的二进制数据. 可以用于WriteData FindData FindDataEx等接口
     */
    public String FloatToData(float value){
        return Dispatch.call(dm, "FloatToData",value).getString();
    }

    /**
     * 根据指定的窗口句柄，来获取对应窗口句柄进程下的指定模块的基址
     * @param hwnd 指定搜索的窗口句柄或者进程ID.  默认是窗口句柄. 如果要指定为进程ID,需要调用SetMemoryHwndAsProcessId
     * @param module 模块名
     * @return 模块的基址
     */
    public int GetModuleBaseAddr(int hwnd,String module){
        return Dispatch.call(dm, "GetModuleBaseAddr",hwnd,module).getInt();
    }

    /**
     * 把整数转换成二进制形式
     * @param value 需要转化的整型数
     * @param type 取值如下:0: 4字节整形数 (一般都选这个);1: 2字节整形数;2: 1字节整形数
     * @return 字符串形式表达的二进制数据. 可以用于WriteData FindData FindDataEx等接口.
     */
    public String IntToData(int value,int type){
        return Dispatch.call(dm, "IntToData",value,type).getString();
    }

    /**
     * 读取指定地址的二进制数据
     * @param hwnd 指定搜索的窗口句柄或者进程ID.  默认是窗口句柄. 如果要指定为进程ID,需要调用SetMemoryHwndAsProcessId
     * @param addr 用字符串来描述地址，类似于CE的地址描述，数值必须是16进制,里面可以用[ ] + -这些符号来描述一个地址。+表示地址加，-表示地址减,模块名必须用<>符号来圈起来<br/>
     *  例如:<br/>
     *  1.         "4DA678" 最简单的方式，用绝对数值来表示地址<br/>
     *  2.         "<360SE.exe>+DA678" 相对简单的方式，只是这里用模块名来决定模块基址，后面的是偏移<br/>
     *  3.         "[4DA678]+3A" 用绝对数值加偏移，相当于一级指针<br/>
     *  4.         "[<360SE.exe>+DA678]+3A" 用模块定基址的方式，也是一级指针<br/>
     *  5.         "[[[<360SE.exe>+DA678]+3A]+5B]+8" 这个是一个三级指针<br/>
     *  总之熟悉CE的人 应该对这个地址描述都很熟悉,我就不多举例了
     * @param len 二进制数据的长度
     * @return 读取到的数值,以16进制表示的字符串 每个字节以空格相隔 比如"12 34 56 78 ab cd ef"
     */
    public String ReadData(int hwnd,String addr,int len){
        return Dispatch.call(dm, "ReadData",hwnd,addr,len).getString();
    }

    /**
     * 读取指定地址的双精度浮点数
     * @param hwnd 指定搜索的窗口句柄或者进程ID.  默认是窗口句柄. 如果要指定为进程ID,需要调用SetMemoryHwndAsProcessId
     * @param addr 用字符串来描述地址，类似于CE的地址描述，数值必须是16进制,里面可以用[ ] + -这些符号来描述一个地址。+表示地址加，-表示地址减;模块名必须用<>符号来圈起来<br/>
     * 例如:<br/>
     * 1.         "4DA678" 最简单的方式，用绝对数值来表示地址<br/>
     * 2.         "<360SE.exe>+DA678" 相对简单的方式，只是这里用模块名来决定模块基址，后面的是偏移<br/>
     * 3.         "[4DA678]+3A" 用绝对数值加偏移，相当于一级指针<br/>
     * 4.         "[<360SE.exe>+DA678]+3A" 用模块定基址的方式，也是一级指针<br/>
     * 5.         "[[[<360SE.exe>+DA678]+3A]+5B]+8" 这个是一个三级指针<br/>
     * 总之熟悉CE的人 应该对这个地址描述都很熟悉,我就不多举例了
     * @return 读取到的数值,注意这里无法判断读取是否成功
     */
    public double ReadDouble(int hwnd,String addr){
        return Dispatch.call(dm, "ReadDouble",hwnd,addr).getDouble();
    }

    /**
     * 读取指定地址的双精度浮点数
     * @param hwnd 指定搜索的窗口句柄或者进程ID.  默认是窗口句柄. 如果要指定为进程ID,需要调用SetMemoryHwndAsProcessId
     * @param addr 用字符串来描述地址，类似于CE的地址描述，数值必须是16进制,里面可以用[ ] + -这些符号来描述一个地址。+表示地址加，-表示地址减;模块名必须用<>符号来圈起来<br/>
     * 例如:<br/>
     * 1.         "4DA678" 最简单的方式，用绝对数值来表示地址<br/>
     * 2.         "<360SE.exe>+DA678" 相对简单的方式，只是这里用模块名来决定模块基址，后面的是偏移<br/>
     * 3.         "[4DA678]+3A" 用绝对数值加偏移，相当于一级指针<br/>
     * 4.         "[<360SE.exe>+DA678]+3A" 用模块定基址的方式，也是一级指针<br/>
     * 5.         "[[[<360SE.exe>+DA678]+3A]+5B]+8" 这个是一个三级指针<br/>
     * 总之熟悉CE的人 应该对这个地址描述都很熟悉,我就不多举例了
     * @return 读取到的数值,注意这里无法判断读取是否成功
     */
    public float ReadFloat(int hwnd,String addr){
        return Dispatch.call(dm, "ReadFloat",hwnd,addr).getFloat();
    }

    /**
     * 读取指定地址的整数数值，类型可以是8位，16位 或者 32位
     * @param hwnd 指定搜索的窗口句柄或者进程ID.  默认是窗口句柄. 如果要指定为进程ID,需要调用SetMemoryHwndAsProcessId
     * @param addr 用字符串来描述地址，类似于CE的地址描述，数值必须是16进制,里面可以用[ ] + -这些符号来描述一个地址。+表示地址加，-表示地址减;模块名必须用<>符号来圈起来<br/>
     * 例如:<br/>
     * 1.         "4DA678" 最简单的方式，用绝对数值来表示地址<br/>
     * 2.         "<360SE.exe>+DA678" 相对简单的方式，只是这里用模块名来决定模块基址，后面的是偏移<br/>
     * 3.         "[4DA678]+3A" 用绝对数值加偏移，相当于一级指针<br/>
     * 4.         "[<360SE.exe>+DA678]+3A" 用模块定基址的方式，也是一级指针<br/>
     * 5.         "[[[<360SE.exe>+DA678]+3A]+5B]+8" 这个是一个三级指针<br/>
     * 总之熟悉CE的人 应该对这个地址描述都很熟悉,我就不多举例了
     * @param type 整数类型,  0 : 32位  1 : 16 位 2 : 8位
     * @return 读取到的数值,注意这里无法判断读取是否成功
     */
    public int ReadInt(int hwnd,String addr,int type){
        return Dispatch.call(dm, "ReadInt",hwnd,addr,type).getInt();
    }

    /**
     * 读取指定地址的字符串，可以是GBK字符串或者是Unicode字符串.(必须事先知道内存区的字符串编码方式)
     * @param hwnd 指定搜索的窗口句柄或者进程ID.  默认是窗口句柄. 如果要指定为进程ID,需要调用SetMemoryHwndAsProcessId
     * @param addr 用字符串来描述地址，类似于CE的地址描述，数值必须是16进制,里面可以用[ ] + -这些符号来描述一个地址。+表示地址加，-表示地址减;模块名必须用<>符号来圈起来<br/>
     * 例如:<br/>
     * 1.         "4DA678" 最简单的方式，用绝对数值来表示地址<br/>
     * 2.         "<360SE.exe>+DA678" 相对简单的方式，只是这里用模块名来决定模块基址，后面的是偏移<br/>
     * 3.         "[4DA678]+3A" 用绝对数值加偏移，相当于一级指针<br/>
     * 4.         "[<360SE.exe>+DA678]+3A" 用模块定基址的方式，也是一级指针<br/>
     * 5.         "[[[<360SE.exe>+DA678]+3A]+5B]+8" 这个是一个三级指针<br/>
     * 总之熟悉CE的人 应该对这个地址描述都很熟悉,我就不多举例了
     * @param type 字符串类型,取值如下  0 : GBK字符串; 1 : Unicode字符串
     * @param len 需要读取的字节数目
     * @return 读取到的字符串,注意这里无法判断读取是否成功
     */
    public String ReadString(int hwnd,String addr,int type,int len){
        return Dispatch.call(dm, "ReadString",hwnd,addr,type,len).getString();
    }

    /**
     * 把字符串转换成二进制形式
     * @param value 需要转化的字符串
     * @param type 0: 返回Ascii表达的字符串;1: 返回Unicode表达的字符串
     * @return 字符串形式表达的二进制数据. 可以用于WriteData FindData FindDataEx等接口
     */
    public String StringToData(String value,int type){
        return Dispatch.call(dm, "StringToData",value,type).getString();
    }

    /**
     * 对指定地址写入二进制数据
     * @param hwnd 指定搜索的窗口句柄或者进程ID.  默认是窗口句柄. 如果要指定为进程ID,需要调用SetMemoryHwndAsProcessId.
     * @param addr 用字符串来描述地址，类似于CE的地址描述，数值必须是16进制,里面可以用[ ] + -这些符号来描述一个地址。+表示地址加，-表示地址减;模块名必须用<>符号来圈起来<br/>
     * 例如:<br/>
     * 1.         "4DA678" 最简单的方式，用绝对数值来表示地址<br/>
     * 2.         "<360SE.exe>+DA678" 相对简单的方式，只是这里用模块名来决定模块基址，后面的是偏移<br/>
     * 3.         "[4DA678]+3A" 用绝对数值加偏移，相当于一级指针<br/>
     * 4.         "[<360SE.exe>+DA678]+3A" 用模块定基址的方式，也是一级指针<br/>
     * 5.         "[[[<360SE.exe>+DA678]+3A]+5B]+8" 这个是一个三级指针<br/>
     * 总之熟悉CE的人 应该对这个地址描述都很熟悉,我就不多举例了
     * @param data 二进制数据，以字符串形式描述，比如"12 34 56 78 90 ab cd"
     * @return 0 : 失败 ;1 : 成功
     */
    public int WriteData(int hwnd,String addr,String data){
        return Dispatch.call(dm, "WriteData",hwnd,addr,data).getInt();
    }

    /**
     * 对指定地址写入双精度浮点数
     * @param hwnd 指定搜索的窗口句柄或者进程ID.  默认是窗口句柄. 如果要指定为进程ID,需要调用SetMemoryHwndAsProcessId.
     * @param addr 用字符串来描述地址，类似于CE的地址描述，数值必须是16进制,里面可以用[ ] + -这些符号来描述一个地址。+表示地址加，-表示地址减;模块名必须用<>符号来圈起来<br/>
     * 例如:<br/>
     * 1.         "4DA678" 最简单的方式，用绝对数值来表示地址<br/>
     * 2.         "<360SE.exe>+DA678" 相对简单的方式，只是这里用模块名来决定模块基址，后面的是偏移<br/>
     * 3.         "[4DA678]+3A" 用绝对数值加偏移，相当于一级指针<br/>
     * 4.         "[<360SE.exe>+DA678]+3A" 用模块定基址的方式，也是一级指针<br/>
     * 5.         "[[[<360SE.exe>+DA678]+3A]+5B]+8" 这个是一个三级指针<br/>
     * 总之熟悉CE的人 应该对这个地址描述都很熟悉,我就不多举例了
     * @param v 双精度浮点数
     * @return 0 : 失败 ;1 : 成功
     */
    public int WriteDouble(int hwnd,String addr,double v){
        return Dispatch.call(dm, "WriteDouble",hwnd,addr,v).getInt();
    }

    /**
     * 对指定地址写入单精度浮点数
     * @param hwnd 指定搜索的窗口句柄或者进程ID.  默认是窗口句柄. 如果要指定为进程ID,需要调用SetMemoryHwndAsProcessId.
     * @param addr 用字符串来描述地址，类似于CE的地址描述，数值必须是16进制,里面可以用[ ] + -这些符号来描述一个地址。+表示地址加，-表示地址减;模块名必须用<>符号来圈起来<br/>
     * 例如:<br/>
     * 1.         "4DA678" 最简单的方式，用绝对数值来表示地址<br/>
     * 2.         "<360SE.exe>+DA678" 相对简单的方式，只是这里用模块名来决定模块基址，后面的是偏移<br/>
     * 3.         "[4DA678]+3A" 用绝对数值加偏移，相当于一级指针<br/>
     * 4.         "[<360SE.exe>+DA678]+3A" 用模块定基址的方式，也是一级指针<br/>
     * 5.         "[[[<360SE.exe>+DA678]+3A]+5B]+8" 这个是一个三级指针<br/>
     * 总之熟悉CE的人 应该对这个地址描述都很熟悉,我就不多举例了
     * @param v 单精度浮点数
     * @return 0 : 失败 ;1 : 成功
     */
    public int WriteFloat(int hwnd,String addr,float v){
        return Dispatch.call(dm, "WriteFloat",hwnd,addr,v).getInt();
    }

    /**
     * 对指定地址写入整数数值，类型可以是8位，16位 或者 32位
     * @param hwnd 指定搜索的窗口句柄或者进程ID.  默认是窗口句柄. 如果要指定为进程ID,需要调用SetMemoryHwndAsProcessId.
     * @param addr 用字符串来描述地址，类似于CE的地址描述，数值必须是16进制,里面可以用[ ] + -这些符号来描述一个地址。+表示地址加，-表示地址减;模块名必须用<>符号来圈起来<br/>
     * 例如:<br/>
     * 1.         "4DA678" 最简单的方式，用绝对数值来表示地址<br/>
     * 2.         "<360SE.exe>+DA678" 相对简单的方式，只是这里用模块名来决定模块基址，后面的是偏移<br/>
     * 3.         "[4DA678]+3A" 用绝对数值加偏移，相当于一级指针<br/>
     * 4.         "[<360SE.exe>+DA678]+3A" 用模块定基址的方式，也是一级指针<br/>
     * 5.         "[[[<360SE.exe>+DA678]+3A]+5B]+8" 这个是一个三级指针<br/>
     * 总之熟悉CE的人 应该对这个地址描述都很熟悉,我就不多举例了
     * @param type 0 : 32位 ;1 : 16 位;2 : 8位
     * @param v 整形数值
     * @return 0 : 失败 ;1 : 成功
     */
    public int WriteInt(int hwnd,String addr,int type,int v){
        return Dispatch.call(dm, "WriteInt",hwnd,addr,type,v).getInt();
    }

    /**
     * 对指定地址写入字符串，可以是Ascii字符串或者是Unicode字符串
     * @param hwnd 指定搜索的窗口句柄或者进程ID.  默认是窗口句柄. 如果要指定为进程ID,需要调用SetMemoryHwndAsProcessId.
     * @param addr 用字符串来描述地址，类似于CE的地址描述，数值必须是16进制,里面可以用[ ] + -这些符号来描述一个地址。+表示地址加，-表示地址减;模块名必须用<>符号来圈起来<br/>
     * 例如:<br/>
     * 1.         "4DA678" 最简单的方式，用绝对数值来表示地址<br/>
     * 2.         "<360SE.exe>+DA678" 相对简单的方式，只是这里用模块名来决定模块基址，后面的是偏移<br/>
     * 3.         "[4DA678]+3A" 用绝对数值加偏移，相当于一级指针<br/>
     * 4.         "[<360SE.exe>+DA678]+3A" 用模块定基址的方式，也是一级指针<br/>
     * 5.         "[[[<360SE.exe>+DA678]+3A]+5B]+8" 这个是一个三级指针<br/>
     * 总之熟悉CE的人 应该对这个地址描述都很熟悉,我就不多举例了
     * @param type 0 : Ascii字符串;1 : Unicode字符串
     * @param v 字符串
     * @return 0 : 失败 ;1 : 成功
     */
    public int WriteString(int hwnd,String addr,int type,String v){
        return Dispatch.call(dm, "WriteString",hwnd,addr,type,v).getInt();
    }

}
