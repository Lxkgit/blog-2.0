import axios from "axios";

const service = axios.create({
    baseURL: "/api"
})

//2. 请求拦截器
service.interceptors.request.use(config => {
    return config;
}, error => {
    Promise.reject(error);
});

//3. 响应拦截器
service.interceptors.response.use(response => {
    //判断code码
    return response.data.result;
}, error => {
    return Promise.reject(error);
});

export default service
