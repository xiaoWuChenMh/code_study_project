<template>
  <div id="app">
    <h2>------------ 获取状态信息-------------------</h2>
    <h2>{{$store.state.counter}}}</h2>

    <h2>------------ mutations相关信息-------------------</h2>
    <button @click="increment">+</button>
    <button @click="decrement">-</button>


    <h2>------------ getters相关信息-------------------</h2>
    <h2>{{$store.getters.powerCounter}}}</h2>
    <h2>{{$store.getters.powerCounter}}</h2>
    <h2>{{$store.getters.more20stu}}</h2>
    <h2>{{$store.getters.more20stuLength}}</h2>
    <h2>{{$store.getters.moreAgeStu(12)}}</h2>
  </div>
</template>

<script>
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
        this.$store.commit('increment')
      },
      decrement: function () {
        this.$store.commit('decrement')
      }
    }
  }
</script>
<style>
</style>