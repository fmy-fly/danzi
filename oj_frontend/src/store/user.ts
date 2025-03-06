// initial state
import { StoreOptions } from "vuex";
import ACCESS_ENUM from "@/access/accessEnum";
import { UserControllerService } from "../../generated";

export default {
  namespaced: true,
  state: () => ({
    loginUser: {
      userName: "",
    },
    questions: {
      questionMap: new Map<number, number>(),
      sum: 0, // 用 Map 来存储每个问题及其对应的 ID
    },
  }),
  actions: {
    async getLoginUser({ commit, state }, payload) {
      // 从远程请求获取登录信息
      const res = await UserControllerService.getLoginUserUsingGet();
      console.log("用户", res);
      if (res.code === 0) {
        commit("updateUser", res.data);
        console.log("mao", state.questions.questionMap);
      } else {
        commit("updateUser", {
          ...state.loginUser,
          userRole: ACCESS_ENUM.NOT_LOGIN,
        });
      }
    },

    updateAllQuestions({ state }, dataList: any[]) {
      if (!state.questions.questionMap) {
        console.error("questionMap is undefined");
        return;
      }
      // 假设你需要从 dataList 中提取 question 和 id
      dataList.forEach((item) => {
        if (!state.questions.questionMap.has(item.id)) {
          state.questions.sum += 1; // 如果 Map 中没有该 id，则增加 sum
          state.questions.questionMap.set(item.id, state.questions.sum); // 设置新的映射
        }
      });
      console.log("sum", state.questions.sum);
    },
  },
  mutations: {
    updateUser(state, payload) {
      state.loginUser = payload;
    },
  },
} as StoreOptions<any>;
