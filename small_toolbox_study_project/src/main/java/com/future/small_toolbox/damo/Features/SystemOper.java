package com.future.small_toolbox.damo.Features;

import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.Dispatch;

/**
 * @Description 系统操作
 *
 * @Author xiaowuchen
 * @Date 2020/9/23 10:47
 */
public class SystemOper {

    private ActiveXComponent dm;

    public SystemOper(ActiveXComponent dm){
        this.dm=dm;
    }

    /**
     * 蜂鸣器.
     * @param f 频率
     * @param duration 时长(ms).
     * @return 0: 失败 1: 成功
     */
    public int Beep(int f,int duration){
        return Dispatch.call(dm,"Beep",f,duration).getInt();
    }

    /**
     * 检测当前系统是否有开启UAC(用户账户控制).<br/>
     * 注: 只有WIN7 VISTA WIN2008以及以上系统才有UAC设置
     * @return 0 : 没开启UAC 1 : 开启了UAC
     */
    public int CheckUAC(){
        return Dispatch.call(dm,"CheckUAC").getInt();
    }

    /**
     * 关闭电源管理，不会进入睡眠.<br/>
     * 注 :此函数调用以后，并不会更改系统电源设置.<br/>
     * 此函数经常用在后台操作过程中. 避免被系统干扰
     * @return 0: 失败 1: 成功
     */
    public int DisablePowerSave(){
        return Dispatch.call(dm,"DisablePowerSave").getInt();
    }

    /**
     * 关闭屏幕保护<br/>
     * 注 : 调用此函数后，可能在系统中还是看到屏保是开启状态。但实际上屏保已经失效了.<br/>
     * 系统重启后，会失效。必须再重新调用一次.<br/>
     * 此函数经常用在后台操作过程中. 避免被系统干扰.
     * @return 0: 失败 1: 成功
     */
    public int DisableScreenSave(){
        return Dispatch.call(dm,"DisableScreenSave").getInt();
    }

    /**
     * 退出系统(注销 重启 关机)
     * @param type 取值为以下类型<br/>
     * 0 : 注销系统
     * 1 : 关机
     * 2 : 重新启动
     * @return 0:失败,1:成功
     */
    public int ExitOs(int type){
        return Dispatch.call(dm,"ExitOs",type).getInt();
    }

    /**
     * 获取剪贴板的内容
     * @return 以字符串表示的剪贴板内容
     */
    public String GetClipboard(){
        return Dispatch.call(dm,"GetClipboard").getString();
    }

    /**
     * 得到系统的路径
     * @param type 得到系统的路径 <br/>
     * 0 : 获取当前路径<br/>
     * 1 : 获取系统路径(system32路径)<br/>
     * 2 : 获取windows路径(windows所在路径)<br/>
     * 3 : 获取临时目录路径(temp)<br/>
     * 4 : 获取当前进程(exe)所在的路径<br/>
     * @return 返回路径
     */
    public String GetDir(int type){
        return Dispatch.call(dm,"GetDir",type).getString();
    }

    /**
     * 获取本机的硬盘序列号.支持ide scsi硬盘. 要求调用进程必须有管理员权限. 否则返回空串.
     * @return 字符串表达的硬盘序列号
     */
    public String GetDiskSerial(){
        return Dispatch.call(dm,"GetDiskSerial").getString();
    }

    /**
     * 获取本机的机器码.(带网卡). 此机器码用于插件网站后台. 要求调用进程必须有管理员权限. 否则返回空串.
     * @return 字符串表达的机器机器码
     */
    public String GetMachineCode(){
        return Dispatch.call(dm,"GetMachineCode").getString();
    }

    /**
     * 获取本机的机器码.(不带网卡) 要求调用进程必须有管理员权限. 否则返回空串
     * @return 字符串表达的机器机器码
     */
    public String GetMachineCodeNoMac(){
        return Dispatch.call(dm,"GetMachineCodeNoMac").getString();
    }

    /**
     * 从网络获取当前北京时间
     * @return 时间格式. 和now返回一致. 比如"2001-11-01 23:14:08"
     */
    public String GetNetTime(){
        return Dispatch.call(dm,"GetNetTime").getString();
    }

    /**
     * 得到操作系统的类型
     * @return 0 : win95/98/me/nt4.0 <br/>
     * 1 : xp/2000<br/>
     * 2 : 2003<br/>
     * 3 : win7/vista/2008
     */
    public int GetOsType(){
        return Dispatch.call(dm,"GetOsType").getInt();
    }

    /**
     * 获取屏幕的色深.
     * @return 返回系统颜色深度.(16或者32等)
     */
    public int GetScreenDepth(){
        return Dispatch.call(dm,"GetScreenDepth").getInt();
    }

    /**
     * 获取屏幕的高度.
     * @return 返回屏幕的高度
     */
    public int GetScreenHeight(){
        return Dispatch.call(dm,"GetScreenHeight").getInt();
    }

    /**
     * 获取屏幕的宽度.
     * @return 返回屏幕的宽度
     */
    public int GetScreenWidth(){
        return Dispatch.call(dm,"GetScreenWidth").getInt();
    }

    /**
     * 获取当前系统从开机到现在所经历过的时间，单位是毫秒
     * @return 时间(单位毫秒)
     */
    public int GetTime(){
        return Dispatch.call(dm,"GetTime").getInt();
    }

    /**
     * 判断当前系统是否是64位操作系统
     * @return 0 : 不是64位系统 1 : 是64位系统
     */
    public int Is64Bit(){
        return Dispatch.call(dm,"Is64Bit").getInt();
    }

    /**
     * 播放指定的MP3或者wav文件.
     * @param media_file 指定的音乐文件，可以采用文件名或者绝对路径的形式.
     * @return 0 : 失败,非0表示当前播放的ID。可以用Stop来控制播放结束.
     */
    public int Play(String media_file){
        return Dispatch.call(dm,"Play",media_file).getInt();
    }

    /**
     * 设置剪贴板的内容
     * @param value 以字符串表示的剪贴板内容
     * @return 0: 失败 1: 成功
     */
    public int SetClipboard(String value){
        return Dispatch.call(dm,"SetClipboard",value).getInt();
    }

    /**
     * 设置系统的分辨率 系统色深
     * @param width 屏幕宽度
     * @param height 屏幕高度
     * @param depth 系统色深
     * @return 0: 失败 1: 成功
     */
    public int SetScreen(int width,int height,int depth){
        return Dispatch.call(dm,"SetScreen",width,height,depth).getInt();
    }

    /**
     * 设置当前系统的UAC(用户账户控制).
     * @param enable 0 : 关闭UAC 1 : 开启UAC
     * @return 0: 操作失败 1: 操作成功
     */
    public int SetUAC(int enable){
        return Dispatch.call(dm,"SetUAC",enable).getInt();
    }

    /**
     * 停止指定的音乐.
     * @param id Play返回的播放id.
     * @return 0: 失败 1: 成功
     */
    public int Stop(int id){
        return Dispatch.call(dm,"Stop",id).getInt();
    }

}
