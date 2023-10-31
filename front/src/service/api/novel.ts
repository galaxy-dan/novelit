import { PatchDirectory, PostDirectory } from '@/model/novel';
import { patch, post } from './http';


export const getDirectory = async () => {
    
}

export const postDirectory = async (req: PostDirectory) => {
  const data = await post('/directory', req);
  return data;
};


export const patchDirectory = async (req: PatchDirectory) => {
    const data = await patch("/directory", req);
    return data;
}