package com.future.small_toolbox.damo.Features;

import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.Dispatch;

/**
 * @Description 汇编操作
 * @Author xiaowuchen
 * @Date 2020/9/22 18:26
 */
public class CompileOper {

    private ActiveXComponent dm;

    public CompileOper(ActiveXComponent dm){
        this.dm=dm;
    }

    /**
     * 添加指定的MASM汇编指令
     * @param asm_ins MASM汇编指令,大小写均可以  比如 "mov eax,1"
     * @return 0:失败  1:成功
     */
    public int AsmAdd(String asm_ins){
        return Dispatch.call(dm, "AsmAdd", asm_ins).getInt();
    }

    /**
     * 清除汇编指令缓冲区 用AsmAdd添加到缓冲的指令全部清除
     * @return 0:失败  1:成功
     */
    public int AsmClear(){
        return Dispatch.call(dm, "AsmClear").getInt();
    }

    /**
     * 把汇编缓冲区的指令转换为机器码 并用16进制字符串的形式输出
     * @param base_addr 用AsmAdd添加到缓冲区的第一条指令所在的地址
     * @return 机器码，比如 "aa bb cc"这样的形式
     */
    public String AsmCode(String base_addr){
        return Dispatch.call(dm, "AsmCode",base_addr).getString();
    }

    /**
     * 把指定的机器码转换为汇编语言输出(未测试过)
     * @param asm_code 机器码，形式如 "aa bb cc"这样的16进制表示的字符串(空格无所谓)
     * @param base_addr 指令所在的地址
     * @param is_upper 表示转换的汇编语言是否以大写输出
     * @return MASM汇编语言字符串
     */
    public String Assemble(String asm_code,int base_addr,int is_upper){
        return Dispatch.call(dm, "Assemble",asm_code,base_addr,is_upper).getString();
    }

}
