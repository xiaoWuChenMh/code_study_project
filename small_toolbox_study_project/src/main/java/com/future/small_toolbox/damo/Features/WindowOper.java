package com.future.small_toolbox.damo.Features;

import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.Dispatch;
import com.jacob.com.Variant;

/**
 * @Description 窗口操作
 * @Author xiaowuchen
 * @Date 2020/9/22 18:22
 */
public class WindowOper {

    private ActiveXComponent dm;

    public WindowOper(ActiveXComponent dm){
        this.dm=dm;
    }


// ***************************** 坐标转换操作 ***************************

    /**
     * 把窗口坐标转换为屏幕坐标
     * @param hwnd 指定的窗口句柄
     * @param x 窗口X坐标
     * @param y 窗口Y坐标
     * @return 0: 失败 1: 成功
     */
    public int ClientToScreen(int hwnd, Variant x, Variant y){
        Variant x1= new Variant(0,true);
        Variant y1= new Variant(0,true);
        Variant x2= new Variant(0,true);
        Variant y2= new Variant(0,true);
        int dm_ret = GetClientRect(hwnd,x1,y1,x2,y2);
        if(dm_ret==1){
            int ck_x = x.getIntRef();
            int ck_y = y.getIntRef();
            int pm_x = ck_x + x1.getInt();
            int pm_y = ck_y + y1.getInt();
            x.putIntRef(pm_x);
            x.putInt(pm_x);
            y.putIntRef(pm_y);
            y.putInt(pm_y);
            return 1;
        }
        return 0;
    }

    /**
     * 把屏幕坐标转换为窗口坐标
     * @param hwnd 指定的窗口句柄
     * @param x 变参指针: 屏幕X坐标
     * @param y 变参指针: 屏幕Y坐标
     * @return 0: 失败 1:成功
     */
    public int ScreenToClient(int hwnd,Variant x,Variant y){
        Variant x1= new Variant(0,true);
        Variant y1= new Variant(0,true);
        Variant x2= new Variant(0,true);
        Variant y2= new Variant(0,true);
        int dm_ret = GetClientRect(hwnd,x1,y1,x2,y2);
        if(dm_ret==1){
            int pm_x = x.getIntRef();
            int pm_y = y.getIntRef();
            int ck_x = pm_x - x1.getInt();
            int ck_y = pm_y - y1.getInt();
            x.putIntRef(ck_x);
            y.putIntRef(ck_y);
            return 1;
        }
        return 0;
    }


// ***************************** 根据条件获取窗口句柄 ***************************
// ****
// **** GetForegroundFocus ： 获取顶层活动窗口中具有输入焦点的窗口句柄 (可用）
// **************************************************************************

    /**
     * 根据指定条件,枚举系统中符合条件的窗口,可以枚举到按键自带的无法枚举到的窗口
     * @param parent 获得的窗口句柄是该窗口的子窗口的窗口句柄,取0时为获得桌面句柄
     * @param title 窗口标题. 此参数是模糊匹配
     * @param class_name 窗口类名. 此参数是模糊匹配
     * @param filter 取值定义如下<br/>
     *  1 : 匹配窗口标题,参数title有效 <br/>
     *	2 : 匹配窗口类名,参数class_name有效.<br/>
     *	4 : 只匹配指定父窗口的第一层孩子窗口<br/>
     *	8 : 匹配所有者窗口为0的窗口,即顶级窗口<br/>
     *	16 : 匹配可见的窗口<br/>
     *	32 : 匹配出的窗口按照窗口打开顺序依次排列 <收费功能，具体详情点击查看><br/>
     *	这些值可以相加,比如4+8+16就是类似于任务管理器中的窗口列表<br/>
     * @return 回所有匹配的窗口句柄字符串,格式"hwnd1,hwnd2,hwnd3"
     */
    public String EnumWindow(int parent,String title,String class_name,int filter){
        return Dispatch.call(dm, "EnumWindow", parent,title,class_name,filter).getString();
    }


