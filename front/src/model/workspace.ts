import { TreeProps } from 'react-arborist/dist/types/tree-props';

export type Workspace = {
  workspaceUUID: string;
  title: string;
};

export type User = {
  nickname: string;
  workspaces: Workspace[];
};

export type Directory = {
  title: string;
  directories: Directories[];
};

export type Directories = {
  uuid: string;
  name: string;
  isDirectory: boolean;
  prev: string | null;
  next: string | null;
  children: Directories[] | null;
};

export type Novel = {
  title: string;
  directories: any;
};

export type Novels = {
  id: string;
  name: string;
  children?: Novels[] | null;
};
