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
  </div>
</template>
<script setup lang="ts">
import { computed } from "vue";
import { CalendarHeatmap } from "vue3-calendar-heatmap";
import { defineProps } from "vue";
// props 直接使用 `defineProps` 进行定义
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
const currentDate = new Date().toISOString().split("T")[0]; // 获取当前日期，格式为 YYYY-MM-DD

// 计算属性
const values = computed(() => ({
  values: props.data,
  endDate: new Date(currentDate),
  picked: "submit",
}));

// 不需要返回值，因为所有需要的变量都会自动暴露
</script>
<style scoped>
#heatmap {
  scale: 1.2;
  font-size: 10px;
}
</style>
/
