import { stringList } from 'aws-sdk/clients/datapipeline';

export type groupType = {
  workspaceUUID?: string;
  groupUUID?: string;
  groupName?: string;
  parentGroupUUID?: string;
  userUUID?: string;
};

export type subGroupType = {
  groupUUID: string;
  groupName: string;
};

export type characterType = {
  characterUUID?: string | null;
  groupUUID?: string | null;
  characterName?: string | null;
  description?: string | null;
  characterImage?: string | null;
  information?: informationType[] | null;
  relations: relationshipType[];
  deleted?: boolean | null;
};

export type informationType = {
  [key: string]: string;
};
export type relationshipType = {
  targetUUID?: string | null;
  targetName: string;
  content: string;
};

export type groupItemType = {
  groups: subGroupType[];
  characters: characterType[];
  groupName?: string;
  parentGroupUUID: string;
};

export type characterDirectory = {
  id?: string | null;
  name: string;
  children?: characterDirectory[] | null;
};
