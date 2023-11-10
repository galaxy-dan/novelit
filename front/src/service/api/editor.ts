import { Comment, Editable, PatchEditor } from '@/model/editor';
import { del, delData, get, patch, post } from './http';

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

export const getComment = async (req: { spaceUUID: string }) => {
  const data = await get('/comment', req);
  return data;
};

export const deleteComment = async (req: Comment) => {
  const data = await delData('/comment', req);
  return data;
};

export const patchEditable = async (req: Editable) => {
  const data = await patch('/share/toggle', req);
  return data;
};
