<template>
  <div class="search-body">
    <div class="top">
      <van-icon name="arrow-left" @click="back()"/>
      <input  v-model="searchName" placeholder="用户名/群名" type="text"/>

      <div class="search-btn" @click="search()">搜索</div>
<!--      <van-icon size="30" name="more-o" style="margin-right: 10px;" />-->

    </div>
    <div class="content">
      <div class="search">
        <span class="title">查找人</span>
        <div class="search-item" style="flex-direction: column;" v-if="this.friend.username === ''">
          {{this.searchErr}}
        </div>
        <div class="search-item" v-else>
          <div class="friend-info">
            <van-image
                width="60"
                height="60"
                :src="this.friend.userProfile"
                round
            />
            <div class="info-username">{{this.friend.username}}</div>
          </div>
          <span v-if="alJoin">已添加</span>
          <van-button v-else  plain type="info" color="#080b12" size="small" @click="addFriendShow">添加</van-button>
        </div>
      </div>
      <div class="search">
        <span class="title">查找群</span>
        <div class="search-item">
          <div class="friend-info">
            <van-image
                width="60"
                height="60"
                src="	http://nao190830.oss-cn-beijing.aliyuncs.com/20211114/a33ab955387c43a29f4be767ca648190.jpg"
                round
            />
            <div class="info-username">sss</div>
          </div>
          <van-button plain type="info" color="#080b12" size="small">加入</van-button>
        </div>
        <div class="search-item">
          <div class="friend-info">
            <van-image
                width="60"
                height="60"
                src="	http://nao190830.oss-cn-beijing.aliyuncs.com/20211114/a33ab955387c43a29f4be767ca648190.jpg"
                round
            />
            <div class="info-username">sss</div>
          </div>
          <van-button plain type="info" color="#080b12" size="small">加入</van-button>
        </div>
        <div class="search-item">
          <div class="friend-info">
            <van-image
                width="60"
                height="60"
                src="	http://nao190830.oss-cn-beijing.aliyuncs.com/20211114/a33ab955387c43a29f4be767ca648190.jpg"
                round
            />
            <div class="info-username">sss</div>
          </div>
          <van-button plain type="info" color="#080b12" size="small">加入</van-button>
        </div>
        <div class="search-item">
          <div class="friend-info">
            <van-image
                width="60"
                height="60"
                src="	http://nao190830.oss-cn-beijing.aliyuncs.com/20211114/a33ab955387c43a29f4be767ca648190.jpg"
                round
            />
            <div class="info-username">sss</div>
          </div>
          <van-button plain type="info" color="#080b12" size="small">加入</van-button>
        </div>
        <div class="search-item">
          <div class="friend-info">
            <van-image
                width="60"
                height="60"
                src="	http://nao190830.oss-cn-beijing.aliyuncs.com/20211114/a33ab955387c43a29f4be767ca648190.jpg"
                round
            />
            <div class="info-username">sss</div>
          </div>
          <van-button plain type="info" color="#080b12" size="small">加入</van-button>
        </div>

        <div class="search-item" >
          <div class="friend-info" >
            <van-image
                width="60"
                height="60"
                src="	http://nao190830.oss-cn-beijing.aliyuncs.com/20211114/a33ab955387c43a29f4be767ca648190.jpg"
                round
            />
            <div class="info-username">sss</div>
          </div>
          <van-button plain type="info" color="#080b12" size="small">加入</van-button>
        </div>
      </div>
<!--      <div class="">-->
<!--        查找群-->
<!--      </div>-->
    </div>
    <van-popup v-model="show" position="top" :style="{ height: '50%' }">
      <div style="padding: 0 20px">
        <div class="pop-friend-info">
          <van-image
              width="60"
              height="60"
              :src="this.friend.userProfile"
              round
          />
          <div class="info-username">{{this.friend.username}}</div>
        </div>
        <div class="application-message">
          <span>填写验证信息</span>
<!--          <input v-model="applicationMsg" type="text"/>-->
          <textarea v-model="applicationMsg">一些文本...</textarea>
        </div>
        <van-button class="apply-friend" type="info" @click="addFriend">发送</van-button>

      </div>
    </van-popup>
  </div>
</template>

<script>
export default {
name: "searchContent",
  data(){
    return{
      searchName: '',
      friend: {
        id: '',
        userProfile: '',
        username: ''
      },
      groups: [],
      searchErr: '',
      show: false,
      applicationMsg: '',
      alJoin: false
    };
  },
  methods:{
    addFriendShow(){
      this.show = true;
    },
    addFriend(){
      this.$http.post("/application/saveApplication",{
        applicationFriendId: this.friend.id,
        applicationMsg: this.applicationMsg
      }).then(response=>{
        console.log(response)
        if(response.data.code === 1){
          this.$toast.success("好友申请成功");
        }else{
          this.searchErr = response.data.message;
          this.$toast.fail(this.searchErr);
        }
      })
    },
    search(){
      this.searchFriend()
    },
    back(){
      this.$router.back();
    },

    searchFriend(){
      this.$http.get("/user/searchFriend",{params:{
          userName: this.searchName
        }}).then(response =>{
        // console.log(response.data.response)
        if(response.data.response !== null){
          this.friend = response.data.response;
          // 判断是否已经是好友
          this.isFriend(this.friend.id);
        }else{
          this.friend = {
            id: '',
            userProfile: '',
            username: ''
          };
          this.searchErr = '未搜索到该用户';
        }
        console.log(this.friend);
      })
    },

    isFriend(friendId) {
      this.$http.get("/userFriend/isFriend", {
        params: {
          friendId: friendId
        }
      }).then(response => {
        console.log(response)
        if(response.data.code === 1){
          // console.log(response.data.response.isFriend)
          this.alJoin = response.data.response.isFriend;
        }
      })
    }
  }

}
</script>

<style scoped>
.search-body{
  background-color : #f5f5f5;
  height: 100%;
  position: fixed;
  width: 100%
}
.top{
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 10px 10px;
  background-color: aliceblue;
}
.top .chat-name{
  font-size: 15px;
  color: #03060f;
}
.top .info{
  display: flex;
  align-items: center;
}

.top input{
  background-color: #f4f4f4;
  border: 0;
  width: 80%;
  text-align: center;
  padding: 5px 0;
}

.top .search-btn{
  color: #4776ce
}
.content{
  margin-top: 40px;
  overflow-y: scroll;
  top: 0;
  bottom: 0;
  position: fixed;
  width: 100%;
}
.search{
  display: flex;
  flex-direction: column;
  align-items: flex-start;
  padding:  10px;
  background-color: white;
  margin-bottom: 10px;
}
.search .title{
  font-size: 13px;
  color: #9c9c9c;
}
.search .search-item{
  margin-top: 20px;
  display: flex;
  align-items: center;
  width: 100%;
  justify-content: space-between;
}
.search-item .friend-info{
  display: flex;
  align-items: center;
}
.friend-info .info-username{
  margin-left: 20px;
  font-size: 20px;
  font-weight: 600;
}

.pop-friend-info{
  display: flex;
  align-items: center;
  margin: 10px 0;

}

.application-message{
  display: flex;
  align-items: flex-start;
  flex-direction: column;
}
.application-message span{
  font-size: 15px;
  margin-bottom: 10px;
  color: #b1b1b8;
}
.application-message textarea{
  background-color: #f4f4f4;
  border: 0;
  width: 100%;
  height: 150px;
}
.apply-friend{
  margin-top: 10px;
}
</style>
