<template>
  <div>
    <el-cascader
        :options="pcaOptions"
        :props="addressProps"
        placeholder="请选择"
        @change="handleAddressNode"
        filterable
    ></el-cascader>
  </div>
</template>

<script>
const pca = require('@/assets/pca-code.json')

export default {
  name: "Address",
  data() {
    return {
      pcaOptions:pca,
      addressProps:{
        label: 'name',
        children: 'children',
        value: 'code'
      }
    }
  },

  methods: {}
}
</script>
<style scoped>

</style>

