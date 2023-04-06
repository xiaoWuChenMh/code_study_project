package com.future.small_toolbox.damo.Features;

import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.Dispatch;

/**
 * @Description 后台操作
 * @Author xiaowuchen
 * @Date 2020/9/22 18:25
 */
public class BackstageOper {

    private ActiveXComponent dm;

    public BackstageOper(ActiveXComponent dm){
        this.dm=dm;
    }


    /**
     * 绑定指定的窗口,并指定这个窗口的屏幕颜色获取方式,鼠标仿真模式,键盘仿真模式,以及模式设定,高级用户可以参考BindWindowEx更加灵活强大.
     * @param hwnd 指定的窗口句柄
     * @param display 屏幕颜色获取方式 取值有以下几种<br/>
     * "normal" : 正常模式,平常我们用的前台截屏模式<br/>
     * "gdi" : gdi模式,用于窗口采用GDI方式刷新时. 此模式占用CPU较大.<br/>
     * "gdi2" : gdi2模式,此模式兼容性较强,但是速度比gdi模式要慢许多,如果gdi模式发现后台不刷新时,可以考虑用gdi2模式.<br/>
     * "dx2" : dx2模式,用于窗口采用dx模式刷新,如果dx方式会出现窗口所在进程崩溃的状况,可以考虑采用这种.采用这种方式要保证窗口有一部分在屏幕外.win7或者vista不需要移动也可后台.此模式占用CPU较大.<br/>
     * "dx3" : dx3模式,同dx2模式,但是如果发现有些窗口后台不刷新时,可以考虑用dx3模式,此模式比dx2模式慢许多. 此模式占用CPU较大.<br/>
     * "dx" : dx模式,等同于BindWindowEx中，display设置的"dx.graphic.2d|dx.graphic.3d",具体参考BindWindowEx<br/>
     * 注意此模式需要管理员权限<br/>
     * @param mouse 鼠标仿真模式 取值有以下几种
     * "normal" : 正常模式,平常我们用的前台鼠标模式<br/>
     * "windows": Windows模式,采取模拟windows消息方式 同按键自带后台插件.<br/>
     * "windows2": Windows2 模式,采取模拟windows消息方式(锁定鼠标位置) 此模式等同于BindWindowEx中的mouse为以下组合<br/>
     * "dx.mouse.position.lock.api|dx.mouse.position.lock.message|dx.mouse.state.message"<br/>
     * 注意此模式需要管理员权限<br/>
     * "windows3": Windows3模式，采取模拟windows消息方式,可以支持有多个子窗口的窗口后台.<br/>
     * "dx": dx模式,采用模拟dx后台鼠标模式,这种方式会锁定鼠标输入.有些窗口在此模式下绑定时，需要先激活窗口再绑定(或者绑定以后激活)，否则可能会出现绑定后鼠标无效的情况.此模式等同于BindWindowEx中的mouse为以下组合<br/>
     * "dx.public.active.api|dx.public.active.message|dx.mouse.position.lock.api|dx.mouse.position.lock.message|dx.mouse.state.api|dx.mouse.state.message|dx.mouse.api|dx.mouse.focus.input.api|dx.mouse.focus.input.message|dx.mouse.clip.lock.api|dx.mouse.input.lock.api|dx.mouse.cursor"<br/>
     * 注意此模式需要管理员权限<br/>
     * "dx2"：dx2模式,这种方式类似于dx模式,但是不会锁定外部鼠标输入.<br/>
     * 有些窗口在此模式下绑定时，需要先激活窗口再绑定(或者绑定以后手动激活)，否则可能会出现绑定后鼠标无效的情况. 此模式等同于BindWindowEx中的mouse为以下组合<br/>
     * "dx.public.active.api|dx.public.active.message|dx.mouse.position.lock.api|dx.mouse.state.api|dx.mouse.api|dx.mouse.focus.input.api|dx.mouse.focus.input.message|dx.mouse.clip.lock.api|dx.mouse.input.lock.api| dx.mouse.cursor"<br/>
     * 注意此模式需要管理员权限<br/>
     * @param keypad 键盘仿真模式 取值有以下几种<br/>
     * "normal" : 正常模式,平常我们用的前台键盘模式<br/>
     * "windows": Windows模式,采取模拟windows消息方式 同按键的后台插件.<br/>
     * "dx": dx模式,采用模拟dx后台键盘模式。有些窗口在此模式下绑定时，需要先激活窗口再绑定(或者绑定以后激活)，否则可能会出现绑定后键盘无效的情况. 此模式等同于BindWindowEx中的keypad为以下组合<br/>
     * "dx.public.active.api|dx.public.active.message| dx.keypad.state.api|dx.keypad.api|dx.keypad.input.lock.api"<br/>
     * 注意此模式需要管理员权限<br/>
     * @param mode 模式。 取值有以下两种<br/>
     * 0 : 推荐模式此模式比较通用，而且后台效果是最好的.<br/>
     * @return 0: 失败 1:成功
     */
    public int BindWindow(int hwnd,String display,String mouse,String keypad,int mode){
        return Dispatch.call(dm, "BindWindow", hwnd,display,mouse,keypad,mode).getInt();
    }