    /**
     * 根据指定进程以及其它条件,枚举系统中符合条件的窗口,可以枚举到按键自带的无法枚举到的窗口
     * @param process_name 程映像名.比如(svchost.exe). 此参数是精确匹配,但不区分大小写
     * @param title 窗口标题. 此参数是模糊匹配.
     * @param class_name 窗口类名. 此参数是模糊匹配.
     * @param filter 取值定义如下<br/>
     *1 : 匹配窗口标题,参数title有效<br/>
     *2 : 匹配窗口类名,参数class_name有效<br/>
     *4 : 只匹配指定映像的所对应的第一个进程. 可能有很多同映像名的进程，只匹配第一个进程的.<br/>
     *8 : 匹配所有者窗口为0的窗口,即顶级窗口<br/>
     *16 : 匹配可见的窗口<br/>
     *32 : 匹配出的窗口按照窗口打开顺序依次排列<收费功能，具体详情点击查看><br/>
     *这些值可以相加,比如4+8+16<br/>
     * @return 返回所有匹配的窗口句柄字符串,格式"hwnd1,hwnd2,hwnd3"
     */
    public String EnumWindowByProcess(String process_name,String title,String class_name,int filter){
        return Dispatch.call(dm, "EnumWindowByProcess", process_name,title,class_name,filter).getString();
    }

    /**
     * 查找符合类名或者标题名的顶层可见窗口
     * @param class_name 窗口类名，如果为空，则匹配所有. 这里的匹配是模糊匹配.
     * @param title 窗口标题,如果为空，则匹配所有.这里的匹配是模糊匹配.
     * @return 整形数表示的窗口句柄，没找到返回0
     */
    public int FindWindow(String class_name,String title){
        return Dispatch.call(dm, "FindWindow", class_name,title).getInt();
    }

    /**
     * 查找符合类名或者标题名的顶层可见窗口,如果指定了parent,则在parent的第一层子窗口中查找.
     * @param parent 父窗口句柄，如果为空，则匹配所有顶层窗口
     * @param class_name 窗口类名，如果为空，则匹配所有. 这里的匹配是模糊匹配.
     * @param title 窗口标题,如果为空，则匹配所有. 这里的匹配是模糊匹配.
     * @return 整形数表示的窗口句柄，没找到返回0
     */
    public int FindWindowEx(int parent,String class_name,String title){
        return Dispatch.call(dm, "FindWindowEx", parent,class_name,title).getInt();
    }

    /**
     * 获取顶层活动窗口中具有输入焦点的窗口句柄
     * @return 返回整型表示的窗口句柄
     */
    public int GetForegroundFocus(){
        return Dispatch.call(dm, "GetForegroundFocus").getInt();
    }
    /**
     * 获取顶层活动窗口,可以获取到按键自带插件无法获取到的句柄
     * @return 返回整型表示的窗口句柄
     */
    public int GetForegroundWindow(){
        return Dispatch.call(dm, "GetForegroundWindow").getInt();
    }
    /**
     * 获取鼠标指向的窗口句柄,可以获取到按键自带的插件无法获取到的句柄
     * @return 返回整型表示的窗口句柄
     */
    public int GetMousePointWindow(){
        return Dispatch.call(dm, "GetMousePointWindow").getInt();
    }

    /**
     * 获取给定坐标的窗口句柄,可以获取到按键自带的插件无法获取到的句柄
     * @param x 屏幕X坐标
     * @param y 屏幕Y坐标
     * @return 返回整型表示的窗口句柄
     */
    public int GetPointWindow(int x,int y){
        return Dispatch.call(dm, "GetPointWindow",x,y).getInt();
    }

    /**
     * 获取特殊窗口
     * @param flag 取值定义如下<br/>
     * 0 : 获取桌面窗口<br/>
     * 1 : 获取任务栏窗口<br/>
     * @return 以整型数表示的窗口句柄
     */
    public int GetSpecialWindow(int flag){
        return Dispatch.call(dm, "GetSpecialWindow",flag).getInt();
    }



// ***************************** 根据窗口句柄获取其他信息 ***************************

