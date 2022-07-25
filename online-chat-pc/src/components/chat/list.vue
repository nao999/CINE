<template>
  <div id="list">
    <ul>
      <p style="padding: 2px 4px;height: 20px;text-align: left;font-weight: 600">群聊列表</p>
      <li :class="selectFocus(item.id)" style="display: flex;align-items: center;" v-for="item in groupList" :key="item.id" @click="chatGroup(item)">
        <img class="avatar" :src="item.groupProfile">
        <el-badge is-dot="dd"><p class="name">{{item.groupName}}</p></el-badge>
      </li>
    </ul>
  </div>
</template>

<script>
import bus from "@/event/event";
export default {
name: "list",
  data(){
    return{
      groupList: [],
      focusGroup: ''

    }
  },
  created() {
    this.getGroupList();
  },
  mounted() {
    bus.$on('refreshGroupList',() => {
      this.getGroupList();
    })
  },
  methods:{
    selectFocus(id){
      if(this.focusGroup === id){
        return 'focus-group';
      }
      return '';
    },
    getHistory(){
      this.$http.get("/group-msg/getHistory",{
        params:{
          chatGroupId: this.$store.state.chatGroupId
        }
      }).then(response => {
        console.log(response.data);
        if(response.data.code === 1){
          if((this.$store.state.sessions === undefined || this.$store.state.sessions.length === 0)){
            for(let index in response.data.response){
              this.$store.state.sessions.push(response.data.response[index])
            }
          }

        }


      })
    },
    chatGroup(item){
      this.$store.state.chatGroup = item.groupName;
      this.$store.state.chatGroupId = item.id;
      this.$store.state.friendName = '';

      if(this.$store.state.socket.length !== 0){
        this.$store.commit('close');

      }

      // 初始化websocket
      this.$store.commit('init');
      this.$store.state.sessions = [];
      this.getHistory();
      this.focusGroup = item.id;
      this.$store.state.msgListView = false;
    },
    getGroupList(){
      this.$http.get('/group/getGroupList').then(response => {
        console.log(response);
        if(response.data.code === 1){
          this.groupList = response.data.response;
        }
      })
    },
  }
}
</script>

<style scoped>
#list{
  height: 91%;
  overflow-x: hidden;
  overflow-y: scroll;
}
ul{
  margin-left: 0px;
  padding-left: 0px;
  margin-left: 2px;
}
li {
  padding-top: 14px;
  padding-bottom: 14px;
/*padding-right: 40px;*/
/*//border-bottom: 1px solid #292C33;*/
  list-style: none;
  cursor: pointer;

}
li:hover {
  background-color: #D8D6D6;
}
.focus-group{
  background-color: #D8D6D6;
}
.avatar {
  border-radius: 2px;
  width: 30px;
  height: 30px;
  vertical-align: middle;
  float: left;
}
.name {
  display: inline-block;
  margin-left: 15px;
  margin-top: 0px;
  margin-bottom: 0px;
}
</style>