    /**
     * 更详细的说明参考大漠接口说明
     * 绑定指定的窗口,并指定这个窗口的屏幕颜色获取方式,鼠标仿真模式,键盘仿真模式 高级用户使用.
     * @param hwnd 指定的窗口句柄
     * @param display 屏幕颜色获取方式
     * @param mouse 鼠标仿真模式
     * @param keypad 键盘仿真模式
     * @param pub 公共属性
     * @param mode 模式
     * @return 0: 失败 1:成功
     */
    public int BindWindowEx(int hwnd,String display,String mouse,String keypad,String pub,int mode){
        return Dispatch.call(dm,"BindWindowEx",hwnd,display,mouse,keypad,pub,mode).getInt();
    }

    /**
     * 降低目标窗口所在进程的CPU占用<br/>
     * 注意: 此接口必须在绑定窗口成功以后调用，而且必须保证目标窗口可以支持dx.graphic.3d或者dx.graphic.3d.8或者dx.graphic.2d或者dx.graphic.2d.2方式截图，否则降低CPU无效.<br/>
     * 因为降低CPU是通过降低窗口刷新速度来实现，所以注意，开启此功能以后会导致窗口刷新速度变慢.<br/>
     * @param rate 取值范围0到100   取值为0 表示关闭CPU优化. 这个值越大表示降低CPU效果越好.<br/>
     * @return 0: 失败 1:成功
     */
    public int DownCpu(int rate){
        return Dispatch.call(dm,"DownCpu",rate).getInt();
    }

    /**
     * 设置是否关闭绑定窗口所在进程的输入法.
     * @param enable 1 开启 0 关闭
     * @return 0: 失败 1:成功
     */
    public int EnableIme(int enable){
        return Dispatch.call(dm,"EnableIme",enable).getInt();
    }
    /**
     * 设置是否开启高速dx键鼠模式。 默认是关闭.
     * @param enable 1 开启 0 关闭
     * @return 0: 失败 1:成功
     */
    public int EnableSpeedDx(int enable){
        return Dispatch.call(dm,"EnableSpeedDx",enable).getInt();
    }

    /**
     * 禁止外部输入到指定窗口
     * @param lock <br/>
     *  0关闭锁定<br/>
     *  1 开启锁定(键盘鼠标都锁定)<br/>
     *  2 只锁定鼠标<br/>
     *  3 只锁定键盘<br/>
     * @return 0: 失败 1:成功
     */
    public int LockInput(int lock){
        return Dispatch.call(dm,"LockInput",lock).getInt();
    }

    /**
     * 设置前台鼠标在屏幕上的活动范围
     * @param x1 区域的左上X坐标. 屏幕坐标
     * @param y1 区域的左上Y坐标. 屏幕坐标
     * @param x2 区域的右下X坐标. 屏幕坐标
     * @param y2 区域的右下Y坐标. 屏幕坐标
     * @return 0: 失败 1:成功<br/>
     * 注: 调用此函数后，一旦有窗口切换或者窗口移动的动作，那么限制立刻失效.<br/>
     * 如果想一直限制鼠标范围在指定的窗口客户区域，那么你需要启动一个线程，并且时刻监视当前活动窗口，然后根据情况调用此函数限制鼠标范围.
     */
    public int LockMouseRect(int x1,int y1,int x2,int y2){
        return Dispatch.call(dm,"LockMouseRect",x1,y1,x2,y2).getInt();
    }
    /**
     * 设置dx截图最长等待时间。内部默认是3000毫秒. 一般用不到调整这个
     * @param time 等待时间，单位是毫秒。 注意这里不能设置的过小，否则可能会导致截图失败,从而导致图色函数和文字识别失败
     * @return 0:失败  1:成功
     */
    public int SetDisplayDelay(int time){
        return Dispatch.call(dm,"SetDisplayDelay",time).getInt();
    }

    /**
     * 解除绑定窗口,并释放系统资源.一般在OnScriptExit调用
     * @return 0: 失败 1:成功
     */
    public int UnBindWindow(){
        return Dispatch.call(dm, "UnBindWindow").getInt();
    }
}
