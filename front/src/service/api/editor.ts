import { Comment, PatchEditor } from '@/model/editor';
import { get, patch, post } from './http';

export const getEditor = async (req: { uuid: string | string[] }) => {
  const data = await get('/file', req);
  return data;
};

export const patchEditor = async (req: PatchEditor) => {
  const data = await patch('/file', req);
  return data;
};

export const postComment = async (req: Comment) => {
  const data = await post('/comment', req);
  return data;
};
