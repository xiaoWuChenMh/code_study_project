package com.future.small_toolbox.damo.Features;

import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.Dispatch;

/**
 * @Description 基本设置
 * @Author xiaowuchen
 * @Date 2020/9/23 10:40
 */
public class BaseSetOper {

    private ActiveXComponent dm;

    public BaseSetOper(ActiveXComponent dm){
        this.dm=dm;
    }

    /**
     * 获取注册在系统中的dm.dll的路径.
     *
     * @return 字符串:返回dm.dll所在路径
     */
    public String GetBasePath() {
        return Dispatch.call(dm, "GetBasePath").getString();
    }

    /**
     * 返回当前大漠对象的ID值，这个值对于每个对象是唯一存在的。可以用来判定两个大漠对象是否一致.
     * @return 当前对象的ID值
     */
    public long GetID(){
        return Dispatch.call(dm, "GetID").getLong();
    }

    /**
     * 获取插件命令的最后错误
     * @return 返回值表示错误值。 0表示无错误.<br/>
     *	-1 : 表示你使用了绑定里的收费功能，但是没注册，无法使用.<br/>
     *	-2 : 使用模式0 2 4 6时出现，因为目标窗口有保护，或者目标窗口没有以管理员权限打开. 常见于win7以上系统.或者有安全软件拦截插件.解决办法: 关闭所有安全软件，并且关闭系统UAC,然后再重新尝试. 如果还不行就可以肯定是目标窗口有特殊保护. <br/>
     *	-3 : 使用模式0 2 4 6时出现，可能目标窗口有保护，也可能是异常错误.<br/>
     *	-4 : 使用模式1 3 5 7 101 103时出现，这是异常错误.<br/>
     *	-5 : 使用模式1 3 5 7 101 103时出现, 这个错误的解决办法就是关闭目标窗口，重新打开再绑定即可. 也可能是运行脚本的进程没有管理员权限. <br/>
     *	-6 -7 -9 : 使用模式1 3 5 7 101 103时出现,异常错误. 还有可能是安全软件的问题，比如360等。尝试卸载360.<br/>
     *	-8 -10 : 使用模式1 3 5 7 101 103时出现, 目标进程可能有保护,也可能是插件版本过老，试试新的或许可以解决.<br/>
     *	-11 : 使用模式1 3 5 7 101 103时出现, 目标进程有保护. 告诉我解决。<br/>
     *	-12 : 使用模式1 3 5 7 101 103时出现, 目标进程有保护. 告诉我解决。<br/>
     *	-13 : 使用模式1 3 5 7 101 103时出现, 目标进程有保护. 或者是因为上次的绑定没有解绑导致。 尝试在绑定前调用ForceUnBindWindow.<br/>
     *	-14 : 使用模式0 1 4 5时出现, 有可能目标机器兼容性不太好. 可以尝试其他模式. 比如2 3 6 7<br/>
     *	-16 : 可能使用了绑定模式 0 1 2 3 和 101，然后可能指定了一个子窗口.导致不支持.可以换模式4 5 6 7或者103来尝试. 另外也可以考虑使用父窗口或者顶级窗口.来避免这个错误。还有可能是目标窗口没有正常解绑 然后再次绑定的时候.<br/>
     *	-17 : 模式1 3 5 7 101 103时出现. 这个是异常错误. 告诉我解决.<br/>
     *	-18 : 句柄无效.<br/>
     *	-19 : 使用模式0 1 2 3 101时出现,说明你的系统不支持这几个模式. 可以尝试其他模式.<br/>
     */
    public long GetLastError(){
        return Dispatch.call(dm, "GetLastError").getLong();
    }

    /**
     * 获取全局路径.(可用于调试)
     * @return 以字符串的形式返回当前设置的全局路径
     */
    public String GetPath(){
        return Dispatch.call(dm, "GetPath").getString();
    }

    /**
     * 设定图色的获取方式，默认是显示器或者后台窗口(具体参考BindWindow)
     *
     * @param mode
     *            字符串: 图色输入模式取值有以下几种<br/>
     *            1. "screen" 这个是默认的模式，表示使用显示器或者后台窗口<br/>
     *            2. "pic:file" 指定输入模式为指定的图片,如果使用了这个模式，则所有和图色相关的函数<br/>
     *            均视为对此图片进行处理，比如文字识别查找图片 颜色 等等一切图色函数.<br/>
     *            需要注意的是，设定以后，此图片就已经加入了缓冲，如果更改了源图片内容，那么需要<br/>
     *            释放此缓冲，重新设置.<br/>
     * @return 0: 失败 1: 成功
     */
    public long SetDisplayInput(String mode){
        return Dispatch.call(dm, "SetDisplayInput", mode).getLong();
    }

    /**
     * 设置全局路径,设置了此路径后,所有接口调用中,相关的文件都相对于此路径. 比如图片,字库等.
     * @param path 路径,可以是相对路径,也可以是绝对路径
     * @return 0: 失败 1: 成功
     */
    public int SetPath(String path){
        return Dispatch.call(dm, "SetPath", path).getInt();
    }

    /**
     * 设置是否弹出错误信息,默认是打开.
     * @param show 0表示不打开,1表示打开
     * @return 0: 失败 1: 成功
     */
    public long SetShowErrorMsg (int show){
        return Dispatch.call(dm, "SetPath", show).getLong();
    }

    /**
     * 返回当前插件版本号
     *
     * @return 当前插件的版本描述字符串
     */
    public String Ver() {
        return Dispatch.call(dm, "Ver").getString();
    }

}
