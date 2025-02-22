<template>
  <a-card :style="{ width: '360px' }">
    <template #actions>
      <!--      <span class="icon-hover"> <IconThumbUp /> </span>-->
      <!--      <span class="icon-hover"> <IconShareInternal /> </span>-->
      <span class="icon-hover">
        <a-button status="success" @click="handleClick">修改个人信息</a-button>
      </span>
    </template>
    <template #cover>
      <div
        :style="{
          height: '204px',
          overflow: 'hidden',
        }"
      >
        <img
          :style="{ width: '100%', transform: 'translateY(-20px)' }"
          alt="dessert"
          src="https://p1-arco.byteimg.com/tos-cn-i-uwbnlip3yd/a20012a2d4d5b9db43dfc6a01fe508c0.png~tplv-uwbnlip3yd-webp.webp"
        />
      </div>
    </template>
    <a-card-meta title="个人信息">
      <template #description>
        <icon-user style="margin-right: 14px; margin-top: 10px" />
        {{ userInfo.userName }}
        <br />
        <icon-book style="margin-right: 10px; margin-top: 10px" />
        {{ userInfo.userCollege }}
        <br />
        <icon-message style="margin-right: 10px; margin-top: 10px">
        </icon-message>
        {{ userInfo.userProfile }}
        <br />
        <icon-qq style="margin-right: 10px; margin-top: 10px" />
        {{ userInfo.userQQ }}<br />
        <icon-tag style="margin-right: 10px; margin-top: 10px" />
        <a-tag
          v-for="tag of userInfo.userTags"
          :key="tag"
          @close="handleRemove(tag)"
          color="purple"
          style="margin-right: 5px"
        >
          {{ tag }}
        </a-tag>
      </template>
    </a-card-meta>
    <a-drawer
      :width="380"
      :visible="visible"
      @ok="handleOk"
      @cancel="handleCancel"
      placement="left"
      unmountOnClose
      ok-text="保存修改"
    >
      <template #title> 修改个人信息 </template>
      <div>
        <a-form>
          <a-form-item label="用户名">
            <a-input
              v-model="userInfoTmp.userName"
              :placeholder="userInfoTmp.userName"
            />
          </a-form-item>
          <a-form-item label="学校">
            <a-input
              v-model="userInfoTmp.userCollege"
              :placeholder="userInfoTmp.userCollege"
            />
          </a-form-item>
          <a-form-item label="个性签名">
            <a-input
              v-model="userInfoTmp.userProfile"
              :placeholder="userInfoTmp.userProfile"
            />
          </a-form-item>
          <a-form-item label="QQ">
            <a-input
              v-model="userInfoTmp.userQQ"
              :placeholder="userInfoTmp.userQQ"
            />
          </a-form-item>
          <a-form-item label="标签">
            <a-space wrap>
              <a-tag
                v-for="(tag, index) of userInfoTmp.userTags"
                :key="tag"
                :closable="index !== -1"
                @close="handleRemove(tag)"
                color="purple"
              >
                {{ tag }}
              </a-tag>

              <a-input
                v-if="showInput"
                ref="inputRef"
                :style="{ width: '90px' }"
                size="mini"
                v-model.trim="inputVal"
                @keyup.enter="handleAdd"
                @blur="handleAdd"
              />
              <a-tag
                v-else
                :style="{
                  width: '90px',
                  backgroundColor: 'var(--color-fill-2)',
                  border: '1px dashed var(--color-fill-3)',
                  cursor: 'pointer',
                }"
                @click="handleEdit"
                color="purple"
              >
                <template #icon>
                  <icon-plus />
                </template>
                添加标签
              </a-tag>
            </a-space>
          </a-form-item>
        </a-form>
      </div>
    </a-drawer>
  </a-card>

  <div :style="{ display: 'flex', marginTop: '2%' }">
    <a-card :style="{ width: '360px' }" title="个人简介 | 座右铭">
      <template #extra>
        <a-button status="success" @click="handleClick2"
          >修改座右铭</a-button
        ></template
      >{{ userInfo.userSentence }}
    </a-card>
    <a-drawer
      :width="340"
      :visible="visible2"
      @ok="handleOk2"
      @cancel="handleCancel2"
      placement="left"
      unmountOnClose
    >
      <template #title> 个人简介 | 座右铭 </template>
      <div>
        <a-textarea
          v-model="userInfoTmp.userSentence"
          :placeholder="userInfoTmp.userSentence"
          allow-clear
          style="height: 670px"
        />
      </div>
    </a-drawer>
  </div>
