export type Editor = {
  title: string;
  content: string;
  editable: boolean;
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
  directoryUUID?: string | string[];
  commentContent?: string;
  commentNickname: string;
  // commentPassword: string;
};

export type Editable = {
  directoryUUID: string;
  editable: boolean;
};
