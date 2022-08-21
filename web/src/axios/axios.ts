import axios from "axios";
import { useRouter } from 'vue-router';



const service = axios.create({
    baseURL: "/api",
})

//2. 请求拦截器
service.interceptors.request.use(config => {
    const date = new Date();
    const router = useRouter();
    let access_token = JSON.parse(localStorage.getItem("access_token"));
    // 判断是否存在authorization，如果存在的话，则每个http header都加上token
    if (access_token && access_token.expires > date) {
        config.headers.Authorization = `Bearer ${access_token.data}`;
    }
    return config;
}, error => {
    Promise.reject(error);
});

//3. 响应拦截器
service.interceptors.response.use(response => {
    return response.data.result;
}, error => {
    console.log("---", error.response.status)
    return Promise.reject(error);
});

export default service
