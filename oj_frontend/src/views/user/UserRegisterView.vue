<template>
  <div id="userLoginView">
    <h2 style="margin-bottom: 16px">用户注册</h2>
    <a-form
      style="max-width: 560px; margin: 0 auto"
      label-align="left"
      auto-label-width
      :model="form"
      @submit="handleSubmit"
    >
      <a-form-item field="userAccount" label="账号">
        <a-input v-model="form.userAccount" placeholder="请输入账号" />
      </a-form-item>
      <a-form-item field="userPassword" tooltip="密码不少于 8 位" label="密码">
        <a-input-password
          v-model="form.userPassword"
          placeholder="请输入密码"
        />
      </a-form-item>
      <a-form-item
        field="checkPassword"
        tooltip="密码不少于 8 位"
        label="重复密码"
      >
        <a-input-password
          v-model="form.checkPassword"
          placeholder="请输入和上面相同的密码"
        />
      </a-form-item>

      <a-form-item field="userName" tooltip="昵称不能为空" label="昵称">
        <a-input v-model="form.userName" placeholder="请输入用户昵称" />
      </a-form-item>
      <a-form-item>
        <a-button
          id="loginButton"
          type="primary"
          html-type="submit"
          style="width: 120px"
        >
          注册
        </a-button>
        <a-link @click="to_login">已有账户？点击登录</a-link>
      </a-form-item>
    </a-form>
  </div>
</template>

<script setup lang="ts">
import { reactive } from "vue";
import { UserControllerService, UserLoginRequest } from "../../../generated";
import message from "@arco-design/web-vue/es/message";
import { useRouter } from "vue-router";
import { useStore } from "vuex";

/**
 * 表单信息
 */
const form = reactive({
  userAccount: "",
  userPassword: "",
  checkPassword: "",
  userName: "",
} as UserLoginRequest);

const router = useRouter();
const store = useStore();

const to_login = () => {
  router.push({
    path: "/user/login",
  });
};
/**
 * 提交表单
 * @param data
 */
const handleSubmit = async () => {
  console.log(form.checkPassword);
  if (form.userPassword !== form.checkPassword) {
    message.error("两次密码不一致");
  } else if (form.userName === "") {
    message.error("用户昵称不能为空");
  } else {
    const res = await UserControllerService.userRegisterUsingPost(form);
    console.log("注册结果: ", res);
    // 登录成功，跳转到主页
    if (res.code === 0) {
      router.push({
        path: "/user/login",
        replace: true,
      });
    } else {
      message.error("注册失败，" + res.message);
    }
  }
};
</script>
<style>
#loginButton {
  margin-right: 45%;
}
</style>
