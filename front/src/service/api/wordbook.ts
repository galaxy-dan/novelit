import { PostWordbook } from '@/model/wordbook';
import { del, get, post } from './http';

export const getWord = async (req: { workspaceUUID: string }) => {
  const data = await get('/words', req);
  return data;
};

export const postWord = async (req: PostWordbook) => {
  const data = await post('/words', req);
  return data;
};

export const deleteWord = async (req: { wordUUID: string }) => {
  const data = await del('/words', req);
  return data;
};
