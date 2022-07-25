<template>
  <div>
    <test1></test1>
    <test2></test2>
  </div>
</template>

<script>
import test1 from "@/components/test/test1";
import test2 from "@/components/test/test2";
export default {
name: "testComm",
  components:{test2,test1}
}
</script>

<style scoped>

</style>
