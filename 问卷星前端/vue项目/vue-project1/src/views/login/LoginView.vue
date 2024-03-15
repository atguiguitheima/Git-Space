<template>
  <div id="building">
    <div>
    <el-card class="box-card">
      <h2>用户登录</h2>
      <el-form
        :model="ruleForm"
        status-icon
        :rules="rules"
        ref="ruleForm"
        label-position="left"
        label-width="70px"
        class="login-from"
      >
        <el-form-item label="用户名" prop="uname">
          <el-input v-model="ruleForm.uname"></el-input>
        </el-form-item>
        <el-form-item label="密码" prop="password">
          <el-input
            type="password"
            v-model="ruleForm.password"
            autocomplete="off"
          ></el-input>
  <!-- `checked` 为 true 或 false -->
  <el-checkbox v-model="rememberPassword" @change="f1">记住密码</el-checkbox>
  <el-checkbox v-model="autoLogin" @change="rememberPassword=true">自动登录</el-checkbox>
        </el-form-item>
     

  

 

      </el-form>
      <div class="btnGroup">
        <el-button
          type="primary"
          @click="submitForm('ruleForm')"
          v-loading="loading"
          >登录</el-button>
          <router-link to="/adminlogin">
          <el-button style="margin-left: 10px">管理员登录</el-button>
        </router-link>
        <router-link to="/register">
          <el-button style="margin-left: 10px">注册</el-button>
        </router-link>
        <el-button @click="resetForm('ruleForm')">重置</el-button>
      </div>
    </el-card>
  </div>
  </div>
  
</template>

<script>


export default {
  data() {
    return {
      rememberPassword:false,
      autoLogin:false,
      ruleForm: {
        uname: "",
        password: "",
      },
      rules: {
        uname: [
          { required: true, message: "用户名不能为空！", trigger: "blur" },
        ],
        password: [
          { required: true, message: "密码不能为空！", trigger: "blur" },
        ],
      },
      loading: false, // 是否显示加载动画
    };
  },
  methods: {
    f1(){
      console.log(this.rememberPassword)
        if(this.rememberPassword==false){
          this.autoLogin=false;
        }else{
          this.rememberPassword=true;
        }
    },
    submitForm(formName) {
      // 验证表单中的账号密码是否有效，因为在上面rules中定义为了必填 required: true
      this.$refs[formName].validate((valid) => {
        // 点击登录后，让登录按钮开始转圈圈（展示加载动画）
        this.loading = true;
        // 如果经过校验，账号密码都不为空，则发送请求到后端登录接口
        if (valid) {
          let _this = this;
          // 使用 axios 将登录信息发送到后端
          this.axios({
            url: "/api/users/login",               // 请求地址
            method: "post",                       // 请求方法
            headers: {                            // 请求头
              "Content-Type": "application/json",
            },
            data: JSON.stringify({
              autoLogin:_this.autoLogin,
              rememberPassword:_this.rememberPassword,                             // 请求参数
              user:{username: _this.ruleForm.uname,
              password: _this.ruleForm.password,}
            }),
          }).then((res) => { // 当收到后端的响应时执行该括号内的代码，res 为响应信息，也就是后端返回的信息
            if (res.data.code === 1) {  // 当响应的编码为 1 时，说明登录成功
              // 将用户信息存储到sessionStorage中
              console.log(res.data.data)
              sessionStorage.setItem("userInfo", JSON.stringify(res.data.data));
              localStorage.setItem("userjwt",res.data.data)
              // 跳转页面到首页
              
              let url=window.location.search;
              console.log(url.substring(0,1));
              if(url.substring(0,1)=='?'){
              this.$router.replace(this.$route.query.redirect);}
              else{
                this.$router.push('/home')
              }
              // 显示后端响应的成功信息
              this.$message({
                message: res.data.msg,
                type: "success",
              });
            } else {  // 当响应的编码不为 1 时，说明登录失败
              // 显示后端响应的失败信息
              this.$message({
                message: res.data.msg,
                type: "warning",
              });
            }
            // 不管响应成功还是失败，收到后端响应的消息后就不再让登录按钮显示加载动画了
            _this.loading = false;
            console.log(res);
          });
        } else {  // 如果账号或密码有一个没填，就直接提示必填，不向后端请求
          console.log("error submit!!");
          this.loading = false;
          return false;
        }
      });
    },
    resetForm(formName) {
      this.$refs[formName].resetFields();
    },
  },
  mounted(){
    this.axios({
        url: "/api/users/parseJwt", // 请求地址
        headers: {
          token: localStorage.getItem("userjwt"),
        },
        method: "get", // 请求方法
      }).then((res) => {
        // 当收到后端的响应时执行该括号内的代码，res 为响应信息，也就是后端返回的信息
        console.log(res.data.data);
       var data=res.data.data
       this.ruleForm.uname=data.username;
       if(data.autoLogin==1){
        this.autoLogin==true;
        this.rememberPassword==true;
        this.ruleForm.uname=data.username;
        this.ruleForm.password=data.password;

 let _this = this;
        this.axios({
            url: "/api/users/login",               // 请求地址
            method: "post",                       // 请求方法
            headers: {                            // 请求头
              "Content-Type": "application/json",
            },
            data: JSON.stringify({
              autoLogin:true,
              rememberPassword:true,                             // 请求参数
              user:{username: _this.ruleForm.uname,
              password: _this.ruleForm.password,}
            }),
          }).then((res) => { // 当收到后端的响应时执行该括号内的代码，res 为响应信息，也就是后端返回的信息
            if (res.data.code === 1) {  // 当响应的编码为 1 时，说明登录成功
              // 将用户信息存储到sessionStorage中
              sessionStorage.setItem("userInfo", JSON.stringify(res.data.data));
              localStorage.setItem("userjwt",res.data.data)
              // 跳转页面到首页
              let url=window.location.search;
              console.log(url.substring(0,1));
              if(url.substring(0,1)=='?'){
              this.$router.replace(this.$route.query.redirect);}
              else{
                this.$router.push('/home')
              }
              // 显示后端响应的成功信息
              this.$message({
                message: res.data.msg,
                type: "success",
              });
            } else {  // 当响应的编码不为 1 时，说明登录失败
              // 显示后端响应的失败信息
              this.$message({
                message: res.data.msg,
                type: "warning",
              });
            }
            // 不管响应成功还是失败，收到后端响应的消息后就不再让登录按钮显示加载动画了
            _this.loading = false;
            console.log(res);
          });
       }else{
        if(data.rememberPassword==1){
          this.ruleForm.uname=data.username;
        this.ruleForm.password=data.password;
        this.rememberPassword=true;
        }
       }
      });
    } 
  }

</script>

<style scoped>
/* 设置登录面板居中，宽度为400px */
.box-card {
  margin: 150px 500px;
  width: 400px;
}
/* 设置登录面板中的表单居中 */
.login-from {
  margin: auto auto;
}

#building{
  background:url("D:\问卷星前端\vue项目\vue-project1\src\assets\学生管理系统背景.png");
  width:100%;
  height:100%;
  position:fixed;
  background-size:100% 100%;
}

</style>