    /**
     * 获取窗口客户区域在屏幕上的位置
     * @param hwnd 指定的窗口句柄
     * @param x1 变参指针: 返回窗口客户区左上角X坐标
     * @param y1 变参指针: 返回窗口客户区左上角Y坐标
     * @param x2 变参指针: 返回窗口客户区右下角X坐标
     * @param y2 变参指针: 返回窗口客户区右下角Y坐标
     * @return 0: 失败 1: 成功
     */
    public int GetClientRect(int hwnd,Variant x1,Variant y1,Variant x2,Variant y2){
        return Dispatch.call(dm, "GetClientRect", hwnd,x1,y1,x2,y2).getInt();
    }

    /**
     * 获取窗口客户区域的宽度和高度
     * @param hwnd 指定的窗口句柄
     * @param width 变参指针: 宽度 new Variant(0,true)
     * @param height 变参指针: 高度 new Variant(0,true)
     * @return 0: 失败 1: 成功
     */
    public int GetClientSize(int hwnd,Variant width,Variant height){
        return Dispatch.call(dm, "GetClientSize", hwnd,width,height).getInt();
    }

    /**
     * 获取给定窗口相关的窗口句柄
     * @param hwnd 窗口句柄
     * @param flag 取值定义如下<br/>
     * 0 : 获取父窗口<br/>
     * 1 : 获取第一个儿子窗口<br/>
     * 2 : 获取First 窗口<br/>
     * 3 : 获取Last窗口<br/>
     * 4 : 获取下一个窗口<br/>
     * 5 : 获取上一个窗口<br/>
     * 6 : 获取拥有者窗口<br/>
     * 7 : 获取顶层窗口<br/>
     * @return 返回整型表示的窗口句柄
     */
    public int GetWindow(int hwnd,int flag){
        return Dispatch.call(dm, "GetWindow",hwnd,flag).getInt();
    }

    /**
     * 获取指定窗口所在的进程ID.
     * @param hwnd 窗口句柄
     * @return 返回整型表示的是进程ID
     */
    public int GetWindowProcessId(int hwnd){
        return Dispatch.call(dm, "GetWindowProcessId",hwnd).getInt();
    }

    /**
     * 获取指定窗口所在的进程的exe文件全路径.
     * @param hwnd 窗口句柄
     * @return 返回字符串表示的是exe全路径名
     */
    public String GetWindowProcessPath(int hwnd){
        return Dispatch.call(dm,"GetWindowProcessPath",hwnd).getString();
    }

    /**
     * 获取窗口在屏幕上的位置
     * @param hwnd 指定的窗口句柄
     * @param x1 变参指针: 返回窗口左上角X坐标
     * @param y1 变参指针: 返回窗口左上角Y坐标
     * @param x2 变参指针: 返回窗口右下角X坐标
     * @param y2 变参指针: 返回窗口右下角Y坐标
     * @return 0: 失败 1: 成功
     */
    public int GetWindowRect(int hwnd,Variant x1,Variant y1,Variant x2, Variant y2){
        return Dispatch.call(dm,"GetWindowRect",hwnd,x1,y1,x2,y2).getInt();
    }

    /**
     * 获取指定窗口的一些属性
     * @param hwnd 指定的窗口句柄
     * @param flag 取值定义如下<br/>
     * 0 : 判断窗口是否存在<br/>
     * 1 : 判断窗口是否处于激活<br/>
     * 2 : 判断窗口是否可见<br/>
     * 3 : 判断窗口是否最小化<br/>
     * 4 : 判断窗口是否最大化<br/>
     * 5 : 判断窗口是否置顶<br/>
     * 6 : 判断窗口是否无响应<br/>
     * @return 0: 不满足条件 1: 满足条件
     */
    public int GetWindowState(int hwnd,int flag){
        return Dispatch.call(dm,"GetWindowState",hwnd,flag).getInt();
    }




    // --- 验证可用的
    /**
     * 获取窗口的标题
     * @param hwnd 指定的窗口句柄
     * @return 窗口的标题
     */
    public String GetWindowTitle(int hwnd){
        return Dispatch.call(dm,"GetWindowTitle",hwnd).getString();
    }

    /**
     * 获取窗口的类名
     * @param hwnd 指定的窗口句柄
     * @return 窗口的类名
     */
    public String GetWindowClass(int hwnd){
        return Dispatch.call(dm, "GetWindowClass",hwnd).getString();
    }

