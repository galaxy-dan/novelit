export type Editor = {
  title: string;
  content: string;
};

export type PatchEditor = {
  uuid: string | string[];
  content: string;
};

export type Reply = {
  id: string;
  detail: string;
};

export type Comment = {
  spaceUUID: string;
  commentUUID?: string;
  directoryUUID?: string;
  commentContent?: string;
  commentNickname: string;
  commentPassword: string;
};
