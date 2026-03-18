<template>
  <div class="login">
    <div class="login1">
    </div>
    <div class="login4">
        <div class="login5">

            <div class="login6">欢迎使用旅游信息推荐系统</div>

            <el-input prefix-icon="el-icon-user" v-model="loginAccount" placeholder="请输入登录账号"></el-input>
            <el-input prefix-icon="el-icon-star-off" type="password" v-model="password" placeholder="请输入用户密码"></el-input>
            <div class="login7" @click="toForget">
                忘记密码？
            </div>
            <div class="login8" @click="login">
                登 录
            </div>
            <div class="login8" @click="toRegister">
                注 册
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