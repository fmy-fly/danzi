<template>
  <el-calendar>
    <template #date-cell="{ data }">
      <p :class="getDayClass(data)">
        {{ data.day.split("-").slice(1).join("-") }}
        {{ getCheckSymbol(data) }}
      </p>
    </template>
  </el-calendar>
</template>

<script setup>
import { ref, onMounted } from "vue";
import { DailyCheckControllerService } from "../../generated";
import message from "@arco-design/web-vue/es/message";

const attendanceData = ref([]); // 存储打卡数据

// 获取打卡数据
const fetchAttendanceData = async () => {
  const res = await DailyCheckControllerService.getUserRoutineUsingGet();
  if (res.code === 0) {
    attendanceData.value = res.data; // 将打卡数据存储到 state 中
    console.log("riqi", attendanceData.value);
  } else {
    console.error("加载打卡数据失败", res.message);
  }
};

onMounted(() => {
  fetchAttendanceData();
});

// 获取当前日期
const currentDate = new Date().toISOString().split("T")[0]; // 获取当前日期，格式为 YYYY-MM-DD

// 判断日期的打卡状态并返回对应的样式类
const getDayClass = (data) => {
  const date = data.day.split("T")[0]; // 格式化为 YYYY-MM-DD
  const attendance = attendanceData.value.find(
    (item) => item.stringDate === date
  );

  // 判断日期是否在当前日期之后
  if (date > currentDate) {
    return "future"; // 如果日期是未来的，返回 future 类
  }

  if (attendance) {
    return attendance.checked ? "checked-in" : "missed";
  }
  // 如果没有打卡数据且不是未来的日期，默认返回未打卡状态
  return "missed";
};

// 获取打卡符号，打卡日期显示 "✔️"，未打卡显示 "✘"
const getCheckSymbol = (data) => {
  const date = data.day.split("T")[0]; // 格式化为 YYYY-MM-DD
  const attendance = attendanceData.value.find(
    (item) => item.stringDate === date
  );

  if (attendance) {
    return attendance.checked ? " ✔️" : " ✘";
  }
  return " ✘" + "" + "" + ""; // 如果没有打卡数据，默认不显示符号
};
</script>

<style>
.checked-in {
  color: #1989fa; /* 打卡日期显示蓝色 */
  background-color: #e3f8ff; /* 打卡日期背景色为浅蓝色 */
}

.missed {
  color: #f56c6c; /* 未打卡日期显示红色 */
  background-color: #ffe0e0; /* 未打卡日期背景色为浅红色 */
}

.future {
  color: #d3d3d3; /* 未来的日期显示灰色 */
}
</style>
