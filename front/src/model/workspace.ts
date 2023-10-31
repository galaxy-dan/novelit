export type Workspace = {
  workspaceUUID: string;
  title: string;
};

export type User = {
  nickname: string;
  workspaces: Workspace[];
};

export type Directory = {
  uuid: string;
  name: string;
  isDirectory: boolean;
  prev: string | null;
  next: string | null;
  children: Directory[];
};
