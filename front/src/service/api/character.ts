import { characterType } from '@/model/charactor';
import { del, get, patch, post, put } from './http';

export const getCharacterByName = async (worksapce: string, name: string) => {
  const data = await get(
    `/character/search?workspaceUUID=${worksapce}&characterName=${name}`,
  );
  return data;
};

export const getCharacter = async (uuid: string) => {
  const data = await get(`/character?characterUUID=${uuid}`);
  return data;
};

export const postCharacter = async (workspace: string, group: string) => {
  const data = await post(`/character`, {
    workspaceUUID: workspace,
    groupUUID: group,
    characterName: '새 캐릭터',
    information: [{ '': '' }],
    relations: [{ content: '', targetName: '', targetUUID: null }],
  });
  return data;
};

export const patchCharacter = async (params: string, body: characterType) => {
  const data = await patch(`/character?characterUUID=${params}`, body);
  return data;
};

export const deleteCharacter = async (uuid: string) => {
  const data = await del(`/character?characterUUID=${uuid}`);
  return data;
};
