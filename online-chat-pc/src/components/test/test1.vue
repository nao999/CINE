<template>
  <div>
    <span>{{data}}</span>
  </div>
</template>

<script>
import bus from "@/event/event";
export default {
name: "test1",
  mounted() {
    bus.$on('addNumber',()=>{
      this.data += 1;
    })
  },
  data(){
    return{
      data: 0
    }
  }
}
</script>

<style scoped>

</style>
