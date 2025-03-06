<template>
  <a-layout style="height: 100%">
    <a-layout-sider style="width: 21.5%">
      <PersonInformation />
    </a-layout-sider>
    <a-layout>
      <a-layout-content>
        <div>
          <a-card :style="{ width: '840px' }" title="解题情况" hoverable>
            <QuestionSolve style="margin-left: 30px"></QuestionSolve>
          </a-card>
          <a-card
            class="card-demo"
            title="提交记录"
            hoverable
            style="width: 840px; margin-top: 10px"
          >
            <CommitNum style="margin-left: 50px"></CommitNum>
          </a-card>
          <div
            :style="{
              display: 'grid',
              gridTemplateColumns:
                'repeat(auto-fill, minmax(135px, 1fr))' /* 自动排列列 */,
              gap: '10px' /* 图片之间的间隔 */,
              width: '100%',
              height: '260px',
            }"
          >
            <!-- 假设插入6张图片 -->
            <div
              v-for="n in 10"
              :key="n"
              :style="{
                backgroundImage:
                  'url(https://p1-arco.byteimg.com/tos-cn-i-uwbnlip3yd/a20012a2d4d5b9db43dfc6a01fe508c0.png~tplv-uwbnlip3yd-webp.webp)',
                backgroundSize: 'cover',
                backgroundPosition: 'center',
                backgroundRepeat: 'no-repeat',
                height: '100%',
              }"
            ></div>
          </div>
        </div>
      </a-layout-content>
      <a-layout-sider
        style="width: 42%; margin-left: 1px; height: 760px; margin-top: 0px"
      >
        <!-- 传递 dataList 和 searchParams，更新分页信息时通过 updatePage 更新父组件的 page 和 searchParams -->
        <div id="questionsView">
          <a-table
            :ref="tableRef"
            :columns="columns"
            :data="dataList"
            :pagination="{
              showTotal: true,
              pageSize: searchParams.pageSize,
              current: searchParams.current,
              total: total,
            }"
            @page-change="updatePage"
          >
            <template #tags="{ record }">
              <a-space wrap>
                <a-tag
                  v-for="(tag, index) of record.tags"
                  :key="index"
                  color="green"
                >
                  {{ tag }}
                </a-tag>
              </a-space>
            </template>

            <template #createTime="{ record }">
              {{ moment(record.createTime).format("YYYY-MM-DD") }}
            </template>
          </a-table>
        </div>
      </a-layout-sider>
    </a-layout>
  </a-layout>
</template>

<script setup lang="ts">
import { ref, onMounted, watchEffect } from "vue";
import QuestionSolve from "@/components/personInformation/QuestionSolve.vue";
import CommitNum from "@/components/personInformation/CommitNum.vue";
import PersonInformation from "@/components/personInformation/PersonInformation.vue";
import moment from "moment/moment";
import {
  QuestionControllerService,
  QuestionQueryRequest,
} from "../../../generated";
import message from "@arco-design/web-vue/es/message";

// 定义分页查询参数
const searchParams = ref<QuestionQueryRequest>({
  pageSize: 15,
  current: 1,
  status: 2,
});

// 定义数据列表和总记录数
const dataList = ref([]);
const total = ref(0);
import { useStore } from "vuex";
const store = useStore();
// 加载数据
const loadData = async () => {
  const res =
    await QuestionControllerService.listQuestionAcceptSubmitByPageUsingPost(
      searchParams.value
    );
  console.log("res", res.data);
  if (res.code === 0) {
    dataList.value = res.data.records;
    total.value = res.data.total;
  } else {
    message.error("加载失败，" + res.message);
  }

  saveAllQuestions();
  replaceIdsWithMap();
};
const saveAllQuestions = () => {
  store.dispatch("user/updateAllQuestions", dataList.value);
};
/**
 * 监听 searchParams 变量，改变时触发页面的重新加载
 */
watchEffect(() => {
  loadData();
});

/**
 * 页面加载时，请求数据
 */
onMounted(() => {
  loadData();
  replaceIdsWithMap();
});
// setTimeout(() => {
//   replaceIdsWithMap();
// }, 500); // 2000 毫秒，即 2 秒
// {id: "1", title: "A+ D", content: "新的题目内容", tags: "["二叉树"]", answer: "新的答案", submitNum: 0,…}
function replaceIdsWithMap() {
  dataList.value.forEach((item) => {
    const newId = store.state.user.questions.questionMap.get(item.id); // 从 store 中的 Map 获取新 id
    if (newId !== undefined) {
      // 如果存在新 id，则替换
      item.id = newId;
    }
  });
}

// 更新分页信息
const updatePage = (newPage: number) => {
  searchParams.value.current = newPage;
  loadData(); // 更新后重新加载数据
};

const columns = [
  {
    title: "题号",
    dataIndex: "questionId",
  },

  {
    title: "通过时间",
    slotName: "createTime",
  },
];
</script>

<style scoped>
.icon-hover {
  display: flex;
  align-items: center;
  justify-content: center;
  width: 24px;
  height: 24px;
  border-radius: 50%;
  transition: all 0.1s;
}
.card-demo {
  width: 790px;
  margin-top: 30px;
}
</style>
