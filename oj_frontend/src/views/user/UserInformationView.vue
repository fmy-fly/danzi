<template>
  <a-layout style="height: 100%">
    <a-layout-sider style="width: 21.5%"> <PersonInformation /></a-layout-sider>
    <a-layout>
      <a-layout-content>
        <div>
          <a-card :style="{ width: '790px' }" title="解题情况" hoverable>
            <QuestionSolve style="margin-left: 30px"></QuestionSolve>
          </a-card>
          <a-card class="card-demo" title="Custom hover style" hoverable>
            <template #extra>
              <a-link>More</a-link>
            </template>
            <CommitNum style="margin-left: 100px"></CommitNum>
          </a-card>
        </div>
      </a-layout-content>
      <a-layout-sider style="width: 40%; margin-left: 1px"
        >Sider</a-layout-sider
      >
    </a-layout>
  </a-layout>
</template>

<script setup lang="ts">
import QuestionSolve from "@/components/personInformation/QuestionSolve.vue";
import CommitNum from "@/components/personInformation/CommitNum.vue";
import PersonInformation from "@/components/personInformation/PersonInformation.vue";
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
.icon-hover:hover {
  background-color: rgb(var(--gray-2));
}
</style>
