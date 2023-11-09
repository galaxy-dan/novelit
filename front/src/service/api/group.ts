import { characterType, groupType, subGroupType } from '@/model/charactor';
import { del, get, patch, post, put } from './http';

const transformSubGroupAndCharacter = (data: any) => {
  const groupName: string = data.groupName;
  const childGroup: subGroupType[] = data.childGroups.map(
    (childGroup: groupType) => ({
      id: childGroup.groupUUID,
      name: childGroup.groupName,
    }),
  );
  const childCharacter: characterType[] = data.childCharacters.map(
    (childCharacter: characterType) => ({
      characterUUID: childCharacter.characterUUID,
      characterName: childCharacter.characterName,
      characterImage: childCharacter.characterImage,
      characterInformation: childCharacter.information,
    }),
  );

  return {
    name: groupName,
    childGroup: childGroup,
    childCharacter: childCharacter,
  };
};

export const getGroup = async (uuid: string) => {
  const data = await get(`/group?groupUUID=${uuid}`);
  return data;
};

export const getSubGroupAndCharacter = async (uuid: string) => {
  const data = await get(`/group?groupUUID=${uuid}`);
  return transformSubGroupAndCharacter(data);
};

export const postGroup = async (body: groupType) => {
  const data = await post(`/group`, body);
  return data;
};

export const patchGroup = async (groupUUID : string, newName: string) => {
  const data = await patch(`/group?groupUUID=${groupUUID}&newName=${newName}`);
  return data;
};

export const deleteGroup = async (uuid: string) => {
  const data = await del(`/group?groupUUID=${uuid}`);
  return data;
};

export const getGroupTop = async () => {
  const data = await get(`/group/top`);
  return data;
};
