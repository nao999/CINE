<template>
  <div style="padding: 20px 10px;">
    <div>

      <input v-model="input" size="mini" placeholder="请输入内容"/>
      <div class="member-list">
        <el-button  style="width: 40px;height: 40px" @click="inviteFriend()">
          <i class="iconfont icon-jiahao" style="display: flex;justify-content: center;"></i>
        </el-button>
        <el-image v-for="item in memberList" :key="item.id" :src="item.userProfile"/>

      </div>

    </div>
    <div style="margin-top: 20px">
      <div class="msg-item">
        <div class="header">群聊名称</div>
        <div class="content">{{groupTitle}}</div>
      </div>
      <div class="msg-item">
        <div class="header">群公告</div>
        <div class="content">未设置</div>
      </div>
      <div class="msg-item">
        <div class="header">备注</div>
        <div class="content">群聊的备注仅自己可见</div>
      </div>
      <div class="msg-item">
        <div class="header">我在本群的昵称</div>
        <div class="content">上弦月</div>
      </div>
    </div>

    <el-dialog
        title="编辑群组"
        :visible.sync="dialogVisible"
        width="50%"
        :before-close="handleClose">
      <div class="createGroup-item">
        <div class="createGroup-friend">
          <div class="createGroup-search">
            <input type="text" name="lname" v-model="search" placeholder="进行搜索"/>
            <el-button icon="el-icon-search" @click="searchFriend()" circle></el-button>
          </div>
          <div class="friend-list">

            <div class="friend-item" @click="saveChooseFriend(item)"
                 v-for="item in friendList" :key="item.id">
              <div class="friend-info">
                <el-avatar shape="square" :src="item.userProfile"/>
                <span class="friend-name">{{item.friendUsername}}</span>
              </div>
              <el-switch
                  :value="chooseFriend(item.friendId)"
              >
              </el-switch>
            </div>

          </div>
        </div>
        <div class="createGroup-result"  >
          <div>
            <div style="margin-bottom: 20px">已选联系人：{{friendChooseList.length}}</div>
            <div class="choose-friend" v-for="item in friendChooseList" :key="item.id">

              <div class="friend-info">
                <el-avatar shape="square" :src="item.userProfile"/>
                <span class="friend-name">{{item.friendUsername? item.friendUsername : item.username}}</span>
              </div>
              <el-button type="text"><i class="iconfont icon-guanbi" style="font-size: 20px;color: #dadada" ></i></el-button>

            </div>
          </div>


        </div>

      </div>
      <div>
        <div style="padding: 10px;text-align: right;">
          <el-button size="mini" plain @click="editGroup">确定</el-button>
          <el-button size="mini"   plain @click="closeDialog">取消</el-button>
        </div>
      </div>
    </el-dialog>

  </div>
</template>

<script>
export default {
name: "msg-list",
  data(){
    return{
      input: '',
      memberList:[],
      groupTitle: '',
      dialogVisible: false,
      friendChooseList: [],
      friendList: [],
    }
  },
  created() {
    this.getGroupMsg();
  },
  methods:{
    closeDialog(){
      this.dialogVisible = false;
    },
    editGroup(){
      this.$http.put("/group/editGroup",{
          groupId: this.$store.state.chatGroupId,
          memberList: this.friendChooseList
      }).then(response => {
        console.log(response.data)
        if (response.data.code === 1) {
          this.dialogVisible = false;
          this.$message({
            message: '修改成员成功',
            type: 'success'
          });
          this.getGroupMsg();
        }else{
          this.$message.error('修改失败');
        }
      })
    },
    addChooseFriend(){
      if(this.friendChooseList.length === 0){
        for(let index in this.memberList){
          this.friendChooseList.push(this.memberList[index]);
        }
      }

    },
    getFriendList(){
      this.$http.get("/userFriend/getFriendList").then(response => {
        console.log(response)
        this.friendList = response.data.response;
        // 将群组成员加上已选择
        this.addChooseFriend();
      })
    },
    inviteFriend(){
      this.dialogVisible = !this.dialogVisible;
      // 查询好友
      this.getFriendList();

    },
    getGroupMsg(){
      this.$http.get("/group/getGroupMsg",{
        params:{
          groupId: this.$store.state.chatGroupId
        }}).then(response => {
          console.log(response)
          this.memberList = response.data.response.userVOList;
          this.groupTitle = response.data.response.groupName
      })
    },
    saveChooseFriend(friend){
      let index = this.friendChooseList.findIndex(item => {
        return item.id === friend.friendId;
      })
      if(index !== -1){
        this.friendChooseList.splice(index,1);
      }else{
        let user = {
          id: friend.friendId,
          userProfile: friend.userProfile,
          username: friend.friendUsername
        }
        this.friendChooseList.push(user);
      }
    },
    // 判断是否选中
    chooseFriend(id){
      console.log(id)
      console.log(this.friendChooseList)
      let index = this.friendChooseList.findIndex(item => {
        return item.id === id;
      })

      return index !== -1;
    },
  }
}
</script>

<style scoped>
  input{
    background-color: #DBD9D8;
    height: 30px;
    line-height: 30px;
    padding: 0 10px;
    border: 0;
    border-radius: 4px;
    outline: none;
    color: #FFF;
    width: 80%;
  }

  .member-list{
    display: flex;
    flex-wrap: wrap;
    margin-top: 10px;
    border-bottom: 1px solid #fff;
  }
  .member-list .el-image{
    width: 40px;
    margin: 10px 5px;
  }
  .member-list .el-button{
    width: 40px;
    margin: 10px 5px;
    height: 40px;
  }
  .msg-item{
    text-align: left;
    margin-bottom: 10px;
  }
  .msg-item .content{
    color: #7f93bc;
    margin-top: 10px;
    font-size: 15px;

  }


  .createGroup-item{
    display: flex;
    flex-direction: row;
  }
  .createGroup-friend{
    width: 50%;
    border-right: 1px solid #eee;


  }

  .createGroup-search{
    display: flex;
    margin-bottom: 10px;
    padding: 10px;
    align-items: center;
  }

  .createGroup-search input{
    margin-right: 10px;
    width: 80%;

    background-color: #DBD9D8;
    height: 30px;
    line-height: 30px;
    padding: 0 10px;
    border: 0;
    border-radius: 4px;
    outline: none;
    color: #FFF;
  }

  .friend-list{
    display: flex;
    flex-direction: column;
    overflow-y: auto;
    height: 300px;
  }

  .friend-info{
    display: flex;
    align-items: center;
  }
  .friend-info .friend-name{
    margin-left: 10px;
    font-weight: 600;
    font-size: 15px;
  }

  .friend-item{
    display: flex;
    align-items: center;
    justify-content: space-around;
    padding: 10px 0;
  }

  .friend-item:hover{
    background-color: #eee;
  }

  /*选择的好友*/
  .createGroup-result{
    display: flex;
    width: 50%;
    flex-direction: column;
    justify-content: space-between;
  }

  .choose-friend{
    display: flex;
    align-items: center;
    justify-content: space-around;
    padding: 10px 0;
  }

  .choose-friend .friend-name{
    margin-left: 10px;
    font-weight: 600;
    font-size: 15px;
  }

</style>
