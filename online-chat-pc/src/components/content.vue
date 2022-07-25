<template>
  <div>
  <div class="sidebar">
    <card></card>
    <list></list>
  </div>
  <div class="main">
    <chattitle></chattitle>
    <message></message>
    <usertext></usertext>
  </div>
  </div>
</template>

<script>
import card from "@/components/chat/card";
import list from "@/components/chat/list";
import chattitle from "@/components/chat/chattitle";
import message from "@/components/chat/message";
import usertext from "@/components/chat/usertext";

export default {
name: "content",
  components: {card,list,chattitle,message,usertext}


}
</script>

<style scoped>
#app .sidebar {
  float: left;
  color: #000000;
  background-color: #ECEAE8;
  width: 240px;
}
.main {
  position: relative;
  overflow: hidden;
  background-color: #eee;
}
</style>
