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
  characterUUID?: string;
  groupUUID?: string;
  characterName?: string;
  description?: string;
  characterImage?: string;
  information?: informationType[];
  relationship?: relationshipType[];
};
export type informationType = {
  [key: string]: string;
};
export type relationshipType = {
  [key: string]: string;
};
