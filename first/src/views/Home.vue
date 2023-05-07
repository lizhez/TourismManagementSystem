<template>
  <div class="wrapper" id="home" style="width: 100%;height: 100vh; overflow-x: hidden;" >
    <el-backtop target=".wrapper"></el-backtop>
    <Header/>
    <router-view/>
    <Footer/>
  </div>
</template>

<script>
import Header from "@/components/Header";
import Footer from "@/components/Footer";

export default {
  name: "Home",
  data(){
    return{

    }
  },
  components:{
    Header,
    Footer,
  },
  methods:{}
}
</script>

<style scoped>
</style>