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

export const getConfig = (params = {}, data = {}) => {
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
    data: {
      ...data,
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
    if (
      error?.response?.status === 401 &&
      error?.response?.data?.message === '액세스 토큰 만료'
    ) {
      localStorage.setItem(
        'accessToken',
        localStorage.getItem('refreshToken') ?? '',
      );
      get('/login/reissue')
        .then((data: any) => {
          localStorage.setItem('accessToken', data.accessToken);
          localStorage.setItem('refreshToken', data.refreshToken);
        })
        .catch(() => {});
    }

    if (
      error?.response?.status === 403 &&
      window.location.pathname.startsWith('/share')
    ) {
      localStorage.clear();
      toast("다시 로그인해주세요!");
      location.href = '/share';
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

export const delData = async (url: string, data = {}) => {
  const res = await axios.delete<Response>(getUrl(url), getConfig({}, data));
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
