<template>
  <div class="login">
    <div class="login1">
      <img class="login1-bg" src="../../assets/image/login-bg.jpg" alt="background">
      <div class="login1-overlay"></div>
      <div class="login1-content">
        <div class="login1-icon">
          <i class="el-icon-position"></i>
        </div>
        <h1 class="login1-title">旅途</h1>
        <p class="login1-subtitle">发现美好旅途，探索精彩世界</p>
        <div class="login1-features">
          <div class="feature">
            <i class="el-icon-location-outline"></i>
            <span>精选景点</span>
          </div>
          <div class="feature">
            <i class="el-icon-map-location"></i>
            <span>路线规划</span>
          </div>
          <div class="feature">
            <i class="el-icon-office-building"></i>
            <span>品质酒店</span>
          </div>
        </div>
      </div>
    </div>
    <div class="login4">
        <div class="login5">
            <div class="login-header">
              <h2 class="login6">欢迎回来</h2>
              <p class="login-desc">登录您的账户，开启旅途</p>
            </div>

            <div class="input-group">
              <label class="input-label">账号</label>
              <el-input prefix-icon="el-icon-user" v-model="loginAccount" placeholder="请输入登录账号"></el-input>
            </div>
            <div class="input-group">
              <label class="input-label">密码</label>
              <el-input prefix-icon="el-icon-lock" type="password" v-model="password" placeholder="请输入用户密码"></el-input>
            </div>
            <div class="login7" @click="toForget">
                忘记密码？
            </div>
            <div class="login8" @click="login">
                登 录
            </div>
            <div class="login-divider">
              <span>或者</span>
            </div>
            <div class="login8 secondary" @click="toRegister">
                注册新账号
            </div>
        </div>
    </div>
  </div>
</template>

<script>
  import {login,getUser} from '../../api/api'
  export default {
    data() {
      return{
        loginAccount: "",
        password: "",
      }
    },
    methods: {
        toRegister() {
          this.$router.push("/register")
        },
        toForget() {
          this.$router.push("/forget")
        },
        login() {
            if(!this.loginAccount) {
                this.$message({
                    message: '请输入用户名',
                    type: 'warning'
                });
                return;
            }
            if(!this.password) {
                this.$message({
                    message: '请输入密码',
                    type: 'warning'
                });
                return;
            }
            var params = {
                loginAccount: this.loginAccount,
                password: this.password
            }
            login(params).then(res => {
                if(res.code == 1000) {
                    this.$message({
                        message: '登陆成功',
                        type: 'success'
                    });
                    var token = res.data.token
                    window.localStorage.setItem("user_token",token)
                    // 登陆后根据用户类型区分前台/后台账号
                    this.getUserInfo().then(user => {
                        if (!user) {
                            return
                        }
                        // 默认认为 userType == 1 为普通用户，其它为后台/管理账号
                        if (user.userType !== 1) {
                            this.$message.error('该账号为后台管理账号，请到后台管理系统登录')
                            window.localStorage.removeItem("user_token")
                            window.localStorage.removeItem("user_info")
                            return
                        }
                        setTimeout(() => {
                            this.$router.push("/")
                        },500)
                    })
                } else {
                    this.$message.error(res.message);
                }
            })
        },
        getUserInfo() {
            return getUser().then(res => {
                if(res.code == 1000) {
                    window.localStorage.setItem("user_info",JSON.stringify(res.data))
                    return res.data
                } else {
                    return null
                }
            })
        },
    },
    created() {
     
    },
    mounted() {
      
    }
 }
</script>

<style scoped>
  @import url('../../assets/css/login.css');
</style>
