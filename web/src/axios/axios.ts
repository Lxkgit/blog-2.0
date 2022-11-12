import axios from "axios";
import { useRouter } from 'vue-router';
import { t } from "vxe-table";
import router from "../router";
import { userLoginStore } from '../store/login' 


const service = axios.create({
    baseURL: "/api",
})

//2. 请求拦截器
service.interceptors.request.use(config => {
    const date = new Date();
    const router = useRouter();
    const store = userLoginStore();
    let token = store.token.access_token;
    if(token) {
        config.headers.Authorization = `Bearer ${token}`;
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
        const router = useRouter();
        router.push("/login")
    }
    return Promise.reject(error);
});

export default service
