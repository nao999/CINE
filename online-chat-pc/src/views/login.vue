<template>
  <div class="login_container">
    <div class="login_box">
      <!--      头像区域-->
      <div class="avatar_box">
        <img src="../assets/logo.png" alt="">
      </div>
      <!--      登录表单-->
      <el-form ref="loginFormRef" :model="loginForm" :rules="loginFormRules" label-width="0px" class="login_form">
        <!--        用户名-->
        <el-form-item prop="userName">
          <el-input v-model="loginForm.username"  prefix-icon="iconfont icon-yonghuming"></el-input>
        </el-form-item>
        <!--        密码-->
        <el-form-item prop="password">
          <el-input type="password" v-model="loginForm.password"  prefix-icon="iconfont icon-mima"></el-input>
        </el-form-item>
        <!--        按钮区域-->
        <el-form-item class="btns">
          <el-button @click="regist">注册</el-button>
          <el-button type="primary" @click="login">登录</el-button>
<!--          <el-button type="info" @click="resetLoginForm">重置</el-button>-->

        </el-form-item>
      </el-form>
    </div>
    <el-dialog
        title="提示"
        :visible.sync="dialogVisible"
        width="30%"
        :before-close="handleClose">
      <el-form ref="form" :model="registerForm" label-width="80px">
        <el-form-item label="用户名">
          <el-input v-model="registerForm.username" prefix-icon="iconfont icon-yonghuming"></el-input>
        </el-form-item>
        <el-form-item label="昵称">
          <el-input v-model="registerForm.nickname" prefix-icon="iconfont icon-nicheng"></el-input>
        </el-form-item>
        <el-form-item label="密码">
          <el-input  v-model="registerForm.password" show-password prefix-icon="iconfont icon-mima"></el-input>
        </el-form-item>
        <el-form-item label="头像">

          <el-upload
              class="avatar-uploader"
              action="http://42.193.125.212:8000/upload/uploadImg"
              name="image"
              :show-file-list="false"
              :on-success="handleAvatarSuccess"
              :before-upload="beforeAvatarUpload">
            <img v-if="registerForm.userProfile" :src="registerForm.userProfile" class="avatar">
            <i v-else class="el-icon-plus avatar-uploader-icon"></i>
          </el-upload>
        </el-form-item>
<!--        <el-form-item label="头像">-->
<!--          <el-input v-model="registerForm.userProfile"></el-input>-->
<!--        </el-form-item>-->
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="register">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>

import '@/components/common/toolbar'
export default {
  name: "login",
  data(){
    return {
      imageUrl: '',
      registerForm:{
        username: '',
        nickname: '',
        password: '',
        userProfile: ''
      },
      dialogVisible: false,
      // 登录表单的数据绑定对象
      loginForm: {
        username: 'naoooo',
        password: '000000'
      },
      // 表单的验证规则对象
      loginFormRules: {
        // 验证用户名是否合法
        username: [
          { required: true, message: '请输入登录名称', trigger: 'blur' },
          { min: 3, max: 15, message: '长度在 3 到 15 个字符', trigger: 'blur' }
        ],
        // 验证密码是否合法
        password: [
          { required: true, message: '请输入登录密码', trigger: 'blur' },
          { min: 6, max: 15, message: '长度在 6 到 15 个字符', trigger: 'blur' }
        ]
      }
    }

  },

  methods: {
    register(){
      this.$http.post("/user/register",this.registerForm)
          .then(response => {
        console.log(response)
        if(response.data.code === 1){
          this.dialogVisible = false;
          this.$message({
            message: '注册成功',
            type: 'success'
          });
        }else{
          this.$message.error('注册失败');
        }
      })
    },
    // 注册
    regist(){
      this.dialogVisible = true;
    },
    // 点击重置按钮，重置登录表单
    resetLoginForm(){
      // console.log(this);
      this.$refs.loginFormRef.resetFields();
    },
    login(){
      this.$refs.loginFormRef.validate(async (valid) => {
        if(!valid) return;
        window.localStorage.clear();
        const data = await this.$http.post('/user/login',this.loginForm);
        console.log(data);
        if(data.data.code !== 1) return this.$message.error("登录失败");
        console.log(data.data.response);
        window.localStorage.setItem('token',data.data.response);
        window.localStorage.setItem('username',this.loginForm.username);
        this.$store.state.username = this.loginForm.username
        this.$message.success("登录成功");

        // // 发请求记录登录
        // this.loginSave();
        this.$router.push('/');
      })
    },

    loginSave(){
      this.$http.post("/user/loginSave");
    },

    handleAvatarSuccess(res) {
      this.registerForm.userProfile = res.response;
    },
    beforeAvatarUpload(file) {
      const isJPG = file.type === 'image/jpeg';
      const isLt2M = file.size / 1024 / 1024 < 2;

      if (!isJPG) {
        this.$message.error('上传头像图片只能是 JPG 格式!');
      }
      if (!isLt2M) {
        this.$message.error('上传头像图片大小不能超过 2MB!');
      }
      return isJPG && isLt2M;
    }
  }
}
</script>

<style scoped>



.login_container{
  background-color: #fbfbfb;
  height: 100%;
}
.login_box{
  width: 450px;
  height: 300px;
  background-color: #ffffff;
  border-radius: 3px;
  position: absolute;
  left: 50%;
  top:50%;
  transform: translate(-50%,-50%);
  box-shadow: 0 2px 5px 0 rgb(33 22 22 / 20%), 0 2px 10px 0 rgb(0 0 0 / 12%);
}

.avatar_box {
  height: 130px;
  width: 130px;
  border: 1px solid #eee;
  border-radius: 50%;
  padding: 10px;
  box-shadow: 0 0 10px #ddd;
  position: absolute;
  left: 50% ;
  transform: translate(-50%,-50%);
  background-color: #fff;
}
.avatar_box  img {
  width: 100%;
  height: 100%;
  border-radius: 50%;
  background-color: #eee;
}
.login_form{
  position: absolute;
  bottom: 0;
  width: 100%;
  padding: 0 20px;
  box-sizing: border-box;
}

.btns{
  display: flex;
  justify-content: flex-end;
}


</style>

<style>
.avatar-uploader .el-upload {
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
}
.avatar-uploader .el-upload:hover {
  border-color: #409EFF;
}
.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 178px;
  height: 178px;
  line-height: 178px;
  text-align: center;
}
.avatar {
  width: 178px;
  height: 178px;
  display: block;
}
</style>
