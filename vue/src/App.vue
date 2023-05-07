<template>
  <div>
    <Header/>
    <div style="display:flex;">
      <Nav/>
      <router-view/>
    </div>
  </div>
</template>

<style>

</style>

<script>

import Header from "@/components/Header";
import Nav from "@/components/Nav";

export default {
  components:{
    Header,
    Nav
  }
}
</script>