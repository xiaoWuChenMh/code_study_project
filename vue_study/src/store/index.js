import Vue from 'vue'
import Vuex from 'vuex'

// 导入mutaions对象中的方法名
import {
  INCREMENT,
  DECREMENT ,
  INCREMENT_COUNT ,
  INCREMENT_COUNT_STYLE ,
  ADD_STATE ,
  DELETE_STATE
} from './mutaions-types'

// 安装插件
Vue.use(Vuex)

const stateA ={
  state:{},
  mutations:{},
  getters:{},
  actions:{}
}

const stateB ={
  state:{},
  mutations:{},
  getters:{},
  actions:{}
}

  export default new Vuex.Store({
    // 用于定义应用程序的状态。它是一个对象，包含了应用程序中所有的状态数据。
    state: {
      count: 10,
      students: [
        {id: 110, name: 'why', age: 18},
        {fid: 111, name: 'kobe', age: 24},
        {fid: 112, name: 'james', age: 30},
        {fid: 113, name: 'curry', age: 10}
      ],
      info:{
        name : 'loka',
        age:17,
        height:'19.8'
      }
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
      [INCREMENT](state) {
        state.count++
      },
      [DECREMENT](state) {
        state.count--
      },

      [INCREMENT_COUNT](state,num){
        state.count += num
      },
      //接收特殊风格的提交
      [INCREMENT_COUNT_STYLE](state,payLoad){
        state.count += payLoad.num
      },
      [ADD_STATE](state){
        //直接修改变量，不会将新属性纳入到响应式系统中，使用该变量的页面也就意识不到其值的改变。
        // state.info['local']= '洛杉矶'
        //通过vue.set增加的属性才会放入响应式系统
        Vue.set(state.info,'local','洛杉矶')
      },
      [DELETE_STATE](state){
        //直接删除变量，不会将新属性纳入到响应式系统中，使用该变量的页面也就意识不到其值的改变。
        // delete state.info.age
        //通过vue.delete 删除的属性才会放入响应式系统
        Vue.delete(state.info,'age')
      }
    },
    // 用于处理异步操作和复杂的业务逻辑，是一个对象，包含了一些异步的函数，且可以通过commit函数来调用mutations中的函数，从而修改应用程序的状态。
    actions: {
      // 可以暂时理解为context == state
      asyAddInfo(context,payLoad){
        //模拟异步给状态对象添加变量
        setTimeout(()=>{
          context.commit(ADD_STATE)
          console.info("传递进来的参数："+payLoad)
          // 如果异步函数执行玩后，在想执行其他代码可以使用new Promise().then的方式
        },1000)
      }
    },
    // 用于将应用程序的状态分割成多个模块。它是一个对象，包含了一些子模块，每个子模块都有自己的state、getters、mutations和actions。
    modules: {
      stateA :stateA,
      stateB :stateB
    }
  })
