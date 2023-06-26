import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

export default new Vuex.Store({
  // 用于定义应用程序的状态。它是一个对象，包含了应用程序中所有的状态数据。
  state: {
    count: 10,
    students: [
      {id: 110, name: 'why', age: 18},
      {fid: 111, name: 'kobe', age: 24},
      {fid: 112, name: 'james', age: 30},
      {fid: 113, name: 'curry', age: 10}
    ]
  }
  ,
  // 用于获取应用程序的状态。它是一个对象，包含了一些计算属性，用于从应用程序的状态中派生出一些新的数据。
  getters: {
    powerCounter(state) {
      return state.count * state.count
    },
    more20stu(state) {
      return state.students.filter(s => s.age > 20)
    },
    // getters作为参数
    more20stuLength(state, getters) {
      return getters.more20stu.length
    },
    // getters内的函数接受外部传参,不能直接接受参数，但可以返回一个函数，然后让被返回的函数接受参数
    moreAgeStu(state){
      return function (age) {
        return stete.students.filter(s => s.age>age)
      }
      // 可以改写为箭头函数
      // return age => {
      //   return stete.students.filter(s => s.age>age)
      // }
    }
  },
  // 用于修改应用程序的状态(同步）。是一个对象，包含了一些同步函数
  mutations: {
    //定义一个mutation,其中decrement是事件类型（可以通过commit函数调用指定事件类型），后面的是回调函数（该函数的第一个参数是state)
    decrement(state) {
      state.count--
    },
    increment(state) {
      state.count++
    },
    incrementCount(state,num){
      state.count += num
    },
    //接收特殊风格的提交
    incrementCountStyle(state,payLoad){
      state.count += payLoad.num
    }
  },
  actions: {
    // 用于处理异步操作和复杂的业务逻辑，是一个对象，包含了一些异步的函数，且可以通过commit函数来调用mutations中的函数，从而修改应用程序的状态。
  },
  modules: {
    // 用于将应用程序的状态分割成多个模块。它是一个对象，包含了一些子模块，每个子模块都有自己的state、getters、mutations和actions。
  }
})
