import { DeleteDirectory, PatchDirectory, PostDirectory } from '@/model/novel';
import { del, get, patch, post } from './http';

export const getDirectory = async (req: { uuid: string }) => {
  const data = await get('/directory', req);
  return data;
};

export const postDirectory = async (req: PostDirectory) => {
  const data = await post('/directory', req);
  return data;
};

export const patchDirectory = async (req: PatchDirectory) => {
  const data = await patch('/directory', req);
  return data;
};

export const deleteDirectory = async (req: DeleteDirectory) => {
  const data = await del('/directory', req);
  return data;
};
