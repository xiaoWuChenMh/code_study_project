<template>
    <div id="app">
        <h2>------------ 获取状态信息-------------------</h2>
        <!-- 直接引用状态对象（$store.state.变量名） -->
        <h2>{{$store.state.count}}}</h2>

        <h2>------------ mutations相关信息-------------------</h2>
        <button @click="increment">+</button>
        <button @click="decrement">-</button>
        <button @click="addCount(5)">+5</button>
        <button @click="addCountStyle(10)">+10</button>

        <h2>------------ 通过mutations实现删除和添加状态信息-------------------</h2>
        <button @click="addState">添加状态</button>
        <button @click="deleteState">删除状态</button>

        <h2>------------ getters相关信息-------------------</h2>
        <h2>{{$store.getters.powerCounter}}}</h2>
        <h2>{{$store.getters.powerCounter}}</h2>
        <h2>{{$store.getters.more20stu}}</h2>
        <h2>{{$store.getters.more20stuLength}}</h2>
        <h2>{{$store.getters.moreAgeStu(12)}}</h2>

       <h2>------------ actions相关信息-------------------</h2>
        <button @click="addState">添加状态</button>
    </div>
</template>
<script>
    // 导入mutaions对象中的方法名
    import {
      INCREMENT,
      DECREMENT ,
      INCREMENT_COUNT ,
      INCREMENT_COUNT_STYLE ,
      ADD_STATE ,
      DELETE_STATE
    } from '../store/mutaions-types'

    export default {
        name:'App',
        data() {
            return {
                message:'我是App组件',
                counter: 0
            }
        },
        methods:{
            increment: function () {
                // commit（）用于调用mutations对象里的函数。
                this.$store.commit(INCREMENT)
            },
            decrement: function () {
                this.$store.commit(DECREMENT)
            },
            // mutations传参,如果有多个参数统一放到一个对象中在传递过去,参数被称为mutations的载荷（PayLoad）
            addCount:function (num) {
                // 演示:创建一个对象 const list = {id:125,name:'wulan'}
                this.$store.commit(INCREMENT_COUNT,num)

            },
            // 一种特殊的提交风格，提交后在Store对象中获取到的就是一个对象了
            addCountStyle:function (num) {
                // 2、特殊的提交风格
                this.$store.commit({
                    type:INCREMENT_COUNT,
                    num,
                    age:19
                })
            },
            addState:function (){
              this.$store.commit(ADD_STATE)
            },
            deleteState:function (){
              this.$store.commit(DELETE_STATE)
            },
           asyAddState:function (){
             this.$store.disable('asyAddInfo','传递过去的参数')
            }
        }
    }
</script>
<style>
</style>