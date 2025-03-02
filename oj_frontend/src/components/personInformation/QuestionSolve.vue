<template>
  <a-space direction="vertical" size="large" :style="{ width: '80%' }">
    <!--    <a-radio-group v-model="size" type="button">-->
    <!--      <a-radio value="small">Small</a-radio>-->
    <!--      <a-radio value="medium">Medium</a-radio>-->
    <!--      <a-radio value="large">Large</a-radio>-->
    <!--    </a-radio-group>-->
    <a-progress :size="large" color="green" :percent="acceptRatio.easy" />
    <a-progress color="yellow" :size="size" :percent="acceptRatio.medium" />
    <a-progress color="red" :size="size" :percent="acceptRatio.hard" />
    <a-space>
      <a-progress
        type="circle"
        color="green"
        :size="size"
        :percent="acceptRatio.easy"
      />
      <a-progress
        type="circle"
        color="yellow"
        :size="size"
        :percent="acceptRatio.medium"
      />
      <a-progress
        type="circle"
        color="red"
        :size="size"
        :percent="acceptRatio.hard"
      />
    </a-space>
  </a-space>
  <div class="difficulty-stats">
    <div class="difficulty-card easy">
      <span class="difficulty-title">简单</span>
      <span class="difficulty-stats-text"
        >{{ acceptCount.easy }}/{{ difficultyCount.easy }}</span
      >
    </div>
    <div class="difficulty-card medium">
      <span class="difficulty-title">中等</span>
      <span class="difficulty-stats-text"
        >{{ acceptCount.medium }}/{{ difficultyCount.medium }}</span
      >
    </div>
    <div class="difficulty-card hard">
      <span class="difficulty-title">困难</span>
      <span class="difficulty-stats-text"
        >{{ acceptCount.hard }}/{{ difficultyCount.hard }}</span
      >
    </div>
  </div>
</template>

<script setup lang="ts">
import { onMounted, ref, watchEffect } from "vue";
import RoutineCard from "@/components/RoutineCard.vue";
import {
  Page_Question_,
  Question,
  QuestionControllerService,
  QuestionQueryRequest,
} from "../../../generated";
import message from "@arco-design/web-vue/es/message";
import { useRouter } from "vue-router";
import moment from "moment";

const tableRef = ref();
const dataList = ref([]);
const total = ref(0);
const searchParams = ref<QuestionQueryRequest>({
  title: "",
  tags: [],
  pageSize: 2,
  current: 1,
});

const difficultyCount = ref({
  easy: 0,
  medium: 0,
  hard: 0,
});

const loadData = async () => {
  const res = await QuestionControllerService.listQuestionByTotalUsingPost(
    searchParams.value
  );

  if (res.code === 0) {
    dataList.value = res.data;
    total.value = res.data.total;
    // 更新难度统计
  } else {
    message.error("加载失败，" + res.message);
  }
};
// 创建一个 Map，键为题目 id，值为难度
const questionDifficultyMap = new Map();

/**
 * 根据数量/提交数量来定义难度并统计
 */
const acceptCount = ref({
  easy: 0,
  medium: 0,
  hard: 0,
});
const acceptRatio = ref({
  easy: 0.2,
  medium: 0,
  hard: 0,
});
const updateDifficultyCounts = async () => {
  const res =
    await QuestionControllerService.listQuestionSubmitByTotalRecordUsingPost(
      searchParams.value
    );

  difficultyCount.value.easy = 0;
  difficultyCount.value.medium = 0;
  difficultyCount.value.hard = 0;
  dataList.value.forEach((question: Question) => {
    // 假设 question 包含 quantity 和 submitCount 字段
    const ratio = question.acceptedNum / question.submitNum;

    if (ratio >= 0.8) {
      difficultyCount.value.easy++;
      questionDifficultyMap.set(question.id, 1);
    } else if (ratio >= 0.5) {
      difficultyCount.value.medium++;
      questionDifficultyMap.set(question.id, 2);
    } else {
      difficultyCount.value.hard++;
      questionDifficultyMap.set(question.id, 3);
    }
  });

  res.data.forEach((item) => {
    const difficulty = questionDifficultyMap.get(item.questionId);
    if (difficulty === 1) {
      acceptCount.value.easy++;
    } else if (difficulty === 2) {
      acceptCount.value.medium++;
      console.log(123);
    } else if (difficulty === 3) {
      acceptCount.value.hard++;
    }
  });
  if (difficultyCount.value.easy === 0) {
    acceptCount.value.easy = 0;
  } else {
    acceptRatio.value.easy = (
      acceptCount.value.easy / difficultyCount.value.easy
    ).toFixed(2);
  }

  if (difficultyCount.value.medium === 0) {
    acceptCount.value.medium = 0;
  } else {
    acceptRatio.value.medium = (
      acceptCount.value.medium / difficultyCount.value.medium
    ).toFixed(2);
  }

  if (difficultyCount.value.hard === 0) {
    acceptCount.value.hard = 0;
  } else {
    acceptRatio.value.hard = (
      acceptCount.value.hard / difficultyCount.value.hard
    ).toFixed(2);
  }
};

/**
 * 监听 searchParams 变量，改变时触发页面的重新加载
 */
watchEffect(() => {
  loadData();
  updateDifficultyCounts();
});

/**
 * 页面加载时，请求数据
 */
onMounted(() => {
  loadData();
});
</script>
<style scoped>
.difficulty-stats {
  display: flex;
  justify-content: space-around;
  margin: 20px 0;
}

.difficulty-card {
  padding: 20px;
  border-radius: 8px;
  background-color: #fff;
  display: flex;
  flex-direction: column;
  align-items: center;
  text-align: center;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  width: 100px;
}

.difficulty-title {
  font-size: 16px;
  font-weight: bold;
}

.difficulty-stats-text {
  font-size: 14px;
  color: #555;
}

.easy {
  border: 1px solid #00bcd4;
  color: #00bcd4;
}

.medium {
  border: 1px solid #ffeb3b;
  color: #ffeb3b;
}

.hard {
  border: 1px solid #f44336;
  color: #f44336;
}
</style>
