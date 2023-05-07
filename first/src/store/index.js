import { createStore } from 'vuex'

export default createStore({
  state: {
    user:{}
  },
  mutations: {

  },
  actions: {
    intuser(context, value){
      // console.log(context+"/"+value)
      context.state.user=value
      console.log("jjj",context.state.user)
    }
  },
  modules: {
  }
})
