<template>
  <a-row id="globalHeader" align="center" :wrap="false">
    <a-col flex="auto">
      <a-menu
        mode="horizontal"
        :selected-keys="selectedKeys"
        @menu-item-click="doMenuClick"
      >
        <a-menu-item
          key="0"
          :style="{ padding: 0, marginRight: '38px' }"
          disabled
        >
          <div class="title-bar">
            <img class="logo" src="../assets/oj-logo.svg" />
            <div class="title">鱼 OJ</div>
          </div>
        </a-menu-item>
        <a-menu-item v-for="item in visibleRoutes" :key="item.path">
          {{ item.name }}
        </a-menu-item>
      </a-menu>
    </a-col>
    <a-col flex="200px">
      <div>
        <!-- 如果已登录，显示用户名和下拉菜单 -->
        <template v-if="store.state.user?.loginUser?.userName !== ''">
          <a-space size="large">
            <a-dropdown @select="handleSelect">
              <!-- 显示用户名 -->
              <a-button class="userName" style="background: white"
                >{{ store.state.user.loginUser.userName }}
              </a-button>
              <template #content>
                <a-doption @click="personnalInformation">个人信息</a-doption>
                <!--                <a-doption disabled>Option 2</a-doption>-->
                <!-- 点击退出时调用 logout 方法 -->
                <a-doption @click="logout">退出</a-doption>
              </template>
            </a-dropdown>
          </a-space>
        </template>

        <!-- 如果未登录，显示登录或注册链接 -->
        <template v-else>
          <a-link href="user/login">登录</a-link>
          或
          <a-link href="user/register">注册</a-link>
        </template>
      </div>
    </a-col>
  </a-row>
</template>

<script setup lang="ts">
import { routes } from "../router/routes";
import { useRoute, useRouter } from "vue-router";
import { computed, ref } from "vue";
import { useStore } from "vuex";
import checkAccess from "@/access/checkAccess";
import ACCESS_ENUM from "@/access/accessEnum";
import { UserControllerService } from "../../generated";

const router = useRouter();
const store = useStore();

// 展示在菜单的路由数组
const visibleRoutes = computed(() => {
  return routes.filter((item, index) => {
    if (item.meta?.hideInMenu) {
      return false;
    }
    // 根据权限过滤菜单
    if (
      !checkAccess(store.state.user.loginUser, item?.meta?.access as string)
    ) {
      return false;
    }
    return true;
  });
});

// 默认主页
const selectedKeys = ref(["/"]);

// 路由跳转后，更新选中的菜单项
router.afterEach((to, from, failure) => {
  selectedKeys.value = [to.path];
});

console.log();

setTimeout(() => {
  store.dispatch("user/getLoginUser", {
    userName: "管理员",
    userRole: ACCESS_ENUM.ADMIN,
  });
}, 3000);

const doMenuClick = (key: string) => {
  router.push({
    path: key,
  });
};
const logout = () => {
  UserControllerService.userLogoutUsingPost();
  window.location.reload();
};
const personnalInformation = () => {
  router.push({
    path: `/person/information/${store.state.user.loginUser.userName}`,
    replace: true,
  });
};

const handleSelect = (v) => {
  console.log(v);
};
</script>

<style scoped>
.userName {
  font-size: 18px;
  margin-left: 20px;
}

.title-bar {
  display: flex;
  align-items: center;
}

.title {
  color: #444;
  margin-left: 16px;
}

.logo {
  height: 48px;
}
</style>
