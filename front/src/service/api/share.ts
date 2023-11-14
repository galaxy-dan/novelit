import { get } from './http';

export const getShareToken = async (req: { directoryUUID: string }) => {
  const data = await get('/share/token', req);
  return data;
};

export const getShareTokenValidation = async (req: { token: string }) => {
  const data = await get('/share/token/validation', req);
  return data;
};