</template>
<script setup lang="ts">
import { useStore } from "vuex";
import { onMounted, ref, watchEffect } from "vue";
import { UserControllerService } from "../../../generated";
import message from "@arco-design/web-vue/es/message";

const store = useStore();

const userInfo = ref({
  userName: "",
  userCollege: "",
  userQQ: "",
  userTags: [],
  userProfile: "",
  userSentence: "",
});

const userInfoTmp = ref({
  userName: "",
  userCollege: "",
  userQQ: "",
  userTags: [],
  userProfile: "",
  userSentence: "",
});

const loadData = async () => {
  const res = await UserControllerService.getLoginUserUsingGet();
  if (res.code === 0) {
    userInfo.value.userName =
      res.data.userName === null ? "" : res.data.userName;
    userInfo.value.userCollege =
      res.data.records === null ? "" : res.data.userCollege;
    userInfo.value.userQQ = res.data.userQQ === null ? "" : res.data.userQQ;
    userInfo.value.userTags =
      res.data.userTags === null ? [] : res.data.userTags;
    userInfo.value.userProfile =
      res.data.userProfile === null ? "" : res.data.userProfile;
    userInfo.value.userSentence =
      res.data.userSentence === null ? "" : res.data.userSentence;
  } else {
    message.error("加载失败，" + res.message);
  }
};
/**
 * 监听 searchParams 变量，改变时触发页面的重新加载
 */

// 定义响应式变量 visible 的类型
const visible = ref<boolean>(false);

// 处理点击事件
const handleClick = (): void => {
  visible.value = true;
  Object.assign(userInfoTmp.value, userInfo.value);
};

// 处理确认按钮事件
const handleOk = (): void => {
  if (userInfoTmp.value.userName === "") {
    message.error("用户名不能为空");
    return;
  }
  visible.value = false;

  Object.assign(userInfo.value, userInfoTmp.value);
  console.log("userInfo", userInfo.value);
  const res = UserControllerService.updateMyUserUsingPost(userInfo.value);
  if (res) {
    message.success("更新成功");
    location.reload();
  } else {
    message.error("更新失败");
  }
};

// 处理取消按钮事件
const handleCancel = (): void => {
  visible.value = false;
};

// 定义响应式变量 visible 的类型
const visible2 = ref<boolean>(false);

// 处理点击事件
const handleClick2 = (): void => {
  visible2.value = true;
  Object.assign(userInfoTmp.value, userInfo.value);
};

// 处理确认按钮事件
const handleOk2 = (): void => {
  visible.value = false;
  Object.assign(userInfo.value, userInfoTmp.value);
  const res = UserControllerService.updateMyUserUsingPost(userInfo.value);
  if (res) {
    message.success("更新成功");
    location.reload();
  } else {
    message.error("更新失败");
  }
};

// 处理取消按钮事件
const handleCancel2 = (): void => {
  visible2.value = false;
  Object.assign(userInfoTmp.value, userInfo.value);
};

watchEffect(() => {
  loadData();
});
/** * 页面加载时，请求数据 */
onMounted(() => {
  loadData();
});

import { nextTick } from "vue";

const inputRef = ref<HTMLInputElement | null>(null);
const showInput = ref<boolean>(false);
const inputVal = ref<string>("");

const handleEdit = () => {
  showInput.value = true;

  nextTick(() => {
    if (inputRef.value) {
      inputRef.value.focus();
    }
  });
};

const handleAdd = () => {
  if (inputVal.value) {
    userInfo.value.userTags.push(inputVal.value);
    inputVal.value = "";
  }
  showInput.value = false;
};

const handleRemove = (key: string) => {
  userInfo.value.userTags = userInfo.value.userTags.filter(
    (tag) => tag !== key
  );
};
</script>
<style scoped></style>
