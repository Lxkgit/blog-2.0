export const setInterceptors = (axiosInstance) => {
    // 请求拦截
    axiosInstance.interceptors.request.use(request => {
        return request;
    }, error => Promise.reject(error));

    // 响应拦截
    axiosInstance.interceptors.response.use(response => {
        return response;
    }, error => {
        return Promise.reject(error.response);
    })
};
