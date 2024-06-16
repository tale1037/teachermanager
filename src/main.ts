import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import ElementPlus from 'element-plus';
import {createPinia} from 'pinia'
import { createPersistedState } from 'pinia-plugin-persistedstate'
import 'element-plus/dist/index.css';

const app = createApp(App)
const pinia = createPinia();
const persist = createPersistedState();
pinia.use(persist)
app.use(pinia)
app.use(ElementPlus)
app.use(router).mount('#app')
