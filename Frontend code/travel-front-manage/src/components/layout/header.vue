<template>
    <el-header class="header">
      <div class="icon-div" @click="changeCollapse">
        <i class="icon" :class="collapse ? 'el-icon-s-unfold':'el-icon-s-fold'"></i>
      </div>
      <div class="header-right">
        <div class="search-input">
        </div>
        <div class="icon-div" @click="full">
          <i class="icon el-icon-full-screen"></i>
        </div>
        <div class="icon-div">
          <div>
            <el-dropdown  trigger="click" @command="handleCommand">
              <span class="el-dropdown-link">
                <img style="width:40px;height:40px;border-radius:50%;object-fit: cover;" :src="$store.state.configure.HOST + user.avatar"></img>
              </span>
              <el-dropdown-menu slot="dropdown" style="font-weight:bold">
                <el-dropdown-item command="center"><i class="el-icon-user" ></i>个人中心</el-dropdown-item>
                <el-dropdown-item command="password"><i class="el-icon-ship" ></i>修改密码</el-dropdown-item>
                <!-- <el-dropdown-item command="setting"><i class="el-icon-setting" ></i>设置</el-dropdown-item> -->
                <el-dropdown-item divided style="color:red" command="logout"><i class="el-icon-umbrella"></i>退出</el-dropdown-item>
              </el-dropdown-menu>
            </el-dropdown>
          </div>
        </div>
        <div class="icon-div" style="width:80px;">
          <div style="font-size:13px;font-family:'黑体'">{{user.userName}}</div>
        </div>
      </div>
    </el-header>
</template>

<script>
  import { removeToken } from '@/utils/token'
  import { mapState,mapGetters } from 'vuex'
  import { logout } from '@/api/api'
  export default {
    name: "header",
    data() {
      return{
        user: {
          avatar: "/img/avatar.jpg"
        },
        search: "",
        collapse: false,
      }
    },
    computed: {
      ...mapGetters('user',['getUser'])
    },
    methods: {
      changeCollapse() {
        this.collapse = !this.collapse
        this.$bus.$emit('collapse', this.collapse)
      },
      full() {
        let de = document.documentElement
        if (de.requestFullscreen) {
          de.requestFullscreen()
        } else if (de.mozRequestFullScreen) {
          de.mozRequestFullScreen()
        } else if (de.webkitRequestFullScreen) {
          de.webkitRequestFullScreen()
        }
      },
      handleCommand(command) {
        if(command == "center") {

        } else if(command == "password") {
          //修改密码
          this.$bus.$emit('password', true)
        } else if(command == "setting") {

        }else if(command == "logout") {
          //退出登陆
          logout().then(res => {
            if(res.code == 1000) {
              this.$store.commit('user/setToken', "")
              this.$store.commit('user/setUser', "")
              this.$store.commit('menu/setMenus', [])
              this.$store.commit('menu/setRoutes', [])
              this.$store.commit('menu/setDisplayMenus', [])
              this.$store.commit('menu/setBtnMenus', [])
              this.$store.commit("menu/setFlag",false)
              this.$store.commit('menu/setActiveMenuArrary', [])
              this.$store.commit('menu/setActiveMenu', "/index")
              removeToken()
              this.$router.push("/login")
              this.$message({
                  message: "退出登陆",
                  type: 'success'
              });
              setTimeout(function(){
                window.location.reload()
              },1000)
            } else {
              this.$message({
                  message: res.message,
                  type: 'warning'
              });
            }
          })
        }
      },
      
    },
    created() {
     
    },
    mounted() {
      this.user = JSON.parse(this.getUser)
    }
 }
</script>

<style  scoped>
    .header {
      background-color: #ffffff;
      width: 100%;
      height: 64px !important;
      display: flex;
      flex-direction: row;
      align-items: center;
      padding: 0 24px 0 0;
      justify-content: space-between;
      box-shadow: 0 1px 3px rgba(0, 0, 0, 0.05);
      border-bottom: 1px solid #f1f5f9;
    }
    .icon-div {
      width: 48px;
      height: 48px;
      display: flex;
      justify-content: center;
      align-items: center;
      cursor: pointer;
      border-radius: 10px;
      transition: all 0.3s ease;
    }
    .icon-div:hover {
      background-color: #f1f5f9;
    }
    .icon {
      font-size: 20px;
      color: #475569;
    }
    .header-right {
      display: flex;
      flex-direction: row;
      align-items: center;
      gap: 8px;
    }
    .header-right .icon-div img {
      border: 2px solid #e2e8f0;
      transition: all 0.3s ease;
    }
    .header-right .icon-div:hover img {
      border-color: #3b82f6;
    }
    .search-input {
      width: 60%;
    }
    .el-input {
      background-color: #f1f5f9 !important;
      border-radius: 8px;
    }
    .el-dropdown-link {
      display: flex;
      align-items: center;
    }
</style>
