package com.future.small_toolbox.damo.Features;

import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.Dispatch;

/**
 * @Description 算法
 * @Author xiaowuchen
 * @Date 2020/9/23 10:43
 */
public class AlgorithmOper {

    private ActiveXComponent dm;

    public AlgorithmOper(ActiveXComponent dm){
        this.dm=dm;
    }

    /**
     * 根据部分Ex接口的返回值，排除指定范围区域内的坐标.
     * @param all_pos 坐标描述串,一般是FindStrEx,FindStrFastEx,FindStrWithFontEx,FindColorEx, FindMultiColorEx,和FindPicEx的返回值.
     * @param type 取值为0或者1, 如果all_pos的内容是由FindPicEx,FindStrEx,FindStrFastEx,FindStrWithFontEx返回，那么取值为0,如果all_pos的内容是由FindColorEx, FindMultiColorEx返回，那么取值为1
     * @param x1 左上角横坐标
     * @param y1 左上角纵坐标
     * @param x2 右下角横坐标
     * @param y2 右下角纵坐标
     * @return 经过筛选以后的返回值，格式和type指定的一致.
     */
    public String ExcludePos(String all_pos,int type,int x1,int y1,int x2,int y2 ){
        return Dispatch.call(dm,"ExcludePos",all_pos,type,x1, y1, x2, y2).getString();
    }

    /**
     * 根据部分Ex接口的返回值，然后在所有坐标里找出距离指定坐标最近的那个坐标
     * @param all_pos 坐标描述串。  一般是FindStrEx,FindStrFastEx,FindStrWithFontEx, FindColorEx, FindMultiColorEx,和FindPicEx的返回值.
     * @param type 取值为0或者1,如果all_pos的内容是由FindPicEx,FindStrEx,FindStrFastEx,FindStrWithFontEx返回，那么取值为0,如果all_pos的内容是由FindColorEx, FindMultiColorEx返回，那么取值为1
     * @param x 横坐标
     * @param y 纵坐标
     * @return 返回的格式和type有关，如果type为0，那么返回的格式是"id,x,y";如果type为1,那么返回的格式是"x,y".
     */
    public String FindNearestPos(String all_pos,int type,int x,int y){
        return Dispatch.call(dm,"FindNearestPos",all_pos,type,x,y).getString();
    }

    /**
     * 根据部分Ex接口的返回值，然后对所有坐标根据对指定坐标的距离进行从小到大的排序.
     * @param all_pos  坐标描述串。  一般是FindStrEx,FindStrFastEx,FindStrWithFontEx, FindColorEx, FindMultiColorEx,和FindPicEx的返回值.
     * @param type 取值为0或者1 如果all_pos的内容是由FindPicEx,FindStrEx,FindStrFastEx,FindStrWithFontEx返回，那么取值为0,如果all_pos的内容是由FindColorEx, FindMultiColorEx返回，那么取值为1
     * @param x 横坐标
     * @param y 纵坐标
     * @return 返回的格式和type指定的格式一致.
     */
    public String SortPosDistance(String all_pos,int type,int x,int y){
        return Dispatch.call(dm,"SortPosDistance",all_pos,type,x,y).getString();
    }
}
