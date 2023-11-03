export type characterType = {
  id: string;
  name: string;
  image: string;
  summary: string;
  information: informationType[];
  relation: relationType[];
};

export type informationType = {
  id: string;
  title: string;
  content: string;
};

export type relationType = {
  id: string;
  name: string;
  content: string;
  uuid?: string;
};

export type groupType = {
  id: string;
  name: string;
  subGroups?: subGroupType[];
  characters?: characterType[];
};

export type subGroupType = {
  id: string;
  name: string;
};
