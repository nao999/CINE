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
      <message></message>
      <usertext></usertext>
    </div>
    <div class="msg-bar" v-if="$store.state.msgListView">
      <msg-list></msg-list>
    </div>
  </div>
</template>

<script>

  import toolbar from "@/components/common/toolbar";
  import card from "@/components/chat/card";
  import list from "@/components/chat/list";
  import chattitle from "@/components/chat/chattitle";
  import message from "@/components/chat/message";
  import usertext from "@/components/chat/usertext";
  import msgList from '@/components/chat/msg-list';
  export default {
  name: "chat-room",
    components: {toolbar,card,list,chattitle,message,usertext,msgList}
  }
</script>

<style scoped>
#app {
  display: flex;
  margin: 20px auto;
  width: 1200px;
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
#app .msg-bar{
  background-color: #eee;
  width: 250px;
}

</style>
