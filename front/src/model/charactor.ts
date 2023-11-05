export type groupType = {
  workspaceUUID?: string;
  groupUUID?: string;
  groupName?: string;
  parentUUID?: string;
  userUUID?: string;
};

export type subGroupType = {
  id: string;
  name: string;
};

export type characterType = {
  characterUUID?: string|null;
  groupUUID?: string|null;
  characterName?: string|null;
  description?: string|null;
  characterImage?: string|null;
  information?: informationType[]|null;
  relationship?: relationshipType[]|null;
  deleted?: boolean|null;
};
export type informationType = {
  [key: string]: string;
};
export type relationshipType = {
  uuid: string;
  description: string;
};