    /**
     * 移动指定窗口到指定位置
     * @param hwnd 指定的窗口句柄
     * @param x X坐标
     * @param y y坐标
     * @return 0: 失败 1:成功
     */
    public int MoveWindow(int hwnd,int x,int y){
        return Dispatch.call(dm,"MoveWindow",hwnd,x,y).getInt();
    }



// ***************************** 向指定窗口发送命令  ***************************

    /**
     * 向指定窗口发送粘贴命令. 把剪贴板的内容发送到目标窗口
     * @param hwnd 指定的窗口句柄
     * @return 0: 失败 1:成功
     */
    public int SendPaste(int hwnd){
        return Dispatch.call(dm,"SendPaste",hwnd).getInt();
    }

    /**
     * 向指定窗口发送文本数据
     * @param hwnd 指定的窗口句柄
     * @param str 发送的文本数据
     * @return 0: 失败 1:成功
     */
    public int SendString(int hwnd, String str){
        return Dispatch.call(dm,"SendString",hwnd,str).getInt();
    }

    /**
     * 向指定窗口发送文本数据<br/>
     * 注: 此接口为老的SendString，如果新的SendString不能输入，可以尝试此接口.
     * @param hwnd 指定的窗口句柄
     * @param str 发送的文本数据
     * @return 0: 失败 1:成功
     */
    public int SendString2(int hwnd, String str){
        return Dispatch.call(dm,"SendString2",hwnd,str).getInt();
    }


// ***************************** 设置状态的外观等属性 ***************************
    /**
     * 设置窗口客户区域的宽度和高度
     * @param hwnd 指定的窗口句柄
     * @param width 宽带
     * @param height 高度
     * @return 0: 失败 1:成功
     */
    public int SetClientSize(int hwnd,int width,int height){
        return Dispatch.call(dm,"SetClientSize",hwnd,width,height).getInt();
    }

    /**
     * 设置窗口的大小
     * @param hwnd 指定的窗口句柄
     * @param width 宽度
     * @param height 高度
     * @return 0: 失败 1:成功
     */
    public int SetWindowSize(int hwnd,int width,int height){
        return Dispatch.call(dm,"SetWindowSize",hwnd,width,height).getInt();
    }

    /**
     * 设置窗口的状态
     * @param hwnd 指定的窗口句柄
     * @param flag 取值定义如下<br/>
     * 0 : 关闭指定窗口<br/>
     * 1 : 激活指定窗口<br/>
     * 2 : 最小化指定窗口,但不激活<br/>
     * 3 : 最小化指定窗口,并释放内存,但同时也会激活窗口.<br/>
     * 4 : 最大化指定窗口,同时激活窗口.<br/>
     * 5 : 恢复指定窗口 ,但不激活<br/>
     * 6 : 隐藏指定窗口<br/>
     * 7 : 显示指定窗口<br/>
     * 8 : 置顶指定窗口<br/>
     * 9 : 取消置顶指定窗口<br/>
     * 10 : 禁止指定窗口<br/>
     * 11 : 取消禁止指定窗口<br/>
     * 12 : 恢复并激活指定窗口<br/>
     * 13 : 强制结束窗口所在进程.<br/>
     * @return 0: 失败 1:成功
     */
    public int SetWindowState(int hwnd,int flag){
        return Dispatch.call(dm,"SetWindowState",hwnd,flag).getInt();
    }

    /**
     * 设置窗口的标题
     * @param hwnd 指定的窗口句柄
     * @param title 标题
     * @return 0: 失败 1:成功
     */
    public int SetWindowText(int hwnd,String title){
        return Dispatch.call(dm,"SetWindowText",hwnd,title).getInt();
    }

    /**
     * 设置窗口的透明度
     * @param hwnd 指定的窗口句柄
     * @param trans 透明度取值(0-255) 越小透明度越大 0为完全透明(不可见) 255为完全显示(不透明)
     * @return 0: 失败 1:成功
     */
    public int SetWindowTransparent(int hwnd,int trans){
        return Dispatch.call(dm,"SetWindowTransparent",hwnd,trans).getInt();
    }
}
