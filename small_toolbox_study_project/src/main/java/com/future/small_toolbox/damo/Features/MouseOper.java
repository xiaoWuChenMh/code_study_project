package com.future.small_toolbox.damo.Features;

import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.Dispatch;
import com.jacob.com.Variant;

/**
 * @Description 键鼠
 * @Author xiaowuchen
 * @Date 2020/9/23 10:41
 */
public class MouseOper {

    private ActiveXComponent dm;

    public MouseOper(ActiveXComponent dm){
        this.dm=dm;
    }

    /**
     * 获取鼠标位置<br/>
     * 注: 此接口在3.1223版本之后，返回的值的定义修改。  同大多数接口一样,返回的x,y坐标是根据绑定的鼠标参数来决定.  如果绑定了窗口，那么获取的坐标是相对于绑定窗口，否则是屏幕坐标.
     * @param x 变参指针: 返回X坐标
     * @param y 变参指针: 返回Y坐标
     * @return 0: 失败 1:成功
     */
    public int GetCursorPos(Variant x, Variant y){
        return Dispatch.call(dm,"GetCursorPos",x,y).getInt();
    }

    /**
     * 获取指定的按键状态.(前台信息,不是后台)
     * @param vk_code 虚拟按键码
     * @return 0: 失败 1:成功
     */
    public int GetKeyState(int vk_code){
        return Dispatch.call(dm,"GetKeyState",vk_code).getInt();
    }

    /**
     * 按住指定的虚拟键码
     * @param vk_code 虚拟按键码
     * @return 0: 失败 1:成功
     */
    public int KeyDown(int vk_code){
        return Dispatch.call(dm,"KeyDown",vk_code).getInt();
    }

    /**
     * 按住指定的虚拟键码
     * @param key_str 字符串描述的键码. 大小写无所谓
     * @return 0: 失败 1:成功
     */
    public int KeyDownChar(String key_str){
        return Dispatch.call(dm,"KeyDownChar",key_str).getInt();
    }

    /**
     * 按下指定的虚拟键码
     * @param vk_code 虚拟按键码
     * @return 0: 失败 1:成功
     */
    public int KeyPress(int vk_code){
        return Dispatch.call(dm,"KeyPress",vk_code).getInt();
    }

    /**
     * 按下指定的虚拟键码
     * @param key_str 字符串描述的键码. 大小写无所谓
     * @return 0: 失败 1:成功
     */
    public int KeyPressChar(String key_str){
        return Dispatch.call(dm,"KeyPressChar",key_str).getInt();
    }

    /**
     * 弹起来虚拟键vk_code
     * @param vk_code 虚拟按键码
     * @return 0: 失败 1:成功
     */
    public int KeyUp(int vk_code){
        return Dispatch.call(dm,"KeyUp",vk_code).getInt();
    }

    /**
     * 弹起来虚拟键key_str
     * @param key_str 字符串描述的键码. 大小写无所谓.
     * @return 0: 失败 1:成功
     */
    public int KeyUpChar(String key_str){
        return Dispatch.call(dm,"KeyUpChar",key_str).getInt();
    }

    /**
     * 按下鼠标左键
     * @return 0: 失败 1:成功
     */
    public int  LeftClick(){
        return Dispatch.call(dm,"LeftClick").getInt();
    }

    /**
     * 双击鼠标左键
     * @return 0: 失败 1:成功
     */
    public int  LeftDoubleClick(){
        return Dispatch.call(dm,"LeftDoubleClick").getInt();
    }

    /**
     * 按住鼠标左键
     * @return 0: 失败 1:成功
     */
    public int  LeftDown(){
        return Dispatch.call(dm,"LeftDown").getInt();
    }

    /**
     * 弹起鼠标左键
     * @return 0: 失败 1:成功
     */
    public int  LeftUp(){
        return Dispatch.call(dm,"LeftUp").getInt();
    }

    /**
     * 按下鼠标中键
     * @return 0: 失败 1:成功
     */
    public int  MiddleClick(){
        return Dispatch.call(dm,"MiddleClick").getInt();
    }

    /**
     * 鼠标相对于上次的位置移动rx,ry
     * @param rx 相对于上次的X偏移
     * @param ry 相对于上次的Y偏移
     * @return 0: 失败 1:成功
     */
    public int MoveR(int rx,int ry){
        return Dispatch.call(dm,"MoveR",rx,ry).getInt();
    }

