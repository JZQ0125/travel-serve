<template>
    <el-aside :width="isCollapse ? '64px' : '240px'">
        <div class="back-icon" :style="{width: isCollapse ? '63px' : '239px'}">
            <img class="logo" style="border-radius:20px" src="../../assets/image/logo.png"/>
        </div>
        <el-menu
            @select="handleSelect"
            :collapse="isCollapse"
            :collapse-transition="false"
            class="el-menu-vertical"
            :unique-opened="true"
            background-color="#222B40"
            :text-color="textColor"
            :default-active="this.$route.path + '@' + this.$route.name"
            >
                <el-menu-item index="/index@首页">
                    <i class="el-icon-menu"></i>
                    <span slot="title">首页</span>
                </el-menu-item>
                <el-menu-item index="/attractions@景点管理">
                    <i class="el-icon-s-ticket"></i>
                    <span slot="title">景点管理</span>
                </el-menu-item>
                <el-menu-item index="/line@线路管理">
                    <i class="el-icon-help"></i>
                    <span slot="title">线路管理</span>
                </el-menu-item>
                <el-menu-item index="/hotel@酒店管理">
                    <i class="el-icon-s-shop"></i>
                    <span slot="title">酒店管理</span>
                </el-menu-item>
                <el-menu-item index="/forum@资讯管理">
                    <i class="el-icon-picture-outline-round"></i>
                    <span slot="title">资讯管理</span>
                </el-menu-item>
                <el-menu-item index="/order@景点预约">
                    <i class="el-icon-s-promotion"></i>
                    <span slot="title">景点预约</span>
                </el-menu-item>
                <el-menu-item index="/orderHotel@酒店预约">
                    <i class="el-icon-s-home"></i>
                    <span slot="title">酒店预约</span>
                </el-menu-item>
                <el-menu-item index="/rotations@轮播图管理">
                    <i class="el-icon-picture"></i>
                    <span slot="title">轮播图管理</span>
                </el-menu-item>
                <el-menu-item index="/center@个人中心">
                    <i class="el-icon-user"></i>
                    <span slot="title">个人中心</span>
                </el-menu-item>
                <el-submenu index="1 ">
                    <template slot="title">
                        <i class="el-icon-s-platform"></i>
                        <span v-if="!isCollapse">用户管理</span>
                    </template>
                    <el-menu-item index="/admin@管理员管理">
                        <i class="el-icon-s-promotion"></i>
                        <span slot="title">管理员管理</span>
                    </el-menu-item>
                    <el-menu-item index="/user@普通用户管理">
                        <i class="el-icon-coordinate"></i>
                        <span slot="title">普通用户管理</span>
                    </el-menu-item>
                </el-submenu>
        </el-menu>
    </el-aside>
</template>

<script>
  import { mapState } from 'vuex'
  export default {
    name: "asside",
    data() {
      return{
        isCollapse: false,
        textColor: "#A4B7CC"
      }
    },
    computed: {
      ...mapState({
          activeMenuArrary: state => state.menu.activeMenuArrary,
          displayMenus: state => state.menu.displayMenus,
      })
    },
    methods: {
        handleSelect(key) {
            var menu = key.split("@")
            this.$router.push({
                path: menu[0],
            })
            this.$store.commit('menu/setActiveMenu', menu[0])
            for(let i = 0;i < this.activeMenuArrary.length;i++) {
                if(this.activeMenuArrary[i].url == menu[0]) {
                    return;
                }
            }
            var param = {
                "name": menu[1],
                "url": menu[0]
            }
            this.$store.commit('menu/addActiveMenu', param)
        }
    },
    created() {

    },
    mounted() {
        // 监听collapse
        this.$bus.$on('collapse', res=>{
            this.isCollapse = res
        })
        this.$store.commit('menu/setActiveMenu', this.$route.path)
        for(let i = 0;i < this.activeMenuArrary.length;i++) {
            if(this.activeMenuArrary[i].url == this.$route.path) {
                return;
            }
        }
        var param = {
            "name": this.$route.name,
            "url": this.$route.path
        }
        this.$store.commit('menu/addActiveMenu', param)
    },
 }
</script>

<style scoped lang="scss">
    .el-aside {
        background: linear-gradient(180deg, #0f172a 0%, #1e293b 100%);
        transition: width 0.3s ease;
    }
    .back-icon {
        display: flex;
        height: 80px;
        background: transparent;
        justify-content: center;
        align-items: center;
        border-bottom: 1px solid rgba(255, 255, 255, 0.08);
    }
    .logo {
        width: 48px;
        height: 48px;
        border-radius: 12px;
        box-shadow: 0 4px 12px rgba(0, 0, 0, 0.2);
    }
    .logo-text {
        padding-left: 6px;
        color: #fff;
        font-family: 'PingFang SC', Mulish, sans-serif;
        font-size: 15px;
        font-weight: 700;
        line-height: 24px;
        letter-spacing: 0.4px;
    }
    .el-menu {
        height: calc(100vh - 80px);
        overflow-x: hidden;
        font-family: 'PingFang SC', '黑体', sans-serif !important;
        font-weight: 500 !important;
        padding: 12px 8px;
        background: transparent !important;
    }
    .el-menu::-webkit-scrollbar, .el-aside::-webkit-scrollbar {
        width: 0px;
        height: 0px;
    }
    .el-submenu>>>.el-submenu__title:hover {
        color: #60a5fa !important;
        background-color: rgba(96, 165, 250, 0.1) !important;
        border-radius: 8px;
    } 
    .el-menu-vertical:deep(.el-menu-item) {
        border-radius: 8px;
        margin: 4px 0;
        transition: all 0.3s ease;
    }
    .el-menu-vertical:deep(.el-menu-item:hover) {
        color: #60a5fa !important;
        background-color: rgba(96, 165, 250, 0.1) !important;
    }
    .el-menu-vertical:deep(.el-menu-item.is-active) {
        background: linear-gradient(90deg, rgba(59, 130, 246, 0.2) 0%, rgba(59, 130, 246, 0.05) 100%) !important;
        color: #60a5fa !important;
        box-shadow: inset 3px 0 0 #3b82f6;
        border-radius: 8px;
    }
    .el-menu-item, .el-submenu__title {
        height: 48px !important;
        line-height: 48px !important;
        font-size: 14px;
    }
    .el-aside .el-menu {
        border-right: none !important;
    }
    .el-submenu .el-menu-item {
        background-color: transparent !important;
        padding-left: 48px !important;
    }
    .el-submenu .el-submenu:deep(.el-submenu__title) {
        background-color: transparent !important;
    }
    .el-menu-item i, .el-submenu__title i {
        font-size: 18px;
        margin-right: 12px;
    }
</style>
