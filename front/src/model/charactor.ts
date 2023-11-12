export type groupType = {
  workspaceUUID?: string;
  groupUUID?: string;
  groupName?: string;
  parentUUID?: string;
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
  name?: string;
  groups: subGroupType[];
  characters: characterType[];
};

export type characterDirectory = {
  id?: string | null;
  name: string;
  children?: characterDirectory[] | null;
};

