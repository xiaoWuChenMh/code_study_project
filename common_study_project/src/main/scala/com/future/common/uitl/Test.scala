package com.future.common.uitl


/**
  * @Description
  * @Author v_hhuima
  * @Date 2023/10/20 17:16
  */
class TestTwo {

  def size: Long = _size
  // 修改：数据块大小
  def size_=(s: Long): Unit = {
    _size = s
  }
  // 表示数据块的大小的私有变量
  private[this] var _size: Long = 0

}

object  TestOne {

  def main(args: Array[String]): Unit = {
    val testBa = new TestTwo()
    testBa.size=3
    testBa.size=6
    println(testBa.size)
  }
}
