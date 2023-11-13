export type PostDirectory = {
  name: string;
  workspaceUUID: string | string[];
  parentUUID?: string | null | undefined;
  directory: boolean;
  uuid: string;
};

export type PatchDirectory = {
  uuid: string;
  name: string;
};

export type DeleteDirectory = {
  uuid: string;
};

export type Directory = {
  uuid: string;
  name: string;
};

export type DirectoryList = {
  directories: Directory[];
  files: Directory[];
};

export type Drag = {
  workspaceUUID: string;
  directoryUUID: string;
  parentUUID: string | null | undefined;
  nextUUID: string | null;
};
