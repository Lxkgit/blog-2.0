import axios from "axios";
import { useRouter } from 'vue-router';
import { t } from "vxe-table";
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
    if(response.data.code === 401) {

    }
    return response.data;
}, error => {
    return Promise.reject(error);
});

export default service
