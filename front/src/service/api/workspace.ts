import { Novels } from './../../model/workspace';
import { Directory } from '@/model/workspace';
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

const transformData = (data: any) => {
  return data.map((el: any) => {
    const newItem: Novels = {
      id: el.uuid,
      name: el.name,
    };

    if (el.children && el.children.length >= 0) {
      newItem.children = transformData(el.children);
    }

    return newItem;
  });
};

export const getWorkspace = async (req: {
  workspaceUUID: string | string[];
}) => {
  const data: any = await get('/workspace', req);

  const newData = {
    title: data.title,
    directories: transformData(data.directories),
  };
  
  return newData;
};
