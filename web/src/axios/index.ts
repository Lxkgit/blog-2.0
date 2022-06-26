import axios from 'axios';
import config from './axios.config'
// @ts-ignore
import {setInterceptors} from './axios.interceptors.config'
const axiosInstance = axios.create(config);

setInterceptors(axiosInstance);

export default axiosInstance
