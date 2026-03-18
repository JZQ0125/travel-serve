<template>
  <div class="header">
    <div class="header1">
      <img style="height:90%" src="../assets/image/logo.png">
      <div class="header4">
        <div class="menu-list">
            <div @click="toPage('index')" :class="$route.path == '/'?'menu_item':''">系统主页</div>
            <div @click="toPage('attractions')" :class="$route.path == '/attractions'?'menu_item':''">景点信息</div>
            <div @click="toPage('line')" :class="$route.path == '/line'?'menu_item':''">旅游线路</div>
            <div @click="toPage('hotel')" :class="$route.path == '/hotel'?'menu_item':''">景区酒店</div>
            <div @click="toPage('forum')" :class="$route.path == '/forum'?'menu_item':''">旅游资讯</div>
            <div @click="toPage('favor')" :class="$route.path == '/favor'?'menu_item':''">我的收藏</div>
            <div @click="toPage('order')" :class="$route.path == '/order'?'menu_item':''">我的预定</div>
            <div @click="toPage('manage')" :class="$route.path == '/manage'?'menu_item':''">后台管理</div>
            <div class="ai-menu" :class="isAIPage?'menu_item':''">
              AI助手
              <div class="ai-dropdown">
                <div @click="toPage('ai')" class="dropdown-item">AI解惑</div>
                <div @click="toPage('recommend')" class="dropdown-item">景点推荐</div>
                <div @click="toPage('itinerary')" class="dropdown-item">行程规划</div>
              </div>
            </div>
        </div>
      </div>
      <div class="header5">
        <img @click="toCenter" style="width:40px;height:40px;border-radius:50%" src="../assets/image/image 2.png">
        <div class="header6" @click="loginOut">
          退出
        </div>
      </div>
    </div>
  </div>
</template>

<script>
  export default {
    data() {
      return{
        name: ""
      }
    },
    computed: {
      isAIPage() {
        return ['/ai', '/recommend', '/itinerary'].includes(this.$route.path)
      }
    },
    methods: {
      toCenter() {
        this.$router.push("/center")
      },
      toPage(name) {
        if (name == "index") {
          this.$router.push("/")
        } else if (name == "attractions") {
          this.$router.push("/attractions")
        } else if (name == "line") {
          this.$router.push("/line")
        } else if (name == "hotel") {
          this.$router.push("/hotel")
        } else if (name == "forum") {
          this.$router.push("/forum")
        } else if (name == "manage") {
          // 使用同窗口跳转，避免被浏览器拦截弹窗
          window.location.href = "http://localhost:3000"
        } else if (name == "order") {
          this.$router.push("/order")
        } else if (name == "favor") {
          this.$router.push("/favor")
        } else if (name == "order") {
          this.$router.push("/order")
        } else if (name == "ai") {
          this.$router.push("/ai")
        } else if (name == "recommend") {
          this.$router.push("/recommend")
        } else if (name == "itinerary") {
          this.$router.push("/itinerary")
        }
      },
      loginOut() {
        this.$store.dispatch('logout').then(() => {
          window.localStorage.removeItem("user_info")
          window.localStorage.removeItem("user_token")
          this.$message({
              message: '退出成功',
              type: 'success'
          });
          setTimeout(function(){
              window.location.reload()
          },1000)
        })
      }
    },
    created() {
     
    },
    mounted() {
      
    }
 }
</script>

<style scoped>
  .header {
      width: 100%;
      height: 80px;
      display: flex;
      flex-direction: column;
      align-items: center;
  }
  .header1 {
    width: 80%;
    height: 80px;
    display: flex;
    justify-content: space-between;
    align-items: center;
    background-color: #ffffff;
  }
  .header4 {
    width: 80%;
    height: 60px;
    display: flex;
    justify-content: space-between;
    align-items: center;
  }
  .menu-list {
    display: flex;
    color: #000000;
  }
  .menu-list>div{
    font-family: '黑体';
    font-size: 18px;
    width: 100px;
    cursor: pointer;
  }
  .menu_item {
    color: #DC1616;
  }
  .ai-menu {
    position: relative;
    cursor: pointer;
  }
  .ai-dropdown {
    display: none;
    position: absolute;
    top: 100%;
    left: 0;
    background: white;
    box-shadow: 0 4px 12px rgba(0, 0, 0, 0.15);
    border-radius: 8px;
    padding: 8px 0;
    min-width: 120px;
    z-index: 1000;
    margin-top: 8px;
  }
  .ai-menu:hover .ai-dropdown {
    display: block;
  }
  .dropdown-item {
    padding: 10px 20px;
    font-size: 16px;
    color: #333;
    transition: all 0.3s;
    white-space: nowrap;
  }
  .dropdown-item:hover {
    background-color: #f5f5f5;
    color: #DC1616;
  }
  .header5 {
    display: flex;
    align-items: center;
  }
  .header6 {
    margin-left: 10px;
    width: 80px;
    height: 30px;
    border-radius: 20px;
    color: #ffffff;
    background-color: #4381FF;
    display: flex;
    justify-content: center;
    align-items: center;
    font-size: 13px;
  }
</style>