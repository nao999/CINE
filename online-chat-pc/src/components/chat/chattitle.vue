<template>
  <div class="chatTitle">
    <span class="titleName">{{$store.state.chatGroup}}</span>
    <el-button class="moreBtn" size="small" icon="el-icon-more" @click="msgListView"></el-button>
  </div>
</template>

<script>
export default {
name: "chattitle",
  methods:{
    msgListView(){

      this.$store.state.msgListView = !this.$store.state.msgListView;
    }
  }
}
</script>

<style scoped>
.chatTitle{
  height: 50px;
  width: 100%;
  font-size: 20px;
  display: flex;
  justify-content: space-between;
  border-bottom: 0.5px solid #c7d2db;
}
.moreBtn{
  background-color: #eee;
  border: 0;
  height: 40px;
}
.titleName{
  margin: 10px 20px;
}
</style>
