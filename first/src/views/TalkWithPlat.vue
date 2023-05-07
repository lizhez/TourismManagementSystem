<template>
<div>
    <div>
      <pre style="font-size: 24px">
    感谢您选择我们的平台，真诚的欢迎您向我们提出宝贵的意见。
    维护您的权益是我们毕生坚守的底线！
      </pre>
    </div>
    <el-form
        ref="form"
        v-model="form"
        label-width="70px">
      <el-form-item label="标题">
        <el-input></el-input>
      </el-form-item>
      <el-form-item label="内容">
        <mavon-editor :subfield="false"></mavon-editor>
      </el-form-item>
      <el-form-item>
        <el-button>发送</el-button>
        <el-button >清空</el-button>
      </el-form-item>
    </el-form>
</div>
</template>

<script>
export default {
  name: "TalkWithPlat",
  data(){
    return{
      form:{}
    }
  }
}
</script>

<style scoped>

</style>