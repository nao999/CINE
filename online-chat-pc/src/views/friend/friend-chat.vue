<template>
  <div id="app">
    <div class="toolbar">
      <toolbar></toolbar>
    </div>

    <div class="sidebar">
      <card></card>
      <list></list>
    </div>
    <div class="main">
      <chattitle></chattitle>
      <message v-if="!$store.state.friendApplication"></message>
      <usertext v-if="!$store.state.friendApplication"></usertext>
      <application v-else></application>
    </div>
  </div>
</template>

<script>
import toolbar from "@/components/common/toolbar";
import card from "@/components/friend/card";
import list from "@/components/friend/list";
import chattitle from "@/components/friend/chattitle";
import message from "@/components/friend/message";
import usertext from "@/components/friend/usertext";
import application from "@/components/friend/application";
export default {
name: "friend-chat",
  components: {toolbar,card,list,chattitle,message,usertext,application}
}
</script>

<style scoped>
#app {
  display: flex;
  margin: 20px auto;
  width: 900px;
  height: 650px;
  overflow: hidden;
  border-radius: 10px;
}
#app .sidebar, .main ,.toolbar{
  height: 100%;
}
#app .toolbar{
  float: left;
  color: #f4f4f4;
  background-color: #2e3238;
  width: 60px;
}
#app .sidebar {
  float: left;
  color: #000000;
  background-color: #ECEAE8;
  width: 240px;
}
#app .main {
  position: relative;
  overflow: hidden;
  background-color: #eee;
  width: 700px;

}
</style>
