import axios from "axios";
import qs from "qs";

import action from "./action";

//存储对应的ACTION链接
axios.urls = action

//设置超时
axios.defaults.timeout = 10000
//设置根链接
axios.defaults.baseURL = action.SERVER

axios.defaults.transformRequest = function(data) {
  data = qs.stringify(data)
  return data
}

//请求拦截器
axios.interceptors.request.use(function(config){
  return config
}, function(error){
  return Promise.reject(error)
});

//响应拦截器
axios.interceptors.response.use(function(config){
  return config
}, function(error){
  Promise.reject(error)
});

export default axios
