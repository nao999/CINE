<template>
  <div id="toolbar">
    <el-image class="imgProfile"
              :src="user.userProfile"
              @click="changeUserProfile"
    >
      <div slot="error" class="image-slot">
        <i class="el-icon-picture-outline"></i>
      </div>
    </el-image>
    <div id="btnBar">
      <div class="topBtnBar">
        <el-tooltip  class="item" effect="dark" content="进入群聊" placement="right">
          <el-button @click="$router.push('/')" class="toolBtn" size="small"><i class="iconfont icon-qunliao" aria-hidden="true"></i></el-button>
        </el-tooltip>
        <el-tooltip class="item" effect="dark" content="用户列表" placement="right">
          <el-button @click="$router.push('/friend')" class="toolBtn" size="small"><i class="iconfont icon-weibiaoti--" aria-hidden="true"></i></el-button>
        </el-tooltip>

      </div>
      <div class="bottomBtnBar">
        <el-tooltip class="item" effect="dark" content="个人中心" placement="right">
          <el-button class="toolBtn" size="small"><i class="iconfont icon-gerenzhongxin" aria-hidden="true"></i></el-button>
        </el-tooltip>
        <el-tooltip class="item" effect="dark" content="更多" placement="right">
          <el-popover
              placement="right"
              width="180"
              trigger="click"
              popper-class="moreListPopoverClass"
          >
            <ul id="moreList">
              <li  >意见反馈</li>
              <li>举报</li>
              <li >清空聊天记录</li>
            </ul>
            <el-button slot="reference" class="toolBtn" size="small"><i class="iconfont icon-gengduo" aria-hidden="true"></i></el-button>
          </el-popover>
        </el-tooltip>
        <el-tooltip class="item" effect="dark" content="退出" placement="right">
          <el-button class="toolBtn" size="small" @click="logOut"><i class="iconfont icon-tuichu" aria-hidden="true"></i></el-button>
        </el-tooltip>
      </div>
    </div>
    <!-- vueCropper 剪裁图片实现-->
    <el-dialog title="更改头像" :visible.sync="dialogVisible" append-to-body>

      <div class="cropper-content">
        <div class="cropper" style="text-align:center">
          <test-cut
              :Name="cropperName"
              :img = "user.userProfile"
              @uploadImgSuccess = "handleUploadSuccess"
              ref="child">
          </test-cut>


        </div>
      </div>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="finish" :loading="loading">确认</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import testCut from "@/components/testCut";
import bus from "@/event/event";
export default {
  name: "toolbar",
  components: {testCut},
  data(){
    return{
      cropperName:'',
      user: {},
      dialogVisible: false,

    }
  },
  created() {
    this.getUserInfo();
  },
  mounted() {
    bus.$on("refreshProfile",()=>{
      this.getUserInfo();
      this.dialogVisible = false;
    })
  },

  methods:{

    changeUserProfile(){
      this.dialogVisible = true;
    },
    logOut(){
      this.$http.post("/user/loginout").then(response=>{
        if(response.data.code===1){
          window.localStorage.clear();
          this.$router.push('/login')
        }
      })

    },
    getUserInfo(){
      this.$http.get("/user/getUserInfo").then(response => {
        console.log(response)
        if(response.data.code === 1){
          this.user = response.data.response

        }

      })
    },
    chooseChatList(){
      this.$router.push("/friend");
    },

    // 上传按钮   限制图片大小
    changeUpload(file) {
      const isLt5M = file.size / 1024 / 1024 < 5
      if (!isLt5M) {
        this.$message.error('上传文件大小不能超过 5MB!')
        return false
      }
      this.fileinfo = file

      // 上传成功后将图片地址赋值给裁剪框显示图片
      this.$nextTick(() => {
        this.option.img = file.url
        this.dialogVisible = true
      })
    },
    // 点击裁剪，这一步是可以拿到处理后的地址
    finish() {
      this.$refs.cropper.getCropBlob(() => {
        // var fileName = 'goods' + this.fileinfo.uid
        this.loading = true

      })
    }
  }
}
</script>

<style  scoped>
#toolbar {
  width: 100%;
  height: 100%;
}
#btnBar{
  width: 100%;
  height: 82%;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
}
#toolbar .imgProfile{
  width: 40px;
  height: 40px;
  horiz-align: center;
  margin: 25px 10px;
  border-radius: 100%;
}
.toolBtn{
  background-color: #2e3238;
  border: 0;
  margin: 5px 5px;
}

.iconfont{
  font-size: 22px;
}

.feedbackDialog{
  width: 1000px;
  height: 800px;
  margin: 10px auto;
//background-color: #ECEAE8;
}
.feedbackInput{
  width: 450px;
  height: 200px;
  resize: none;
  padding: 0;
  margin: 0;
}
</style>
