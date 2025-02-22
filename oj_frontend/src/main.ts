import { createApp } from "vue";
import App from "./App.vue";
import ArcoVue from "@arco-design/web-vue";
import "@arco-design/web-vue/dist/arco.css";
import router from "./router";
import store from "./store";
import "@/plugins/axios";
import "@/access";
import "bytemd/dist/index.css";
import ElementPlus from "element-plus";
import "element-plus/dist/index.css";
import zhCn from "element-plus/es/locale/lang/zh-cn";
import VueCalendarHeatmap from "vue3-calendar-heatmap";
import ArcoVueIcon from "@arco-design/web-vue/es/icon";
import "@arco-design/web-vue/dist/arco.css";
createApp(App)
  .use(ArcoVue)
  .use(store)
  .use(router)
  .use(VueCalendarHeatmap)
  .use(ElementPlus, {
    locale: zhCn,
  })
  .use(ArcoVueIcon)
  .use(ElementPlus)
  .mount("#app");
