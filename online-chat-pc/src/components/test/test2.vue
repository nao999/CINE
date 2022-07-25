<template>
  <el-button @click="addNumber">+1</el-button>
</template>

<script>
import bus from "@/event/event";
export default {
name: "test2",
  methods:{
    addNumber(){
      bus.$emit('addNumber');
    }
  }
}
</script>

<style scoped>

</style>
