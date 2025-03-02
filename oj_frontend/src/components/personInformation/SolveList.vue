<template>
  <div id="questionsView">
    <a-divider size="0" />
    <a-table
      :ref="tableRef"
      :columns="columns"
      :data="dataList"
      :pagination="{
        showTotal: true,
        pageSize: props.searchParams.pageSize,
        current: props.searchParams.current,
        total: dataList.length,
      }"
      @page-change="onPageChange"
    >
      <template #tags="{ record }">
        <a-space wrap>
          <a-tag v-for="(tag, index) of record.tags" :key="index" color="green">
            {{ tag }}
          </a-tag>
        </a-space>
      </template>

      <template #createTime="{ record }">
        {{ moment(record.createTime).format("YYYY-MM-DD") }}
      </template>
    </a-table>
  </div>
</template>

<script setup lang="ts">
import { defineProps, defineEmits, ref } from "vue";
import moment from "moment/moment";

// 不要解构 props，直接使用 props 对象
const props = defineProps({
  dataList: {
    type: Array,
    required: true,
  },
  searchParams: {
    type: Object,
    required: true,
  },
});

// 通过 emit 发送更新分页信息
const emit = defineEmits<{
  (event: "update:searchParams", newSearchParams: object): void;
}>();

const columns = [
  {
    title: "题号",
    dataIndex: "id",
  },
  {
    title: "题目名称",
    dataIndex: "title",
  },
  {
    title: "标签",
    slotName: "tags",
  },
  {
    title: "创建时间",
    slotName: "createTime",
  },
];

// 处理分页变化，触发事件传递给父组件
const onPageChange = (page: number) => {
  // 发出事件通知父组件更新分页信息
  emit("update:searchParams", { ...props.searchParams, current: page });
};
</script>
