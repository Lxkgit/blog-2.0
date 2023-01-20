import axios from "axios";
import { useRouter } from 'vue-router';
import { userLoginStore } from '../store/login' 
import { ElMessage } from 'element-plus'

const service = axios.create({
    baseURL: "/api",
})
const router = useRouter();
//2. 请求拦截器
service.interceptors.request.use(config => {
    const date = new Date();
    const store = userLoginStore();
    let token = store.token.access_token;
    if(token) {
        config.headers!.Authorization = `Bearer ${token}`;
    }    
    return config;
}, error => {
    Promise.reject(error);
});

//3. 响应拦截器
service.interceptors.response.use(response => {
    return response.data;
}, error => {
    if(error.response.status === 401) {
        localStorage.removeItem("blog")
        localStorage.removeItem("tags")
    } else if(error.response.status === 403) {
        ElMessage({
            message: '无权限访问',
            grouping: true,
            type: 'error',
          })    
    }
    return Promise.reject(error);
});

export default service
