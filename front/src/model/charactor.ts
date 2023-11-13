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
  targetGroupUUID?: string | null;
  targetGroupName?: string | null;
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

export type NodeType = {
  data: {
    id: string;
    label?: string;
    type?: string;
  };
  position?: {
    x: number;
    y: number;
  };
  style: {
    backgroundImage?: string;
    backgroundFit?: string;
  };
};
export type EdgeType = {
  data: {
    id?: string;
    source: string;
    target: string;
    type?: string;
    label?: string;
    // 추가적으로 필요한 필드들
  };
};

export type graphType = {
  nodes: NodeType[];
  edges: EdgeType[];
};

export type poop = {
  characterUUID : string;
  characterName: string;
  groupUUID: string| null;
  groupName: string|null;
  relations: relationshipType[];
}