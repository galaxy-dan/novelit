import { del, get, patch, post } from './http';

export const getUser = async () => {
  const data = await get('/user');
  return data;
};

export const postWorkspace = async (req: { title: string }) => {
  const data = await post('/workspace', req);
  return data;
};

export const patchWorkspace = async (req: {
  workspaceUUID?: string | undefined;
  title: string;
}) => {
  const data = await patch('/workspace', req);
  return data;
};

export const deleteWorkspace = async (workspaceUUID: string) => {
  const data = await del(`/workspace/${workspaceUUID}`);
  return data;
};

export const getWorkspace = async (req: {
  workspaceUUID: string | string[];
}) => {
  const data = await get('/workspace', req);
  return data;
};
