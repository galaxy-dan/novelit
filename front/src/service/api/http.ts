import Axios from 'axios';
import { toast } from 'react-toastify';
const axios = Axios.create({
  // withCredentials: true,
  // headers: {
  // Authorization:
  //   localStorage && localStorage.getItem('accessToken')
  //     ? `Bearer ${localStorage.getItem('accessToken')}`
  //     : null,
  // 'Content-Type': 'application/json',
  // },
});

export const getConfig = (params = {}) => {
  return {
    headers: {
      Authorization:
        localStorage && localStorage.getItem('accessToken')
          ? `Bearer ${localStorage.getItem('accessToken')}`
          : null,
      'Content-Type': 'application/json',
    },
    params: {
      ...params,
    },
  };
};

axios.interceptors.response.use(
  (response) => {
    if (response?.headers?.newaccesstoken) {
      localStorage.setItem('accessToken', response?.headers?.newaccesstoken);
    }
    return response;
  },
  (error) => {
    if (error?.response?.status === 401) {
    }

    return Promise.reject(error);
  },
);

export const get = async (url: string, query = {}) => {
  const res = await axios.get<Response>(getUrl(url), getConfig(query));
  return res.data;
};

export const post = async (url: string, body?: any) => {
  const res = await axios.post<Response>(getUrl(url), body, getConfig());
  return res.data;
};

export const put = async (url: string, body?: any) => {
  const res = await axios.put<Response>(getUrl(url), body, getConfig());
  return res.data;
};

export const del = async (url: string, query = {}) => {
  const res = await axios.delete<Response>(getUrl(url), getConfig(query));
  return res.data;
};

export const patch = async (url: string, body?: any) => {
  const res = await axios.patch<Response>(getUrl(url), body, getConfig());
  return res.data;
};

export const getUrl = (path: string) => {
  const BASE_URL = process.env.NEXT_PUBLIC_BACKEND_URL;
  return `${BASE_URL}${path}`;
};
