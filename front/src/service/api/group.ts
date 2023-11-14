import { characterType, groupType, subGroupType } from '@/model/charactor';
import { del, get, patch, post } from './http';

const transformSubGroupAndCharacter = (data: any) => {
  const groupName: string = data.groupName;
  const childGroup: subGroupType[] = data.childGroups.map(
    (childGroup: groupType) => ({
      groupUUID: childGroup.groupUUID,
      groupName: childGroup.groupName,
      deleted: childGroup.deleted,
    }),
  );
  const childCharacter: characterType[] = data.childCharacters.map(
    (childCharacter: characterType) => ({
      characterUUID: childCharacter.characterUUID,
      characterName: childCharacter.characterName,
      characterImage: childCharacter.characterImage,
      characterInformation: childCharacter.information,
      deleted: childCharacter.deleted,
    }),
  );

  return {
    groupName: data.groupName,
    parentGroupUUID: data.parentGroupUUID,
    groups: childGroup,
    characters: childCharacter,
  };
};
export const getTopGroupAndCharacter = async (workspaceUUID: string) => {
  const data = await get(`/group/top?workspaceUUID=${workspaceUUID}`);
  return data;
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
  console.log(body);
  const data = await post(`/group`, body);
  return data;
};

export const patchGroup = async (req: {
  groupUUID: string;
  newName: string;
}) => {
  const data = await patch(
    `/group?groupUUID=${req.groupUUID}&newName=${req.newName}`,
  );
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

export const patchGroupNodePosition = async (req: {
  workspaceUUID: string;
  groupUUID: string;
  x: number;
  y: number;
}) => {
  const data = await patch(
    `/group/node?workspaceUUID=${req.workspaceUUID}&groupUUID=${req.groupUUID}&x=${req.x}&y=${req.y}`,
  );
  return data;
};