    /**
     * 把鼠标移动到目的点(x,y)
     * @param x X坐标
     * @param y Y坐标
     * @return 0: 失败 1:成功
     */
    public int MoveTo(int x,int y){
        return Dispatch.call(dm,"MoveTo",x,y).getInt();
    }

    /**
     * 延迟时间
     * @param millis
     * @throws InterruptedException
     */
    public void delay(long millis){
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 把鼠标移动到目的范围内的任意一点<br/>
     * 注: 此函数的意思是移动鼠标到指定的范围(x,y,x+w,y+h)内的任意随机一点.
     * @param x X坐标
     * @param y Y坐标
     * @param w 宽度(从x计算起)
     * @param h 高度(从y计算起)
     * @return 返回要移动到的目标点. 格式为x,y.  比如MoveToEx 100,100,10,10,返回值可能是101,102
     */
    public String MoveToEx(int x,int y,int w,int h){
        return Dispatch.call(dm,"MoveToEx",x,y,w,h).getString();
    }

    /**
     * 按下鼠标右键
     * @return 0: 失败 1:成功
     */
    public int  RightClick(){
        return Dispatch.call(dm,"RightClick").getInt();
    }

    /**
     * 按按住鼠标右键
     * @return 0: 失败 1:成功
     */
    public int  RightDown(){
        return Dispatch.call(dm,"RightDown").getInt();
    }

    /**
     * 弹起鼠标右键
     * @return 0: 失败 1:成功
     */
    public int  RightUp(){
        return Dispatch.call(dm,"RightUp").getInt();
    }

    /**
     * 设置按键时,键盘按下和弹起的时间间隔。高级用户使用。某些窗口可能需要调整这个参数才可以正常按键。<br/>
     * 注 : 此函数影响的接口有KeyPress
     * @param type 键盘类型,取值有以下<br/>
     * "normal" : 对应normal键盘  默认内部延时为30ms<br/>
     * "windows": 对应windows 键盘 默认内部延时为10ms<br/>
     * "dx" :     对应dx 键盘 默认内部延时为50ms<br/>
     * @param delay 延时,单位是毫秒
     * @return 0: 失败 1:成功
     */
    public int SetKeypadDelay(String type,int delay){
        return Dispatch.call(dm,"SetKeypadDelay",type,delay).getInt();
    }

    /**
     * 设置鼠标单击或者双击时,鼠标按下和弹起的时间间隔。高级用户使用。某些窗口可能需要调整这个参数才可以正常点击。<br/>
     * 注 : 此函数影响的接口有LeftClick RightClick MiddleClick LeftDoubleClick
     * @param type 鼠标类型,取值有以下<br/>
     * "normal" : 对应normal鼠标 默认内部延时为 30ms<br/>
     * "windows": 对应windows 鼠标 默认内部延时为 10ms<br/>
     *  "dx" :     对应dx鼠标 默认内部延时为40ms<br/>
     * @param delay 延时,单位是毫秒
     * @return 0: 失败 1:成功
     */
    public int SetMouseDelay(String type,int delay){
        return Dispatch.call(dm,"SetMouseDelay",type,delay).getInt();
    }

    /**
     * 等待指定的按键按下 (前台,不是后台)
     * @param vk_code 虚拟按键码
     * @param time_out 等待多久,单位毫秒. 如果是0，表示一直等待
     * @return 0:超时 1:指定的按键按下
     */
    public int WaitKey(int vk_code,int time_out){
        return Dispatch.call(dm,"WaitKey",vk_code,time_out).getInt();
    }

    /**
     * 滚轮向下滚
     * @return 0: 失败 1:成功
     */
    public int WheelDown(){
        return Dispatch.call(dm,"WheelDown").getInt();
    }

    /**
     * 滚轮向上滚
     * @return 0: 失败 1:成功
     */
    public int WheelUp(){
        return Dispatch.call(dm,"WheelUp").getInt();
    }

    /**
     * 脚本执行到这一句暂停,按下任意鼠标键之后继续
     * @return 整数型，鼠标码
     */
    public int WaitClick(){
        return 0;
    }
}
