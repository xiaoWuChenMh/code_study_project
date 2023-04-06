package com.future.small_toolbox.damo;

import com.jacob.com.Variant;
import study.damo.cankao.DmSoft;

/**
 * @Description
 * @Author xiaowuchen
 * @Date 2020/9/22 16:33
 */
public class DMTest {

    public static void main(String[] args) {

        DmSoft dm = new DmSoft();
        System.out.println("version:" + dm.baseSetOper.Ver());
        System.out.println("path:"+dm.baseSetOper.GetBasePath());

        //获取窗口
        int d = dm.windowOper.GetForegroundFocus();
        String title = dm.windowOper.GetWindowTitle(d);
        Variant windwo_lelft_x = new Variant(0,true);
        System.out.println(title);

    }
}
