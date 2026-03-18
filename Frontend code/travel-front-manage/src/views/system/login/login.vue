<template>
  <div class="login">
    <div class="login1">
    </div>
    <div class="login4">
        <div class="login5">
            <img src="../../../assets/image/logo.png">
            <div class="login6">欢迎使用旅游信息推荐系统</div>
            <div class="login3">
                珍藏每一刻的美好，旅行让生活变得更加有意义！
            </div>
            <el-input prefix-icon="el-icon-user" v-model="loginAccount" placeholder="请输入登录账号"></el-input>
            <el-input prefix-icon="el-icon-star-off" type="password" v-model="password" placeholder="请输入用户密码"></el-input>
            <div class="login8" @click="login">
                登 录
            </div>
        </div>
    </div>
  </div>
</template>

<script>
  import {login,getUser} from '../../../api/api' 
  export default {
    data() {
      return{
        loginAccount: '',
        password: ''
      }
    },
    methods: {
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
                    this.$store.commit('user/setToken', token)
                    // 只允许管理员账号（例如 loginAccount = 'admin'）登录后台
                    this.getUserInfo().then(user => {
                        if (!user) {
                            return
                        }
                        // 如果不是管理员账号，禁止进入后台
                        if (user.loginAccount !== 'admin') {
                            this.$message.error('该账号无权登录后台管理系统，请使用管理员账号 admin 登录')
                            // 清除后台登录状态
                            this.$store.commit('user/setToken', '')
                            this.$store.commit('user/setUser', '')
                            window.localStorage.removeItem("token")
                            window.localStorage.removeItem("user")
                            return
                        }
                        setTimeout(() => {
                            this.$router.push("/index")
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
                    this.$store.commit('user/setUser', JSON.stringify(res.data))
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
.login {
    width: 100%;
    height: 100%;
    font-family: 'PingFang SC', '黑体', sans-serif;
    display: flex;
    background-color: #0f172a;
}
.login1 {
    width: 55%;
    height: 100%;
    background-image: url('../../../assets/image/image 2.png');
    background-size: cover;
    background-position: center;
    display: flex;
    justify-content: center;
    align-items: center;
    flex-direction: column;
    position: relative;
}
.login1::before {
    content: '';
    position: absolute;
    top: 0;
    left: 0;
    right: 0;
    bottom: 0;
    background: linear-gradient(135deg, rgba(15, 23, 42, 0.9) 0%, rgba(30, 41, 59, 0.8) 100%);
}
.login2 {
    font-size: 35px;
    font-weight: bold;
}
.login3 {
    margin-top: 10px;
    letter-spacing: 2px;
    font-size: 20px;
    font-weight: bold;
}
.login4 {
    width: 45%;
    height: 100%;
    display: flex;
    justify-content: center;
    align-items: center;
    background-color: #ffffff;
}
.login5 {
    width: 75%;
    max-width: 420px;
    flex-direction: column;
    display: flex;
    gap: 28px;
    align-items: center;
    padding: 60px 40px;
}
.login5 img {
    width: 80px;
    height: 80px;
    border-radius: 16px;
    box-shadow: 0 8px 24px rgba(0, 0, 0, 0.1);
}
.login5 .el-input {
    width: 100%;
}
.login5 .el-input__inner {
    height: 52px;
    border-radius: 12px;
    border: 2px solid #e2e8f0;
    font-size: 15px;
    padding-left: 48px;
    transition: all 0.3s ease;
}
.login5 .el-input__inner:focus {
    border-color: #1e40af;
    box-shadow: 0 0 0 3px rgba(30, 64, 175, 0.1);
}
.login5 .el-input__prefix {
    left: 16px;
    color: #64748b;
}
.login6 {
    font-size: 26px;
    font-weight: 700;
    color: #0f172a;
    text-align: center;
    line-height: 1.4;
}
.login7 {
    width: 100%;
    text-align: right;
    cursor: pointer;
}
.login8 {
    display: flex;
    justify-content: center;
    align-items: center;
    color: #ffffff;
    width: 100%;
    height: 52px;
    cursor: pointer;
    border-radius: 12px;
    background: linear-gradient(135deg, #1e40af 0%, #3b82f6 100%);
    font-size: 16px;
    font-weight: 600;
    letter-spacing: 4px;
    transition: all 0.3s ease;
    box-shadow: 0 4px 15px rgba(30, 64, 175, 0.3);
}
.login8:hover {
    transform: translateY(-2px);
    box-shadow: 0 6px 20px rgba(30, 64, 175, 0.4);
}
.login8:active {
    transform: translateY(0);
}
</style>
