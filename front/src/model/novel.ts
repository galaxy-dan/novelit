export type PostDirectory = {
  name: string;
  workspaceUUID: string | string[];
  parentUUID: string | null | undefined;
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
