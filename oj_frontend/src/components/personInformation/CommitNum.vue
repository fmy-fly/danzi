<template>
  <div id="heatmap">
    <calendar-heatmap
      :values="values.values"
      :end-date="values.endDate"
      :tooltip-unit="values.picked"
      :style="{ 'max-width': orientation === 'vertical' ? '145px' : '675px' }"
      :max="8"
      :locale="{
        months: [
          '1月',
          '2月',
          '3月',
          '4月',
          '5月',
          '6月',
          '7月',
          '8月',
          '9月',
          '10月',
          '11月',
          '12月',
        ],
        days: ['周日', '周一', '周二', '周三', '周四', '周五', '周六'],
        less: '',
        more: '',
      }"
    />
    <div class="submit-summary" style="margin-top: 20px">
      <span id="year">过去一年共提交 {{ totalCountLast12Months }} 次</span>
      <span id="count">累计提交天数:{{ cumulativeDays }} </span>
      <span id="continue">连续提交: {{ consecutiveDaysLast12Months }}</span>
    </div>
  </div>
</template>
<script setup lang="ts">
import { computed, onMounted, ref, watchEffect } from "vue";
import { CalendarHeatmap } from "vue3-calendar-heatmap";
import { defineProps } from "vue";
import {
  QuestionControllerService,
  QuestionSubmitQueryRequest,
} from "../../../generated";
import message from "@arco-design/web-vue/es/message";
// props 直接使用 `defineProps` 进行定义

const currentDate = new Date().toISOString().split("T")[0]; // 获取当前日期，格式为 YYYY-MM-DD

const props = defineProps({
  data: {
    type: Array,
    default: () => [
      {
        date: "2025-2-20",
        count: 8,
      },
    ],
  },
});
const dataList = ref([]);
const searchParams = ref<QuestionSubmitQueryRequest>({
  questionId: undefined,
  language: undefined,
  pageSize: 10,
  current: 1,
});
const localData = ref([...props.data]);
const loadData = async () => {
  const res =
    await QuestionControllerService.listQuestionSubmitByTotalUsingPost(
      searchParams.value
    );

  localData.value = res.data.map((item) => ({
    date: item.date,
    count: item.count,
  }));
  if (res.code === 0) {
    dataList.value = res.data.records;
  } else {
    message.error("加载失败，" + res.message);
  }
};
// 计算属性
const values = computed(() => ({
  values: localData.value,
  endDate: new Date(currentDate),
  picked: "submit",
}));

// 计算过去12个月提交的总次数
const totalCountLast12Months = computed(() => {
  const currentDateObj = new Date();
  const past12MonthsDate = new Date(
    currentDateObj.setMonth(currentDateObj.getMonth() - 12)
  );
  const past12MonthsString = past12MonthsDate.toISOString().split("T")[0];

  return localData.value
    .filter((item) => new Date(item.date) >= new Date(past12MonthsString))
    .reduce((sum, item) => sum + item.count, 0);
});
// 计算累计提交的天数
const cumulativeDays = computed(() => {
  const uniqueDates = new Set(localData.value.map((item) => item.date));
  return uniqueDates.size;
});

// 计算过去12个月连续提交的天数
const consecutiveDaysLast12Months = computed(() => {
  const currentDateObj = new Date();
  const past12MonthsDate = new Date(
    currentDateObj.setMonth(currentDateObj.getMonth() - 12)
  );
  const past12MonthsString = past12MonthsDate.toISOString().split("T")[0];

  const datesLast12Months = localData.value
    .filter((item) => new Date(item.date) >= new Date(past12MonthsString))
    .map((item) => new Date(item.date).toISOString().split("T")[0])
    .sort((a, b) => new Date(a) - new Date(b));

  let longestStreak = 1;
  let currentStreak = 1;

  for (let i = 1; i < datesLast12Months.length; i++) {
    const prevDate = new Date(datesLast12Months[i - 1]);
    const currentDate = new Date(datesLast12Months[i]);
    const diffDays = (currentDate - prevDate) / (1000 * 3600 * 24);

    if (diffDays === 1) {
      currentStreak++;
      longestStreak = Math.max(longestStreak, currentStreak);
    } else {
      currentStreak = 1;
    }
  }
  return longestStreak;
});

// 计算今天是否提交了
const isTodaySubmitted = computed(() => {
  const today = new Date().toISOString().split("T")[0];
  return localData.value.some((item) => item.date === today);
});

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
});
// 不需要返回值，因为所有需要的变量都会自动暴露
</script>
<style scoped>
#heatmap {
  scale: 1.1;
  font-size: 10px;
}
#year {
  font-size: 20px;
  font-weight: bold;
  margin-right: 40%;
  color: #6f6f6f; /* 设置字体颜色，类似图片中的灰色 */
}
#count {
  margin-right: 20px;
  color: #6f6f6f; /* 设置字体颜色，类似图片中的灰色 */
}
</style>
